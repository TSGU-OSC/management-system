<script lang="ts">
export default {
  name: 'teacher'
};
</script>

<script setup lang="ts">

import {
  getTeacherPage,
  getTeacherForm,
  addTeacher,
  updateTeacher,
  deleteTeacher
} from '@/api/teacher';


import { TeacherForm, TeacherQuery, TeacherPageVO } from '@/api/teacher/types';
import { ElForm, ElMessage, ElMessageBox } from 'element-plus';
import { ref, reactive, onMounted } from 'vue';

const queryFormRef = ref(ElForm); // 查询表单
const teacherFormRef = ref(ElForm); // teacher表单

const loading = ref(false);
const ids = ref([]);
const total = ref(0);
const dialog = reactive<DialogOption>({
  visible: false
});

const queryParams = reactive<TeacherQuery>({
  pageNum: 1,
  pageSize: 10
});
const teacherList = ref<TeacherPageVO[]>();

const formData = reactive<TeacherForm>({
  
});

const rules = reactive({
    teachername: [{ required: true, message: 'teacher姓名不能为空', trigger: 'blur' }],
    teacherage: [{ required: true, message: 'teacher年龄不能为空', trigger: 'blur' }],
    teacheraddress: [{ required: true, message: '地址不能为空', trigger: 'blur' }],
  
});

/**
 * 查询
 */
function handleQuery() {
  loading.value = true;
  getTeacherPage(queryParams)
    .then(({ data }) => {
        teacherList.value = data.list;
      total.value = data.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

/**
 * 重置查询
 */
function resetQuery() {
  queryFormRef.value.resetFields();
  queryParams.pageNum = 1;
  handleQuery();
}

/**
 * 行checkbox change事件
 */
function handleSelectionChange(selection: any) {
  ids.value = selection.map((item: any) => item.id);
}



/**
 * 打开teacher信息弹窗
 *
 * @param userId 用户ID
 */
async function openDialog(teacherId?: number) {
  
  dialog.visible = true;
  if (teacherId) {
    dialog.title = '修改教师';
    getTeacherForm(teacherId).then(({ data }) => {
      Object.assign(formData, data);
    });
  } else {
    dialog.title = '新增教师';
  }
}

/**
 * 关闭用户弹窗
 */
function closeDialog() {
  dialog.visible = false;
  resetForm();
}

/**
 * 重置表单
 */
function resetForm() {
    teacherFormRef.value.resetFields();
    teacherFormRef.value.clearValidate();

  formData.id = undefined;
  
}

/**
 * 表单提交
 */
function handleSubmit() {
    teacherFormRef.value.validate((valid: any) => {
    if (valid) {
      const teacherId = formData.id;
      loading.value = true;
      if (teacherId) {
        updateTeacher(teacherId, formData)
          .then(() => {
            ElMessage.success('修改用户成功');
            closeDialog();
            resetQuery();
          })
          .finally(() => (loading.value = false));
      } else {
        addTeacher(formData)
          .then(() => {
            ElMessage.success('新增用户成功');
            closeDialog();
            resetQuery();
          })
          .finally(() => (loading.value = false));
      }
    }
  });
}

/**
 * 删除用户
 */
function handleDelete(id?: number) {
  const teacherIds = [id || ids.value].join(',');
  if (!teacherIds) {
    ElMessage.warning('请勾选删除项');
    return;
  }

  ElMessageBox.confirm('确认删除教师信息?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(function () {
    deleteTeacher(teacherIds).then(() => {
      ElMessage.success('删除成功');
      resetQuery();
    });
  });
}
onMounted(() => {
  handleQuery();
});

</script>

<template>
  <div class="app-container">
        <div class="search">
          <!-- 搜索表单 -->
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="关键字" prop="keywords">
              <el-input
                v-model="queryParams.keywords"
                placeholder=""
                clearable
                style="width: 200px"
                @keyup.enter="handleQuery"
              />
            </el-form-item>

      

            <el-form-item>
              <el-button type="primary" @click="handleQuery"
                ><i-ep-search />搜索</el-button
              >
              <el-button @click="resetQuery">
                <i-ep-refresh />
                重置</el-button
              >
            </el-form-item>
          </el-form>
        </div>

        <el-card shadow="never">
          <template #header>
            <div class="flex justify-between">
              <div>
                <el-button
                  type="success"
                  @click="openDialog()"
                  
                  ><i-ep-plus />新增</el-button
                >
                <el-button
                  type="danger"
                  :disabled="ids.length === 0"
                  @click="handleDelete()"
                  
                  ><i-ep-delete />删除</el-button
                >
              </div>
             
            </div>
          </template>

          <el-table
            v-loading="loading"
            :data="teacherList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column
              key="id"
              label="编号"
              align="center"
              prop="id"
              width="100"
            />
            <el-table-column
              key="teacherName"
              label="教师名"
              align="center"
              prop="teacherName"
            />
            <el-table-column
              label="教师年龄"
              width="100"
              align="center"
              prop="teacherAge"
            />

            <el-table-column
              label="地址"
              width="120"
              align="center"
              prop="teacherAddress"
            />

          
            <el-table-column label="操作" fixed="right" width="220">
              <template #default="scope">
              
                <el-button
                  type="primary"
                  link
                  size="small"
                  @click="openDialog(scope.row.id)"
                  
                  ><i-ep-edit />编辑</el-button
                >
                <el-button
                  type="primary"
                  link
                  size="small"
                  @click="handleDelete(scope.row.id)"
                  
                  ><i-ep-delete />删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-if="total > 0"
            v-model:total="total"
            v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize"
            @pagination="handleQuery"
          />
        </el-card>
    

    <!-- 表单弹窗 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="600px"
      append-to-body
      @close="closeDialog"
    >
      <el-form
        ref="teacherFormRef"
        :model="formData"
        :rules="rules"
        label-width="80px"
      />
        <el-form-item label="teacher姓名" prop="teacherName">
          <el-input
            :readonly="!!formData.id"
            v-model="formData.teacherName"
            placeholder="请输入teacher名"
          />
        </el-form-item>

        <el-form-item label="teacher年龄" prop="teacherAge">
          <el-input v-model="formData.teacherAge" placeholder="请输入teacher年龄" />
        </el-form-item>


        <el-form-item label="地址" prop="teacherAddress">
          <el-input
            v-model="formData.teacherAddress"
            placeholder="请输入地址"
           
          />
        </el-form-item>

        
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleSubmit">确 定</el-button>
          <el-button @click="closeDialog">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    
  </div>
</template>
