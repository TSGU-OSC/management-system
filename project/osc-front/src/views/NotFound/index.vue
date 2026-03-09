<template>
    <div class="notFound-content" style = "text-align: center;">
        <el-empty></el-empty>
        <p> 404 Not Found ! </p>
        <div class="notFound-buttonGroup">
            <el-button type = "primary" @click="goBackHome">返回首页</el-button>
            <el-button @click="goBackPrevious">返回上一页</el-button>
        </div>
            
        <!-- 可以优化（添加）为返回上一页 -->
    </div>
</template>

<script>
export default {
    // created() {
    //     const h = this.$createElement;

    //     this.$notify({
    //         title: '页面不存在！',
    //         message: h('b', { style: 'color: teal' }, '你所找的页面不存在！')
    //     })
    // },
    methods: {
        goBackHome() {
            this.$router.push('/home');
        },
        goBackPrevious() {
            this.$router.back();
        }
    }
}
</script>

<style>

</style>
