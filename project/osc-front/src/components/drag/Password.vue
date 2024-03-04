<template>
      <div class="streng-box">
        <div class="schedule">
            <span class="bg-span p-a"></span>
            <span class="bg-span p-b"></span>
            <span class="bg-span p-c"></span>
            <span v-if="strength >= 1" class="low p-a"></span>
            <span v-if="strength >= 2" class="centre p-b"></span>
            <span v-if="strength >= 3" class="tall p-c"></span>
        </div>
        <div class="schedule-text">
            <span class="low">弱</span>
            <span class="centre">中</span>
            <span class="tall">强</span>
        </div>
          
    </div>
</template>
    
    <script>
    export default {
        components: {},
        props: {
            password: { // 密码
                type: String,
                default: ''
            }
        },
        data() {
            return {}
        },
        computed: {
            // 密码强度 0~3  0无强度、1低强度、2中强度、3高强度
            strength({ password }) { //验证密码强度的函数
                var val = password || '';
                var lv = 0; //初始化提示消息为空
                if (val.match(/[a-z]/g)) { lv++; } //验证是否包含字母
                if (val.match(/[0-9]/g)) { lv++; }  // 验证是否包含数字
                if (val.match(/(.[^a-z0-9])/g)) { lv++; } //验证是否包含字母，数字，字符
                if (val.length < 6) { lv = 0; } //如果密码长度小于6位，提示消息为空
                if (lv > 3) { lv = 3; }
                return lv
            }
        },
        watch: {
            // 密码强度 0~3  0无强度、1低强度、2中强度、3高强度
            strength({ password }) { //验证密码强度的函数
                var val = password || '';
                var lv = 0; //初始化提示消息为空
                if (val.match(/[a-z]/g)) { lv++; } //验证是否包含字母
                if (val.match(/[0-9]/g)) { lv++; }  // 验证是否包含数字
                if (val.match(/(.[^a-z0-9])/g)) { lv++; } //验证是否包含字母，数字，字符
                if (val.length < 6) { lv = 0; } //如果密码长度小于6位，提示消息为空
                if (lv > 3) { lv = 3; }
                return lv
            }
        },
        created() { },
        mounted() { },
        methods: {}
    }
</script>
    
    <style scoped>
      .streng-box {
        width: 100%;
      }
      .schedule {
        position: relative;
      }
      .schedule span {
        width: 45px;
        height: 8px;
        border-radius: 2px;
      }
      .schedule .bg-span {
        background-color: #EBEEF5;
      }
      .schedule .p-a {
        position: absolute;
        top: 0;
        left: 0;
      }
      .schedule .p-b {
        position: absolute;
        top: 0;
        left: 50px;
      }
      .schedule .p-c {
        position: absolute;
        top: 0;
        left: 100px;
      }
      .schedule .low {
        z-index: 9;
        background: #F56C6C;
      }
      .schedule .centre {
        z-index: 9;
        background: #E6A23C;
      }
      .schedule .tall {
        z-index: 9;
        background: #67C23A;
      }
    
      .schedule-text {
        padding-top: 10px;
        font-size: 13px;
        
      }
      .schedule-text span {
        width: 45px;
        height: 20px;
        font-weight: 400px;
        display: inline-block;
        line-height: 20px;
        text-align: center;
        margin-right: 5px;
      }
    
      
    
      .schedule-text .low {
        color: #F56C6C;
      }
      .schedule-text .centre {
        color: #E6A23C;
      }
      .schedule-text .tall {
        color: #67C23A;
      }
    </style>
    