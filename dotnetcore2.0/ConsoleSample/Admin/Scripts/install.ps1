param ([Parameter(Mandatory=$true)][string]$find,[Parameter(Mandatory=$true)][string]$projectName)
gci -r -include "*.cs","*.sln","*.csproj","*.json" | foreach-object { $a = $_.fullname; ( get-content $a ) |
  foreach-object { $_ -replace $find,$projectName } | set-content $a }