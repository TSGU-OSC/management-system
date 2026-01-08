<template>
  <el-container class="home-container">
    <!--顶部-->
    <el-header class="home-header">
      <div class="header-left">
        <div class="logo-container">
          <img src="../assets/img/iconmax.jpg" class="logo-img" />
        </div>
        <div class="title-container">开源鸿蒙社团成员管理系统</div>
      </div>
      
      <div class="header-right">
        <div class="user-greeting">
          {{ getTimeState() }}{{ this.$store.state.user.name }}
        </div>
          <el-dropdown>
            <el-avatar shape="circle" size="large" fit="fill" :src="this.avatorUrl" class="user-avatar" />

            <template>
              <el-dropdown-menu>
                <el-dropdown-item @click.native="myPage">个人主页</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
        </el-dropdown>
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
      { name: "/announcement", title: "公告管理", icon: "el-icon-s-comment" },
      { name: "/screen", title: "成员展板", icon: "el-icon-monitor" },
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
      // {name: "/dictionary", title: "字典管理", icon: "el-icon-bank-card"},
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
      { name: "/announcement", title: "公告管理", icon: "el-icon-s-comment" },
      { name: "/screen", title: "成员展板", icon: "el-icon-monitor" },
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

<style scoped>
/* 整个头部容器：使用 flex 布局，左右分配空间 */
.home-header {
  display: flex !important;
  align-items: center;
  justify-content: space-between;
  padding: 0 clamp(8px, 1.5vw, 16px); /* 动态 padding */
  box-sizing: border-box;
  width: 100%;
  overflow: hidden;
  gap: clamp(8px, 1.5vw, 16px); /* 左右两部分之间的间距也动态 */
}

/* 左侧容器：logo + 标题，自动分配空间 */
.header-left {
  display: flex;
  align-items: center;
  min-width: 0; /* 允许 flex 子元素收缩 */
  flex: 1 1 auto; /* 自动伸缩，优先保证显示 */
  gap: clamp(8px, 1.2vw, 12px); /* 动态间距 */
  overflow: hidden; /* 防止溢出 */
}

/* logo 容器：固定不收缩 */
.logo-container {
  flex-shrink: 0; /* logo 不收缩 */
  display: flex;
  align-items: center;
}

/* logo 图片：使用 clamp 动态调整，无需媒体查询 */
.logo-img {
  width: clamp(50px, 8vw, 120px); /* 最小50px，最大120px，中间按视口宽度8%动态调整 */
  height: auto;
  display: block;
  object-fit: contain;
}

/* 标题容器：自动收缩，空间不足时显示省略号 */
.title-container {
  font-size: clamp(0.9rem, 1.5vw, 1.3rem); /* 动态字体大小 */
  font-weight: bold;
  white-space: nowrap; /* 不换行 */
  flex: 1 1 auto; /* 自动伸缩 */
  min-width: 0; /* 允许收缩到0 */
  overflow: hidden;
  text-overflow: ellipsis; /* 文字过长显示省略号 */
}

/* 右侧容器：用户信息，尽量不收缩 */
.header-right {
  display: flex;
  align-items: center;
  flex-shrink: 0; /* 右侧不收缩，优先保证用户信息显示 */
  margin-left: clamp(15px, 4vw, 20px);
}

.user-avatar {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  margin-right: 15px;
}

.user-greeting {
  white-space: nowrap;
  font-size: clamp(0.75rem, 1vw, 0.9rem); /* 动态字体大小 */
  gap: clamp(10px, 2vw, 15px);
  margin-right: 10px;
}


/* 当空间极度不足时，允许标题换行（通过容器换行实现） */
@media (max-width: 600px) {
  .home-header {
    flex-wrap: wrap;
    row-gap: 8px;
  }
  
  .header-left {
    width: 100%;
  }
  
  .header-right {
    width: 100%;
    justify-content: flex-end;
    margin-left: 0;
  }
  
  .title-container {
    white-space: normal; /* 允许换行 */
  }
}
</style>
