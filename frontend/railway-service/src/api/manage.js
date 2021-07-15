import request from '@/utils/request'

const api = {
  user: '/user',
  role: '/role',
  service: '/service',
  trainList: '/train/lines',
  arrangeLine: '/train/arrange-line',
  lineStation: '/train/line-station',
  stationList: '/train/stations',
  creditList: '/train/credit',
  saveCredit: '/train/save-credit',
  deleteCredit: '/train/delete-credit',
  feedbackList: '/train/feedback',
  saveLine: '/train/save-line',
  permission: '/permission',
  permissionNoPager: '/permission/no-pager',
  orgTree: '/org/tree'
}

export default api

export function getUserList (parameter) {
  return request({
    url: api.user,
    method: 'get',
    params: parameter
  })
}

export function getRoleList (parameter) {
  return request({
    url: api.role,
    method: 'get',
    params: parameter
  })
}

export function getServiceList (parameter) {
  return request({
    url: api.service,
    method: 'get',
    params: parameter
  })
}

export function getTrainList (parameter) {
  return request({
    url: api.trainList,
    method: 'get',
    params: parameter
  })
}

export function getLineStation (parameter) {
  return request({
    url: api.lineStation,
    method: 'get',
    params: parameter
  })
}

export function saveLine (parameter) {
  return request({
    url: api.saveLine,
    method: 'post',
    data: parameter
  })
}

export function arrangeLine (parameter) {
  return request({
    url: api.arrangeLine,
    method: 'post',
    data: parameter
  })
}

export function saveCredit (parameter) {
  return request({
    url: api.saveCredit,
    method: 'post',
    data: parameter
  })
}

export function deleteCredit (parameter) {
  return request({
    url: api.deleteCredit,
    method: 'post',
    data: parameter
  })
}

export function getStationList (parameter) {
  return request({
    url: api.stationList,
    method: 'get',
    params: parameter
  })
}

export function getCreditList (parameter) {
  return request({
    url: api.creditList,
    method: 'get',
    params: parameter
  })
}

export function getFeedbackList (parameter) {
  return request({
    url: api.feedbackList,
    method: 'get',
    params: parameter
  })
}

export function getPermissions (parameter) {
  return request({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter
  })
}

export function getOrgTree (parameter) {
  return request({
    url: api.orgTree,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService (parameter) {
  return request({
    url: api.service,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function saveSub (sub) {
  return request({
    url: '/sub',
    method: sub.id === 0 ? 'post' : 'put',
    data: sub
  })
}
