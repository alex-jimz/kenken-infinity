@ECHO OFF

IF "%1"=="" (
    echo Please provide the name of a LogicTier Class or "help"
    echo Usage: ./unitTest.bat name
    exit /B 1
)
SET name=%1

IF "%name%" == "help" (
    echo Usage: ./unitTest.bat name
    echo List of LogicTier classes with UnitTests:
    echo Coordinates
    echo Game
    echo Generator
    echo GOperation
    echo GSolver
    echo Kenken
    echo Operation
    echo Ranking
    echo Region
    echo Statistics
    echo Translator
    echo User
    echo Verifier
) ELSE (
    java -cp ".\\build\\classes\\java\\main;.\\build\\classes\\java\\test;..\\FONTS\\lib\\gson-2.8.9.jar;..\\FONTS\\lib\\ortools-java-9.9.3963.jar;..\\FONTS\\lib\\json-simple-1.1.1.jar; ..\\FONTS\\lib\\jna-platform-5.14.0.jar;..\\FONTS\\lib\\protobuf-java-3.25.3.jar;..\\FONTS\\lib\\junit-4.10.jar;..\\FONTS\\lib\\jna-5.14.0.jar;..\\FONTS\\lib\\hamcrest-core-1.1.jar;..\\FONTS\\lib\\ortools-linux-x86-64-9.9.3963.jar;..\\FONTS\\lib\\ortools-darwin-x86-64-9.9.3963.jar;..\\FONTS\\lib\\ortools-win32-x86-64-9.9.3963.jar;..\\FONTS\\lib\\ortools-linux-aarch64-9.9.3963.jar;..\\FONTS\\lib\\ortools-darwin-aarch64-9.9.3963.jar;..\\FONTS\\lib\\junit-4.13.2.jar;..\\FONTS\\lib\\hamcrest-core-1.3.jar" "LogicTier.%name%.%name%Test" 2>NUL
)