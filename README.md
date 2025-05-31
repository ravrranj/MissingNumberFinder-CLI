
# MissingNumberFinder-CLI

A simple and powerful Java-based Command Line Interface (CLI) tool to find the missing number in an array using various algorithms.

---

## 🔧 Features

- **Two methods available**:
  - Sum formula approach
  - XOR approach
- **Supports simulation mode** with randomly generated data
- **Reads input from file or CLI**
- **Exports results to a file (optional)**

---

## 📦 Requirements

- Java 17 or higher
- Terminal / Command Prompt access

---

## 🚀 Usage

### 📁 Compile the project (if using source)
```bash
javac MissingNumberFinderCLI.java

▶️ Run using java

java MissingNumberFinderCLI --method xor --input "1,2,3,5" --export

💡 CLI Flags
Flag	Description
--simulate	Generates a random array
--method	sum or xor (algorithm selection)
--input	Comma-separated list of integers
--export	(Optional) Writes output to a file

🧪 Example:
java -jar MissingNumberFinderCLI.jar --method sum --input 1,2,3,4,6

🛠️ Build JAR File
To package it into a JAR with Main-Class:

1. Create manifest.txt:

Main-Class: MissingNumberFinderCLI

2. Compile and package:

javac MissingNumberFinderCLI.java
jar cfm MissingNumberFinderCLI.jar manifest.txt MissingNumberFinderCLI.class

3. Run:

java -jar MissingNumberFinderCLI.jar --simulate --method xor

📂 Project Structure

MissingNumberFinder-CLI/
├── MissingNumberFinderCLI.java
├── MissingNumberFinderCLI.jar
├── manifest.txt
└── README.md

🤝 Contribution
Feel free to fork, clone, and improve this project.

📜 License
This project is licensed under the MIT License.