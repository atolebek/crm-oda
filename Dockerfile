FROM openjdk:11
WORKDIR /app
COPY ./target/CrmOda-1.0.0.jar /app/crm-oda.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","crm-oda.jar"]