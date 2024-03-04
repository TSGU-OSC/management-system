<template>
  <body id="login-page">
  <el-form class="login-container" label-position="left" label-width="0px">
    <h3 class="login_title">开源鸿蒙社团管理系统</h3>
  
    <el-form-item>
      <el-input
        type="text"
        v-model="loginForm.code"
        auto-complete="off"
        placeholder="请输入学号"
      ></el-input>
    </el-form-item>
    <el-form-item>
      <el-input
        type="password"
        v-model="loginForm.password"
        auto-complete="off"
        placeholder="请输入密码"
        show-password
      ></el-input>
    </el-form-item>
    <el-form-item prop="verifyCode">
      <el-input
        v-model="loginForm.verifyCode"
        type="text"
        auto-complete="off"
        placeholder="请输入验证码,30秒有效"
        style="width: 60%"
        @keyup.enter.native="login"
      >
        <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
      </el-input>
      <div class="login-code" style="text-align: right;">
        <img :src="codeUrl" class="login-code-img" @click="getCode">
      </div>
    </el-form-item>
    <el-form-item>
      <el-button
        type="primary"
        style="width: 50%; border: none;border-radius: 10px;"
        @click="login"
        icon="el-icon-s-promotion"
      >登录
      </el-button
      >
      <el-button
        type="primary"
        style="width: 40%; border-radius: 10px;"
        @click="register"
        icon="el-icon-user-solid
"
      >注册
      </el-button
      >
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
import {getCodeImg, userLogin} from "@/api/user";

export default {
  name: "Login",
  data() {
    return {
      codeUrl: '',
      loginForm: {
        code: "",
        password: "",
        verifyCode: "",
        verifyCodeKey: "",
      },
      responseResult: [],
    };
  },
  created() {
    this.getCode()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = res.data.data.image
        this.loginForm.verifyCodeKey = res.data.data.key
      })
    },
    register(){
      this.$router.replace('/register') 
    },
    login() {
      var _this = this;
      userLogin({
        code: this.loginForm.code,
        password: this.loginForm.password,
        verifyCode: this.loginForm.verifyCode,
        verifyCodeKey: this.loginForm.verifyCodeKey,
      }).then((resp) => {
        let code = resp.data.code;
        if (code === 200) {
          this.$message.success("登录成功");
          let user = resp.data.data;
          var path = this.$route.query.redirect
          this.$router.replace({path: path === '/' || path === undefined ? '/' : path})
        } else {
          this.$message.error(resp.data.description);
          console.log(resp);
        }
      }).catch((err) => {
        console.log(err);
      });
    },
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

