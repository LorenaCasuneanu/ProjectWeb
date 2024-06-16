# Use the critoma/linux-u20-dev-security-ism image as the base
FROM critoma/linux-u20-dev-security-ism

# Install ActiveMQ on top of the base image
RUN apt-get update && apt-get -y upgrade
RUN apt-get -y install curl
RUN apt-get -y install default-jre

RUN curl -O http://archive.apache.org/dist/activemq/5.16.0/apache-activemq-5.16.0-bin.tar.gz
RUN mkdir -p /opt/apache/activemq
RUN tar xvzf apache-activemq-5.16.0-bin.tar.gz -C /opt/apache/activemq

WORKDIR /opt/apache/activemq/apache-activemq-5.16.0/bin
VOLUME /opt/apache/activemq/apache-activemq-5.16.0/conf

RUN echo './activemq start && tail -f /opt/apache/activemq/apache-activemq-5.16.0/data/activemq.log' > start.sh

# Admin interface
EXPOSE 8161
# Active MQ's default port (Listen port)
EXPOSE 61616

CMD ["/bin/bash", "./start.sh"]
