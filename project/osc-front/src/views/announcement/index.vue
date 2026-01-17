<template>
    <div class="announcement-main-container">
        <div class="announcement-content-warpper">
            <div class="announcement-search">
                <!-- 整个查询区域使用 el-row 实现左右分栏 -->
                <el-row :gutter="24" type="flex" align="top">

                    <!-- 左侧查询区域：占据 18/24 = 75% -->
                    <el-col :xs="24" :sm="24" :md="16" class="search-left">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-input placeholder="请输入公告编号" clearable></el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-input placeholder="请输入公告标题" clearable></el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-select placeholder="请选择标签" clearable style="width: 100%;">
                                    <el-option label="标签1" value="0"></el-option>
                                    <el-option label="标签2" value="1"></el-option>
                                    <el-option label="标签3" value="2"></el-option>
                                    <el-option label="标签4" value="3"></el-option>
                                </el-select>
                            </el-col>
                        </el-row>
                    </el-col>

                    <!-- 右侧按钮区域：占据 6/24 = 25% -->
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" class="search-tight">
                        <div class="button-group">
                            <div class="left-button">
                                <el-button type="primary" icon="el-icon-search">查询</el-button>
                            </div>
                            <div class="right-button">
                                <el-button icon="el-icon-refresh">重置</el-button>
                            </div>

                            <!-- 单独区分开添加按钮 -->
                            <div class="plus-button">
                                <el-button type="primary" icon="el-icon-plus">添加</el-button>
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </div>

            <div class="announcement-showTable">
                <el-table :data="adList" border style="width: 100%">
                    <el-table-column prop="id" label="公告编号">
                    </el-table-column>
                    <el-table-column prop="title" label="公告标题" style="color: red;">
                    </el-table-column>
                    <el-table-column prop="status" label="标签">
                        <template v-slot:default="scope">
                            <el-tag type="info" v-if="scope.row.status == 0">日常公告</el-tag>
                            <el-tag type="danger" v-if="scope.row.status == 1">紧急公告</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间">
                    </el-table-column>
                    <el-table-column prop="updateTime" label="更新时间">
                    </el-table-column>
                    <el-table-column prop="createUser" label="创建者ID">
                    </el-table-column>
                    <el-table-column prop="updateUser" label="更新者ID">
                    </el-table-column>

                    <el-table-column fixed="right" label="操作">
                        <template slot-scope="scope">
                            <el-button @click="showAdDetail(scope.row)" type="text" size="small">详情</el-button>
                            <el-button type="text" size="small" @click="showEditDialog(scope.row)"
                                v-if="$store.state.user.role >= 1">编辑</el-button>
                            <el-button @click="deleteAd(scope.row.id)" type="text" size="small"
                                v-if="$store.state.user.role >= 1">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>

<script>
    
</script>

<style scoped>
.announcement-main-container {
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    box-sizing: border-box;
}

.announcement-content-warpper {
    display: flex;
    flex-direction: column;
    min-height: 0;
}

.announcement-search, .announcement-showTable {
    background: #fff;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 8px #f0f1f2;
    flex-shrink: 0;
}

.button-group {
    display: flex;
    align-items: center;
    height: 100%;
    gap: 8px;
}

.left-button, .right-button {
    flex-shrink: 0;
}

.plus-button {
    margin-left: 20px;
}
</style>