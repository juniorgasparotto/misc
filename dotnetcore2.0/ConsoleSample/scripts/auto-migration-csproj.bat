echo 0 >> migrating
set now=%date:~-4%_%date:~3,2%_%date:~0,2%__%time:~0,2%_%time:~3,2%_%time:~6,2%
dotnet ef migrations add %now%
dotnet ef database update
del migrating

REM Add in csproj
REM <Target Name="BuildMigration" AfterTargets="Build">
REM     <Exec Command="scripts/migration.bat"  Condition="!Exists('scripts/migrating')"/>
REM </Target>