FROM openjdk:11
WORKDIR /app
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
COPY ./target/CrmOda-1.0.0.jar /app/crm-oda.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","crm-oda.jar"]