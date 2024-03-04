import request from "@/utils/request"

// 用户登录
export function userLogin(data) {
  return request({
    url: '/api/user/login',
    method: 'post',
    data
  })
}
export function userRegister(data) {
  return request({
    url: '/api/user/register',
    method: 'post',
    data
  })
}


// 获取验证码
export function getCodeImg() {
  return request({
    url: '/api/user/verify',
    method: 'post'
  })
}



// 用户登出
export function logOut() {
  return request({
    url: '/api/user/logout',
    method: 'post'
  })
}
// 获取当前登录用户
export function currentUser() {
  return request({
    url: '/api/user/current',
    method: 'get',
  })
}

//获取用户列表
export function userList(data,pageNumber,pageSize) {
  return request({
    url: '/api/user/search',
    method: 'post',
    params: {
      pageNumber: pageNumber,
      pageSize: pageSize,
    },
    data
  })
}

//添加用户
export function userAdd(data) {
  return request({
    url: '/api/user/add',
    method: 'post',
    data
  })
}

//修改用户
export function userUpdate(data) {
  return request({
    url: '/api/user/update',
    method: 'post',
    data
  })
}

//删除用户
export function userDelete(id) {
  return request({
    url: '/api/user/delete',
    method: 'post',
    params: {
      id
    }
  })
}

// 导出Excel
export function outputExcel() {
  return request({
    url: '/api/excel/output',
    method: 'get',
    responseType: 'blob',// 服务器响应的数据类型
  })
}

//统计用户来自的省
export function userProvinceCount() {
  return request({
    url: '/api/user/count/province',
    method: 'get',
  })
}

//统计用户性别
export function userGenderCount(data) {
  return request({
    url: '/api/user/count/gender',
    method: 'get',
    data
  })
}

//统计用户总人数
export function userCount() {
  return request({
    url: '/api/user/count/sum',
    method: 'get',
  })
}

//获取审核用户列表
export function userAudit(data,pageNumber,pageSize) {
  return request({
    url: '/api/user/audit',
    method: 'post',
    params: {
      pageNumber: pageNumber,
      pageSize: pageSize,
    },
    data
  })
}