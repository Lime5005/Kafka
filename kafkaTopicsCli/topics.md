### Kafka Topics CLI
1, First, start the servers:
- `zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties`
- `kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties`

2, List down all topics:
`kafka-topics.sh --list --bootstrap-server localhost:9092`

3, Create a topic:
`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my_first_topic`


