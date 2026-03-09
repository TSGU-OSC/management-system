<template>
    <div class="users-main-container">
        <div class="users-content-warpper">
            <div class="users-search">
                <!-- 整个查询区域使用 el-row 实现左右分栏 -->
                <el-row :gutter="24" type="flex" align="top">

                    <!-- 左侧表单区：占据 18/24 = 75% -->
                    <el-col :span="18" class="search-left">
                        <!-- 第一行 -->
                        <el-row :gutter="24" class="left-first-row">
                            <el-col :span="8">
                                <el-input 
                                    placeholder="请输入成员姓名" 
                                    v-model="queryInfo.name"
                                    clearable
                                    @clear="getUserList"
                                    @keyup.native.enter="getUserList">
                                </el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-input 
                                    placeholder="请输入成员学号"
                                    v-model="queryInfo.code"
                                    clearable
                                    @clear="getUserList"
                                    @keyup.native.enter="getUserList"></el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-input 
                                    placeholder="请输入成员手机号" 
                                    v-model="queryInfo.phone" 
                                    clearable
                                    @clear="getUserList"
                                    @keyup.native.enter="getUserList"
                                    ></el-input>
                            </el-col>
                        </el-row>

                        <!-- 第二行 -->
                        <el-row :gutter="24" class="left-second-row">
                            <el-col :span="8">
                                <el-select 
                                    placeholder="请选择部门" 
                                    v-model="queryInfo.department"
                                    clearable 
                                    style="width: 100%;">
                                        <el-option 
                                            v-for="item in optionsDepartment" 
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                        </el-option>
                                        <!-- <el-option label="社长团" value="0"></el-option>
                                        <el-option label="技术部" value="1"></el-option>
                                        <el-option label="宣传部" value="2"></el-option>
                                        <el-option label="运营部" value="3"></el-option> 
                                        <el-option label="社区成员" value="4"></el-option> -->
                                </el-select>
                                <!-- <el-input placeholder="请选择部门"></el-input> -->
                            </el-col>

                            <el-col :span="8">
                                <el-select 
                                    placeholder="请选择角色" 
                                    v-model="queryInfo.role"
                                    clearable 
                                    style="width: 100%;">
                                        <el-option
                                            v-for="item in optionsRole"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                        </el-option>
                                        <!-- <el-option label="超管" value="0"></el-option>
                                        <el-option label="管理员" value="1"></el-option>
                                        <el-option label="普通成员" value="2"></el-option> -->
                                </el-select>
                                <!-- <el-input placeholder="请选择角色"></el-input> -->
                            </el-col>

                            <el-col :span="8">
                                <el-select 
                                    placeholder="请选择状态"
                                    v-model="queryInfo.status" 
                                    clearable 
                                    style="width: 100%;">
                                        <el-option
                                            v-for="item in optionsStatus"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                        </el-option>
                                        <!-- <el-option label="正常" value="0"></el-option>
                                        <el-option label="离职" value="1"></el-option> -->
                                </el-select>
                                <!-- <el-input placeholder="请选择状态"></el-input> -->
                            </el-col>
                        </el-row>  
                    </el-col>

                    <!-- 右侧按钮区：占据 6/24 = 25% -->
<<<<<<< HEAD
                    <el-col :span="6" class="search-right-button">
                        <div class="search-button-group">
                            <div class="search-up-button">
                                <el-button type="primary" icon="el-icon-search" @click="getUserList">查询</el-button>
                            </div>
                            <div class="search-down-button">
                                <el-button icon="el-icon-refresh" @click="resetUserList">重置</el-button>
                            </div>  
=======
                    <el-col :span="6" class="right-button">
                        <div class="button-group">
                            <div class="up-button">
                                <el-button type="primary" icon="el-icon-search">查询</el-button>
                            </div>
                            <div class="down-button">
                                <el-button icon="el-icon-refresh">重置</el-button>
                            </div>
                            
>>>>>>> c25ea4ac08132610ca7ce2befaeeb545d96a9c2f
                        </div>
                    </el-col>
                </el-row>
                    <!-- <el-col :span="5" style="margin-bottom: 20px;">
                        <el-input placeholder="请输入成员班级"></el-input>
                    </el-col> -->
                    <!-- <el-col :span="5" style="margin-bottom: 20px;">
                        <el-input placeholder="请输入成员专业"></el-input>
                    </el-col> -->
                    <!-- <el-col :span="5" style="margin-bottom: 20px;">
                        <el-input placeholder="请输入成员学院"></el-input>
                    </el-col> -->
                    <!-- <el-col :span="5" style="margin-bottom: 20px;">
                        <el-input placeholder="请输入成员所在省"></el-input>
                    </el-col> -->
                    <!-- <el-col :span="5" style="margin-bottom: 20px;">
                        <el-input placeholder="请输入成员所在市"></el-input>
                    </el-col> -->
                    <!-- <el-col :span="5" style="margin-bottom: 20px;">
                        <el-input placeholder="请选择性别"></el-input>
                    </el-col> -->
                    <!-- <el-col :span="5" style="margin-bottom: 20px;">
                        <el-input placeholder="请选择职位"></el-input>
                    </el-col> -->
            </div>

            <div class="users-showTable">
                <div class="showTable-button-group">
                    <!-- <div class="showTable-left-button"></div> -->
                    <el-col :span="2.5" style="float: left;" class="showTable-left-button">
                        <el-button type="primary" 
                            v-if="this.$store.state.user.role !== 0" 
                            icon="el-icon-plus"
                            @click="addDialogVisible = true">
                            添加用户
                        </el-button>

                        <el-dropdown v-if="this.$store.state.user.role !== 0">
                            <el-button type="success" icon="el-icon-document">Excel
                                <template>
                                    <el-dropdown-menu>
                                        <el-dropdown-item>
                                            <el-upload 
                                                action="/api/excel/input" 
                                                accept=".xlsx,.xls" 
                                                :show-file-list="false"
                                                :on-success="uploadSuccess" 
                                                :on-error="uploadError">
                                            <el-button size="small" icon="el-icon-upload2">点击上传</el-button>
                                            </el-upload>
                                        </el-dropdown-item>
                                        <el-dropdown-item>
                                            <el-button size="small" icon="el-icon-download" @click="excelOutput">点击导出</el-button>
                                        </el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-button>
                        </el-dropdown>
                    </el-col>
                    
                    <el-col :span="2.5" style="float: right;" class="showTable-right-button">
                        <el-button type="primary" icon="el-icon-refresh-right" @click="resetUserList">刷新</el-button>
                    </el-col>
                    
                    <!-- <div class="showTable-right-button"></div> -->
                </div>

                <el-table :data="userList" border stripe>
                    <el-table-column prop="name" label="姓名" width="95"></el-table-column>
                    
                    <el-table-column prop="code" label="学号" width="130"
                        v-if="this.$store.state.user.role > 0">
                    </el-table-column>

                    <el-table-column prop="phone" label="手机号" v-if="this.$store.state.user.role > 0">
                    </el-table-column>

                    <el-table-column prop="department" label="部门">
                        <template v-slot:default="scope">
                            <span v-if="scope.row.department === 0">运营部</span>
                            <span v-else-if="scope.row.department === 1">技术部</span>
                            <span v-else-if="scope.row.department === 2">宣传部</span>
                            <span v-else-if="scope.row.department === 3">其它</span>
                        </template>
                    </el-table-column>

                    <el-table-column prop="role" label="角色">
                        <template v-slot:default="scope">
                            <span v-if="scope.row.role === 0">普通成员</span>
                            <span v-else-if="scope.row.role === 1">管理员</span>
                            <span v-else-if="scope.row.role === 2">超管</span>
                        </template>
                    </el-table-column>

                    <el-table-column prop="status" label="状态" width="80" v-if="this.$store.state.user.role !== 0">
                        <template v-slot:default="scope">
                            <el-tag type="success" v-if="scope.row.status === 0">正常</el-tag>
                            <el-tag type="danger" v-if="scope.row.status === 1">封禁</el-tag>
                        </template>
                    </el-table-column>

                    <el-table-column label="操作" :width=this.width fixed="right">
                        <!-- 作用域插槽 -->
                        <template v-slot:default="scope">
                            <!--修改按钮-->
                            <el-button type="primary" size="mini" icon="el-icon-edit" @click="showEditDialog(scope.row)"
                            v-if="$store.state.user.role !== 0">
                            </el-button>

                            <!-- 删除按钮-->
                            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeUserById(scope.row.id)"
                            v-if="$store.state.user.role !== 0">
                            </el-button>

                            <!-- 查看用户个人介绍按钮-->
                            <el-button type="success" size="mini" icon="el-icon-view"
                            @click="seeUserIntroduction(scope.row)">
                            </el-button>
                        </template>
                    </el-table-column>

                    <!-- <el-table-column prop="avator" label="头像">
                        <template v-slot:default="scope">
                             <img :src="scope.row.avator" width="70" height="70" />
                        </template>
                    </el-table-column> -->

                    <!-- <el-table-column el-table-column prop="gender" label="性别" width="55">
                        <template v-slot:default="scope">
                            <span v-if="scope.row.gender === 1">男</span>
                            <span v-else-if="scope.row.gender === 0">女</span>
                        </template>
                    </el-table-column> -->
                    
                    <!-- <el-table-column prop="clazz" label="班级" width="55">
                    </el-table-column> -->

                    <!-- <el-table-column prop="major" label="专业">
                    </el-table-column> -->

                    <!-- <el-table-column prop="academy" label="学院" width="120">
                    </el-table-column> -->

                    <!-- <el-table-column prop="province" label="所在省" width="80">
                    </el-table-column> -->

                    <!-- <el-table-column prop="city" label="所在市" width="80">
                    </el-table-column> -->

                    <!-- <el-table-column prop="duty" label="职位">
                        <template v-slot:default="scope">
                            <span v-if="scope.row.duty === 0">普通成员</span>
                            <span v-else-if="scope.row.duty === 1">副部长</span>
                            <span v-else-if="scope.row.duty === 2">部长</span>
                            <span v-else-if="scope.row.duty === 3">副社长</span>
                            <span v-else-if="scope.row.duty === 4">社长</span>
                        </template>
                    </el-table-column> -->
                    
                    <!-- <el-table-column prop="idCard" label="身份证号" width="200" v-if="this.$store.state.user.role > 1 ">
                    </el-table-column>

                    <el-table-column prop="createTime" label="创建时间" width="160" v-if="this.$store.state.user.role !== 0">
                    </el-table-column>

                    <el-table-column prop="createUser" label="创建者ID" width="100" v-if="this.$store.state.user.role !== 0">
                    </el-table-column>

                    <el-table-column prop="updateTime" label="更新时间" width="160" v-if="this.$store.state.user.role !== 0">
                    </el-table-column>

                    <el-table-column prop="updateUser" label="更新者ID" width="100" v-if="this.$store.state.user.role !== 0">
                    </el-table-column> -->
                </el-table>
            </div>
        </div>
    </div>
</template>

<script>
// 从@/api/user导入API函数，用于后端交互
import { outputExcel, userAdd, userDelete, userList, userUpdate } from "@/api/user";
// element-ui
import row from "element-ui/packages/row";
// 头像
import defaultAvatar from "@/assets/img/avator.jpg";
// element
import { pcTextArr } from "element-china-area-data";
// CSS
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
// 富文本编辑器组件引入
import { quillEditor } from "vue-quill-editor";

export default {
  components: {
    quillEditor  // 富文本编辑器组件
  },
  computed: {
    row() {
      return row
    }
  },
  // 数据
  data() {
    return {
      editorOption: {
        modules: {
          // 编辑器工具栏配置，包括加粗、斜体、标题等工具
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线引用  
            [{ header: 1 }, { header: 2 }], // 1、2 级标题
            [{ indent: '-1' }, { indent: '+1' }], // 缩进
            [{ direction: 'rtl' }], // 文本方向
            [{ header: [1, 2, 3, 4, 5, 6] }], // 标题
            [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
            // [{ font: ['songti'] }], // 字体种类
            [{ align: [] }], // 对齐方式
            ['clean'], // 清除文本格式
          ]
        },
      },
      optionsnative_place: pcTextArr,
      // 编辑器-性别选项
      optionsGender: [
        {
            value: '1',
            label: '男'
        }, 
        {
            value: '0',
            label: '女'
        }
      ],
      // 编辑器-职位选项
      optionsDuty: [
        {
            value: '0',
            label: '成员'
        },
        {
            value: '1',
            label: '副部长'
        }, 
        {
            value: '2',
            label: '部长'
        }, 
        {
            value: '3',
            label: '副社长'
        }, 
        {
            value: '4',
            label: '社长'
        }
      ],
      // 编辑器-部门选项
      optionsDepartment: [
        {
            value: '0',
            label: '社长团'
        },
        {
            value: '1',
            label: '技术部'
        },
        {
            value: '2',
            label: '运营部'
        },
        {
            value: '3',
            label: '宣传部'
        },
        {
            value: '4',
            label: '社区成员'
        }
      ],
      // 编辑器-角色选项
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
      // 编辑器-账号状态选项
      optionsStatus: [{
        value: '0',
        label: '正常'
      }, {
        value: '1',
        label: '封禁'
      }],
      // 用户对象数组，用于表格显示
      userList: [], 
      // 当前页数
      pageNumber: 1,
      // 每页数目
      pageSize: 5,
      // 分页总数目
      total: 0,
      // 查询参数，包括所有搜索字段
      queryInfo: {
        // 用户查询区域：第一行
        name: "",
        code: "",
        phone: "",

        // 用户查询区域：第二行
        department: "",
        role: "",
        status: "",

        // gender: "",
        // province: "",
        // city: "",
        // clazz: "",
        // major: "",
        // academy: "",
        // duty: "",

      },
      // 控制添加用户对话框是否显示
      addDialogVisible: false, 
      // 控制修改用户信息对话框是否显示
      editDialogVisible: false, 
      // 控制查看用户信息对话框是否显示
      seeDialogVisible: false, 
      // 添加用户信息
      userForm: {
        // 表单展示区域：直接展示
        name: "",
        code: "",
        phone: "",
        department: "",
        role: "",

        // 表单展示区域：点击详情
        gender: "",
        clazz: "",
        major: "",
        academy: "",
        city: "",
        province: "",
        duty: "",
        status: "",
        introduction: "",
      },

      // 修改用户信息
      // 修改前用户信息
      preEditForm: {
        // 表单展示区域：直接展示
        name: "",
        code: "",
        phone: "",
        department: "",
        role: "",
        
        // 表单展示区域：点击详情
        gender: "",
        clazz: "",
        major: "",
        academy: "",
        city: "",
        province: "",
        duty: "",
        status: "",
        introduction: "",
      },
      // 修改后的用户信息
      editForm: {
        // 表单展示区域：直接展示
        name: "",
        code: "",
        phone: "",
        department: "",
        role: "",

        // 表单展示区域：点击详情
        gender: "",
        clazz: "",
        major: "",
        academy: "",    
        city: "",
        province: "",     
        duty: "",
        status: "",
        introduction: "",
      },
      // 操作列宽度，根据用户角色动态调整
      width: this.$store.state.user.role >= 1 ? 200 : 70
    };

  },
  watch: {
    "$store.state.user.role"(newVal) {
      this.width=this.$store.state.user.role >= 1 ? 200 : 70
      this.$forceUpdate();  // 强制更新视图
    },
  },

  // 生命周期函数
  created() {
    // 获取用户信息
    // 页面创建时立即获取用户列表
    this.getUserList();
  },
  // 方法
  methods: {
    // 用户信息查看/编辑
    //查看用户个人信息（打开查看对话框）
    seeUserIntroduction(userinfo) {
      this.seeDialogVisible = true;
      // 将userinfo复制给editForm
      this.editForm = { ...userinfo };
      this.editForm.province = [this.editForm.province, this.editForm.city];
      // 删除脏数据avator
      delete this.editForm.avator;
      // 将userinfo复制给preEditForm
      this.preEditForm = { ...userinfo };
      delete this.preEditForm.avator;
      console.log(this.editForm)
    },
    // 监听 修改用户状态
    showEditDialog(userinfo) {
      this.editDialogVisible = true;
      // 将userinfo复制给editForm
      this.editForm = { ...userinfo };
      this.editForm.province = [this.editForm.province, this.editForm.city];
      // 删除脏数据avator
      delete this.editForm.avator;
      // 将userinfo复制给preEditForm
      this.preEditForm = { ...userinfo };
      delete this.preEditForm.avator;

    },
    // 提交用户编辑（同步更新到后端）
    editUser() {
      const place = this.editForm.province;
      this.editForm.province = place[0];
      this.editForm.city = place[1];
      userUpdate(this.editForm)
        .then((res) => {
          if (res.data.code === 200) {
            this.editDialogVisible = false;
            this.seeDialogVisible = false;
            this.getUserList();
            this.$message({
              message: "修改用户成功",
              type: "success",
            });
          } else {
            this.$message.error("修改用户失败:" + res.data.description);
            // 重置修改信息表
            this.editForm = { ...this.preEditForm }
          }
        })
        .catch((err) => {
          this.$message.error("修改用户异常");
          console.loge(err);
        });
    },


    // 用户列表操作
    // 更新用户信息列表
    resetUserList() {
      // 清空查询信息
      for (let key in this.queryInfo) {
        this.queryInfo[key] = "";
      }
      this.pageNumber = 1;
      // 获取用户信息
      this.getUserList();
    },
    // 获取用户信息数组/列表
    getUserList() {
      // const place = this.queryInfo.province;
      // this.queryInfo.province = place[0];
      // this.queryInfo.city = place[1];
      userList(this.queryInfo, this.pageNumber, this.pageSize)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.total = res.data.data.total;
            //用户列表
            this.userList = res.data.data.list;
            this.userList.forEach(item => {
              let fileName = item.avator;
              item.avator = fileName === '' ? defaultAvatar : "/api/file/download?fileName=" + fileName
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


    // 用户增删操作
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
            this.$message.error("添加用户失败:" + res.data.description);
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
              this.$message.error("删除用户失败" + res.data.description);
            }
          })
          .catch((err) => {
            this.$message.error("删除用户异常");
            console.log(err);
          });
      }
    },


    // Excel 导入/导出
    // 导入用户数据到Excel
    excelOutput() {
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
    // Excel导入成功回调
    uploadSuccess(res) {
      if (res.code === 200) {
        this.$message.success("Excel导入成功")
      } else {
        this.$message.error("Excel导入异常: " + res.description);
      }
    },
    // Excel导入失败回调
    uploadError(err) {
      this.$message.error("Excel导入异常");
      console.log("Excel导入异常", err)
    },


    //查看用户信息
    seeUser() {
      this.seeDialogVisible = false;
    }

    // 富文本编辑器相关
    // 编辑器失去焦点
    // onEditorBlur()
    // 编辑器获得焦点
    // onEditorFocus()
    // 编辑器准备就绪
    // onEditorReady
  },
}
</script>

<style scoped>
/* 主容器 */
.users-main-container {
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    box-sizing: border-box;
}

/* 正文容器 */
.users-content-warpper {
    flex: 1;
    display: flex;
    flex-direction: column;
    min-height: 0;
}

/* 用户查询区域 */
.users-search {
    background: #fff;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 8px #f0f1f2;
    flex-shrink: 0;
}

/* 用户查询区域：左侧输入框和选择框样式 */
.users-search .el-col {
    /* padding-left: 20px; */
    display: flex;
    align-items: center;
}

/* 用户查询区域：左侧输入框和选择框样式 */
.users-search .search-left {
    display: flex;
    flex-direction: column;
}

/* 用户查询区域：查询框样式 */
.users-search .left-first-row, 
.users-search .left-right-row {
    margin-bottom: 20px;
    display: flex;
}

<<<<<<< HEAD
/* 用户查询区域：右侧按钮区域样式 */
.users-search .search-right-button {
=======
/* 右侧按钮区域样式 */
.users-search .right-button {
>>>>>>> c25ea4ac08132610ca7ce2befaeeb545d96a9c2f
    display: flex;
    justify-content: center;
    align-items: flex-start;
    padding-left: 20px;
    border-left: 1px solid #e8e8e8;
}

<<<<<<< HEAD
/* 用户查询区域：右侧按钮组容器 */
.users-search .search-button-group {
=======
/* 按钮组容器 */
.users-search .button-group {
>>>>>>> c25ea4ac08132610ca7ce2befaeeb545d96a9c2f
    width: 100%;
    max-width: 200px; /* 控制按钮的最大高度，避免在大屏幕上过宽 */
}

<<<<<<< HEAD
/* 用户查询区域：上下按钮间隔 */
.search-button-group .search-up-button {
=======
.button-group .up-button {
>>>>>>> c25ea4ac08132610ca7ce2befaeeb545d96a9c2f
    padding-bottom: 20px;
}

/* 响应式调整：在狭窄屏幕上，让左右布局变为上下布局 */
@media (max-width: 992px) {
    .user-search .el-col {
        width: 100% !important;
        max-width: 100% !important;
        flex: 0 0 100% !important;
    }

    .users-search .right-bottom {
        padding-left: 0;
        padding-top: 20px;
        border-left: none;
        border-top: 1px solid #e8e8e8;
        margin-top: 20px;
    }

    .users-search .button-group {
        max-width: 100%;
    }
}

/* 表单展示区域 */
.users-showTable {
    flex: 1;
    background: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 8px #f0f1f2;
    min-height: 0;
    overflow-y: auto;
} 

/* 表单展示区域：上层按钮样式*/
.users-showTable .showTable-button-group {
    margin-bottom: 55px;
}
</style>