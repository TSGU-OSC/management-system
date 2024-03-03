<template>
    <div>
        <!--Layout布局-->
        <el-row>
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
                <el-col :span="5" style="margin-bottom: 20px">
                    <el-select v-model="queryInfo.status" placeholder="请选择状态">
                        <el-option v-for="item in optionsStatus" :key="item.value" :label="item.label"
                            :value="item.value">
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

        </el-row>

        <!--显示用户信息表格-->
        <el-table :data="userList" border stripe style="width: 100%;">
            <el-table-column prop="createTime" label="申请时间" width="160"></el-table-column>
            <el-table-column prop="name" label="姓名" width="95"></el-table-column>
            <el-table-column prop="gender" label="性别" width="45">
                <template v-slot:default="scope">
                    <span v-if="scope.row.gender === 1">男</span>
                    <span v-else-if="scope.row.gender === 0">女</span>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="80">

                <template v-slot:default="scope">
                    <el-tag type="success" v-if="scope.row.status === 2">待通过</el-tag>
                    <el-tag type="danger" v-if="scope.row.status === 3">未通过</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="code" label="学号" width="130"></el-table-column>
            <el-table-column prop="clazz" label="班级" width="45"></el-table-column>
            <el-table-column prop="major" label="专业"></el-table-column>
            <el-table-column prop="academy" label="学院" width="120"></el-table-column>
            <el-table-column prop="introduction" label="个人介绍" width="200"></el-table-column>
            <el-table-column prop="phone" label="手机号" v-if="this.$store.state.user.role !== 0"></el-table-column>
            <el-table-column prop="province" label="所在省" width="80"
                v-if="this.$store.state.user.role !== 0"></el-table-column>
            <el-table-column prop="city" label="所在市" width="80"
                v-if="this.$store.state.user.role !== 0"></el-table-column>


            <el-table-column prop="idCard" label="身份证号" width="200"
                v-if="this.$store.state.user.role !== 0"></el-table-column>
            <el-table-column label="操作" width="128" fixed="right" v-if="this.$store.state.user.role !== 0">
                <!-- 作用域插槽 -->

                <template v-slot:default="scope">
                    <!--通过按钮-->
                    <el-button type="success" size="mini" icon="el-icon-check"
                        @click="showEditDialog(scope.row)"></el-button>
                    <!-- 拒绝按钮-->
                    <el-button type="danger" size="mini" icon="el-icon-close"
                        @click="removeUserById(scope.row)"></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-row>
            <!--分页区域-->

            <template>
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="this.pageNumber" :page-sizes="[5, 10, 15, 20]" :page-size="this.pageSize"
                    layout="total, sizes, prev, pager, next, jumper" :total="this.total">
                </el-pagination>
            </template>
        </el-row>

        <!--通过用户的对话框-->
        <el-dialog title="通过用户" :visible.sync="editDialogVisible" width="65%">
            <!--内容主体区域-->
            <el-form :model="editForm" label-width="80px" :inline="true">
                <el-form-item label="职位" prop="duty" style="width: 100%;">
                    <el-radio-group v-model="editForm.duty">
                        <el-radio :label="0">成员</el-radio>
                        <el-radio :label="1">副部长</el-radio>
                        <el-radio :label="2">部长</el-radio>
                        <el-radio :label="3">副社长</el-radio>
                        <el-radio :label="4">社长</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="角色" prop="role" style="width: 100%;">
                    <el-radio-group v-model="editForm.role">
                        <el-radio :label="2">超管</el-radio>
                        <el-radio :label="1">管理员</el-radio>
                        <el-radio :label="0">普通成员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <!-- <el-form-item label="状态" prop="status" style="width: 100%;">
                    <el-radio-group v-model="editForm.status">
                        <el-radio :label="1">封禁</el-radio>
                        <el-radio :label="2">正常</el-radio>
                    </el-radio-group>
                </el-form-item> -->
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
import { userUpdate, userAudit } from "@/api/user";
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
                value: '2',
                label: '待通过'
            }, {
                value: '3',
                label: '未通过'
            }],
            userList: [], // 用户对象数组
            // 当前页数
            pageNumber: 1,
            // 每页数目
            pageSize: 5,
            // 分页总数目
            total: "0",
            // 查询参数
            queryInfo: {
                code: "",
                name: "",
                status: "2",
            },

            editDialogVisible: false, // 控制修改用户信息对话框是否显示
            // 修改用户信息
            // 修改前用户信息
            preEditForm: {
                duty: "",
                department: "",
                role: "",
                status: "",
            },
            // 修改后的用户信息
            editForm: {
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
            this.pageNumber = 1;
            // 获取用户信息
            this.getUserList();
        },
        // 获取用户信息
        getUserList() {
            userAudit(this.queryInfo, this.pageNumber, this.pageSize)
                .then((res) => {
                    console.log(res);
                    if (res.data.code === 200) {
                        this.total = res.data.data.total;
                        //用户列表
                        this.userList = res.data.data.list;
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
        // 监听 修改用户状态
        showEditDialog(userinfo) {
            this.editDialogVisible = true;
            // 将userinfo复制给editForm
            this.editForm = { ...userinfo };
            // 删除脏数据avator
            delete this.editForm.avator;
            // 将userinfo复制给preEditForm
            this.preEditForm = { ...userinfo };
            delete this.preEditForm.avator;
            this.editForm.status = 0;
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
        // 拒绝用户
        async removeUserById(userinfo) {
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
                this.editForm.status = 3;
                // 将userinfo复制给editForm
                this.editForm = { ...userinfo };
                // 删除脏数据avator
                delete this.editForm.avator;
                // 将userinfo复制给preEditForm
                this.preEditForm = { ...userinfo };
                delete this.preEditForm.avator;
                this.editForm.status = 0;
                this.editUser();
                //     this.editForm.status=3;
                //     userUpdate(this.editForm)
                //         .then((res) => {
                //             if (res.data.code === 200) {
                //                 this.editDialogVisible = false;
                //                 this.getUserList();
                //                 this.$message({
                //                     message: "修改用户成功",
                //                     type: "success",
                //                 });
                //             } else {
                //                 this.$message.error("修改用户失败:" + res.data.description);
                //                 // 重置修改信息表
                //                 this.editForm = { ...this.preEditForm }
                //             }
                //         })
                //         .catch((err) => {
                //             this.$message.error("修改用户异常");
                //             console.loge(err);
                //         });
            }
        }
    }
};
</script>

<style></style>