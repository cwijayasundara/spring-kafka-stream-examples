This repo contains samples done using
- spring-cloud-starter-stream-kafka
- spring-cloud-stream-binder-kafka-streams
- spring-kafka

This also contains an example usage of an inmemory Kafka broker for (spring-kafka-test) for integration tests

These example uses Kafka and Zookeepr images.

**How to start a Kafka broker & create topics :**

Navigate to the below location:

/opt/homebrew/Cellar/kafka/3.3.1_1/bin


Start Zookeeper :

zookeeper-server-start /opt/homebrew/etc/kafka/zookeeper.properties

Start Kafka Broker :

kafka-server-start /opt/homebrew/etc/kafka/server.properties

Create the Kafka topics

kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic orders.buy

kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic orders.sell

kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test_topic
