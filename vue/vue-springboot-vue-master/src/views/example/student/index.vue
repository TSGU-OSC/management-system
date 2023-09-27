<script lang="ts">
export default {
  name: 'student'
};
</script>

<script setup lang="ts">

import {
  getStudentPage,
  getStudentForm,
  addStudent,
  updateStudent,
  deleteStudent
} from '@/api/student';


import { StudentForm, StudentQuery, StudentPageVO } from '@/api/student/types';

const queryFormRef = ref(ElForm); // 查询表单
const studentFormRef = ref(ElForm); // 学生表单

const loading = ref(false);
const ids = ref([]);
const total = ref(0);
const dialog = reactive<DialogOption>({
  visible: false
});

const queryParams = reactive<StudentQuery>({
  pageNum: 1,
  pageSize: 10
});
const studentList = ref<StudentPageVO[]>();

const formData = reactive<StudentForm>({
  
});

const rules = reactive({
  studentname: [{ required: true, message: '学生姓名不能为空', trigger: 'blur' }],
  studentage: [{ required: true, message: '学生年龄不能为空', trigger: 'blur' }],
  studentaddress: [{ required: true, message: '地址不能为空', trigger: 'blur' }],
  
});

/**
 * 查询
 */
function handleQuery() {
  loading.value = true;
  getStudentPage(queryParams)
    .then(({ data }) => {
      studentList.value = data.list;
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
 * 打开学生信息弹窗
 *
 * @param userId 用户ID
 */
async function openDialog(studentId?: number) {
  
  dialog.visible = true;
  if (studentId) {
    dialog.title = '修改学生';
    getStudentForm(studentId).then(({ data }) => {
      Object.assign(formData, data);
    });
  } else {
    dialog.title = '新增学生';
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
  studentFormRef.value.resetFields();
  studentFormRef.value.clearValidate();

  formData.id = undefined;
  
}

/**
 * 表单提交
 */
function handleSubmit() {
  studentFormRef.value.validate((valid: any) => {
    if (valid) {
      const studentId = formData.id;
      loading.value = true;
      if (studentId) {
        updateStudent(studentId, formData)
          .then(() => {
            ElMessage.success('修改用户成功');
            closeDialog();
            resetQuery();
          })
          .finally(() => (loading.value = false));
      } else {
        addStudent(formData)
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
  const studentIds = [id || ids.value].join(',');
  if (!studentIds) {
    ElMessage.warning('请勾选删除项');
    return;
  }

  ElMessageBox.confirm('确认删除学生信息?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(function () {
    deleteStudent(studentIds).then(() => {
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
            :data="studentList"
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
              key="studentName"
              label="学生名"
              align="center"
              prop="studentName"
            />
            <el-table-column
              label="学生年龄"
              width="100"
              align="center"
              prop="studentAge"
            />

            <el-table-column
              label="地址"
              width="120"
              align="center"
              prop="studentAddress"
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
        ref="studentFormRef"
        :model="formData"
        :rules="rules"
        label-width="80px"
      />
        <el-form-item label="学生姓名" prop="studentName">
          <el-input
            :readonly="!!formData.id"
            v-model="formData.studentName"
            placeholder="请输入学生名"
          />
        </el-form-item>

        <el-form-item label="学生年龄" prop="studentAge">
          <el-input v-model="formData.studentAge" placeholder="请输入学生年龄" />
        </el-form-item>


        <el-form-item label="地址" prop="studentAddress">
          <el-input
            v-model="formData.studentAddress"
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
