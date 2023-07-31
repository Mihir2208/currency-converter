- Git log graph check:
git log --decorate --graph

- Git sync remote branches:
git remote update origin --prune

- Java Version check command:
java -version

- Java Version Requirements:
java version "18.0.2.1" 2022-08-18
Java(TM) SE Runtime Environment (build 18.0.2.1+1-1)
Java HotSpot(TM) 64-Bit Server VM (build 18.0.2.1+1-1, mixed mode, sharing)

- Maven version check command:
mvn -version

- Recommended Maven Version:
Apache Maven 3.8.6

- Maven command to clean compile and build project:
mvn clean compile install

- Maven command to run project:
mvn javafx:run

- System paramter to set the log4j configuration File:
log4j.configurationFile

- Maven command to make new build and run JavaFX (It is recommeneded to do so for new changes to reflect back):
mvn clean compile javafx:run

- Maven command to make new build, pass external logging configuration file and run JavaFX:
mvn javafx:run -Dlog4j.configurationFile=<log-file-location>
mvn javafx:run -Dlog4j.configurationFile=logger-config-files/log4j2.xml

- Building the FAT shade JAR:
mvn clean compile install

- Running FAT shade JAR with external log configuration file:
java -Dlog4j.configurationFile=logger-config-files/log4j2.xml -jar target/currency-converter-1.0.0-shaded.jar
