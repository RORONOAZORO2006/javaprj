# PowerShell script to run the automated tests
$ErrorActionPreference = "Stop"
$binDir = Join-Path $PSScriptRoot "bin"

& "$PSScriptRoot\build.ps1"

Write-Host "`nExecuting Unit Tests..." -ForegroundColor Cyan
java -cp $binDir com.example.test.TestRunner
