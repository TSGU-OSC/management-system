<template>
  <div>
    <div>
      <div class="PersonTop_img" >
        <el-upload
          ref="upload"
          action="/file/upload"
          accept="image/*"
          :data="{id: this.user.id}"
          :on-success="handleAvatarSuccess"
          :show-file-list="false">
          <img v-if="avatorUrl" :src="avatorUrl">
        </el-upload>
      </div>
      <div class="PersonTop_text">
        <div style="line-height: 40px">
          <div style="font-weight: bold; color: #3967FF">
            <span> {{ user.name }} </span>
          </div>
          <div style="color: #99a9bf">
            <span>ID: {{ user.id }} </span>
          </div>
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
                <el-button type="primary" size="small" @click="showEditDialog">编辑</el-button>
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
                  <i class="el-icon-male" v-if="user.gender===1"></i>
                  <i class="el-icon-female" v-if="user.gender===0"></i>
                  性别
                </template>
                {{ user.gender===1?'男':'女' }}
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
                {{ user.duty===0?'成员':user.duty===1?'副部长':user.duty===2?'部长':user.duty===3?'副社长':'社长' }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-office-building"></i>
                  部门
                </template>
                {{ user.department===0?'运营部':user.department===1?'技术部':user.department===2?'宣传部':'其它' }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  角色
                </template>
                {{ user.role===0?'0普通成员':user.role===1?'管理员':'超管' }}
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
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%">
      <!--内容主体区域-->
      <el-form :model="this.editUser" label-width="70px">
        <el-form-item label="学号" prop="code">
          <el-input v-model="editUser.code"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editUser.name"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="editUser.status" placeholder="请选择状态">
            <el-option v-for="item in optionsStatus" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="editUser.gender" placeholder="请选择性别">
            <el-option v-for="item in optionsGender" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
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
        <el-form-item label="个人介绍" prop="introduction">
          <el-input v-model="editUser.introduction"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="duty">
          <el-select v-model="editUser.duty" placeholder="请选择职位">
            <el-option v-for="item in optionsDuty" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-select v-model="editUser.department" placeholder="请选择部门">
            <el-option v-for="item in optionsDepartment" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="editUser.role" placeholder="请选择角色">
            <el-option v-for="item in optionsRole" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="edit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import {currentUser, userUpdate} from "@/api/user";
import defaultAvatar from "@/assets/img/avator.jpg";

export default {
  data() {
    return {
      avatorUrl: this.$store.state.user.avator===''?defaultAvatar:"/file/download?fileName=" + this.$store.state.user.avator,
      user: this.$store.state.user,
      editDialogVisible: false, // 控制修改用户信息对话框是否显示
      // 性别选项
      optionsGender: [{
        value: '1',
        label: '男'
      }, {
        value: '0',
        label: '女'
      }
      ],
      // 职位选项
      optionsDuty: [{
        value: '0',
        label: '成员'
      }, {
        value: '1',
        label: '副部长'
      }, {
        value: '2',
        label: '部长'
      }, {
        value: '3',
        label: '副社长'
      }, {
        value: '4',
        label: '社长'
      }
      ],
      // 部门选项
      optionsDepartment: [{
        value: '0',
        label: '运营部'
      }, {
        value: '1',
        label: '技术部'
      }, {
        value: '2',
        label: '宣传部'
      },{
        value: null,
        label: '无'
      }
      ],
      // 角色选项
      optionsRole: [{
        value: '0',
        label: '普通成员'
      }, {
        value: '1',
        label: '管理员'
      }, {
        value: '2',
        label: '超管'
      }
      ],
      // 账号状态选项
      optionsStatus: [{
        value: '0',
        label: '正常'
      }, {
        value: '1',
        label: '封禁'
      }],
      editUser: {},
      preEditUser: {},
    };
  },
  mounted() {

  },
  methods: {
    handleAvatarSuccess() {
      currentUser().then(res=>{
        if (res.data.code === 200) {
          this.$store.commit('SET_USER', res.data.data);
          this.user=this.$store.state.user;
          this.avatorUrl= "/file/download?fileName=" + this.$store.state.user.avator;
        } else {
          console.error(res.data.message);
        }
      })
    },
    // 监听 修改用户状态
    showEditDialog() {
      this.editDialogVisible = true;
      // 将user复制给editUser
      this.editUser={...this.user};
      // 将user复制给preEditUser
      this.preEditUser={...this.user};
    },
    edit(){
      userUpdate(this.editUser)
        .then((res) => {
          if (res.data.code === 200) {
            this.editDialogVisible = false;
            this.$store.commit('SET_USER', res.data.data);
            this.user=this.$store.state.user;
            this.$message({
              message: "修改用户成功",
              type: "success",
            });
          } else {
            this.$message.error("修改用户失败:"+res.data.description);
            // 重置修改信息表
            this.editUser={...this.preEditUser}
          }
        })
        .catch((err) => {
          this.$message.error("修改用户异常");
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

.user_num > div {
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
</style>
