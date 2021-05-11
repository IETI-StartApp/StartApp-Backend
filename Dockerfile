FROM adoptopenjdk/openjdk11:alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8080
ARG OUTPUT_FOLDER=build/libs/
ENV PORT 8080
COPY ${OUTPUT_FOLDER} /app/lib
WORKDIR /app/lib
ENTRYPOINT ["java", "-Dserver.port=$PORT", "-jar", "startapp-0.0.1-SNAPSHOT.jar"]