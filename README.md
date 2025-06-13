

## 🛠️ Project Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/ClowderDev/QLDA_Project.git
   cd QLDA_Project
   ```

2. Configure your database connection in `src/main/resources/application.properties`

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

## 🔧 Project Structure

```
QLDA_Project/
├── src/
│   ├── main/
│   │   ├── java/        # Java source files
│   │   └── resources/   # Configuration files and static resources
│   └── test/           # Test files
├── target/             # Compiled files
├── pom.xml            # Maven configuration
└── README.md          # This file
```
