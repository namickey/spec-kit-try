# Quickstart: 家族間メッセージ共有 (MPA, Spring Boot)

This quickstart shows how to run the backend locally using H2 and view the MPA pages.

1. Prerequisites
   - JDK 21
   - Maven (Gradle is not used)
   - No external DB required; H2 will be used for both development and production
   - Lombok (annotation processor) recommended for IDEs to avoid editing generated POJOs

2. Run the app (development)
   - Build: `./mvnw package` or `mvn -DskipTests package` (Maven only)
   - Run: `java -jar backend/target/*.jar` (or use IDE run configuration)

3. Open browser
   - Visit `http://localhost:8080` and navigate to "Create Group" or "Sign in".

4. Dev notes
   - H2 console enabled at `/h2-console` for local inspection.
   - Static assets served from `/static` and server-side templates rendered with Thymeleaf.
