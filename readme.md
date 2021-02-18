# HAPI FHIR Server 
HAPI FHIR Server integrated with latest Spring boot application

## Technologies: 
- Spring Boot 2.3.4.RELEASE 
- Maven 3.3.6 
- Java 14 
- HAPI FHIR 5.1.0 

## Running application

Clone this repo:

https://github.com/emanuelemusto/TirocinioUnisaFhir.git


open cmd as Admin

install maven: 

[Maven Download][MAVEN]

if you want have a mysql Database you must install Mysql server, change the file "application.property" with user, pass and url of your mySql server

move into the project directory and run command: 
```sh
mvn install
```
and move into "target" directory with 
```sh
cd target
```
and run jar file with following command
```sh
java -jar unisa-fhirStarter-1.0-SNAPSHOT.jar
```

## Functionality
The following url indicates the functionality of this application: using your ip and your port

| CALL | URL |
| ------ | ------ |
| GET | http://127.0.0.1:8183/STU3/AllergyIntolerance |
| GET | http://127.0.0.1:8183/STU3/Patient |
| GET | http://127.0.0.1:8183/STU3/Practitioner
| GET | http://127.0.0.1:8183/STU3/Condition |
| GET | http://127.0.0.1:8183/STU3/DiagnosticReport |
| GET | http://127.0.0.1:8183/STU3/Medication |
| GET | http://127.0.0.1:8183/STU3/Schedule |
| POST | http://127.0.0.1:8183/rejectSchedule |
| POST | http://127.0.0.1:8183/confirmSchedule |
| POST | http://127.0.0.1:8183/addSchedulePractitioner |
| POST | http://127.0.0.1:8183/addpatient |
| POST | http://127.0.0.1:8183/addDiagnosticReport |
| POST | http://127.0.0.1:8183/addContition |
| POST | http://127.0.0.1:8183/addMedication |
| POST | http://127.0.0.1:8183/registrazione |
| POST | http://127.0.0.1:8183/login |



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [MAVEN]: <https://maven.apache.org/download.cgi#>

