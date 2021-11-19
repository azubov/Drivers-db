//--// Start environment //--//

Terminal 1:  
cd kafka_2.13-3.0.0  
bin/zookeeper-server-start.sh config/zookeeper.properties

Terminal 2:  
cd kafka_2.13-3.0.0  
bin/kafka-server-start.sh config/server.properties

Terminal 3:  
linux:  
sudo systemctl start mongod  
or  
mac os:  
brew services start mongodb-community@5.0

//--// Start application //--//

config -> mongo -> MongoConfiguration  
@Value("${spring.data.uri.primary}")

Terminal 1:  
mvn clean compile spring-boot:run  
browser 1:  
http://localhost:8080/drivers

config -> mongo -> MongoConfiguration  
@Value("${spring.data.uri.secondary}")

Terminal 2:  
env SERVER_PORT=8081 mvn spring-boot:run  
browser 2:  
http://localhost:8081/drivers