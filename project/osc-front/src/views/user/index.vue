<template>
    <div class="users-main-container">
        <div class="users-content-warpper">
            <div class="users-search">
                <!-- 整个查询区域使用 el-row 实现左右分栏 -->
                <el-row :gutter="20" type="flex" align="top">

                    <!-- 左侧表单区：占据 18/24 = 75% -->
                    <el-col :span="18" class="search-left">
                        <!-- 第一行 -->
                        <el-row :gutter="20" class="left-first-row">
                            <el-col :span="8">
                                <el-input placeholder="请输入成员姓名" clearable></el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-input placeholder="请输入成员学号" clearable></el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-input placeholder="请输入成员手机号" clearable></el-input>
                            </el-col>
                        </el-row>

                        <!-- 第二行 -->
                        <el-row :gutter="20" class="left-second-row">
                            <el-col :span="8">
                                <el-select placeholder="请选择部门" clearable style="width: 100%;">
                                    <el-option label="社长团" value="0"></el-option>
                                    <el-option label="技术部" value="1"></el-option>
                                    <el-option label="宣传部" value="2"></el-option>
                                    <el-option label="运营部" value="3"></el-option>
                                </el-select>
                                <!-- <el-input placeholder="请选择部门"></el-input> -->
                            </el-col>

                            <el-col :span="8">
                                <el-select placeholder="请选择角色" clearable style="width: 100%;">
                                    <el-option label="超管" value="0"></el-option>
                                    <el-option label="管理员" value="1"></el-option>
                                    <el-option label="普通成员" value="2"></el-option>
                                </el-select>
                                <!-- <el-input placeholder="请选择角色"></el-input> -->
                            </el-col>

                            <el-col :span="8">
                                <el-select placeholder="请选择状态" clearable style="width: 100%;">
                                    <el-option label="正常" value="0"></el-option>
                                    <el-option label="离职" value="1"></el-option>
                                </el-select>
                                <!-- <el-input placeholder="请选择状态"></el-input> -->
                            </el-col>
                        </el-row>  
                    </el-col>

                    <!-- 右侧按钮区：占据 6/24 = 25% -->
                    <el-col :span="6" class="right-buttom">
                        <div class="buttom-group">
                            <div class="up-bottom">
                                <el-button type="primary" icon="el-icon-search">查询</el-button>
                            </div>
                            <div class="down-buttom">
                                <el-button icon="el-icon-refresh">重置</el-button>
                            </div>
                            
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

            <div class="users-showTabel">
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

</script>

<style scoped>
.user-main-container {
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    box-sizing: border-box;
}

.users-content-warpper {
    flex: 1;
    display: flex;
    flex-direction: column;
    min-height: 0;
}

.users-search {
    background: #fff;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 8px #f0f1f2;
    flex-shrink: 0;
}

/* 左侧查询区域样式 */
.users-search .search-left {
    display: flex;
    flex-direction: column;
}

/* 查询框样式 */
.users-search .left-first-row, 
.users-search .left-right-row {
    margin-bottom: 20px;
    display: flex;
}

/* 右侧按钮区域样式 */
.users-search .right-bottom {
    display: flex;
    justify-content: center;
    align-items: flex-start;
    padding-left: 20px;
    border-left: 1px solid #e8e8e8;
}

/* 按钮组容器 */
.users-seach .buttom-group {
    width: 100%;
    max-width: 200px; /* 控制按钮的最大高度，避免在大屏幕上过宽 */
}

.buttom-group .up-bottom {
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

    .users-search .buttom-group {
        max-width: 100%;
    }
}

.users-search .el-col {
    /* padding-left: 20px; */
    display: flex;
    align-items: center;
}

.users-showTabel {
    flex: 1;
    background: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 8px #f0f1f2;
    min-height: 0;
    overflow-y: auto;
} 
</style>