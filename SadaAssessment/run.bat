@ECHO OFF
CALL mvn clean
CALL echo ------Compiling-------
CALL mvn compile
CALL mvn exec:java -Dexec.mainClass=com.company.Main