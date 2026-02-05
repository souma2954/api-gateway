

#Level 2
FROM maven:3.9.12-eclipse-temurin-17 AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp
EXPOSE 8765
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["sh","-c","java -jar /app.jar"]



	
							
							
							



