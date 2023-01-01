package com.lime.demos.kafka;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 1, Start the servers, and set 3 partitions with 1 topic
 * `./kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --create --topic demo_java --partitions 3 --replication-factor 1
 * 2, `Receive the message:
 *  `./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic demo_java`
 * 3, Launch the project, see the logs and also the messages.
 * * *
 */
public class ProducerWithCallback {
    private static final Logger log = LoggerFactory.getLogger(ProducerWithCallback.class);

    public static void main(String[] args) {
        log.info("Producer with callback");
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create a producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 10; i++) {
            // Create a producer record
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "Hello world " + i);
            // Send the data - asynchronous

            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        log.info("receive new meta data/ \n" +
                                "Topic: " + recordMetadata.topic() + "\n" +
                                "Partition " + recordMetadata.partition() + "\n" +
                                "Offset " + recordMetadata.offset() + "\n" +
                                "Timestamp " + recordMetadata.timestamp()
                        );
                    }  else {
                        log.error("Error while producing ", e);
                    }
                }
            }); // see all use the same one: partition 2

            // To force kafka to use different partitions instead of StickyPartitioner (when batch processed, kafka uses this default way)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Flush data - synchronous
        producer.flush();
        // Flush and close producer
        producer.close();

    }
}
