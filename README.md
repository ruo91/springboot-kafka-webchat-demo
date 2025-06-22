# Kafka Web Chat (Spring Boot)
This is a simple web-based chat application built using **Spring Boot** and **Kafka**.  
It demonstrates real-time messaging via WebSocket and uses Kafka for message brokering.

## 🔧 Tech Stack

- Java 21+
- Spring Boot
- Kafka
- WebSocket (custom handler, STOMP)
- Gradle
- Thymeleaf (UI)
- VS Code (as development environment)

---

## 📦 Requirements

- Java 21 or higher
- Kafka (local or remote)
- Visual Studio Code  
  Recommended Extension: [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- Gradle (bundled or installed separately)

---

## 🚀 Getting Started with VS Code

### 1. Clone or extract the project

Open the extracted project folder in VS Code:
```
File > Open Folder...
```

### 2. Build the project

In the terminal or Gradle Tasks pane:

```bash
./gradlew build
```

> On Windows, use `gradlew.bat build`

### 3. Run the application

```bash
./gradlew bootRun
```

Once started, access the chat at:  
📍 `http://localhost:8080/`

---

## 🗂 Project Structure

```
springboot-kafka-webchat-demo/
├── src/main/java/com/demo/kafka/
│   ├── DemoApplication.java           # Spring Boot entry point
│   ├── WebSocketConfig.java           # WebSocket & STOMP configuration
│   ├── ChatController.java            # Handles chat messaging
│   ├── KafkaProducerService.java      # Kafka producer service
│   ├── KafkaConsumerService.java      # Kafka consumer service
│   ├── KafkaInfoService.java          # Retrieves Kafka cluster info
│   ├── ChatViewController.java        # Loads chat UI
│   ├── PartnerController.java         # Matchmaking controller
│   ├── MatchMakerService.java         # Partner matching logic
├── src/main/resources/
│   ├── application.yml                # App configuration
│   └── static/css/bootstrap.min.css   # Bootstrap UI
├── build.gradle
```

---

## 🧪 Example Endpoints

- **Web Chat UI**
  ```
  GET /
  ```
---

## 🛠 Troubleshooting

- **Kafka not connected**: Make sure Kafka is accessible at `localhost:9092`
- **Port conflict**: Modify `server.port` in `application.yml`
- **WebSocket issues**: Use a modern browser, disable any interfering browser plugins or proxies

---

## 🤝 Contributions

This project is for educational and demonstration purposes.  
Feel free to contribute by submitting a pull request.
