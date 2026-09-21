# PowerShell script to run the Task Manager CLI application
$binDir = Join-Path $PSScriptRoot "bin"

if (-not (Test-Path $binDir)) {
    Write-Host "Bin directory not found. Building project first..." -ForegroundColor Yellow
    & "$PSScriptRoot\build.ps1"
}

java -cp $binDir com.example.Main
