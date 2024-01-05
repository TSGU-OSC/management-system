import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: sessionStorage.getItem("token"),
        user: JSON.parse(sessionStorage.getItem("user"))
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
