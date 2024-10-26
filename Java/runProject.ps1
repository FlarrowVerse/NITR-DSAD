# PowerShell Script: runProject.ps1

param (
    [string]$projectName
)

# Check if project name is provided
if (-not $projectName) {
    Write-Host "Usage: .\runProject.ps1 <project_name>"
    exit
}

# Assuming the script is in the parent directory, navigate to the project folder
$projectPath = Join-Path -Path $PSScriptRoot -ChildPath $projectName

if (-not (Test-Path $projectPath)) {
    Write-Host "Error: Project directory '$projectName' does not exist."
    exit
}

# Navigate to project directory
Set-Location -Path $projectPath

# Run Maven commands
Write-Host "Running 'mvn clean install -U'"
mvn clean install -U

Write-Host "Running 'mvn exec:java -pl driver'"
mvn exec:java -pl driver

# Return to the original directory
Set-Location -Path $PSScriptRoot
