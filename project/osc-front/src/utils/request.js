import axios from 'axios'
import store from '@/store'
import router from "@/router";

//const baseURL="localhost:8088/api"

//创建axios实例
const service = axios.create({
  // baseURL: process.env.BASE_API, // api的base_url
  //withCredentials: true,  // 携带Cookie
})

// request 请求拦截
// service.interceptors.request.use(
//   config => {
//
//     if (store.state.token) {
//       config.headers['token'] = window.sessionStorage.getItem("token")
//     }
//     return config
//   },
//   error => {
//     // do something with request error
//     console.log(error) // for debug
//     return Promise.reject(error)
//   }
// )

//response响应拦截
service.interceptors.response.use(response => {
    console.log('响应：')
    if (response.data.code === 40100) {
      router.push('/login')
    } else {
      return response
    }
  },
  error => {
  console.log('出错')
    console.log(error)
    if (error.response.data) {
      error.message = error.response.data.message
    }

    if (error.response.data.code === 40100) {
      router.push("/login")
    }
    return Promise.reject(error)
  }
)


export default service
