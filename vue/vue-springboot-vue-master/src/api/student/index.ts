import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { StudentForm, StudentPageVO, StudentQuery } from './types';



/**
 * 获取学生分页列表
 *
 * @param queryParams
 */
export function getStudentPage(
  queryParams: StudentQuery
): AxiosPromise<PageResult<StudentPageVO[]>> {
  return request({
    url: '/api/students/page',
    method: 'get',
    params: queryParams
  });
}

/**
 * 获取学生表单详情
 *
 * @param id
 */
export function getStudentForm(id: number): AxiosPromise<StudentForm> {
  return request({
    url: '/api/students/' + id + '/form',
    method: 'get'
  });
}

/**
 * 添加学生
 *
 * @param data
 */
export function addStudent(data: StudentForm) {
  return request({
    url: '/api/students',
    method: 'post',
    data: data
  });
}

/**
 * 修改学生
 *
 * @param id
 * @param data
 */
export function updateStudent(id: number, data: StudentForm) {
  return request({
    url: '/api/students/' + id,
    method: 'put',
    data: data
  });
}


/**
 * 删除学生
 *
 * @param id
 */
export function deleteStudent(ids: string) {
  return request({
    url: '/api/students/' + ids,
    method: 'delete'
  });
}

