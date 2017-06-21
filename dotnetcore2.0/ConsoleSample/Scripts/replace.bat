@echo OFF
for %%f in (Migrations/2*.cs) do (
    set name="../Migrations/%%f";
    echo %name%;
    findstr /r /c:"migrationBuilder." %name%;
    
    REM findstr /r /c:a /F:file
)
exit /b
REM call :onefile %%f
:onefile
echo Processing file %1...
echo ... commands go here ...
exit /b

REM 