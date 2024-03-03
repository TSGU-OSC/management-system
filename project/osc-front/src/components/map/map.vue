<template>
    <div class="about">
      <div ref="chart" style="width: 1200px;height:800px;"></div>
    </div>
  </template>
  <script>
  // 导入图表
  import echarts from 'echarts'
  // 导入中国地图
  import 'echarts/map/js/china'
  import { reactive,ref,onMounted } from 'vue'
  export default {
    props:{
        content:{
            type:Array,
            default:[]
        }
    },
    

    // 启动函数
    setup(){
      const state = reactive({
        chart:ref()
      })
   
      const init = () => {
        if(state.chart){
          // 图表的初始化
          let mycat = echarts.init(state.chart)
    
          const option = {
            title:{
              text:'开源鸿蒙社社团成员分布',
              x:'center',
              textStyle: {
                color:'#9c0505'
              }
            },
            // 数据和类型
            series: [{
              type:'map',
              map:'china',
              label:{
                show: true,
                color:'red',
                fontSize:10
              },
              // 地图大小倍数
              zoom:1.2,
              data:this.content
            //   [
                // {name:'北京',value:40000},
                // {name:'山西',value:30000},
                // {name:'内蒙古',value:5000},
                // {name:'青海',value:7000},
                // {name:'河北',value:25000},
                // {name:'广东',value:10000},
                // {name:'黑龙江',value:40000},
                // {name:'南海诸岛',value:20000},
            //   ]
            }],
            visualMap:{
              min: 800,
              max: 50000,
              text: ['High', 'Low'],
              realtime: false,
              calculable: true,
              inRange: {
                color: ['lightskyblue', 'yellow', 'orangered']
              }
            }
          }
          // 指定数据项和数据显示
          mycat.setOption(option);
        }
      }
   
      onMounted(() => {
        init();
      })
   
      return state
    }
  }
  </script>
  
  