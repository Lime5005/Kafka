package com.lime.demos.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {
        private static final Logger log = LoggerFactory.getLogger(ConsumerDemo.class);

        public static void main(String[] args) {
            log.info("Producer with consumer");

            String bootstrapServer = "127.0.0.1:9092";
            String groupId = "my-consumer-group";
            String topic = "demo_java";

            // Create consumer config:
            Properties properties = new Properties();
            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            properties.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,
                    CooperativeStickyAssignor.class.getName());

            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
            final Thread mainThread = Thread.currentThread();

            // To get out of the endless while loop below
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    log.info("Detected a shutdown, let's exit by calling consumer.wake.up()...");
                    consumer.wakeup();

                    // Keep going until the main thread is finished
                    try {
                        mainThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });

            try {
                consumer.subscribe(Arrays.asList(topic));

                while (true) {
                    log.info("polling");
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                    for (ConsumerRecord<String, String> record : records) {
                        log.info("Key " + record.key() + ", value " + record.value());
                        log.info("Partition " + record.partition() + ", offset "  + record.offset());
                    }
                }

            } catch (WakeupException e) {
                // When click STOP button, will launch the addShutdownHook above, and then this log
                log.info("Wake up exception!");
            } catch (Exception e) {
                log.error("Unexpected exception" + e);
            } finally {
                consumer.close();
                log.info("Closed in finally");
            }
        }

}
