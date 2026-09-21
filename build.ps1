# PowerShell script to compile Java source files
$ErrorActionPreference = "Stop"

$srcDir = Join-Path $PSScriptRoot "src"
$binDir = Join-Path $PSScriptRoot "bin"

if (Test-Path $binDir) {
    Remove-Item -Recurse -Force $binDir
}
New-Item -ItemType Directory -Path $binDir | Out-Null

$javaFiles = Get-ChildItem -Path $srcDir -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName

Write-Host "Compiling Java files to '$binDir'..." -ForegroundColor Cyan
javac -d $binDir $javaFiles

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful!" -ForegroundColor Green
} else {
    Write-Host "Compilation failed." -ForegroundColor Red
    exit 1
}
