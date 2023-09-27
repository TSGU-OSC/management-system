import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { TeacherForm, TeacherPageVO, TeacherQuery } from './types';



/**
 * 获取teacher分页列表
 *
 * @param queryParams
 */
export function getTeacherPage(
  queryParams: TeacherQuery
): AxiosPromise<PageResult<TeacherPageVO[]>> {
  return request({
    url: '/api/teachers/page',
    method: 'get',
    params: queryParams
  });
}

/**
 * 获取Teacher表单详情
 *
 * @param id
 */
export function getTeacherForm(id: number): AxiosPromise<TeacherForm> {
  return request({
    url: '/api/teachers/' + id + '/form',
    method: 'get'
  });
}

/**
 * 添加Teacher
 *
 * @param data
 */
export function addTeacher(data: TeacherForm) {
  return request({
    url: '/api/teachers',
    method: 'post',
    data: data
  });
}

/**
 * 修改Teacher
 *
 * @param id
 * @param data
 */
export function updateTeacher(id: number, data: TeacherForm) {
  return request({
    url: '/api/teachers/' + id,
    method: 'put',
    data: data
  });
}


/**
 * 删除Teacher
 *
 * @param id
 */
export function deleteTeacher(ids: string) {
  return request({
    url: '/api/teachers/' + ids,
    method: 'delete'
  });
}

