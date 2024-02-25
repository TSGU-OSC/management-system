<template>
  <div id="demo">
   你好
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Hello Vue!'
    }
  },
  computed: {
    userName() {
      return this.$store.state.user.userName
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#demo{
  background-color: bisque;
  font-size: 20pt;
  color:darkcyan;
  margin-left: 30%;
  margin-right: 30%;
}
</style>
