cd kafka_2.13-3.0.0
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

mvn clean compile spring-boot:run