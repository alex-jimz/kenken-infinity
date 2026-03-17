@ECHO OFF
call ./gradlew build
Xcopy .\build ..\EXE\build /E /H /C /I /Y 1>NUL
rmdir /S /Q .\build