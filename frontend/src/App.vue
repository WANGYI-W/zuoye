<template>
  <div style="padding:20px;font-family:Arial, sans-serif;">
    <h2>校园饭卡系统（前端示例）</h2>
    <div style="display:flex;gap:20px;">
      <div style="min-width:320px;">
        <h3>学生列表</h3>
        <ul>
          <li v-for="s in students" :key="s.studentId" style="margin-bottom:6px;">
            <a href="#" @click.prevent="selectStudent(s)">{{ s.studentId }} - {{ s.name }}</a>
          </li>
        </ul>
        <div style="margin-top:20px;padding:12px;border:1px solid #ccc;border-radius:8px;background:#f9f9f9;">
          <h4>添加学生</h4>
          <div style="display:grid;gap:8px;">
            <input v-model="newStudent.studentId" placeholder="学号" />
            <input v-model="newStudent.name" placeholder="姓名" />
            <input v-model="newStudent.college" placeholder="学院" />
            <input v-model="newStudent.studentClass" placeholder="班级" />
            <input v-model="newStudent.cardNumber" placeholder="饭卡号" />
            <input type="number" v-model.number="newStudent.balance" placeholder="初始余额" />
            <input v-model="newStudent.officeId" placeholder="管理办公室编号" />
            <input v-model="newStudent.officeHours" placeholder="办公室营业时间" />
            <button @click="submitStudent">添加学生</button>
          </div>
        </div>
      </div>
      <div style="flex:1;">
        <div v-if="selected">
          <h3>学生详情</h3>
          <p><strong>{{ selected.studentId }}</strong> {{ selected.name }}</p>
          <p>学院: {{ selected.college }} | 班级: {{ selected.studentClass }}</p>
          <h4>饭卡：{{ selected.mealCard.cardNumber }}</h4>
          <p>余额：{{ selected.mealCard.balance.toFixed(2) }} 元</p>
          <p>管理办公室：{{ selected.mealCard.office.officeId }} / {{ selected.mealCard.office.businessHours }}</p>
          <div style="margin-top:16px;padding:12px;border:1px solid #ddd;border-radius:8px;background:#fafafa;">
            <h5>充值</h5>
            <input type="number" v-model.number="rechargeAmount" placeholder="充值金额" style="width:120px;" />
            <button @click="submitRecharge">充值</button>
          </div>
          <h5 style="margin-top:16px;">消费记录</h5>
          <ul>
            <li v-for="c in selected.mealCard.consumptions" :key="c.id">{{ c.merchant.name }} - {{ c.amount }}元 - {{ c.dateTime }}</li>
          </ul>
          <div style="margin-top:12px;padding:12px;border:1px solid #ddd;border-radius:8px;background:#fafafa;">
            <h5>添加消费</h5>
            <div style="display:flex;flex-wrap:wrap;gap:8px;align-items:center;">
              <select v-model="newConsum.merchantType">
                <option value="canteen">食堂</option>
                <option value="supermarket">超市</option>
              </select>
              <input v-model="newConsum.merchantId" placeholder="商户编号" />
              <input v-model="newConsum.merchantName" placeholder="商户名称" />
              <input type="number" v-model.number="newConsum.amount" placeholder="金额" style="width:100px;" />
              <button @click="submitConsumption">添加消费</button>
            </div>
          </div>
        </div>
        <div v-else>
          <p>请选择学生查看详情。</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchStudents, fetchStudent, addStudent, addConsumption, rechargeCard } from './api'

export default {
  data() {
    return {
      students: [],
      selected: null,
      // 添加学生时的数据模型
      newConsum: { merchantType: 'canteen', merchantId: '', merchantName: '', amount: 0 },
      newStudent: { studentId: '', name: '', college: '', studentClass: '', cardNumber: '', balance: 0, officeId: 'O100', officeHours: '08:30-17:30' },
      rechargeAmount: 0
    }
  },
  async mounted() {
    this.students = await fetchStudents();
  },
  methods: {
    async refreshStudents() {
      // 重新加载学生列表
      this.students = await fetchStudents();
    },
    async selectStudent(s) {
      // 点击学生后加载该学生的详细信息
      this.selected = await fetchStudent(s.studentId);
    },
    async submitStudent() {
      // 提交新增学生请求
      const payload = { ...this.newStudent };
      const res = await addStudent(payload);
      if (res.ok) {
        alert('添加学生成功');
        await this.refreshStudents();
        this.selected = await fetchStudent(payload.studentId);
        this.newStudent = { studentId: '', name: '', college: '', studentClass: '', cardNumber: '', balance: 0, officeId: 'O100', officeHours: '08:30-17:30' };
      } else {
        const body = await res.json();
        alert('失败：' + (body.message || res.statusText));
      }
    },
    async submitRecharge() {
      // 提交饭卡充值请求
      if (!this.selected) return;
      const res = await rechargeCard(this.selected.mealCard.cardNumber, this.rechargeAmount);
      if (res.ok) {
        alert('充值成功');
        this.selected = await fetchStudent(this.selected.studentId);
        this.rechargeAmount = 0;
      } else {
        const body = await res.json();
        alert('失败：' + (body.message || res.statusText));
      }
    },
    async submitConsumption() {
      // 提交新增消费记录请求
      if (!this.selected) return;
      const payload = { ...this.newConsum };
      const res = await addConsumption(this.selected.mealCard.cardNumber, payload);
      if (res.ok) {
        this.selected = await fetchStudent(this.selected.studentId);
        alert('消费已添加');
        this.newConsum = { merchantType: 'canteen', merchantId: '', merchantName: '', amount: 0 };
      } else {
        const body = await res.json();
        alert('失败：' + (body.message || res.statusText));
      }
    }
  }
}
</script>

<style>
a { color: #2c3e50 }
</style>
