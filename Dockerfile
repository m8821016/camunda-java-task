FROM openjdk:8-jre-alpine
COPY target/charge-card-worker-0.0.1-SNAPSHOT.jar /charge-card-worker-0.0.1-SNAPSHOT.jar
COPY target/lib/* /lib/
CMD java -jar charge-card-worker-0.0.1-SNAPSHOT.jar
