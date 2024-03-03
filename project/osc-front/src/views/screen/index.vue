<template>
    <Map ref="map" :content="content"  />
</template>

<script>
// 引入echarts以及中国地图china.js

import * as echarts from 'echarts';


import 'echarts/map/js/china'
import Map from '../../components/map/map';
import axios from 'axios';
export default {
    data(){
        return {
            content:[]
        }
    },
    components: {
        Map
    },
    methods: {
    },
    mounted() {
        axios.get("/api/user/count/province").then(res => {
            if (res.data.code === 200) {
                console.log(res)
                this.content=res.data.data.map(obj=>{
                    return {name:obj.province,value:obj.count}
                })
                console.log("testdata",this.content)
            } else {
                this.$message.error("获取用户信息失败")
            }
        }).catch((err) => {
            this.$message.error("获取用户信息异常");
        })
    }

}


</script>