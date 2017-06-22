param ([Parameter(Mandatory=$true)][string]$find,[Parameter(Mandatory=$true)][string]$projectName)
gci -r -include "*.cs","*.sln","*.csproj","*.json" | 
foreach-object {
  $a = $_.fullname;
  $file = Get-Content $_.FullName
  echo $a;
  $containsWord = $file | %{$_ -match $find};

  If($containsWord -contains $true) {
    (get-content $a)  |
    foreach-object {
      $_ -replace $find,$projectName 
    } | set-content $a 
  }
}