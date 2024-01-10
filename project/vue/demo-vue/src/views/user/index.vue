<template>
  <div>
    <!--Layout布局-->
    <el-row>
      <el-col :span="24">
        <el-row :gutter="25">
          <el-col :span="4">
            <!--搜索区域-->
            <el-input placeholder="请输入成员姓名" v-model="queryInfo.userDTO.username" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入成员学号" v-model="queryInfo.userDTO.code" clearable @clear="getUserList">
            </el-input>
          </el-col>

          <el-col :span="4">
            </el-input>
            <el-input placeholder="请输入成员班级" v-model="queryInfo.userDTO.clazz" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4">
            </el-input>
            <el-input placeholder="请输入成员专业" v-model="queryInfo.userDTO.major" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5">
            </el-input>
            <el-input placeholder="请输入成员学院" v-model="queryInfo.userDTO.academy" clearable @clear="getUserList">
            </el-input>
          </el-col>
        </el-row>
          <el-row :gutter="25">
            <el-col :span="5">
              </el-input>
              <el-input placeholder="请输入成员职位" v-model="queryInfo.userDTO.duty" clearable @clear="getUserList">
              </el-input>
            </el-col>
            <el-col :span="5">
              <el-select v-model="queryInfo.userDTO.gender" placeholder="请选择性别">
                <el-option v-for="item in optionsGender" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="5">
              <el-select v-model="queryInfo.userDTO.status" placeholder="请选择状态">
                <el-option v-for="item in optionsStatus" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="2.5">
              <el-button type="primary" icon="el-icon-search" @click="getUserList">搜索</el-button>
            </el-col>
            <el-col :span="2.5">
              <el-button type="primary" @click="resetUserList">重置</el-button>
            </el-col>
        
          </el-row>

        <el-row :gutter="25">
          <el-col :span="2.5">
            <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
          </el-col>
          <el-col :span="2.5">
            <el-button type="danger" @click="batchDeleteUser">批量删除</el-button>
          </el-col>
        </el-row>
      </el-col>

      <el-col :span="24">
        <!--表格-->
        <el-table :data="userList" border stripe @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="45"> </el-table-column>
          <el-table-column type="index" label="序号"></el-table-column>
          <el-table-column prop="username" label="姓名"></el-table-column>
          <el-table-column prop="gender" label="性别" width="45"></el-table-column>
          <el-table-column prop="code" label="学号"></el-table-column>
          <el-table-column prop="clazz" label="班级" width="45"></el-table-column>
          <el-table-column prop="major" label="专业"></el-table-column>
          <el-table-column prop="academy" label="学院"></el-table-column>
          <el-table-column prop="introduction" label="个人介绍"></el-table-column>
          <el-table-column prop="duty" label="职位"></el-table-column>
          <el-table-column prop="status" label="状态" width="80">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.status == 1">正常</el-tag>
              <el-tag type="danger" v-if="scope.row.status == 0">封禁</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间"></el-table-column>
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
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-row>
    <!--添加用户的对话框-->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!--内容主体区域-->
      <el-form :model="userForm" label-width="70px">
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
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="editForm.status" placeholder="请选择状态">
                <el-option v-for="item in optionsStatus" :key="item.value" :label="item.label" :value="item.value">
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
          <el-input v-model="editForm.duty"></el-input>
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
import { userList, userAdd, userUpdate, userDelete, userBatchDelete } from "@/api/user";
export default {
  data() {
    return {
      optionsGender: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }
    ],
      optionsStatus:[{
        value: '1',
        label: '正常'
      }, {
        value: '0',
        label: '封禁中'
      }],
      userList: [], // 用户列表
      total: 0, // 用户总数
      // 获取用户列表的参数对象
      queryInfo: {
        userDTO: {// 查询参数
          username: "",
          code: "",
          gender: "",
          duty: "",
          clazz: "",
          academy: "",
          major: "", 
          status: "",
        },
        pageNo: 1, // 当前页码
        pageSize: 5, // 每页显示条数
      },
      addDialogVisible: false, // 控制添加用户对话框是否显示
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
      editDialogVisible: false, // 控制修改用户信息对话框是否显示
      editForm: {
        password: "",
        clazz: "",
        major: "",
        status:"",
        academy: "",
        duty: "",
        introduction: "",
      },
      multipleSelection: [],
      ids: [],
    };
  },
  created() {
    // 生命周期函数
    this.getUserList();
  },
  methods: {
    resetUserList() {
      for (let key in this.queryInfo.userDTO) {
        this.queryInfo.userDTO[key] = "";
      }
      userList(this.queryInfo)
        .then((res) => {
          if (res.data.code === 200) {
            //用户列表
            this.userList = res.data.data.records;
            this.total = res.data.data.total;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getUserList() {
      userList(this.queryInfo)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            //用户列表
            this.userList = res.data.data.records;
            this.total = res.data.data.total;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 监听 pageSize 改变的事件
    handleSizeChange(newSize) {
      // console.log(newSize)
      this.queryInfo.pageSize = newSize;
      // 重新发起请求用户列表
      this.getUserList();
    },
    // 监听 当前页码值 改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage)
      this.queryInfo.pageNo = newPage;
      // 重新发起请求用户列表
      this.getUserList();
    },
    //添加用户
    addUser() {
      userAdd(this.userForm)
        .then((res) => {
          if( this.$store.state.user.isAdmin!=1 ){
            this.$message.error("权限不足！");
            return;
          }
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
    },

    // 监听 添加用户对话框的关闭事件
    addDialogClosed() {
      // 表单内容重置为空
      this.$refs.addFormRef.resetFields();
    },

    // 监听 修改用户状态
    showEditDialog(userinfo) {
      this.editDialogVisible = true;
      console.log(userinfo);
      this.editForm = userinfo;
    },
    //修改用户
    editUser() {
      userUpdate(this.editForm)
        .then((res) => {
          if( this.$store.state.user.isAdmin!=1 ){
            this.$message.error("权限不足！");
            return;
          }
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
    },
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
      if (confirmResult == "confirm") {
        //删除用户
        userDelete(id)
          .then((res) => {
            if( this.$store.state.user.isAdmin!=1 ){
            this.$message.error("权限不足！");
            return;
          }
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
    },
    //批量选中事件处理
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
      //向被删除的ids赋值
      this.multipleSelection.forEach((item) => {
        this.ids.push(item.id);
        console.log(this.ids);
      });
    },
    //批量删除用户
    async batchDeleteUser() {
      // 弹框 询问用户是否删除
      const confirmResult = await this.$confirm(
        "此操作将永久删除用户, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);
      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消删除，则返回值为字符串 cancel
      if (confirmResult == "confirm") {
        //批量删除用户
        userBatchDelete(this.ids)
          .then((res) => {
            if( this.$store.state.user.isAdmin!=1 ){
            this.$message.error("权限不足！");
            return;
          }
            if (res.data.code === 200) {
              this.$message({
                message: "批量删除用户成功",
                type: "success",
              });
              this.getUserList();
            } else {
              this.$message.error("批量删除用户失败");
            }
          })
          .catch((err) => {
            this.$message.error("批量删除用户异常");
            console.log(err);
          });
      }
    }
  },
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
