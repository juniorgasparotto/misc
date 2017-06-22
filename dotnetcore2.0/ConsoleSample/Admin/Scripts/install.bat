@echo off
setlocal
:PROMPT
SET /P AREYOUSURE="Are you sure you want to install/reinstall this project? In case of reinstallation, this process will exclude all data, including the database. (y/[n])? "
IF /I "%AREYOUSURE%" NEQ "Y" GOTO END

:ENTER
echo.
SET /P projName="Project name: "
IF /I "%projName%" == "" GOTO ENTER

echo "Deleting database if exists..."
dotnet ef database drop -f

echo "Deleting 'bin', 'obj', 'Migrations' folder if exists..."

IF EXIST "obj" rd /s /q "obj"
IF EXIST "bin" rd /s /q "bin"
IF EXIST "Migrations" rd /s /q "Migrations"

echo "Installing..."



PowerShell.exe -ExecutionPolicy RemoteSigned -File admin\Scripts\install.ps1 "ConsoleSample" "%projName%"

echo "Restore packages..."
dotnet restore

echo "Creating database..."

admin\Scripts\dbupdate.bat

echo "Install done!"
timeout 100

:END
endlocal

