import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
//导入登录页面组件
import Login from '@/views/login.vue'
import Home from '@/views/home.vue'
import Register from '@/views/register/index.vue'
import VueRouter from 'vue-router'
 
// 解决Vue-Router升级导致的Uncaught(in promise) navigation guard问题
 
// push
// const originalPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push (location, onResolve, onReject) {
//   if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
//   return originalPush.call(this, location).catch(err => err)
// }
 
// // replace
// const originalReplace = VueRouter.prototype.replace
// VueRouter.prototype.replace= function replace(location) {
//   return originalReplace.call(this, location).catch(err => err)
// }
Vue.use(VueRouter)
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Default',
      redirect: '/home',
      component: Home
    },
    {
       // home页面并不需要被访问，只是作为其它组件的父组件
      path: '/home',
      name: 'Home',
      component: Home,
      meta: {
        requireAuth: true
      },
      redirect: '/index',
      children:[
        {
          path:'/index',
          name:'Index',
          component:() => import('@/views/home/index'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/user',
          name:'User',
          component:()=>import('@/views/user/index'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/dictionary',
          name:'Dictionary',
          component:()=>import('@/views/dictionary/index'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/activity',
          name:'Activity',
          component:()=>import('@/views/activity/index'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/announcement',
          name:'Announcement',
          component:()=>import('@/views/announcement/index'),
          meta:{
            requireAuth:true
          }
        }
       
      ]
    },
    //添加登录页面路由
    {
      path:'/login',
      name: 'Login',
      component: Login
    } ,
     //添加注册页面路由
     {
      path:'/register',
      name: 'Register',
      component: Register
    } ,
    {
          path: '/404',
          name: 'NotFound',
          meta: {
            requireAuth:false
          },
          component: () => import('@/views/NotFound/index')
        },
        // 所有未定义路由，全部重定向到404页
        {
          path: '/*',
          redirect: '/404'
        }
  ]
})
