# 校园饭卡管理 — 前后端分离示例

本仓库为教学/演示用途的校园饭卡管理示例（前后端分离）。

项目结构概览：

- `backend/` — 基于 Node.js + Express 的示例 REST API（内存数据，用于开发）。
- `frontend/` — 基于 Vue 3 + Vite 的前端单页应用（示例界面，调用后端 API）。

先决条件：

- 已安装 Node.js（建议 16+ 或 18+）。

本地运行（先后端顺序）：

1) 启动后端（开发用内存数据）

```powershell
cd backend
npm install
npm start
```

默认监听端口：`http://localhost:3000`

可用 API:

- `GET /students` — 学生列表（含 `studentId` 与 `name`）
- `GET /students/:id` — 学生详情（含 `mealCard`）
- `GET /cards/:cardNumber` — 饭卡详情与消费记录
- `POST /cards/:cardNumber/consumptions` — 添加消费，示例请求体：

```json
// { "merchantType":"canteen","merchantId":"C-UI","merchantName":"界面食堂","amount":28.5 }
```

2) 启动前端（另开终端）

```powershell
cd frontend
npm install
npm run dev
```

前端开发服务器默认使用 Vite，默认会在 `http://localhost:5173`（或控制台提示的端口）启动。

若后端不在 `http://localhost:3000`，可在前端项目根创建 `.env` 或在命令行设置 `VITE_API_BASE`，例如：

```powershell
# Windows PowerShell
$env:VITE_API_BASE='http://your-backend:3000'; npm run dev
```

部署/生产准备（建议流程）：

1. 将后端替换为有持久化的实现（如 Spring Boot + 数据库，或 Node.js + SQLite/Postgres）。
2. 在前端运行 `npm run build` 生成静态文件并由反向代理或静态服务器托管。

备注：项目中曾有一个基于 Swing 的旧桌面示例，已从仓库中移除，当前以前后端分离的示例为主。

如需我：我可以将后端改为 Spring Boot 实现并重用现有 Java 模型，或添加 SQLite 持久化、或生成一键启动脚本。告诉我你的偏好。
