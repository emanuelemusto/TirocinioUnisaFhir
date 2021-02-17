HAPI FHIR Server
HAPI FHIR Server integrated with latest Spring boot application

Technologies:
Spring Boot 2.3.4.RELEASE
Maven 3.3.6
Java 14
HAPI FHIR 5.1.0
Useful Links

Clone this repo:  https://github.com/emanuelemusto/TirocinioUnisaFhir.git

open cmd as Admin

install maven: https://maven.apache.org/download.cgi#

if you want have a mysql Database you must install Mysql server, change the file "application.property" with user, pass and url of your mySql server

move into the project directory and run command: "mvn install" and move into "target" directory with "cd target" and run: jar file with following command "java -jar unisa-fhirStarter-1.0-SNAPSHOT.jar"


The following url indicates the functionality of this application:
POST/http://127.0.0.1:8183/login
GET/http://127.0.0.1:8183/STU3/Patient
GET/http://127.0.0.1:8183/STU3/AllergyIntolerance
GET/http://127.0.0.1:8183/STU3/Condition
GET/http://127.0.0.1:8183/STU3/DiagnosticReport
GET/http://127.0.0.1:8183/STU3/Medication
GET/http://127.0.0.1:8183/STU3/Schedule
POST/http://127.0.0.1:8183/rejectSchedule
POST/http://127.0.0.1:8183/confirmSchedule
POST/http://127.0.0.1:8183/addSchedulePractitioner
POST/http://127.0.0.1:8183/addpatient
POST/http://127.0.0.1:8183/addDiagnosticReport
POST/http://127.0.0.1:8183/addAllergy
POST/http://127.0.0.1:8183/addContition
POST/http://127.0.0.1:8183/addMedication
POST/http://127.0.0.1:8183/registrazione
GET/http://127.0.0.1:8183/STU3/Practitioner

