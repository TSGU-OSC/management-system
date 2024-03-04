<template>
  <el-container class="home-container">
    <!--顶部-->
    <el-header style="margin-right: 15px; width: 100%">
      <span class="nav-logo"></span>
      <img src="../assets/img/iconmax.jpg" style="width: 120px;" />

      <span class="head-title">开源鸿蒙社团成员管理系统</span>

      <div style="float: right;border: 2px;">
        <div>
          <div class="demo-fit">
            <div style="margin-top: 10px;margin-right: 10px;">{{ getTimeState() }}{{ this.$store.state.user.name }}</div>
            <el-dropdown>
              <div class="block">
                <el-avatar shape="circle" size="large" fit="fill" :src="this.avatorUrl" style="margin-top: 2px;" />
              </div>

              <template>
                <el-dropdown-menu>
                  <el-dropdown-item @click.native="myPage">个人主页</el-dropdown-item>
                  <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>

    </el-header>
    <!-- 主体 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="13%">
        <el-menu :default-active="$route.path" router text-color="black" active-text-color="red">
          <el-menu-item v-for="(item, i) in this.navList" :key="i" :index="item.name">
            <i :class="item.icon"></i>
            {{ item.title }}
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <!--路由占位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { currentUser, logOut } from "@/api/user";
import defaultAvatar from "@/assets/img/avator.jpg";

export default {
  name: "Home",
  data() {
    return {
      avatorUrl: this.$store.state.user.avator === '' ? defaultAvatar : "/api/file/download?fileName=" + this.$store.state.user.avator,
      navList: '',
      currentTime: new Date(),

    }
  },

  created() {
    if(this.$store.state.user.role!=0){
      this.navList = [
      { name: "/index", title: "首页", icon: "el-icon-s-home" },
      { name: "/myPage", title: "个人", icon: "el-icon-setting" },
      { name: "/user", title: "用户管理", icon: "el-icon-s-custom" },
      // {name: "/dictionary", title: "字典管理", icon: "el-icon-bank-card"},
      // { name: "/announcement", title: "公告管理", icon: "el-icon-s-comment" },
      // { name: "/screen", title: "成员展板", icon: "el-icon-monitor" },
      { name: "/audit", title: "成员审核", icon: "el-icon-bell" },
    ];
  }else{
    this.navList = [
      { name: "/index", title: "首页", icon: "el-icon-s-home" },
      { name: "/myPage", title: "个人", icon: "el-icon-setting" },
      { name: "/user", title: "用户管理", icon: "el-icon-s-custom" },
      // {name: "/dictionary", title: "字典管理", icon: "el-icon-bank-card"},
      // { name: "/announcement", title: "公告管理", icon: "el-icon-s-comment" },
      // { name: "/screen", title: "成员展板", icon: "el-icon-monitor" },
    ];
  }
  
  },
  watch: {
    "$store.state.user.avator"(newVal) {
      this.avatorUrl = newVal === '' ? defaultAvatar : "/api/file/download?fileName=" + newVal;
      this.$forceUpdate();// 更新数据
    },
    "$store.state.user.role"(newVal) {
      if(this.$store.state.user.role!=0){
      this.navList = [
      { name: "/index", title: "首页", icon: "el-icon-s-home" },
      { name: "/myPage", title: "个人", icon: "el-icon-setting" },
      { name: "/user", title: "用户管理", icon: "el-icon-s-custom" },
      {name: "/dictionary", title: "字典管理", icon: "el-icon-bank-card"},
      { name: "/announcement", title: "公告管理", icon: "el-icon-s-comment" },
      { name: "/screen", title: "成员展板", icon: "el-icon-monitor" },
      { name: "/audit", title: "成员审核", icon: "el-icon-bell" },
    ];
  }else{
    this.navList = [
      { name: "/index", title: "首页", icon: "el-icon-s-home" },
      { name: "/myPage", title: "个人", icon: "el-icon-setting" },
      { name: "/user", title: "用户管理", icon: "el-icon-s-custom" },
      // {name: "/dictionary", title: "字典管理", icon: "el-icon-bank-card"},
      // { name: "/announcement", title: "公告管理", icon: "el-icon-s-comment" },
      // { name: "/screen", title: "成员展板", icon: "el-icon-monitor" },
    ];
  }
    }
  },
  mounted() {
    this.getUserInfo();

  },
  methods: {
    getTimeState() {
      // 获取当前时间
      let timeNow = new Date();
      // 获取当前小时
      let hours = timeNow.getHours();
      // 设置默认文字
      let state = ``;
      // 判断当前时间段
      if (hours >= 0 && hours <= 10) {
        state = `早上好!`;
      } else if (hours > 10 && hours <= 14) {
        state = `中午好!`;
      } else if (hours > 14 && hours <= 18) {
        state = `下午好!`;
      } else if (hours > 18 && hours <= 24) {
        state = `晚上好!`;
      }
      return state;
    },

    logout() {
      logOut().then(res => {
        if (res.data.code === 200) {
          this.$notify({
            title: '成功',
            message: '您已退出登录',
            type: 'success'
          });
        } else {
          this.$notify({
            title: '失败',
            message: '退出登录失败，请稍后重试',
            type: 'error'
          });
        }
      })
      this.$router.push('/login');
    },
    myPage() {
      this.$router.push('/myPage');
    },
    getUserInfo() {
      currentUser().then(res => {
        if (res.data.code === 200) {
          this.$store.commit('SET_USER', res.data.data);
        } else {
          console.error(res.data.message);
        }
      })
    }
  }
};
</script>

<style>
.nav-logo {
  position: absolute;
  padding-top: -1%;
  left: 5%;
  font-size: 40px;
}

.head-title {
  position: absolute;
  padding-top: 20px;
  left: 15%;
  font-size: 20px;
  font-weight: bold;
}

.item {
  margin: 4px;
}

.demo-fit {
  display: flex;
  text-align: center;
  justify-content: space-between;
}

.demo-fit .block {
  flex: 1;
  display: flex;
  flex-direction: column;
  flex-grow: 0;
}
</style>
