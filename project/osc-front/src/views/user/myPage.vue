<template>
  <div>
    <div>
      <div class="PersonTop_img">
        <el-upload ref="upload" action="/api/file/upload" accept="image/*" :data="{ id: this.user.id }"
          :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload"
          :show-file-list="false">
          <img v-if="avatorUrl" :src="avatorUrl">
        </el-upload>
      </div>
      <div class="PersonTop_text">
        <div style="line-height: 40px">
          <div style="font-weight: bold; color: #3967FF">
            <span> {{ user.name }} </span>
          </div>
          <!-- <div style="color: #99a9bf">
            <span>ID: {{ user.id }} </span>
          </div> -->
          <div style="color: #b3d4fc">
            <span>学号： {{ user.code }} </span>
          </div>
        </div>
      </div>
    </div>
    <el-divider></el-divider>
    <div style="margin-right: 40px; margin-left: 30px">
      <div>
        <el-descriptions title="个人" :column="2" border>
          <template slot="extra">
            <el-button type="primary" size="small" icon="el-icon-edit" @click="showEditDialog">修改个人信息</el-button>
            <el-button type="danger" size="small" icon="el-icon-warning
" @click="showEditPasswordDialog">修改密码</el-button>
          </template>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-user"></i>
              学号
            </template>
            {{ user.code }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-s-custom"></i>
              姓名
            </template>
            {{ user.name }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-male" v-if="user.gender === 1"></i>
              <i class="el-icon-female" v-if="user.gender === 0"></i>
              性别
            </template>
            {{ user.gender === 1 ? '男' : '女' }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              手机号码
            </template>
            {{ user.phone }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-location-outline"></i>
              地区
            </template>
            {{ user.province }} {{ user.city }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-s-home"></i>
              学院
            </template>
            {{ user.academy }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-s-management"></i>
              专业
            </template>
            {{ user.major }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-reading"></i>
              班级
            </template>
            {{ user.clazz }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-suitcase-1"></i>
              职位
            </template>
            {{
          user.duty === 0 ? '成员' : user.duty === 1 ? '副部长' : user.duty === 2 ? '部长' : user.duty === 3 ? '副社长' : '社长'
        }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-office-building"></i>
              部门
            </template>
            {{
            user.department === 0 ? '运营部' : user.department === 1 ? '技术部' : user.department === 2 ? '宣传部' : '其它'
          }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-s-custom"></i>
              角色
            </template>
            {{ user.role === 0 ? '普通成员' : user.role === 1 ? '管理员' : '超管' }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-s-opportunity"></i>
              身份证号
            </template>
            {{ user.idCard }}
          </el-descriptions-item>
          <el-descriptions-item>

            <template slot="label">
              <i class="el-icon-collection-tag"></i>
              个人介绍
            </template>
            {{ user.introduction }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>

    <!--修改用户的对话框-->
    <el-dialog title="修改个人信息" :visible.sync="editDialogVisible1" width="65%">
      <!--内容主体区域-->
      <el-form :model="this.editUser" label-width="80px" :inline="true">

        <el-form-item label="姓名" prop="name">
          <el-input v-model="editUser.name"></el-input>
        </el-form-item>
        <el-form-item label="学号" prop="code">
          <el-input v-model="editUser.code"></el-input>
        </el-form-item>
        <!-- <el-form-item label="状态" prop="status">
          <el-select v-model="editUser.status" placeholder="请选择状态">
            <el-option v-for="item in optionsStatus" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="editUser.idCard"></el-input>
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input v-model="editUser.phone"></el-input>
        </el-form-item>
        <el-form-item label="班级" prop="clazz">
          <el-input v-model="editUser.clazz"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="editUser.major"></el-input>
        </el-form-item>
        <el-form-item label="学院" prop="academy">
          <el-input v-model="editUser.academy"></el-input>
        </el-form-item>

        <el-form-item label="地区" prop="place">
          <el-cascader v-model="editUser.province" :options="optionsnative_place" />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="editUser.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="部门" prop="department" style="width: 100%;">
          <el-radio-group v-model="editUser.department">
            <el-radio :label="2">宣传部</el-radio>
            <el-radio :label="1">技术部</el-radio>
            <el-radio :label="0">运营部</el-radio>
            <el-radio :label="3">无</el-radio>
          </el-radio-group>
        </el-form-item>


        <el-form-item label="个人介绍" prop="introduction">
          <el-input type="textarea" v-model="editUser.introduction" style="width: 800px;"></el-input>
        </el-form-item>

      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="edit">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改密码 -->
    <el-dialog title="修改密码" :visible.sync="editDialogVisible2" width="25%">
      <el-form label-width="80px" style="width:300px;">
        <el-form-item label="密码">
          <el-input placeholder="请输入密码" v-model="editUser.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input placeholder="请再次输入密码" v-model="passwordValid" show-password></el-input>
        </el-form-item>
        <el-form-item label="密码强度">
           <div class="streng-box">
            <div class="schedule">
              <span class="bg-span p-a"></span>
              <span class="bg-span p-b"></span>
              <span class="bg-span p-c"></span>
              <span v-if="strength >= 1" class="low p-a"></span>
              <span v-if="strength >= 2" class="centre p-b"></span>
              <span v-if="strength >= 3" class="tall p-c"></span>
            </div>
            <div class="schedule-text">
              <span class="low">弱</span>
              <span class="centre">中</span>
              <span class="tall">强</span>
            </div>
              
          </div>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="editPassword">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { currentUser, userUpdate } from "@/api/user";
import defaultAvatar from "@/assets/img/avator.jpg";
import { pcTextArr } from "element-china-area-data";
// import PasswordStrength from "@/components/drag/PasswordStrength";


export default {

  computed: {
    // 密码强度 0~3  0无强度、1低强度、2中强度、3高强度
    strength({ }) { //验证密码强度的函数
      var val = this.editUser.password || this.user.password;
      var lv = 0; //初始化提示消息为空
      if (val.match(/[a-z]/g)) { lv++; } //验证是否包含字母
      if (val.match(/[0-9]/g)) { lv++; }  // 验证是否包含数字
      if (val.match(/(.[^a-z0-9])/g)) { lv++; } //验证是否包含字母，数字，字符
      if (val.length < 6) { lv = 1; } //如果密码长度小于6位，提示消息为弱
      if (lv > 3) { lv = 3; }
      return lv
    }
  },
  data() {
    return {
      avatorUrl: this.$store.state.user.avator === '' ? defaultAvatar : "/api/file/download?fileName=" + this.$store.state.user.avator,
      user: this.$store.state.user,
      editDialogVisible1: false, // 控制修改用户信息对话框是否显示
      editDialogVisible2: false, // 控制修改用户信息对话框是否显示
      optionsnative_place: pcTextArr,
      editUser: {},
      preEditUser: {},
      passwordValid: "",
    };
  },
  created() {

    this.alertPassword();
  },
  methods: {
    beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 <5;
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 5MB!');
        }
        return  isLt2M;
      },
    alertPassword() {
      if (this.strength <= 1) {
        this.$message.error("检测到您的密码为弱密码，请及时修改密码");
      }
    },
    handleAvatarSuccess(response) {

      currentUser().then(res => {
        if (res.data.code === 200) {
          this.$store.commit('SET_USER', res.data.data);
          this.user = this.$store.state.user;
          this.avatorUrl = "/api/file/download?fileName=" + this.$store.state.user.avator;
        } else {
          console.error(res.data.message);
        }
      })
    },
    // 监听 修改用户状态
    showEditDialog() {
      // 将user复制给editUser
      this.editUser = { ...this.user };
      this.editUser.province = [this.editUser.province, this.editUser.city];
      // 将user复制给preEditUser
      this.preEditUser = { ...this.user };


      this.editDialogVisible1 = true;
    },
    // 监听 修改用户状态
    showEditPasswordDialog() {
      // 将user复制给editUser
      this.editUser = { ...this.user };
      this.editUser.province = [this.editUser.province, this.editUser.city];
      // 将user复制给preEditUser
      this.preEditUser = { ...this.user };

      this.editDialogVisible2 = true;
      this.passwordValid = ""
    },
    editPassword() {
      if (this.editUser.password !== this.passwordValid) {
        this.$message.error("两次输入的密码不一致，请检查后重新输入！");
        return;
      }
      this.edit();

    },
    edit() {
      const place = this.editUser.province;
      this.editUser.province = place[0];
      this.editUser.city = place[1];
      userUpdate(this.editUser)
        .then((res) => {
          if (res.data.code === 200) {
            this.editDialogVisible1 = false;
            this.editDialogVisible2 = false;
            this.$store.commit('SET_USER', res.data.data);
            this.user = this.$store.state.user;
            this.$message({
              message: "修改信息成功",
              type: "success",
            });
          } else {
            this.$message.error("修改信息失败:" + res.data.description);
            // 重置修改信息表
            this.editUser = { ...this.preEditUser }
          }
        })
        .catch((err) => {
          this.$message.error("修改信息异常");
          console.loge(err);
        });
    }
  },

};
</script>

<style scoped>
.PersonTop_img {
  width: 150px;
  height: 120px;
  background-color: #8c939d;
  margin-right: 24px;
  margin-left: 20px;
  overflow: hidden;
  border-radius: 20px;
  float: left;
}

.PersonTop_img img {
  width: 150px;
  height: 120px;
  border-radius: 20px;
}

.PersonTop_text {
  height: 120px;
  width: 880px;
  display: flex;
}

.user_num>div {
  text-align: center;
  border-right: 1px dotted #999;
  box-sizing: border-box;
  width: 80px;
  height: 40px;
  line-height: 20px;
}

.el-menu-item>span {
  font-size: 16px;
  color: #999;
}

.el-icon-check:before {
  content: "\e6da";
}


.streng-box {
  width: 100%;
}

.schedule {
  position: relative;
}

.schedule span {
  width: 45px;
  height: 8px;
  border-radius: 2px;
}

.schedule .bg-span {
  background-color: #EBEEF5;
}

.schedule .p-a {
  position: absolute;
  top: 0;
  left: 0;
}

.schedule .p-b {
  position: absolute;
  top: 0;
  left: 50px;
}

.schedule .p-c {
  position: absolute;
  top: 0;
  left: 100px;
}

.schedule .low {
  z-index: 9;
  background: #F56C6C;
}

.schedule .centre {
  z-index: 9;
  background: #E6A23C;
}

.schedule .tall {
  z-index: 9;
  background: #67C23A;
}

.schedule-text {
  padding-top: 10px;
  font-size: 13px;

}

.schedule-text span {
  width: 45px;
  height: 20px;
  font-weight: 400px;
  display: inline-block;
  line-height: 20px;
  text-align: center;
  margin-right: 5px;
}



.schedule-text .low {
  color: #F56C6C;
}

.schedule-text .centre {
  color: #E6A23C;
}

.schedule-text .tall {
  color: #67C23A;
}
</style>
