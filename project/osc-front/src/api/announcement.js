export function getAd(data) {
    return request({
      url: '/api/announce/search',
      method: 'get',
      data
    })
}
  