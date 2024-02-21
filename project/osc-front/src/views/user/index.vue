<template>
  <div>
    <!--Layout布局-->
    <el-row>
      <el-col :span="24">
        <el-row :gutter="25">
          <el-col :span="4">
            <!--搜索区域-->
            <el-input placeholder="请输入成员姓名" v-model="queryInfo.name" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入成员学号" v-model="queryInfo.code" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4">
            <el-input placeholder="请输入成员班级" v-model="queryInfo.clazz" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4">
            <el-input placeholder="请输入成员专业" v-model="queryInfo.major" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入成员学院" v-model="queryInfo.academy" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入成员手机号" v-model="queryInfo.phone" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4">
            <el-input placeholder="请输入成员所在省" v-model="queryInfo.province" clearable
                      @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4">
            <el-input placeholder="请输入成员所在市" v-model="queryInfo.city" clearable @clear="getUserList">
            </el-input>
          </el-col>
        </el-row>
        <el-row :gutter="25">
          <el-col :span="5">
            <el-select v-model="queryInfo.gender" placeholder="请选择性别">
              <el-option v-for="item in optionsGender" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            <el-select v-model="queryInfo.duty" placeholder="请选择职位">
              <el-option v-for="item in optionsDuty" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            <el-select v-model="queryInfo.role" placeholder="请选择角色">
              <el-option v-for="item in optionsRole" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            <el-select v-model="queryInfo.status" placeholder="请选择状态">
              <el-option v-for="item in optionsStatus" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="2.5">
            <el-button type="primary" icon="el-icon-search" @click="getUserList">搜索</el-button>
          </el-col>
          <el-col :span="2.5">
            <el-button type="primary" @click="resetUserList">刷新</el-button>
          </el-col>

        </el-row>

        <el-row :gutter="25">
          <el-col :span="2.5">
            <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
          </el-col>
        </el-row>
      </el-col>

      <el-col :span="24">
        <!--表格-->
        <el-table :data="userList" border stripe>
          <el-table-column prop="id" label="ID"></el-table-column>
          <el-table-column prop="avator" label="头像">
            <template slot-scope="scope">
              <img :src="scope.row.avator" width="70" height="70"/>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="姓名"></el-table-column>
          <el-table-column prop="gender" label="性别" width="45"></el-table-column>
          <el-table-column prop="idCard" label="身份证号"></el-table-column>
          <el-table-column prop="code" label="学号"></el-table-column>
          <el-table-column prop="clazz" label="班级" width="45"></el-table-column>
          <el-table-column prop="major" label="专业"></el-table-column>
          <el-table-column prop="academy" label="学院"></el-table-column>
          <el-table-column prop="introduction" label="个人介绍"></el-table-column>
          <el-table-column prop="phone" label="手机号"></el-table-column>
          <el-table-column prop="province" label="所在省"></el-table-column>
          <el-table-column prop="city" label="所在市"></el-table-column>
          <el-table-column prop="duty" label="职位"></el-table-column>
          <el-table-column prop="role" label="角色"></el-table-column>
          <el-table-column prop="status" label="状态" width="80">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.status === 0">正常</el-tag>
              <el-tag type="danger" v-if="scope.row.status === 1">封禁</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间"></el-table-column>
          <el-table-column prop="createUser" label="创建者ID"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间"></el-table-column>
          <el-table-column prop="updateUser" label="更新者ID"></el-table-column>
          <el-table-column label="操作" width="128">
            <!-- 作用域插槽 -->
            <template slot-scope="scope">
              <!--修改按钮-->
              <el-button type="primary" size="mini" icon="el-icon-edit" @click="showEditDialog(scope.row)"></el-button>
              <!-- 删除按钮-->
              <el-button type="danger" size="mini" icon="el-icon-delete"
                         @click="removeUserById(scope.row.id)"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <!--分页区域-->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="queryInfo.pageNo" :page-sizes="[1, 2, 5, 10]" :page-size="queryInfo.pageSize"
                     layout="sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-row>
    <!--添加用户的对话框-->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!--内容主体区域-->
      <el-form :model="userForm" label-width="70px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="userForm.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="userForm.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio v-model="userForm.gender" label="1">男</el-radio>
          <el-radio v-model="userForm.gender" label="0">女</el-radio>
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
          <el-select v-model="userForm.duty" placeholder="请选择职位">
            <el-option v-for="item in optionsDuty" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.role" placeholder="请选择角色">
            <el-option v-for="item in optionsRole" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>
    <!--修改用户的对话框-->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%">
      <!--内容主体区域-->
      <el-form :model="editForm" label-width="70px">
        <el-form-item label="学号" prop="code">
          <el-input v-model="editForm.code"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="editForm.status" placeholder="请选择状态">
            <el-option v-for="item in optionsStatus" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="editForm.gender" placeholder="请选择性别">
            <el-option v-for="item in optionsGender" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="clazz">
          <el-input v-model="editForm.clazz"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="editForm.major"></el-input>
        </el-form-item>
        <el-form-item label="学院" prop="academy">
          <el-input v-model="editForm.academy"></el-input>
        </el-form-item>
        <el-form-item label="个人介绍" prop="introduction">
          <el-input v-model="editForm.introduction"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="duty">
          <el-select v-model="editForm.duty" placeholder="请选择职位">
            <el-option v-for="item in optionsDuty" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="editForm.role" placeholder="请选择角色">
            <el-option v-for="item in optionsRole" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!--底部按钮区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUser">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {userAdd, userDelete, userList, userUpdate} from "@/api/user";

export default {
  data() {
    return {
      optionsGender: [{
        value: '1',
        label: '男'
      }, {
        value: '0',
        label: '女'
      }
      ],
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
      optionsStatus: [{
        value: '0',
        label: '正常'
      }, {
        value: '1',
        label: '封禁中'
      }],
      userList: [], // 用户列表
      // 获取用户列表的参数对象
      queryInfo: {
        // 查询参数
        code: "",
        name: "",
        gender: "",
        province: "",
        phone: "",
        city: "",
        clazz: "",
        major: "",
        academy: "",
        duty: "",
        status: "",
        role: ""

      },
      avatorList: [],
      addDialogVisible: false, // 控制添加用户对话框是否显示
      userForm: {
        //用户
        name: "",
        password: "",
        gender: "",
        code: "",
        clazz: "",
        major: "",
        academy: "",
        duty: "",
        role: "",
      },
      editDialogVisible: false, // 控制修改用户信息对话框是否显示
      editForm: {
        name: "",
        gender: "",
        code: "",
        clazz: "",
        major: "",
        academy: "",
        introduction: "",
        phone: "",
        province: "",
        city: "",
        duty: "",
        role: "",
        status: "",
      },
      multipleSelection: [], ids:
        [],
    }
      ;
  },
  created() {
    // 生命周期函数
    this.getUserList();
  }
  ,
  methods: {
    resetUserList() {
      for (let key in this.queryInfo) {
        this.queryInfo[key] = "";
      }
      userList(this.queryInfo)
        .then((res) => {
          if (res.data.code === 200) {
            //用户列表
            this.userList = res.data.data;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
    ,
    getUserList() {
      userList(this.queryInfo)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            //用户列表
            this.userList = res.data.data;
            this.userList.forEach(item => {
              let fileName = item.avator;
              item.avator = "/file/download?fileName=" + fileName
              this.avatorList.push(item.avator)
            })
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
    ,
    // 监听 pageSize 改变的事件
    handleSizeChange(newSize) {
      // console.log(newSize)
      this.queryInfo.pageSize = newSize;
      // 重新发起请求用户列表
      this.getUserList();
    }
    ,
    // 监听 当前页码值 改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage)
      this.queryInfo.pageNo = newPage;
      // 重新发起请求用户列表
      this.getUserList();
    }
    ,
    //添加用户
    addUser() {
      userAdd(this.userForm)
        .then((res) => {
          if (res.data.code === 200) {
            this.addDialogVisible = false;
            this.getUserList();
            this.$message({
              message: "添加用户成功",
              type: "success",
            });
            for (let key in this.userForm) {
              this.userForm[key] = "";
            }
          } else {
            this.$message.error("添加用户失败");
          }
        })
        .catch((err) => {
          this.$message.error("添加用户异常,请正确填写字段！")
          console.log(err);
        });
    }
    ,

    // 监听 修改用户状态
    showEditDialog(userinfo) {
      this.editDialogVisible = true;
      console.log("userinfo----", userinfo);
      // this.editForm = userinfo;
      Object.assign(this.editForm, userinfo)
      console.log("edit----", this.editForm)
    }
    ,
    //修改用户
    editUser() {
      userUpdate(this.editForm)
        .then((res) => {
          if (res.data.code === 200) {
            this.editDialogVisible = false;
            this.getUserList();
            this.$message({
              message: "修改用户成功",
              type: "success",
            });
          } else {
            this.$message.error("修改用户失败");
          }
        })
        .catch((err) => {
          this.$message.error("修改用户异常");
          console.loge(err);
        });
    }
    ,
    // 根据ID删除对应的用户信息
    async removeUserById(id) {
      // 弹框 询问用户是否删除
      const confirmResult = await this.$confirm(
        "此操作将永久删除该用户, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);
      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消删除，则返回值为字符串 cancel
      // console.log(confirmResult)
      if (confirmResult === "confirm") {
        //删除用户
        userDelete(id)
          .then((res) => {
            if (res.data.code === 200) {
              this.getUserList();
              this.$message({
                message: "删除用户成功",
                type: "success",
              });
            } else {
              this.$message.error("删除用户失败");
            }
          })
          .catch((err) => {
            this.$message.error("删除用户异常");
            console.log(err);
          });
      }
    }
    ,
  }
  ,
};
</script>

<style>
.el-row {
  margin-bottom: 20px;
}

.el-col {
  border-radius: 4px;
}

.el-card {
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1) !important;
  height: 60pt;
}
</style>
