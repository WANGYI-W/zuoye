const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const { students, Consumption, Canteen, Supermarket, MealCard, Student } = require('./data');

const app = express();
app.use(cors());
app.use(bodyParser.json());

function findStudentByCard(cardNumber) {
  return students.find(x => x.mealCard.cardNumber === cardNumber);
}

// GET /students
app.get('/students', (req, res) => {
  res.json(students.map(s => ({ studentId: s.studentId, name: s.name })));
});

// GET /students/:id
app.get('/students/:id', (req, res) => {
  const s = students.find(x => x.studentId === req.params.id);
  if (!s) return res.status(404).json({ message: '未找到学生' });
  res.json(s);
});

// POST /students
// body: { studentId, name, college, studentClass, cardNumber, balance, officeId, officeHours }
app.post('/students', (req, res) => {
  const { studentId, name, college, studentClass, cardNumber, balance, officeId, officeHours } = req.body;
  if (!studentId || !name || !college || !studentClass || !cardNumber || typeof balance !== 'number' || !officeId || !officeHours) {
    return res.status(400).json({ message: '参数缺失或类型错误' });
  }
  if (students.some(x => x.studentId === studentId)) {
    return res.status(400).json({ message: '学号已存在' });
  }
  if (students.some(x => x.mealCard.cardNumber === cardNumber)) {
    return res.status(400).json({ message: '卡号已存在' });
  }
  const student = Student(studentId, name, college, studentClass, MealCard(cardNumber, balance, { officeId, businessHours: officeHours }));
  students.push(student);
  res.status(201).json(student);
});

// GET /cards/:cardNumber
app.get('/cards/:cardNumber', (req, res) => {
  const cardOwner = findStudentByCard(req.params.cardNumber);
  if (!cardOwner) return res.status(404).json({ message: '未找到卡片' });
  res.json(cardOwner.mealCard);
});