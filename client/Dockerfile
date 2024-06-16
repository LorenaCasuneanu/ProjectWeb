# Use the specified base image
FROM critoma/linux-u20-dev-security-ism

# Set the working directory inside the container
WORKDIR /app

ENV CERT_HOME=/usr/local/share/ca-certificates
ENV CERT_FILE_PATH=${CERT_HOME}/ZscalerRootCertificate-2048-SHA256.crt
RUN mkdir -p ${CERT_HOME}
COPY ./zscaler_cert.crt ${CERT_FILE_PATH}
RUN update-ca-certificates
RUN apt-get update && apt-get install -y ca-certificates && rm -rf /var/lib/apt/lists/*
RUN chmod 644 ${CERT_FILE_PATH} && update-ca-certificates
RUN git config --global http.sslcainfo ${CERT_FILE_PATH}

# Install Java 17
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Set JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64

# Add Java to PATH
ENV PATH $JAVA_HOME/bin:$PATH

# Copy the gradlew and gradle related files
COPY ./clientMq/ /app/



# Give execute permission to gradlew
RUN chmod +x gradlew

# Run the build process
RUN ./gradlew build -x test

# Set the entry point for the application
RUN ln -sf /bin/bash /bin/sh
ENTRYPOINT ["java","-jar","./build/libs/client-0.0.1.jar"]
