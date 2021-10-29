@echo off
git pull
call mvnw clean 
call mvnw package
java -jar target/demo-0.0.1-SNAPSHOT.jar
pause
echo "Your message here"
pause
