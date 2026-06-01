# Java RMI Laboratory - "Trino" Social Network

A simplified social network project implemented using a **three-tier distributed architecture** powered by **Java RMI (Remote Method Invocation)**. The system enables asynchronous communication between users through a callback mechanism.

## 🏗 Architecture Overview

The system is decoupled into three main components:

* **Persistence Layer (Database)**: Handles data storage for users, posts ("trinos"), and follow relationships.
* **Server Layer (Manager)**: Acts as the orchestrator, containing the core business logic and managing real-time notifications.
* **Client Layer**: A console-based interface for user interaction.

## 🚀 Key Features

* **Authentication**: Secure user login and registration managed by the persistence layer.
* **Social Feed**: Ability to create and publish posts ("trinos").
* **Follow System**: Manage user relationships (follow/unfollow other users).
* **Real-Time Callbacks**: Asynchronous message delivery from followed users using RMI callback mechanisms, eliminating the need for manual polling.

## 🛠 Prerequisites

* **Java JDK 8** or higher.
* **Windows Environment** (required for the provided `.bat` deployment scripts).

## ⚡ Installation & Execution

Follow these steps in order to launch the distributed system:

1. **Start the Database**: Run `01_Lanzar_BD.bat` to initialize the RMI Registry and the persistence service.
2. **Start the Server**: Run `02_Lanzar_Servidor.bat`. The server will connect to the DB and await client connections.
3. **Start Clients**: Run `03_Lanzar_Cliente.bat`. You can open multiple instances to simulate real-time communication between different users.

## 📂 Project Structure

* `src/`: Java source code organized by packages.
* `*.jar`: Compiled executable files for each layer.
* `*.bat`: Automation scripts for deployment.
* `*.iml`: Project configuration file for IntelliJ IDEA.

---
*Developed for educational purposes to master Java Distributed Systems and Remote Method Invocation.*
