import data from './data'

const ok = (payload) => ({ data: { code: 200, data: payload, description: '' } })
const parse = (d) => {
  if (!d) return {}
  if (typeof d === 'string') {
    try { return JSON.parse(d || '{}') } catch (e) { return {} }
  }
  return d
}
const delay = (ms) => new Promise(r => setTimeout(r, ms))

export default async function mockRequest(config) {
  const url = config.url || ''
  const method = (config.method || 'get').toLowerCase()
  const params = config.params || {}
  const body = parse(config.data)
  await delay(120)

  if (url === '/api/user/current' && method === 'get') {
    return ok(data.currentUser)
  }
  if (url === '/api/user/login' && method === 'post') {
    return ok({ token: 'mock-token', user: data.currentUser })
  }
  if (url === '/api/user/register' && method === 'post') {
    return ok({ id: 999, ...body })
  }
  if (url === '/api/user/logout' && method === 'post') {
    return ok(true)
  }
  if (url === '/api/user/search' && method === 'post') {
    const pageNumber = Number(params.pageNumber || 1)
    const pageSize = Number(params.pageSize || 5)
    const start = (pageNumber - 1) * pageSize
    const end = start + pageSize
    const list = data.users.slice(start, end)
    return ok({ total: data.users.length, list })
  }
  if (url === '/api/user/add' && method === 'post') {
    return ok({ id: Date.now(), ...body })
  }
  if (url === '/api/user/update' && method === 'post') {
    return ok({ ...body })
  }
  if (url === '/api/user/delete' && method === 'post') {
    return ok(true)
  }
  if (url === '/api/excel/output' && method === 'get') {
    return ok(true)
  }
  if (url === '/api/user/count/gender' && method === 'get') {
    return ok(data.genderCount)
  }
  if (url === '/api/user/count/province' && method === 'get') {
    return ok(data.provinceCount)
  }
  if (url === '/api/user/count/sum' && method === 'get') {
    return ok(data.users.length)
  }
  if (url === '/api/user/audit' && method === 'post') {
    const pageNumber = Number(params.pageNumber || 1)
    const pageSize = Number(params.pageSize || 5)
    const start = (pageNumber - 1) * pageSize
    const end = start + pageSize
    const list = data.audits.slice(start, end)
    return ok({ total: data.audits.length, list })
  }
  if (url === '/api/announce/search' && method === 'get') {
    const pageNumber = Number(params.pageNumber || 1)
    const pageSize = Number(params.pageSize || 5)
    const start = (pageNumber - 1) * pageSize
    const end = start + pageSize
    const list = data.announcements.slice(start, end)
    return ok({ total: data.announcements.length, list })
  }
  if (url === '/api/announce/update' && method === 'post') {
    return ok({ ...body })
  }
  if (url === '/api/announce/delete' && method === 'post') {
    return ok(true)
  }
  if (url === '/api/announce/add' && method === 'post') {
    return ok({ id: Date.now(), ...body })
  }
  return ok(null)
}
