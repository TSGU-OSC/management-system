<template>
  <el-container class="home-container">
    <!--顶部-->
    <el-header style="margin-right: 15px; width: 100%">
      <span class="nav-logo"></span>
      <img src="../assets/img/iconmax.jpg" style="width: 120px;"/>

      <span class="head-title">开源鸿蒙社团成员管理系统</span>

      <div style="float: right;border: 2px;">
        <div>
          <div class="demo-fit">
            <el-dropdown>
            <div class="block">
              <el-avatar shape="square" size="large" fit="cover" :src="this.avatorUrl" />
            </div>
              <div>{{ this.$store.state.user.name }}</div>
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
          <el-menu-item v-for="(item, i) in navList" :key="i" :index="item.name">
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
import {currentUser, logOut} from "@/api/user";
export default {
  name: "Home",
  data() {
    return {
      avatorUrl: "/file/download?fileName=" + this.$store.state.user.avator,
      navList: [
        {name: "/index", title: "首页", icon: "el-icon-s-home"},
        {name: "/myPage", title: "个人", icon: "el-icon-setting"},
        {name: "/user", title: "用户管理", icon: "el-icon-s-custom"},
        // {name: "/dictionary", title: "字典管理", icon: "el-icon-bank-card"},
        // {name: "/announcement", title: "公告管理", icon: "el-icon-s-comment"},
      ],
    };
  },
  mounted() {
    this.getUserInfo();
  },
  methods: {
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
      currentUser().then(res=>{
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
