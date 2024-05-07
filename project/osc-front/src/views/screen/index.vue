<template>
  <div class="Echarts">
    <div id="gender"  style="width: 30%;height:600px;float: left"></div>
    <div id="main" style="width: 70%;height:600px;float: right"></div>
  </div>
</template>

<script>
import chinaJson from './china.json';
import {userGenderCount, userProvinceCount} from "../../api/user";
export default {
  name: 'Echarts',
  data(){
    return {
      content: [],
      gender: [],
    }
  },
  methods:{
    myEcharts1(){
      // 基于准备好的dom，初始化echarts实例
      const myChart = this.$echarts.init(document.getElementById('gender'));
      // 指定图表的配置项和数据
      const option = {
        title: {
          text: '开源鸿蒙社男女比例',
          left: 'center'
        },
        color: ['#7B68EE','#FFC0CB'],
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '人数',
            type: 'pie',
            radius: '70%',
            data: this.gender,
            emphasis: {
              itemStyle: {

                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      userGenderCount().then((res) => {
        if (res.data.code === 200) {
          this.gender.push({"value": res.data.data[0],"name": '男'});
          this.gender.push({"value": res.data.data[1],"name": '女'});
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option);
        } else {
          this.$message.error("获取用户信息失败")
        }
      }).catch((err) => {
        this.$message.error("获取用户信息异常");
      })
    },
    myEcharts2(){
      // 基于准备好的dom，初始化echarts实例
      const myChart = this.$echarts.init(document.getElementById('main'));
      this.$echarts.registerMap('China', chinaJson);
      // 指定图表的配置项和数据
      const option = {
        title: {
          text: '开源鸿蒙社人员分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          transitionDuration: 0.2
        },
        visualMap: {
          left: 'right',
          min: 0,
          max: 50,
          inRange: {
            color: [
              '#ffffbf',
              '#fee090',
              '#fdae61',
              '#f46d43',
              '#d73027',
              '#a50026'
            ]
          },
          text: ['High', 'Low'],
          calculable: true
        },
        series: [
          {
            name: '地区/人数',
            type: 'map',
            map: 'China',
            layoutCenter: ['50%', '50%'],
            layoutSize: '600',
            // label: {
            //   show: true
            // },
            emphasis: {
              label: {
                show: true
              }
            },
            data: this.content
          }
        ]
      };
      userProvinceCount().then((res) => {
        if (res.data.code === 200) {
          console.log(res.data.data)
          res.data.data.forEach(obj=>{
            let province=obj.province;
            let count=obj.count;
            let con={"name": province,"value": count}
            this.content.push(con)
          })
          console.log("testdata",this.content)
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option);
        } else {
          this.$message.error("获取用户信息失败")
        }
      }).catch((err) => {
        this.$message.error("获取用户信息异常");
      })
    }
  },
  mounted() {
    this.myEcharts1();
    this.myEcharts2();
  }
}
</script>

<style>

</style>
