<template>
  <div>
    <!--Layout布局-->
    <el-row>
      <el-col :span="24">
        <el-row :gutter="25">
          <el-col :span="4" style="margin-bottom: 20px">
            <!--搜索区域-->
            <el-input placeholder="请输入成员姓名" v-model="queryInfo.name" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5" style="margin-bottom: 20px">
            <el-input placeholder="请输入成员学号" v-model="queryInfo.code" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4" style="margin-bottom: 20px">
            <el-input placeholder="请输入成员班级" v-model="queryInfo.clazz" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4" style="margin-bottom: 20px">
            <el-input placeholder="请输入成员专业" v-model="queryInfo.major" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5" style="margin-bottom: 20px">
            <el-input placeholder="请输入成员学院" v-model="queryInfo.academy" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5" style="margin-bottom: 20px">
            <el-input placeholder="请输入成员手机号" v-model="queryInfo.phone" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4" style="margin-bottom: 20px">
            <el-input placeholder="请输入成员所在省" v-model="queryInfo.province" clearable
                      @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="4" style="margin-bottom: 20px">
            <el-input placeholder="请输入成员所在市" v-model="queryInfo.city" clearable @clear="getUserList">
            </el-input>
          </el-col>
          <el-col :span="5" style="margin-bottom: 20px">
            <el-select v-model="queryInfo.gender" placeholder="请选择性别">
              <el-option v-for="item in optionsGender" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-row :gutter="25" style="margin-bottom: 20px">
          <el-col :span="5">
            <el-select v-model="queryInfo.duty" placeholder="请选择职位">
              <el-option v-for="item in optionsDuty" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            <el-select v-model="queryInfo.department" placeholder="请选择部门">
              <el-option v-for="item in optionsDepartment" :key="item.value" :label="item.label" :value="item.value">
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
          <el-col :span="2">
            <el-button type="primary" icon="el-icon-search" @click="getUserList">搜索</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="resetUserList">刷新</el-button>
          </el-col>
        </el-row>

        <el-row :gutter="25">
          <el-col :span="2.5" style="margin-bottom: 10px">
            <el-button type="primary" v-if="this.$store.state.user.role!==0"  @click="addDialogVisible = true">添加用户</el-button>
          </el-col>
          <el-col :span="2.5" :offset="19" style="margin-bottom: 10px">
            <el-dropdown v-if="this.$store.state.user.role!==0">
              <el-button type="success">Excel</el-button>
              <template>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-upload
                      action="/api/excel/input"
                      accept=".xlsx,.xls"
                      :show-file-list="false"
                      :on-success="uploadSuccess"
                      :on-error="uploadError">
                      <el-button size="small">点击上传</el-button>
                    </el-upload>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button size="small" @click="excelOutput">点击导出</el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-col>

      <el-col :span="24">
        <!--显示用户信息表格-->
        <el-table :data="userList" border stripe :default-sort = "{prop: 'updateTime', order: 'descending'}">
          <!-- <el-table-column prop="id" label="ID"></el-table-column> -->
          <el-table-column prop="avator" label="头像">
            <template v-slot:default="scope">
              <img :src="scope.row.avator" width="70" height="70"/>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="95"></el-table-column>
          <el-table-column prop="gender" label="性别" width="45">
            <template v-slot:default="scope">
              <span v-if="scope.row.gender === 1">男</span>
              <span  v-else-if="scope.row.gender === 0">女</span>
            </template>
          </el-table-column>

          <el-table-column prop="code" label="学号"  width="130"></el-table-column>
          <el-table-column prop="clazz" label="班级" width="45"></el-table-column>
          <el-table-column prop="major" label="专业"></el-table-column>
          <el-table-column prop="academy" label="学院" width="120"></el-table-column>
          <el-table-column prop="introduction" label="个人介绍"  width="200"></el-table-column>
          <el-table-column prop="phone" label="手机号" v-if="this.$store.state.user.role!==0"></el-table-column>
          <el-table-column prop="province" label="所在省" width="80" v-if="this.$store.state.user.role!==0"></el-table-column>
          <el-table-column prop="city" label="所在市" width="80" v-if="this.$store.state.user.role!==0"></el-table-column>
          <el-table-column prop="duty" label="职位">
            <template v-slot:default="scope">
              <span v-if="scope.row.duty === 0">普通成员</span>
              <span  v-else-if="scope.row.duty === 1">副部长</span>
              <span  v-else-if="scope.row.duty === 2">部长</span>
              <span  v-else-if="scope.row.duty === 3">副社长</span>
              <span  v-else-if="scope.row.duty === 4">社长</span>
            </template>
          </el-table-column>
          <el-table-column prop="department" label="部门">
            <template v-slot:default="scope">
              <span v-if="scope.row.department === 0">运营部</span>
              <span  v-else-if="scope.row.department === 1">技术部</span>
              <span  v-else-if="scope.row.department === 2">宣传部</span>
              <span  v-else-if="scope.row.department === 3">其它</span>
            </template>
          </el-table-column>
          <el-table-column prop="role" label="角色">
            <template v-slot:default="scope">
              <span v-if="scope.row.role === 0">普通成员</span>
              <span  v-else-if="scope.row.role === 1">管理员</span>
              <span  v-else-if="scope.row.role === 2">超管</span>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="80">
            <template v-slot:default="scope">
              <el-tag type="success" v-if="scope.row.status === 0">正常</el-tag>
              <el-tag type="danger" v-if="scope.row.status === 1">封禁</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="idCard" label="身份证号"  width="200" v-if="this.$store.state.user.role!==0"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="160" ></el-table-column>
          <el-table-column prop="createUser" label="创建者ID" width="100"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" width="160" sortable></el-table-column>
          <el-table-column prop="updateUser" label="更新者ID" width="100"></el-table-column>
          <el-table-column label="操作" width="128" fixed="right" v-if="this.$store.state.user.role!==0">
            <!-- 作用域插槽 -->
            <template v-slot:default="scope" >
              <!--修改按钮-->
              <el-button type="primary" size="mini" icon="el-icon-edit" @click="showEditDialog(scope.row)"></el-button>
              <!-- 删除按钮-->
              <el-button type="danger" size="mini" icon="el-icon-delete" 
                         @click="removeUserById(scope.row.id)" ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <!--分页区域-->
      <template>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="this.pageNumber"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="this.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="this.total">
        </el-pagination>
      </template>
    </el-row>
    <!--添加用户的对话框-->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="80%" @close="addDialogClosed">
      <!--内容主体区域-->
      <el-form :model="userForm" label-width="70px" :inline="true">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="userForm.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="userForm.password" show-password placeholder="默认为 12345678"	></el-input>
        </el-form-item>
          <el-form-item label="学号" prop="code">
          <el-input v-model="userForm.code"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio v-model="userForm.gender" label="1">男</el-radio>
          <el-radio v-model="userForm.gender" label="0">女</el-radio>
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
        <el-form-item label="部门" prop="department">
          <el-select v-model="userForm.department" placeholder="请选择部门">
            <el-option v-for="item in optionsDepartment" :key="item.value" :label="item.label" :value="item.value">
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
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="65%">
      <!--内容主体区域-->
      <el-form :model="editForm" label-width="80px" :inline="true">
        <el-form-item label="学号" prop="code">
          <el-input v-model="editForm.code"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="editForm.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
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
        <el-form-item label="职位" prop="duty">
          <el-radio-group v-model="editForm.duty">
            <el-radio :label="0">成员</el-radio>
            <el-radio :label="1">副部长</el-radio>
            <el-radio :label="2">部长</el-radio>
            <el-radio :label="3">副社长</el-radio>
            <el-radio :label="4">社长</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="部门" prop="department" style="width: 100%;">
          <el-radio-group v-model="editForm.department">
            <el-radio :label="2">宣传部</el-radio>
            <el-radio :label="1">技术部</el-radio>
            <el-radio :label="0">运营部</el-radio>
            <el-radio :label="3">无</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="editForm.status">
            <el-radio :label="1">封禁</el-radio>
            <el-radio :label="0">正常</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色" prop="role">
            <el-radio-group v-model="editForm.role">
              <el-radio :label="2">超管</el-radio>
              <el-radio :label="1">管理员</el-radio>
              <el-radio :label="0">普通成员</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="个人介绍" prop="introduction">
          <el-input type="textarea" v-model="editForm.introduction" style="width: 800px;"></el-input>
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
import {outputExcel, userAdd, userDelete, userList, userUpdate} from "@/api/user";
import row from "element-ui/packages/row";
import defaultAvatar from "@/assets/img/avator.jpg";

export default {
  computed: {
    row() {
      return row
    }
  },
  data() {
    return {
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
        value: '3',
        label: '其它'
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
      userList: [], // 用户对象数组
      // 当前页数
      pageNumber: 1,
      // 每页数目
      pageSize: 5,
      // 分页总数目
      total: "",
      // 查询参数
      queryInfo: {
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
        department: "",
        status: "",
        role: ""
      },
      addDialogVisible: false, // 控制添加用户对话框是否显示
      // 添加用户信息
      userForm: {
        name: "",
        password: "",
        gender: "",
        code: "",
        clazz: "",
        major: "",
        academy: "",
        duty: "",
        department: "",
        role: "",
      },
      editDialogVisible: false, // 控制修改用户信息对话框是否显示
      // 修改用户信息
      // 修改前用户信息
      preEditForm: {
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
        department: "",
        role: "",
        status: "",
      },
      // 修改后的用户信息
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
        department: "",
        role: "",
        status: "",
      },   
    };
 
  },
  // 生命周期函数
  created() {
    // 获取用户信息
    this.getUserList();
  },
  methods: {
    // 更新用户信息列表
    resetUserList() {
      // 清空查询信息
      for (let key in this.queryInfo) {
        this.queryInfo[key] = "";
      }
      this.pageNumber=1;
      // 获取用户信息
      this.getUserList();
    },
    // 获取用户信息
    getUserList() {
      userList(this.queryInfo,this.pageNumber,this.pageSize)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.total = res.data.data.total;
            //用户列表
            this.userList = res.data.data.list;
            this.userList.forEach(item => {
              let fileName = item.avator;
              item.avator = fileName===''?defaultAvatar:"/api/file/download?fileName=" + fileName
            })
          } else {
            this.$message.error(res.data.description);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 监听 当前页码值 改变的事件
    handleCurrentChange(newPage) {
      console.log(newPage)
      this.pageNumber = newPage;
      // 重新发起请求用户列表
      this.getUserList();
    },
    // 监听 当前页大小 改变的事件
    handleSizeChange(newPageSize) {
      // 重新设置每页显示的条数
      this.pageSize = newPageSize;
      // 重新发起请求用户列表
      this.getUserList();
    },
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
            // 清空添加用户信息表
            for (let key in this.userForm) {
              this.userForm[key] = "";
            }
          } else {
            this.$message.error("添加用户失败:"+res.data.description);
          }
        })
        .catch((err) => {
          this.$message.error("添加用户异常,请正确填写字段！")
          console.log(err);
        });
    },
    // 取消添加用户
    addDialogClosed() {
      // 清空添加用户信息表
      for (let key in this.userForm) {
        this.userForm[key] = "";
      }
    },
    // 监听 修改用户状态
    showEditDialog(userinfo) {
      this.editDialogVisible = true;
      // 将userinfo复制给editForm
      this.editForm={...userinfo};
      // 删除脏数据avator
      delete this.editForm.avator;
      // 将userinfo复制给preEditForm
      this.preEditForm={...userinfo};
      delete this.preEditForm.avator;
    },
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
            this.$message.error("修改用户失败:"+res.data.description);
            // 重置修改信息表
            this.editForm={...this.preEditForm}
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
              this.$message.error("删除用户失败"+res.data.description);
            }
          })
          .catch((err) => {
            this.$message.error("删除用户异常");
            console.log(err);
          });
      }
    },
    excelOutput(){
      outputExcel()
        .then((res) => {
          const blob = new Blob([res.data], { type: 'application/vnd.ms-excel' }) // 构造一个blob对象来处理数据，并设置文件类型
          const href = URL.createObjectURL(blob) //创建新的URL表示指定的blob对象
          const a = document.createElement('a') //创建a标签
          a.style.display = 'none'
          a.href = href // 指定下载链接
          a.download = 'userInfo' //指定下载文件名
          a.click() //触发下载
          URL.revokeObjectURL(a.href) //释放URL对象
        })
        .catch((err) => {
          this.$message.error("Excel导出异常");
          console.log(err);
        });
    },
    uploadSuccess(res){
      if (res.code===200){
        this.$message.success("Excel导入成功")
      }else {
        this.$message.error("Excel导入异常: "+res.description);
      }
    },
    uploadError(err){
      this.$message.error("Excel导入异常");
      console.log("Excel导入异常",err)
    }
  },
};
</script>

<style>
</style>
