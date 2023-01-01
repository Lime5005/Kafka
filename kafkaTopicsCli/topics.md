### Kafka Topics CLI
1, First, start the servers: 
- Go to the repository where you installed kafka, e.g kafka_2.13-3.1.0/bin
- `./zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties`
- `./kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties`

2, List down all topics:
`./kafka-topics.sh --list --bootstrap-server localhost:9092`

3, Create a topic: 
(default is 1 partition and 1 replica)
`./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my_first_topic`

4, Create a topic with 3 partition:
`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my_second_topic --partitions 3`

5, Create a topic with 3 partition and a replication factor of 1: 
(1 replication factor for 1 broker, no more)
`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my_third_topic --partitions 3 --replication-factor 1`

6, Describe a topic:
`kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic my_first_topic`

7, Describe all topics:
`kafka-topics.sh --bootstrap-server localhost:9092 --describe`

8, Delete a topic:
`kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic my_first_topic`

