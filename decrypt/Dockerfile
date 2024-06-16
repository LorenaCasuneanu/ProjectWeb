# Use the base image that includes necessary development tools and MPI
FROM critoma/linux-u20-dev-security-ism:latest

# Set the working directory inside the container
WORKDIR /app

ENV CERT_HOME=/usr/local/share/ca-certificates
ENV CERT_FILE_PATH=${CERT_HOME}/ZscalerRootCertificate-2048-SHA256.crt
RUN mkdir -p ${CERT_HOME}
COPY ./zscaler_cert.crt ${CERT_FILE_PATH}
RUN update-ca-certificates
RUN apt-get update && apt-get install -y ca-certificates && rm -rf /var/lib/apt/lists/*
RUN chmod 644 ${CERT_FILE_PATH} && update-ca-certificates

# Copy the source code into the container
COPY code/decrypt.cpp /app/

# Set the environment variables to allow running mpirun as root
ENV OMPI_ALLOW_RUN_AS_ROOT=1
ENV OMPI_ALLOW_RUN_AS_ROOT_CONFIRM=1

# Install OpenMPI and development libraries
RUN apt-get update && \
    apt-get install -y wget build-essential && \
    apt-get install -y openmpi-bin libopenmpi-dev && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Download and install OpenSSL 3
RUN wget https://www.openssl.org/source/openssl-3.0.2.tar.gz && \
    tar -xvzf openssl-3.0.2.tar.gz && \
    cd openssl-3.0.2 && \
    ./config && \
    make && \
    make install && \
    cd .. && \
    rm -rf openssl-3.0.2 openssl-3.0.2.tar.gz

# Compile the C program with OpenMP and OpenMPI support
RUN g++ -fopenmp -o decrypt decrypt.cpp -I/usr/lib/x86_64-linux-gnu/openmpi/include/openmpi -I/usr/lib/x86_64-linux-gnu/openmpi/include -I/usr/include/openssl -pthread -L/usr/lib/x86_64-linux-gnu/openmpi/lib -lmpi_cxx -lmpi -lssl -lcrypto
RUN chmod +x decrypt

# Set the command to run the compiled program
#CMD ["mpirun", "-np", "4", "--allow-run-as-root","./decrypt", "your_key", "your_image_data"]
CMD ["tail", "-f", "/dev/null"]