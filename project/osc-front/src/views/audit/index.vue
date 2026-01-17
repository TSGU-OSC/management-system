<template>
    <div class="audit-main-container">
        <div class="audit-search-warpper">
            <div class="audit-select">
                <!-- 整个输入区域使用 el-row 实现左右分栏 -->
                <el-row :gutter="24" type="flex" align="top">

                    <!-- 左侧输入框和选择框区域：占据 18/24 = 75% -->
                    <el-col :xs="24" :sm="24" :md="18" :lg="18" class="select-left">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-input placeholder="请输入姓名" clearable></el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-input placeholder="请输入学号" clearable></el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-select placeholder="请选择部门" clearable style="width: 100%;">
                                    <el-option label="社长团" value="0"></el-option>
                                    <el-option label="技术部" value="1"></el-option>
                                    <el-option label="宣传部" value="2"></el-option>
                                    <el-option label="运营部" value="3"></el-option>
                                </el-select>
                            </el-col>
                        </el-row>
                    </el-col>

                     <!-- 右侧按钮区域：占据 6/24 = 25% -->
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" class="select-right">
                        <div class="button-group">
                            <div class="left-button">
                                <el-button type="primary" icon="el-icon-search">查询</el-button>
                            </div>
                            <div class="right-button">
                                <el-button icon="el-icon-refresh">重置</el-button>
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </div>
        </div>

        <div class="audit-showTable">
            <!--显示用户信息表格-->
            <el-table border stripe>
                <el-table-column prop="createTime" label="申请时间"></el-table-column>
                <el-table-column prop="name" label="姓名"></el-table-column>
                <el-table-column prop="status" label="状态">
                    <template v-slot:default="scope">
                        <el-tag type="success" v-if="scope.row.status === 2">待通过</el-tag>
                        <el-tag type="danger" v-if="scope.row.status === 3">未通过</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="code" label="学号"></el-table-column>
                <el-table-column prop="clazz" label="班级"></el-table-column>
                <el-table-column label="操作" fixed="right" 
                    v-if="this.$store.state.user.role !== 0">
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
        </div>
    </div>
</template>

<script>

</script>

<style scoped>
.audit-main-container {
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    box-sizing: border-box;
}

.audit-search-warpper {
    display: flex;
    flex-direction: column;
    min-height: 0;
}

.audit-select {
    background: #fff;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 8px #f0f1f2;
    flex-shrink: 0;
}

.audit-showTable {
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
</style>