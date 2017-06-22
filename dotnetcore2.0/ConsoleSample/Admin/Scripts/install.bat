@echo off
setlocal
:PROMPT
SET /P AREYOUSURE="Are you sure you want to install/reinstall this project? In case of reinstallation, this process will exclude all data, including the database. (y/[n])? "
IF /I "%AREYOUSURE%" NEQ "Y" GOTO END

echo "Deleting database if exists..."
REM dotnet ef database drop -f
echo "Deleting 'bin' and 'obj' folder if exists..."

IF EXIST "obj" rd /s /q "obj"
IF EXIST "bin" rd /s /q "bin"

echo "Installing..."

:ENTER
SET /P projName="Project name: "
IF /I "%projName%" == "" GOTO ENTER

PowerShell.exe -ExecutionPolicy RemoteSigned -File admin\Scripts\install.ps1 "ConsoleSample" "%projName%"
dotnet restore
echo "Intall done!" 
timeout 100

:END
endlocal

