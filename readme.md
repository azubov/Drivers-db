cd kafka_2.13-3.0.0
bin/zookeeper-server-start.sh config/zookeeper.properties

cd kafka_2.13-3.0.0
bin/kafka-server-start.sh config/server.properties

linux
sudo systemctl start mongod
osx
brew services start mongodb-community@5.0

mvn clean compile spring-boot:run
//
docker build -t drivers-db .
docker run -dp 8080:8080 drivers-db