<template>
  <!-- <el-row>
      <el-card class="box-card">
        <div class="text item">
          {{'当前注册用户数 :' + countUserDTO.total }}
        </div>
        <div  class="text item">
          {{'当前正常用户数: ' + countUserDTO.enable }}
        </div>
     </el-card>
  </el-row> -->
  <div>
    <!-- <el-row style="text-align: center;height: 400px;width: 100%">
      
      <el-carousel style="height: 400px;" >
      <el-carousel-item v-for="(item,index) in images" :key="index" style="height: 500px; " >
            <el-image
                :class="className"
                style="width:100%; height:500px"
                :src="item.idView"
                fit="fit"
                ></el-image>
            </el-carousel-item>
        </el-carousel>
      </el-row> -->
    <el-row>
      <el-col :span="24">
        <p style="text-align: center;font-family: 楷书;font-size: 20px;color: rgb(0, 26, 255);">社团工具</p>
        <div class="grid-content  show">
          <el-tooltip class="item" effect="dark" content="社团Gitee仓库" placement="bottom">
            <a href="https://gitee.com/TSGU-OSC"> <i class="iconfont icon-gitee"></i></a>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="社团AI绘画工具" placement="bottom">
            <a href="http://172.19.15.12:7860"><i class="iconfont icon-AIhuihua"></i></a>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="社团公众号" placement="bottom">
            <a href="https://mp.weixin.qq.com/s/tezHi4tD7jHyq1_MDgSbCw"><i class="iconfont icon-gongzhonghao"></i></a>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="社团Alist存储系统" placement="bottom">
            <a href="http://172.19.15.13:5244"><i class="iconfont icon-baocun"></i></a>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="社团Gitlab" placement="bottom">
            <a href="http://82.157.80.221:2000"><i class="iconfont icon-Gitlab-fill"></i></a></el-tooltip>
        </div>

      </el-col>
    </el-row>

    <el-row style="height: 600px;">
      <el-col :span="24" style="text-align: center;">
        <p style="text-align: center;font-family: 楷书;font-size: 20px;color: rgb(0, 26, 255);">社团简介</p>
        <p>我们是一支致力于推动开源技术发展的社团，名为“开源鸿蒙社”。</p>
        <img src="../../assets/img/logo.jpg">
        <img src="../../assets/img/pic1.jpg">
        <img src="../../assets/img/pic2.jpg">
        <img src="../../assets/img/pic4.jpg">
        <img src="../../assets/img/pic5.jpg">
        <img src="../../assets/img/pic3.jpg">
      </el-col>

    </el-row>


    <el-row>
      <el-col :span="24">
        <div class="grid-content  showdata ">
          <div>
            <p style="text-align: center;font-family: 楷书;font-size: 20px;color: rgb(0, 26, 255);margin-right: 100px;">社团信息
            </p>
          </div>
          <div class="data">
            <i class="iconfont icon-tijiao "></i>
            <p style="font-family: 幼圆;">PR数量</p>
            <p class="num"><VueCountUp :start-value="0" :end-value="157" /></p>
          </div>
          <div class="data">
            <i class="iconfont icon-huodong"></i>
            <p style="font-family: 幼圆;">活动数量</p>
            <p class="num"><VueCountUp :start-value="0" :end-value="18" /></p>
          </div>
          <div class="data">
            <i class="iconfont icon-cangku"></i>
            <p style="font-family: 幼圆;">仓库数量</p>
            <p class="num"><VueCountUp :start-value="0" :end-value="13" /></p>
          </div>
          <div class="data">
            <i class="iconfont icon-fork "></i>
            <p style="font-family: 幼圆;">fork数量</p>
            <p class="num"><VueCountUp :start-value="0" :end-value="123" /></p>
          </div>
          <div class="data">
            <i class="iconfont icon-renshu"></i>
            <p style="font-family: 幼圆;">成员数量</p>
            <p class="num"><VueCountUp :start-value="0" :end-value="115" /></p>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24" style="text-align: center;">
        <p style="text-align: center;font-family: 楷书;font-size: 20px;color: rgb(0, 26, 255);">联系我们</p>
        <p>如果你对开源技术充满热情，想要了解更多的开源项目和技术，欢迎加入我们的社团。请发送申请邮件至（填写社团联系人的邮箱），我们将尽快与你联系。</p>
        <p>邮箱：jacklau1222@foxmail.com</p>
      </el-col>
    </el-row>
  </div>
</template>

  <!-- <script src="https://cdn.bootcss.com/countup.js/1.9.3/countUp.js"></script> -->
<script>
// import { userCount } from '@/api/user';
import VueCountUp from 'vue-countupjs'
import "../../assets/icon/iconfont.css";
export default {

  data() {
    // return {
    //   currentDate: new Date(),
    //   countUserDTO: {
    //     total: "",
    //     enable: "",
    //   }
    // };

    // mounted() {
    //   this.getCount();
    // },
    // methods: {
    //   getCount() {
    //     userCount()
    //       .then((res) => {
    //         if (res.data.code === 200) {
    //           this.countUserDTO.total = res.data.data.total,
    //             this.countUserDTO.enable = res.data.data.enable;
    //         }
    //         else {
    //           this.$message.error("统计用户失败");
    //         }
    //       })
    //       .catch((err) => {
    //         this.$message.error("统计用户异常！");
    //         console.log(err);
    //       });
    //   },
    // },
    // }
    return {
      options: {
        useEasing: true,
        useGrouping: true,
        separator: '',
        decimal: '',
        prefix: '',
        suffix: ''
      },
      currentDate: new Date(), 
      // images:[ 
      //           {id: 0, idView: require('../../assets/img/pic2.png')},
      //           {id: 1, idView: require('../../assets/img/pic5.jpg')},
      //           {id: 2, idView: require('../../assets/img/pic3.jpg')},
      //           {id: 3, idView: require('../../assets/img/pic4.jpg')},
      // ],
    };

  },
  name: 'countUp',
  components: {
    VueCountUp,
  },
 
}
</script>

<style scoped>


  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    /* line-height: 500px; */
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }


img {
  width: 360px;
  height: 220px;
}

.iconfont {
  font-size: 40px;
}

.data {
  float: left;
  margin-right: 30px
}

.show {
  text-align: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.num {
  font-size: 20px;
  font-weight: bolder;
}

.showdata {
  text-align: center;
  display: flex;
  position: absolute;
  ;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }

  /* background-color: lightblue; */
  border: 1px dotted;
  height: 200px;

}

.el-col {
  border-radius: 4px;

}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  height: 200px;
  background-color: lightblue;
}

.item {
  /* margin: 4px; */
  padding: 10px;
}

i {
  margin: 20px;
}

a {
  text-decoration: none
}

a {
  color: inherit;
}

/*正常的未被访问过的链接*/
a:link {
  text-decoration: none;
}

/*已经访问过的链接*/
a:visited {
  text-decoration: none;
}

/*鼠标划过(停留)的链接*/
a:hover {
  text-decoration: none;
}

/* 正在点击的链接*/
a:active {
  text-decoration: none;
}
</style>
