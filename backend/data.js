// 简单的内存数据，基于现有 Java 示例
const { v4: uuidv4 } = require('uuid');

const Office = (officeId, businessHours) => ({ officeId, businessHours });

const Canteen = (id, name) => ({ type: 'canteen', id, name });
const Supermarket = (id, name) => ({ type: 'supermarket', id, name });

const Consumption = (merchant, amount, dateTime) => ({ id: uuidv4(), merchant, amount, dateTime });

const MealCard = (cardNumber, balance, office) => ({ cardNumber, balance, office, consumptions: [] });

const Student = (studentId, name, college, studentClass, mealCard) => ({ studentId, name, college, studentClass, mealCard });

const office = Office('O100', '08:30-17:30');
const canteen = Canteen('C001', '学生食堂');
const supermarket = Supermarket('S001', '校园超市');

const card1 = MealCard('M1001', 500.00, office);
card1.consumptions.push(Consumption(canteen, 28.50, new Date().toISOString()));
card1.consumptions.push(Consumption(supermarket, 15.20, new Date(Date.now() - 24*3600*1000).toISOString()));

const student1 = Student('20241001', '李华', '计算机学院', '软件工程一班', card1);

const students = [student1];

module.exports = { students, MealCard, Consumption, Canteen, Supermarket, Student, Office };
