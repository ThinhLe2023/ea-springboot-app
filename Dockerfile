FROM eclipse-temurin:21-jdk-alpine
#WORKDIR /app
COPY cs544-202403-project/target/ea-project.jar ea-project.jar
EXPOSE 8080
# Add JVM option with --add-opens
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "ea-project.jar"]
