# Generate or refresh src/test/resources/golden_master_expected.txt (Golden Master baseline).
$ErrorActionPreference = "Stop"
Set-Location (Join-Path $PSScriptRoot "..")

mvn -q test-compile
mvn -q "-Dexec.classpathScope=test" "-Dexec.mainClass=GoldenMasterGenerator" "-Dexec.args=--write" exec:java

Write-Host ""
Write-Host "Next: git add src/test/resources/golden_master_expected.txt"
