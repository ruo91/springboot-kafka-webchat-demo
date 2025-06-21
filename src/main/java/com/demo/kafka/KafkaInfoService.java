package com.demo.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaInfoService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private AdminClient adminClient;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        this.adminClient = AdminClient.create(props);
    }

    public Map<String, Object> getKafkaInfo() {
        Map<String, Object> info = new LinkedHashMap<>();
        try {
            DescribeClusterResult clusterResult = adminClient.describeCluster();
            Collection<Node> nodes = clusterResult.nodes().get();
            Node controller = clusterResult.controller().get();

            info.put("Bootstrap Servers", bootstrapServers);
            info.put("Cluster ID", clusterResult.clusterId().get());
            info.put("Controller", formatNode(controller));
            info.put("Broker Count", nodes.size());

            List<String> brokers = new ArrayList<>();
            for (Node node : nodes) {
                brokers.add(formatNode(node));
            }
            info.put("Brokers", brokers);

            List<String> topics = new ArrayList<>(adminClient.listTopics().names().get());
            info.put("Topic Count", topics.size());
            info.put("Topics", topics);

            Map<String, TopicDescription> topicDescriptions = adminClient.describeTopics(topics).all().get();
            Map<String, Object> topicDetails = new LinkedHashMap<>();
            for (Map.Entry<String, TopicDescription> entry : topicDescriptions.entrySet()) {
                String topicName = entry.getKey();
                TopicDescription desc = entry.getValue();
                Map<String, Object> tinfo = new LinkedHashMap<>();
                tinfo.put("Partitions", desc.partitions().size());
                tinfo.put("Internal", desc.isInternal());
                List<String> leaders = new ArrayList<>();
                for (TopicPartitionInfo p : desc.partitions()) {
                    leaders.add("Partition " + p.partition() + " → Leader: " + formatNode(p.leader()));
                }
                tinfo.put("Partition Info", leaders);
                topicDetails.put(topicName, tinfo);
            }

            info.put("Topic Descriptions", topicDetails);

            Collection<ConsumerGroupListing> groups = adminClient.listConsumerGroups().all().get();
            info.put("Consumer Group Count", groups.size());

        } catch (InterruptedException | ExecutionException e) {
            info.put("Error", e.getMessage());
        }
        return info;
    }

    private String formatNode(Node node) {
        return node.id() + "@" + node.host() + ":" + node.port();
    }
}
