package com.example.broker;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrokerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BrokerApplication.class, args);
		BrokerService broker = new BrokerService();

// configure the broker
//		broker.addConnector("tcp://localhost:61616");
	String BrokerUrl = System.getenv("BROKER_URL");
	if(BrokerUrl == null || BrokerUrl.isEmpty()) {
		BrokerUrl = "tcp://localhost:61616";
	}
	broker.addConnector(BrokerUrl);
	broker.start();
	}

//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(BrokerApplication.class, args);
//		BrokerService broker = new BrokerService();
//
//		// configure the broker
//		broker.setPersistenceAdapter(new org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter());
//		broker.addConnector("tcp://localhost:61616");
//
//		broker.start();
//	}

}
