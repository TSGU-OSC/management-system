<template>

    <body id="login-page">
        <el-form class="login-container" label-position="left" label-width="0px">
            <div>
                <el-button type="info" style="width: 10%; " @click="goBack" icon="el-icon-back">返回
                </el-button>
                <h3 class="login_title">注册</h3>
            </div>



            <!--内容主体区域-->
            <el-form :model="this.userForm" label-width="70px" :inline="true">
                <el-form-item label="姓名" prop="name" :rules="[
                    { required: true, message: '姓名不能为空', trigger: 'blur' },
                ]">
                    <el-input placeholder="请输入姓名" v-model="userForm.name"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password"  :rules="[
                    { required: true, message: '密码不能为空', trigger: 'blur' },
                ]">
                    <el-input placeholder="密码默认为12345678" show-password v-model="userForm.password"></el-input>
                </el-form-item>
                <el-form-item label="学号" prop="code"  :rules="[
                    { required: true, message: '学号不能为空', trigger: 'blur' },
                ]">
                    <el-input placeholder="请输入学号" v-model="userForm.code"></el-input>
                </el-form-item>
                <el-form-item label="身份证号" prop="idCard" >
                    <el-input placeholder="请输入身份证号(非必填)" v-model="userForm.idCard"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input placeholder="请输入手机号(非必填)" v-model="userForm.phone"></el-input>
                </el-form-item>
                <el-form-item label="班级" prop="clazz"  :rules="[
                    { required: true, message: '班级不能为空', trigger: 'blur' },
                ]">
                    <el-input placeholder="请输入班级" v-model="userForm.clazz"></el-input>
                </el-form-item>
                <el-form-item label="专业" prop="major":rules="[
                    { required: true, message: '专业不能为空', trigger: 'blur' },
                ]" >
                    <el-input placeholder="请输入专业" v-model="userForm.major"></el-input>
                </el-form-item>
                <el-form-item label="学院" prop="academy" :rules="[
                    { required: true, message: '学院不能为空', trigger: 'blur' },
                ]">
                    <el-input placeholder="请输入学院" v-model="userForm.academy"></el-input>
                </el-form-item>
                <el-form-item label="地区" prop="place" :rules="[
                    { required: true, message: '地区不能为空', trigger: 'blur' },
                ]">
                    <el-cascader v-model="userForm.province" :options="optionsnative_place" />
                </el-form-item>
                <div>
                    <el-form-item label="性别" prop="gender" style="width: 50%;" :rules="[
                    { required: true, message: '地区不能为空', trigger: 'blur' },
                ]">
                        <el-radio-group v-model="userForm.gender">
                            <el-radio :label="1">男</el-radio>
                            <el-radio :label="0">女</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="部门" prop="department" style="width: 50%;">
                        <el-radio-group v-model="userForm.department">
                            <el-radio :label="2">宣传部</el-radio>
                            <el-radio :label="1">技术部</el-radio>
                            <el-radio :label="0">运营部</el-radio>
                            <!-- <el-radio :label="3">无</el-radio> -->
                        </el-radio-group>
                    </el-form-item>
                </div>

                <el-form-item label="个人介绍" prop="introduction">
                    <el-input placeholder="展示一下自己吧~" type="textarea" v-model="userForm.introduction"
                        style="width: 880px;" maxlength="300" show-word-limit rows="5"></el-input>
                </el-form-item>




            </el-form>
            <div>
                <el-button type="primary"
                    style="width: 30%; border: none;border-radius: 20px;display:block;margin:0 auto" @click="register"
                    icon="el-icon-user">注册
                </el-button>
            </div>
        </el-form>

    </body>
</template>

<script>
import { userRegister } from "@/api/user";
import { pcTextArr } from "element-china-area-data";
export default {
    name: "Register",
    data() {
        return {
            optionsnative_place: pcTextArr,

            responseResult: [],
            // 注册用户信息
            userForm: {
                name: "",
                password: "",
                gender: "",
                code: "",
                clazz: "",
                major: "",
                academy: "",
                department: "",
                province: "",
                city: "",
                introduction: "",
                idCard: "",
                phone: ""
            },
        };
    },
    created() {

    },
    methods: {
        register() {
            const place = this.userForm.province;
            this.userForm.province = place[0];
            this.userForm.city = place[1];
            userRegister(this.userForm).then((res) => {
                if (res.data.code === 200) {
                    this.$message({
                        message: "注册用户成功，请等待管理员同意方可正常登录",
                        type: "success",
                    });
                    this.$router.replace('/login')
                } else {
                    this.$message.error("注册用户失败:" + res.data.description);
                    this.userForm.province = "";
                    this.userForm.city = "";
                }
            })
                .catch((err) => {
                    this.$message.error("注册用户异常");
                    console.log(err);
                });
        },
        goBack() {
            this.$router.replace('/login')
        },
    },

}

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
    width: 80%;
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