package com.pWeb.proiect.Services;

import com.pWeb.proiect.DataModel.Image;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class JmsPublisher {

    private static final String BROKER_URL = System.getenv("ACTIVEMQ_URL") != null ? System.getenv("ACTIVEMQ_URL") : "tcp://localhost:61616";  // ActiveMQ broker URL

    public void publishImage(Image image) {
        Connection connection = null;
        try {
            // Create a connection factory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

            // Create a JMS connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the topic
            Topic topic = session.createTopic("YourTopicName");

            // Create a producer
            MessageProducer producer = session.createProducer(topic);

            // Create a message
            BytesMessage message = session.createBytesMessage();
            message.writeBytes(image.getImage().getBytes());  // Write the image bytes
            message.setStringProperty("Key", image.getKey());  // Set file name as message property
            message.setBooleanProperty("Encrypted", image.isEncrypt());
            message.setStringProperty("Image", image.getImage());

            // Send the message
            producer.send(message);

            System.out.println("Image published successfully to JMS Topic");

            // Clean up
            producer.close();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }


}
