import request from "@/utils/request"

// 用户登录
export function userLogin(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}


// 获取验证码
export function getCodeImg() {
  return request({
    url: '/user/verify',
    method: 'post'
  })
}



// 用户登出
export function logOut() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

//获取用户总数量
export function userCount() {
  return request({
    url: '/user/count/sum',
    method: 'get',

  })
}

// 获取当前登录用户
export function currentUser() {
  return request({
    url: '/user/current',
    method: 'get',
  })
}

//获取用户列表
export function userList(data,pageNumber,pageSize) {
  return request({
    url: '/user/search',
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
    url: '/user/add',
    method: 'post',
    data
  })
}

//修改用户
export function userUpdate(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

//删除用户
export function userDelete(id) {
  return request({
    url: '/user/delete',
    method: 'post',
    params: {
      id
    }
  })
}

//下载文件
export function downloadFile(data) {
  return request({
    url: '/file/download',
    responseType: "blob",
    method: 'post',
    params:{
      fileName: data
    }
  })
}
