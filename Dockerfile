FROM openjdk:17
RUN mkdir /app
RUN rm -f /app/*.jar
WORKDIR /app
COPY target/task5-1.0.jar task5-1.0.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "task5-1.0.jar"]
