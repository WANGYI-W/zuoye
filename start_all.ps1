$root = Split-Path -Parent $MyInvocation.MyCommand.Definition

Write-Host "Project root: $root"

Write-Host "Starting backend (backend)..."
Start-Process -FilePath "powershell" -ArgumentList @(
    "-NoExit",
    "-Command",
    "Set-Location -Path '$root\\backend'; if (-not (Test-Path node_modules)) { Write-Host '安装后端依赖...'; npm install }; Write-Host '启动后端...'; npm start"
)

Start-Sleep -Milliseconds 300

Write-Host "Starting frontend (frontend)..."
Start-Process -FilePath "powershell" -ArgumentList @(
    "-NoExit",
    "-Command",
    "Set-Location -Path '$root\\frontend'; if (-not (Test-Path node_modules)) { Write-Host '安装前端依赖...'; npm install }; Write-Host '启动前端 (vite)...'; npm run dev"
)

Write-Host "Backend and frontend started in two new windows."
