FROM eclipse-temurin:21
ENV TZ=Europe/Warsaw
RUN groupadd -g 1001 dockergr && useradd -m -u 1001 -g dockergr dockerusr
USER dockerusr
COPY target/cama-otp-mock.jar cama-otp-mock.jar
ENTRYPOINT ["java", "-jar", "cama-otp-mock.jar"]
