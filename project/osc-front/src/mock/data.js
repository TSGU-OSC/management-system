export const currentUser = {
  id: 1,
  code: 'U0001',
  name: '演示用户',
  gender: '1',
  province: '北京',
  city: '北京',
  phone: '13800000000',
  duty: '1',
  department: '1',
  role: 1,
  status: '0',
  avator: ''
}

export const users = [
  {
    id: 1,
    code: 'U0001',
    name: '张三',
    gender: '1',
    province: '北京',
    city: '北京',
    phone: '13800000001',
    duty: '0',
    department: '1',
    role: 1,
    status: '0',
    avator: ''
  },
  {
    id: 2,
    code: 'U0002',
    name: '李四',
    gender: '0',
    province: '上海',
    city: '上海',
    phone: '13800000002',
    duty: '2',
    department: '0',
    role: 0,
    status: '0',
    avator: ''
  },
  {
    id: 3,
    code: 'U0003',
    name: '王五',
    gender: '1',
    province: '广东',
    city: '广州',
    phone: '13800000003',
    duty: '3',
    department: '2',
    role: 1,
    status: '0',
    avator: ''
  }
]

export const audits = [
  {
    id: 101,
    code: 'A0101',
    name: '赵六',
    status: '2',
    duty: '0',
    department: '3',
    role: '0'
  },
  {
    id: 102,
    code: 'A0102',
    name: '钱七',
    status: '3',
    duty: '1',
    department: '1',
    role: '0'
  }
]

export const announcements = [
  {
    id: 1,
    title: '系统维护通知',
    content: '本周末进行系统维护，届时服务暂不可用。',
    status: 1
  },
  {
    id: 2,
    title: '社团活动报名',
    content: '新一期技术沙龙开始报名，请在系统内提交信息。',
    status: 1
  }
]

export const genderCount = [
  18,
  12
]

export const provinceCount = [
  { province: '北京', count: 8 },
  { province: '上海', count: 6 },
  { province: '广东', count: 10 },
  { province: '浙江', count: 4 },
  { province: '四川', count: 2 }
]

export default {
  currentUser,
  users,
  audits,
  announcements,
  genderCount,
  provinceCount
}
