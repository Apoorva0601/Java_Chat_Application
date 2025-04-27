# Chat Application (UDP)

## Description

This is a simple chat application built using UDP sockets. The server and client communicate with each other in real-time, sending and receiving text messages. Both the server and client have a graphical interface to input and display messages. The communication stops when either side sends the message "stop".

### Components

1. **ChatServer.java**:
   - The server listens for incoming messages and sends responses back to the client.

2. **ChatClient.java**:
   - The client sends messages to the server and displays received messages in a text area.

### How to Run

1. **Start the Server**: 
   - Compile and run the `ChatServer` class:
   ```bash
   javac ChatServer.java
   java ChatServer
2. **Start the Client**:
    - Compile and run the ChatClient class:
    ```bash
    javac ChatClient.java
    java ChatClient
