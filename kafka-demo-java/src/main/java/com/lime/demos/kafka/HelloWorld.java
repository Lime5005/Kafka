package com.lime.demos.kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HelloWorld {
    private static final Logger log = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        log.info("Hello world");
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create a producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // Create a producer record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "Hello world");
        // Send the data - asynchronous
        producer.send(producerRecord);
        // Flush data - synchronous
        producer.flush();
        // Flush and close producer
        producer.close();

    }
}
