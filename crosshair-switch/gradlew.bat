@echo off
set DIR=%~dp0
set APP_HOME=%DIR%
set JAVA_EXE=java

if defined JAVA_HOME set JAVA_EXE=%JAVA_HOME%\bin\java.exe

%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
    exit /b 1
)

set WRAPPER_JAR=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

%JAVA_EXE% -classpath "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
