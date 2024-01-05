<template>
    <div id="register-page">
      
        <el-form class="register-container" :model="userForm" label-position="left" label-width="100px" >  <h3 class="login_title" style="text-align: center;margin-top: 10px;">开源鸿蒙社团成员管理系统注册</h3>
            <!--内容主体区域-->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="userForm.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio v-model="userForm.gender" label="男">男</el-radio>
          <el-radio v-model="userForm.gender" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="学号" prop="code">
          <el-input v-model="userForm.code"></el-input>
        </el-form-item>
        <el-form-item label="班级" prop="clazz">
          <el-input v-model="userForm.clazz"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="userForm.major"></el-input>
        </el-form-item>
        <el-form-item label="学院" prop="academy">
          <el-input v-model="userForm.academy"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="duty">
          <el-input v-model="userForm.duty"></el-input>
        </el-form-item>
        <el-form-item label="个人介绍" prop="introduction">
          <el-input v-model="userForm.introduction"></el-input>
       </el-form-item>
        <el-form-item style="margin-bottom: 10px;">
            <el-button type="primary" @click="register">立即注册</el-button>
            <el-button @click="goBack">返回登录页面</el-button>
        </el-form-item>
       </el-form>
     </div>
</template>
<script> 
import {userRegister} from '@/api/user';
export default {
   
    data() {
        return {
            userForm: {
                //用户
                username: "",
                password: "",
                gender: "",
                code: "",
                status: "",
                clazz: "",
                major: "",
                academy: "",
                duty: "",
                introduction: "",
            },
        }
    },
    methods: {
        goBack() {
            this.$router.push('/login');
        },
        register() {
            userRegister(this.userForm)
                .then((res) => {
                    if (res.data.code === 200) {
                        this.$message({
                            message: "注册用户成功",
                            type: "success",
                        });
                        this.$router.push('/login');
                    } else {
                        this.$message.error("注册用户失败");
                    }
                })
                .catch((err) => {
                    this.$message.error("注册用户异常,请正确填写字段！")
                    console.log(err);
                });
        }
    }

}
</script>
<style>
#register-page {
    background: url("../../assets/img/pic2.jpg") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
    /* margin: -8px; */
}

.register-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 10px auto;
    width: 600px;
    padding: 35px 35px ;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}
</style>