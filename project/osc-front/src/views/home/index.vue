<template>
    <div class="main-container">
        <el-row :gutter="20">
            <!-- 左侧主区块，占据75%，用于仪表盘展示 -->
            <el-col :span="18" :offset="0">
                <div class="left-content">

                </div>
            </el-col>

            <!-- 右侧侧边区块，占据25%，用于快捷功能和最近访问 -->
            <el-col :span="6" :offset="0">
                <div class="right-content">
                    <!-- 分为上下两个，各占50% -->
                    <div class="right-top">

                    </div>
                    <div class="right-bottom">

                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>

</script>

<style scoped>
.main-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

.main-container, .el-row {
    flex: 1;
    display: flex;
}

.main-container, .el-col {
    display: flex;
    flex-direction: column;
}

.left-content {
    flex: 1;
    background-color: #f5f7fa;
    border-radius: 5px;
    padding: 20px;
    min-height: 300px;
}

.right-content {
    flex: 1;
    display: flex;
    flex-direction: column;
}

.right-top, .right-bottom {
    background-color: #f5f7fa;
    border-radius: 5px;
    padding: 20px;
}

.right-top {
    flex: 1;
}

.right-bottom {
    flex: 1;
    margin-top: 20px;
}
</style>