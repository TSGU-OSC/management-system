import request from '@/utils/request'

export function userLogin(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function userRegister(data) {
  return request({
    url: '/register',
    method: 'post',
    data
  })
}

export function getCodeImg() {
  return request({
    url: '/getCode',
    method: 'get'
  })
}



// 前端 登出   同样是在action中管理
export function LogOut({ commit }) {
  return new Promise(resolve => {
    commit('SET_TOKEN', '')
    removeToken()
    resolve()
  })
}

//获取用户数量
export function userCount() {
  return request({
    url: '/user/count',
    method: 'get',

  })
}


//获取用户列表
export function userList(data) {
  return request({
    url: '/user/list',
    method: 'post',
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

//批量删除用户
export function userBatchDelete(data) {
  return request({
    url: '/user/delete/batch',
    method: 'post',
    data
  })
}
