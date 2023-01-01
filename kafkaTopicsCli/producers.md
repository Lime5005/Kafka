### Produce without keys
- The key is null, and the data will be distributed across all the partitions.

### Produce with keys
- The same key will always go to the same partition.

1, Use the `kafka-console-producer.sh` to see all hints.

2, Send a message to the topic:
`kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic`

3, Producing with properties:
`kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --producer-property acks=all`

4, Producing with key:
(must use the key value pair to send the message)
`kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:`

