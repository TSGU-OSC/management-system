<template>
    <div style="text-align: center;">

        <el-empty>
        </el-empty>


           <p> 你似乎来到了无人之境</p>

            <el-button type="primary" @click="goBack">返回首页</el-button>




    </div>
</template>


<script>
export default {
    created() {
        const h = this.$createElement;

        this.$notify({
            title: '页面不存在！',
            message: h('b', { style: 'color: teal' }, '你所找的页面不存在！')
        })
    },
    methods: {
        goBack() {
            this.$router.push('/home');
        }
    }

}





</script>
<style>
.bottom {
    margin-top: 13px;
    line-height: 12px;
}

.button {
    padding: 0;
    float: right;
}

.image {
    width: 100%;
    display: block;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}
</style>
