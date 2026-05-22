# Backend (Express)

进入 `backend` 目录，安装依赖并启动：

```powershell
cd backend
npm install
npm start
```

API:
- `GET /students` — 列表
- `GET /students/:id` — 学生详情（含饭卡）
- `GET /cards/:cardNumber` — 饭卡详情与消费记录
- `POST /cards/:cardNumber/consumptions` — 添加消费，body JSON
