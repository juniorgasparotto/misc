@echo OFF
setlocal
:PROMPT
SET /P AREYOUSURE="Are you sure you want to install/reinstall this project? In case of reinstallation, this process will exclude all data, including the database. (y/[n])? "
IF /I "%AREYOUSURE%" NEQ "Y" GOTO END

set /p currentName=<admin\config

:ENTER
echo.
SET /P projName="Project name: "
IF /I "%projName%" == "" GOTO ENTER

echo.
echo *** Installing...
echo *** Setting namespace to %projName%...

PowerShell.exe -ExecutionPolicy RemoteSigned -File admin\Scripts\install.ps1 "%currentName%" "%projName%"
echo %projName%>admin\config

echo.
echo *** Deleting 'bin', 'obj', 'Migrations' folder if exists...

IF EXIST "obj" rd /s /q "obj"
IF EXIST "bin" rd /s /q "bin"
IF EXIST "Migrations" rd /s /q "Migrations"

echo.
echo *** Rename "%currentName%.csproj" to "%projName%.csproj"...
ren "%currentName%.csproj" "%projName%.csproj"

echo.
echo *** Rename "%currentName%.sln" to "%projName%.sln"...
ren "%currentName%.sln" "%projName%.sln"

echo.
echo *** Restore packages...
dotnet restore

echo.
echo *** Deleting database if exists...
dotnet ef database drop -f

echo.
echo *** Creating database...
admin\Scripts\dbupdate.bat

echo.
echo *** DONE!

timeout 100

:END
endlocal

