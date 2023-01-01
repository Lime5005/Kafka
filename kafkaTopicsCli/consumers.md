### Consume from tail of the topic

### Consume from the beginning of the topic

- `./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic demo_java`  

### To see all groups
- `./kafka-consumer-groups.sh bat -bootstrap-server localhost:9092 -list`

### With the same groupID, start the identical app twice:
- Edit config -> ConsumerDemo -> modify options -> Allow multiple instances
- Start twice the ConsumerDemo app, then start ProducerWithKey app
- See one instance is running for partition 0 and 1, the other for partition 2

### Partitions will be dispatched between instances
- Add one instance, all 3 partitions will be fairly shared by the 3 instances.
- Stop 1 instance, others will be notified and take that partition.
( one of them will log: Notifying assignor about the new Assignment(partitions=[demo_java-0, demo_java-1])
Adding newly assigned partitions: demo_java-0, demo_java-1, 
and other one will log: Notifying assignor about the new Assignment(partitions=[demo_java-2]), 
Adding newly assigned partitions: demo_java-2).

- If I stop another instance, the only instance left will log:
( Notifying assignor about the new Assignment(partitions=[demo_java-0, demo_java-1, demo_java-2])
  Adding newly assigned partitions: demo_java-0, demo_java-1, demo_java-2)

## Consumer Groups and Partition Rebalance
### Eager Rebalance
- If a new consumer join up, all the others will stop and give up the partitions.
- Then they rejoin the group and get new assignment for each one of them.
** During a short period of time, the entire consumer group stops processing.**
** Consumers don't necessarily "get back" what they used to work on.**
### Cooperative Rebalance (Incremental Rebalance)
- Reassigning a subset of the partitions from one consumer to another
- Other consumers will keep processing. 
** Can go through several iterations until find a stable assignment.**
** Skip the "stop-the-world" scenario like from eager rebalance.**

### How to use Cooperative Rebalance
- Kafka Consumer: partition.assignment.stratedy
- Kafka Connect
- Kafka Streams: turned on by default using StreamsPartitionAssignor
** Specify a `group.instance.id` can make the consumer join back within the `session.timeout.ms`.**
** Offsets are commited when you call `.poll()` and `auto.commit.interval.ms` has elapsed.**

