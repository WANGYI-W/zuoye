echo 启动后端（backend）...
@echo off
REM 一键启动后端与前端（在两个新窗口中）
SET ROOT=%~dp0

echo 启动后端（backend）...
start "backend" cmd /k "cd /d "%ROOT%backend" && if not exist node_modules (echo 安装后端依赖... && npm install) && echo 启动后端... && npm start"

echo 启动前端（frontend）...
start "frontend" cmd /k "cd /d "%ROOT%frontend" && if not exist node_modules (echo 安装前端依赖... && npm install) && echo 启动前端 (vite)... && npm run dev"

echo 后端与前端已分别在新窗口中启动。按任意键返回。
pause >nul
