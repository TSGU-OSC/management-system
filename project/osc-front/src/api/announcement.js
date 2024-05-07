import request from "@/utils/request"
export function getAd(data,pageNumber,pageSize) {
  return request({
    url: '/api/announce/search',
    method: 'get',
    params: {
      title: data,
      pageNumber: pageNumber,
      pageSize: pageSize,
    },
  })
}
//修改用户
export function AdUpdate(data) {
  return request({
    url: '/api/announce/update',
    method: 'post',
    data
  })
}

export function deleteAd(data) {
  return request({
    url: '/api/announce/delete',
    method: 'post',
    params: {
      id: data
    }
  })
}

//添加公告
export function adAdd(data) {
  return request({
    url: '/api/announce/add',
    method: 'post',
    data
  })
}

