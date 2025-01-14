<template>
    <div>
        <el-row>
            <el-col :span="24">
                <el-row :gutter="25">
                    <el-col :span="6" style="margin-bottom: 20px">
                        <!--搜索区域-->
                        <el-input placeholder="请输入公告标题" v-model="queryInfo.title" clearable>
                        </el-input>
                    </el-col>
                    <el-col :span="2">
                        <el-button type="primary" icon="el-icon-search" @click="getAdList">搜索</el-button>
                    </el-col>
                    <el-col :span="2.5">
                        <el-button type="primary" icon="el-icon-refresh-right" @click="resetUserList">刷新</el-button>
                    </el-col>
                    <el-col :span="2.5" style="float: right;">
                        <el-button type="primary" icon="el-icon-plus" @click="addDialogVisible = true"
                            v-if="$store.state.user.role >= 1">添加</el-button>
                    </el-col>
                </el-row>
                <el-table :data="adList" border style="width: 100%">
                    <el-table-column prop="title" label="公告标题" width="400" style="color: red;">
                    </el-table-column>
                    <el-table-column prop="status" label="状态" width="92">
                        <template v-slot:default="scope">
                            <el-tag type="info" v-if="scope.row.status == 0">日常公告</el-tag>
                            <el-tag type="danger" v-if="scope.row.status == 1">紧急公告</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="200">
                    </el-table-column>
                    <el-table-column prop="updateTime" label="更新时间" width="200">
                    </el-table-column>
                    <el-table-column prop="createUser" label="创建者ID">
                    </el-table-column>
                    <el-table-column prop="updateUser" label="更新者ID">
                    </el-table-column>

                    <el-table-column fixed="right" label="操作" width="150">

                        <template slot-scope="scope">
                            <el-button @click="showAdDetail(scope.row)" type="text" size="small">查看</el-button>
                            <el-button type="text" size="small" @click="showEditDialog(scope.row)"
                                v-if="$store.state.user.role >= 1">编辑</el-button>
                            <el-button @click="deleteAd(scope.row.id)" type="text" size="small"
                                v-if="$store.state.user.role >= 1">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>

        </el-row>
        <el-row>
            <!--分页区域-->

            <template>
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="this.pageNumber" :page-sizes="[5, 10, 15, 20]" :page-size="this.pageSize"
                    layout="total, sizes, prev, pager, next, jumper" :total="this.total">
                </el-pagination>
            </template>
        </el-row>
        <el-dialog title="公告详情" :visible.sync="AdDialogVisible" width="80%" center>
            <div style="text-align: center;font-size: 20px;">
                <h1>{{ editForm.title }}</h1>
            </div>
            <!-- <quill-editor v-if="this.$store.state.user.role >= 1" ref="myQuillEditor" v-model=""
                :options="editorOption" @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
                @ready="onEditorReady($event)" style="height: 200px;margin-bottom: 4%;" />-->
            <div v-html="editForm.content"></div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="AdDialogVisible = false">返回</el-button>

            </span>
        </el-dialog>
        <!--修改公告的对话框-->
        <el-dialog title="修改公告" :visible.sync="editDialogVisible" width="60%">
            <!--内容主体区域-->
            <el-form :model="editForm" label-width="80px" :inline="true">
                <el-form-item label="公告标题" prop="title" style="width: 100%;">
                    <el-input v-model="editForm.title"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-checkbox v-model="editForm.status" label="1">是否紧急</el-checkbox>
                </el-form-item>
                <el-form-item label="公告内容" prop="content">
                    <quill-editor ref="myQuillEditor" v-model="editForm.content" :options="editorOption"
                        @blur="onEditorBlur($event)" @focus="onEditorFocus($event)" @ready="onEditorReady($event)"
                        style="height: 200px;margin-bottom: 4%;" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editAd">确 定</el-button>
            </span>
        </el-dialog>
        <!--添加公告的对话框-->
        <el-dialog title="添加公告" :visible.sync="addDialogVisible" width="80%" @close="addDialogClosed">
            <!--内容主体区域-->
            <el-form :model="AdForm" label-width="70px">
                <el-form-item label="级别" prop="status">
                    <el-radio v-model="AdForm.status" label="1">紧急公告</el-radio>
                    <el-radio v-model="AdForm.status" label="0">日常公告</el-radio>
                </el-form-item>
                <el-form-item label="标题" prop="title">
                    <el-input v-model="AdForm.title" maxlength="30" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <quill-editor ref="myQuillEditor" v-model="AdForm.content" :options="editorOption"
                        @blur="onEditorBlur($event)" @focus="onEditorFocus($event)" @ready="onEditorReady($event)"
                        style="height: 200px;margin-bottom: 4%;" />
                </el-form-item>
            </el-form>
            <!--底部按钮区域-->
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addAd">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { getAd, AdUpdate, adAdd, deleteAd } from "@/api/announcement";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import { quillEditor } from "vue-quill-editor";
export default {
    components: {
        quillEditor
    },
    data() {
        return {
            editorOption: {
                modules: {
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
            // 查询参数
            queryInfo: {
                title: "",
            },
            adList: [],
            // 当前页数
            pageNumber: 1,
            // 每页数目
            pageSize: 5,
            // 分页总数目
            total: 0,
            AdDialogVisible: false,
            editDialogVisible: false,
            addDialogVisible: false, // 控制添加公告对话框是否显示
            // 修改前公告信息
            preEditForm: {
                title: "",
                content: "",
                status: "",
            },
            // 修改后的公告信息
            editForm: {
                title: "",
                content: "",
                status: "",
            },
            AdForm: {
                title: "",
                content: ""
            },
        }
    },
    created() {
        // 获取公告信息
        this.getAdList();
    },
    methods: {
        showAdDetail(userinfo) {
            this.AdDialogVisible = true;
            // 将userinfo复制给editForm
            this.editForm = { ...userinfo };
            // 将userinfo复制给preEditForm
            this.preEditForm = { ...userinfo };
        },
        // 监听 修改用户状态
        showEditDialog(userinfo) {
            this.editDialogVisible = true;
            // 将userinfo复制给editForm
            this.editForm = { ...userinfo };
            // 将userinfo复制给preEditForm
            this.preEditForm = { ...userinfo };
            this.editForm.status = this.editForm.status == 0 ? false : true;
        },
        // 更新用户信息列表
        resetUserList() {
            // 清空查询信息
            for (let key in this.queryInfo) {
                this.queryInfo[key] = "";
            }
            this.pageNumber = 1;
            // 获取用户信息
            this.getAdList();
        },
        getAdList() {

            getAd(this.queryInfo.title, this.pageNumber, this.pageSize).then((res) => {
                if (res.data.code === 200) {
                    this.total = res.data.data.total;
                    this.adList = res.data.data.list;
                } else {
                    this.$message.error("公告加载异常");
                }
            }).catch((err) => {
                this.$message.error("公告加载异常");
                console.log(err);
            });
        },
        // 监听 当前页码值 改变的事件
        handleCurrentChange(newPage) {
            console.log(newPage)
            this.pageNumber = newPage;
            // 重新发起请求用户列表
            this.getAdList();
        },
        // 监听 当前页大小 改变的事件
        handleSizeChange(newPageSize) {
            // 重新设置每页显示的条数
            this.pageSize = newPageSize;
            // 重新发起请求用户列表
            this.getAdList();
        },
        //修改用户
        editAd() {
            this.editForm.status = this.editForm.status ? 1 : 0;
            AdUpdate(this.editForm)
                .then((res) => {
                    if (res.data.code === 200) {
                        this.editDialogVisible = false;
                        this.AdDialogVisible = false;
                        this.getAdList();
                        this.$message({
                            message: "修改公告成功",
                            type: "success",
                        });
                    } else {
                        this.$message.error("修改公告失败:" + res.data.description);
                        // 重置修改信息表
                        this.editForm = { ...this.preEditForm }
                    }
                })
                .catch((err) => {
                    this.$message.error("修改公告异常");
                    console.loge(err);
                });
        }, async deleteAd(id) {
            // 弹框 询问用户是否删除
            const confirmResult = await this.$confirm(
                "此操作将永久删除该公告, 是否继续?",
                "提示",
                {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }
            )
            if (confirmResult === "confirm") {
                deleteAd(id).then((res) => {
                    this.getAdList();
                    if (res.data.code === 200) {
                        this.$message.success("公告删除成功");
                    } else {
                        this.$message.error("公告删除失败");
                    }
                }).catch((err) => {
                    this.$message.error("公告删除异常");
                    console.log(err);
                });
            }
        },
        //添加用户
        addAd() {
            adAdd(this.AdForm)
                .then((res) => {
                    if (res.data.code === 200) {
                        this.addDialogVisible = false;
                        this.getAdList();
                        this.$message({
                            message: "添加公告成功",
                            type: "success",
                        });
                        // 清空添加公告信息表
                        for (let key in this.AdForm) {
                            this.AdForm[key] = "";
                        }
                    } else {
                        this.$message.error("添加公告失败:" + res.data.description);
                    }
                })
                .catch((err) => {
                    this.$message.error("添加公告异常,请正确填写字段！")
                    console.log(err);
                });
        },
        // 取消添加公告
        addDialogClosed() {
            // 清空添加公告信息表
            for (let key in this.AdForm) {
                this.AdForm[key] = "";
            }
        },
    },

}
</script>

<style></style>