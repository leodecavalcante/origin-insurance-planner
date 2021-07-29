FROM openjdk:11-jdk-oracle
EXPOSE 8080
ADD /build/libs/origin-insurance-planner-*.jar origin-insurance-planner.jar
ENTRYPOINT ["java", "-jar", "/origin-insurance-planner.jar"]