<template>
  <el-container class="home-container">
    <!--顶部-->
    <el-header class="home-header">
      <div class="header-left">
        <div class="logo-container">
          <!-- <img src="../assets/img/iconmax.jpg" class="logo-img" /> -->
          <!-- <img src="../assets/new-icon/HSD-Light.png" class="logo-img"> -->
          <img src="../assets/new-icon/HSD-Dark.png" class="logo-img" alt="Logo">
        </div>
        <div class="title-container">天津中德开源鸿蒙社管理系统</div>
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
      <el-aside>
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
        <!-- 用于检测主要区域内容占据不全整个屏幕原因的测试 -->
        <!-- <div style="background: #409EFF; height: 100%; width: 100%; display: flex;">
          <div style="flex: 3; background: #67C23A;">主内容区模拟 (75%)</div>
          <div style="flex: 1; background: #E6A23C; display: flex; flex-direction: column;">
            <div style="flex: 1; background: #F56C6C;">右侧上部 (50%)</div>
            <div style="flex: 1; background: #909399;">右侧下部 (50%)</div>
          </div>
        </div> -->
      </el-main>
    </el-container>
    
  </el-container>
</template>

<script>
import { currentUser, logOut } from "@/api/user";
import defaultAvatar from "@/assets/img/avator.jpg";

export default {
  name: "Home",
  // 数据
  data() {
    return {
      avatorUrl: this.$store.state.user.avator === '' ? defaultAvatar : "/api/file/download?fileName=" + this.$store.state.user.avator,
      navList: '',
      currentTime: new Date(),

    }
  },

  // 生命周期：挂载
  created() {
    // 权限区分
    // 角色 role: 0-普通成员, 1-管理员, 2-超级管理员
    const userRole = this.$store.state.user.role;
    
    // 基础菜单（所有用户可见）
    const baseNav = [
      { name: "/index", title: "首页", icon: "el-icon-s-home" },
      { name: "/myPage", title: "个人", icon: "el-icon-setting" },
      { name: "/screen", title: "成员展板", icon: "el-icon-monitor" },
    ];

    // 管理员菜单（管理员及以上可见）
    const adminNav = [
      { name: "/user", title: "用户管理", icon: "el-icon-s-custom" },
      { name: "/dictionary", title: "字典管理", icon: "el-icon-bank-card" },
      { name: "/announcement", title: "公告管理", icon: "el-icon-s-comment" },
      { name: "/audit", title: "成员审核", icon: "el-icon-bell" },
    ];

    // 普通用户可见菜单
    const userNav = [
      { name: "/announcement", title: "公告查看", icon: "el-icon-s-comment" }, 
    ];

    if (userRole > 0) {
      // 管理员及以上权限
      this.navList = [...baseNav, ...adminNav];
    } else {
      // 普通成员权限
      this.navList = [...baseNav, ...userNav];
    }
  
  },
  watch: {
    "$store.state.user.avator"(newVal) {
      this.avatorUrl = newVal === '' ? defaultAvatar : "/api/file/download?fileName=" + newVal;
      this.$forceUpdate();// 更新数据
    },
    "$store.state.user.role"(newVal) {
      // 角色 role: 0-普通成员, 1-管理员, 2-超级管理员
      const userRole = newVal;
      
      // 基础菜单（所有用户可见）
      const baseNav = [
        { name: "/index", title: "首页", icon: "el-icon-s-home" },
        { name: "/myPage", title: "个人", icon: "el-icon-setting" },
        { name: "/screen", title: "成员展板", icon: "el-icon-monitor" },
      ];

      // 管理员菜单（管理员及以上可见）
      const adminNav = [
        { name: "/user", title: "用户管理", icon: "el-icon-s-custom" },
        { name: "/dictionary", title: "字典管理", icon: "el-icon-bank-card" },
        { name: "/announcement", title: "公告管理", icon: "el-icon-s-comment" },
        { name: "/audit", title: "成员审核", icon: "el-icon-bell" },
      ];

      // 普通用户可见菜单
      const userNav = [
        { name: "/announcement", title: "公告查看", icon: "el-icon-s-comment" }, 
      ];

      if (userRole > 0) {
        this.navList = [...baseNav, ...adminNav];
      } else {
        this.navList = [...baseNav, ...userNav];
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
/* 最外层整个容器占满整个屏幕高度 */
.home-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: auto;
}

/* 侧边栏 + 主要内容区域自动撑满剩余高度 */
.home-container > .el-container {  
  flex: 1 !important;
  min-height: 0 !important;
  /* overflow: hidden !important; */
}

/* 侧边栏和主要内容区域在水平方向自动填充 */
.home-container .el-aside, .home-container .el-main {
  height: 100% !important;
}

/* 具体设置侧边栏和主要内容区域 */
/* 侧边栏：固定最小宽度，允许滚动 */
.home-container .el-aside {
  width: auto !important;
  min-width: 180px !important;
  max-width: 250px !important;
  border-right: 1px solid #e6e6e6;
  /* overflow-x: hidden;
  overflow-y: auto; */
}

/* 主要内容区域：自动占据剩余宽度，并管理内部滚动 */
.home-container .el-main {
  /* padding: 20px; */
  /* overflow-y: auto !important;  主要内容区域内部可以滚动 */
  flex: 1 !important;  /* 占据侧边栏剩下的所有水平空间 */
  min-width: 0 !important;  /* 允许在狭窄空间收缩 */
}

/* 确保菜单栏不会因为文字过长而破坏布局 */
.home-container .el-menu {
  border-right: none;
  height: 100%;
}

.home-container .el-menu-item {
  min-width: 0;
}

.home-container .el-menu-item > * {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

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
