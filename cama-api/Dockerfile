FROM eclipse-temurin:21
ENV TZ=Europe/Warsaw
RUN groupadd -g 1001 dockergr && useradd -m -u 1001 -g dockergr dockerusr
USER dockerusr
COPY target/cama-api.jar cama-api.jar
ENTRYPOINT ["java", "-jar", "cama-api.jar"]
