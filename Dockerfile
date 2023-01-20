# FROM maven:3.6.3 AS maven
# # Create a workdir for our app
# WORKDIR /usr/src/app
# COPY . /usr/src/app
#
# # Compile and package the application to an executable JAR
# RUN mvn clean package -DskipTests
# # Using java 11
# FROM openjdk:11-jdk
# #D:\condigence\clients\brahma-shakti\bs-service-app\target
# ARG JAR_FILE=/usr/src/bs-service-app/target/*.jar
# # Copying JAR file
# COPY --from=maven ${JAR_FILE} brahmashakti.jar
#
# ENTRYPOINT ["java","-jar","/brahmashakti.war"]