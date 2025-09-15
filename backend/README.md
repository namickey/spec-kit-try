Backend module for family-messages

Prerequisites:
- JDK 21

Build and run (Windows PowerShell):

Using Maven Wrapper (recommended):

```powershell
# from backend/
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Run the packaged JAR directly:

```powershell
# from backend/
java -jar .\target\family-messages-0.1.0-SNAPSHOT.jar
```

Notes:
- Lombok is used; enable annotation processing in your IDE.
- H2 is used for both dev and production per project decision.
