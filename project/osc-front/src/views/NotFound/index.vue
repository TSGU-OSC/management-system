<template>
    <div style="text-align: center;">
   
        <el-empty>
        </el-empty> 
        <img
          src="../../assets/img/404.jpg"
          class="image"
        />
        <p>不好意思，页面走丢了~</p>
        <el-button type="primary" @click="goBack">返回首页</el-button>
    </div>
</template>


<script>
export default {
    created() {
        const h = this.$createElement;

        this.$notify({
        title: '页面不存在！',
        message: h('b', { style: 'color: teal'}, '你所找的页面不存在！')
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
</style>