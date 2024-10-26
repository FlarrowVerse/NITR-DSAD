@echo off

:: Check if the project name argument is provided
if "%~1"=="" (
    echo Please provide a project name.
    exit /b 1
)

set PROJECT_NAME=%~1
echo Project Name: %PROJECT_NAME%

:: Navigate to the project directory
cd "%~dp0%PROJECT_NAME%" || (echo Project directory not found! & exit /b 1)

:: Execute Maven commands
echo Executing Maven commands in %PROJECT_NAME%...
call mvn clean install -U || (echo Maven command failed! & exit /b 1)
echo Executing Maven execute command in %PROJECT_NAME%...
call mvn exec:java -pl driver || (echo Execution command failed! & exit /b 1)

cd ../

echo Done.
