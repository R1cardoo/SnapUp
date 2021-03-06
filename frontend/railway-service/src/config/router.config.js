// eslint-disable-next-line
import { UserLayout, BasicLayout } from '@/layouts'
import { bxAnaalyse } from '@/core/icons'

const RouteView = {
  name: 'RouteView',
  render: h => h('router-view')
}

export const asyncRouterMap = [
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: 'menu.home' },
    redirect: '/dashboard/workplace',
    children: [
      // dashboard
      {
        path: '/dashboard',
        name: 'dashboard',
        redirect: '/dashboard/workplace',
        component: RouteView,
        meta: { title: 'menu.dashboard', keepAlive: true, icon: bxAnaalyse, permission: ['dashboard'] },
        children: [
          {
            path: '/dashboard/analysis/:pageNo([1-9]\\d*)?',
            name: 'Analysis',
            component: () => import('@/views/dashboard/Analysis'),
            meta: { title: 'menu.dashboard.analysis', keepAlive: false, permission: ['dashboard'] }
          },
          {
            path: '/dashboard/workplace',
            name: 'Workplace',
            component: () => import('@/views/dashboard/Workplace'),
            meta: { title: 'menu.dashboard.workplace', keepAlive: true, permission: ['dashboard'] }
          }
        ]
      },

      {
        path: '/train',
        redirect: '/train/lines',
        component: RouteView,
        meta: { title: 'menu.train', icon: 'control', permission: [ 'train' ] },
        children: [
          {
            path: '/train/lines',
            name: 'Lines',
            component: () => import('@/views/train/Lines'),
            meta: { title: 'menu.train.line', keepAlive: false, permission: [ 'train' ] }
          },
          {
            path: '/train/form',
            name: 'Form',
            hidden: true,
            component: () => import('@/views/train/AdvancedForm'),
            meta: { title: 'menu.train.form', hiddenHeaderContent: true, permission: [ 'train' ] }
          },
          {
            path: '/train/form/:id',
            name: 'Form',
            hidden: true,
            component: () => import('@/views/train/AdvancedForm'),
            meta: { title: 'menu.train.form', hiddenHeaderContent: true, permission: [ 'train' ] }
          }
        ]
      },
      {
        path: '/passenger',
        redirect: '/passenger/credit',
        component: RouteView,
        meta: { title: 'menu.train.passenger', icon: 'control', permission: [ 'train' ] },
        children: [
          {
            path: '/passenger/credit',
            name: 'Credit',
            component: () => import('@/views/train/Credit'),
            meta: { title: 'menu.train.credit', permission: [ 'train' ] }
          },
          {
            path: '/passenger/feedback',
            name: 'Feedback',
            component: () => import('@/views/train/Feedback'),
            meta: { title: 'menu.train.feedback', permission: [ 'train' ] }
          }
        ]
      }
    ]
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]

/**
 * ????????????
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      }
    ]
  },

  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }
]
