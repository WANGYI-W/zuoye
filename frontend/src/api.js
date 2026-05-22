// api.js - 前端与后端的 HTTP 封装（已清理无 BOM）
// 定义后端 API 基础地址，可通过环境变量覆盖
const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:3000';

export async function fetchStudents() {
  const res = await fetch(`${API_BASE}/students`);
  return res.json();
}

export async function fetchStudent(id) {
  const res = await fetch(`${API_BASE}/students/${id}`);
  return res.json();
}

export async function fetchCard(cardNumber) {
  const res = await fetch(`${API_BASE}/cards/${cardNumber}`);
  return res.json();
}

export async function addStudent(payload) {
  // 新增学生记录到后端
  const res = await fetch(`${API_BASE}/students`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  return res;
}

export async function addConsumption(cardNumber, payload) {
  // 添加消费记录到对应饭卡
  const res = await fetch(`${API_BASE}/cards/${cardNumber}/consumptions`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  return res;
}

export async function rechargeCard(cardNumber, amount) {
  // 充值指定饭卡余额
  const res = await fetch(`${API_BASE}/cards/${cardNumber}/recharge`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ amount })
  });
  return res;
}
