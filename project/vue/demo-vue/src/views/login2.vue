<template>
  <body id="login-page">
    <el-form class="login-container" label-position="left" label-width="0px">
      <h3 class="login_title">开源鸿蒙社团成员管理系统登录</h3>
      <el-form-item>
        <el-input
          type="text"
          v-model="loginForm.username"
          auto-complete="off"

          placeholder="姓名"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input
          type="password"
          v-model="loginForm.password"
          auto-complete="off"
          placeholder="密码"
        ></el-input>
      </el-form-item>   
      <el-form-item prop="code">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="验证码"
            style="width: 58%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" class="login-code-img" @click="getCode">
          </div>
        </el-form-item>   
      <el-form-item style="width: 100%">
        <el-button
          type="primary"
          style="width: 40%; border: none;float: left;"
          @click="login"
          icon="el-icon-s-promotion"
          >登录</el-button
        >
        <el-button
          type="primary"
          style="width: 40%; border: none;float: right;"
          @click="register"
          icon="el-icon-user-solid"
          >注册</el-button
        >
     
       
          </el-form-item>
    </el-form>
  </body>
</template>

<script>
import { userLogin,getCodeImg } from "@/api/user";
export default {
  name: "Login",
  data() {
    return {
      codeUrl: '',
      loginForm: {
        username: "",
        password: "",
        code:"",
        uuid:"",
      },
      responseResult: [],
    };
  },
  created () {
    this.getCode()
  },
  methods: {
    getCode () {
      getCodeImg().then(res => {
        this.codeUrl = 'data:image/png;base64,' + res.data.data.img
        console.log(res)
        console.log(res.data.data.uuid);
        this.loginForm.uuid = res.data.data.uuid
      })
    },
    login() {
      var _this = this;
      userLogin({
        username: this.loginForm.username,
        password: this.loginForm.password,
        code:this.loginForm.code,
        uuid:this.loginForm.uuid,
      }).then((resp) => {
        let code=resp.data.code;
        if(code===200){
          let data=resp.data.data;
          let token=data.token;
          let user=data.user;
          //存储token
          _this.$store.commit('SET_TOKENN', token);
          //存储user，优雅一点的做法是token和user分开获取
          _this.$store.commit('SET_USER', user);
          console.log(_this.$store.state.token);
          var path = this.$route.query.redirect
          this.$router.replace({path: path === '/' || path === undefined ? '/' : path})
        }else{
          this.$message.error("登录失败");
        }
      }).catch((err) => {
          this.$message.error("登录异常！")
          console.log(err);
        });
    },
    register (){
      this.$router.push('/register');
    }
  },
};
</script>

<style scoped>


#login-page {
  background: url("../assets/img/pic2.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  /* margin: -8px; */
  /* border-width: -8px; */
  
}
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 50px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
.login-code {
  width: 40%;
  height: 32px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.login-code-img {
  height: 32px;
}
</style>

