import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem("token") || '',
    user: JSON.parse(sessionStorage.getItem("user")) || { id: 1, code: 'U0001', name: '演示用户', gender: '1', province: '北京', city: '北京', phone: '13800000000', duty: '1', department: '1', role: 1, status: '0', avator: '' }
  },
  mutations: {
    // set
    SET_TOKENN: (state, token) => {
      state.token = token
      sessionStorage.setItem("token", token)
    },
    SET_USER: (state, user) => {
      state.user = user
      sessionStorage.setItem("user", JSON.stringify(user))
    },
    REMOVE_INFO : (state) => {
      state.token = ''
      state.user = {}
      sessionStorage.setItem("token", '')
      sessionStorage.setItem("user", JSON.stringify(''))
    }
  },
  getters: {

  },
  actions: {
  },
  modules: {
  }
})
