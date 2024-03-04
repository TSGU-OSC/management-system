<template>
    <div>
        <el-row>
            <el-col :span="24">
                <el-row :gutter="25">
                    <el-col :span="6" style="margin-bottom: 20px">
                        <!--搜索区域-->
                        <el-input placeholder="请输入公告标题" v-model="queryInfo.title" clearable>
                        </el-input>
                    </el-col>
                    <el-col :span="2">
                        <el-button type="primary" icon="el-icon-search" @click="getAdList">搜索</el-button>
                    </el-col>
                </el-row>
                <el-table :data="adList" height="250" border style="width: 100%">
                    <el-table-column prop="title" label="日期" width="180">
                    </el-table-column>
                    <el-table-column prop="name" label="姓名" width="180">
                    </el-table-column>
                    <el-table-column prop="address" label="地址">
                    </el-table-column>
                </el-table>
            </el-col>

        </el-row>

    </div>
</template>

<script>
import { getAd } from "@/api/announcement";
import request from '@/utils/request';
export default {
    data() {
        return {
            // 查询参数
            queryInfo: {
                title: "",
            },
            adList: [],
        }
    },
    mounted() {
         // 获取公告信息
         this.getAdList();
    },
    methods: {
        getAdList() {
        getAd(this.queryInfo).then((res) => {
                console.log(res);
                if (res.data.code === 200) {
                    // this.total = res.data.data.total;
                    //公告列表
                    this.adList = res.data.data.list;
                } else {
                    this.$message.error(res.data.description);
                }
            }).catch((err) => {
                console.log(err);
            });
        },
    },

}
</script>

<style></style>