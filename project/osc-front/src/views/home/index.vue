<template>
    <div class="home-main-container">
        <el-row :gutter="20">
            <!-- 左侧主区块，占据75%，用于仪表盘展示 -->
            <el-col :span="18" :offset="0">
                <div class="home-left-content">

                </div>
            </el-col>

            <!-- 右侧侧边区块，占据25%，用于快捷功能和最近访问 -->
            <el-col :span="6" :offset="0">
                <div class="home-right-content">
                    <!-- 分为上下两个，各占50% -->
                    <div class="home-right-top">

                    </div>
                    <div class="home-right-bottom">

                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>

</script>

<style scoped>
.home-main-container {
    min-height: 100%;
    display: flex;
    flex-direction: column;
}

.home-main-container .el-row {
    flex: 1;
    display: flex;
}

.home-main-container .el-col {
    display: flex;
    flex-direction: column;
}

.home-left-content {
    flex: 1;
    background-color: #f5f7fa;
    border-radius: 5px;
    padding: 20px;
    min-height: 300px;
}

.home-right-content {
    flex: 1;
    display: flex;
    flex-direction: column;
}

.home-right-top, .home-right-bottom {
    background-color: #f5f7fa;
    border-radius: 5px;
    padding: 20px;
}

.home-right-top {
    flex: 1;
}

.home-right-bottom {
    flex: 1;
    margin-top: 20px;
}
</style>