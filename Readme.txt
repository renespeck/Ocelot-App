java -version

openjdk version "1.8.0_442"


mvn clean compile assembly:single -U

java -jar target/ocelot3.jar "Obama was born in Hawaii. " "PERSON" "LOCATION" 0 5 18 24

java -jar target/ocelot3.jar spouse
