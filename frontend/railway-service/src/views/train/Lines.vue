<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="列车班次">
                <a-input v-model="queryParam.trainNo" placeholder="请输入列车班次号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="状态">
                <a-select v-model="queryParam.status" placeholder="请选择" default-value="0">
                  <a-select-option value="0">不限</a-select-option>
                  <a-select-option value="1">正常</a-select-option>
                  <a-select-option value="2">关闭</a-select-option>
                  <a-select-option value="3">暂停</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="列车类型">
                  <a-select v-model="queryParam.trainType" placeholder="请选择" default-value="0">
                    <a-select-option value="0">不限</a-select-option>
                    <a-select-option value="G">高速动车组</a-select-option>
                    <a-select-option value="C">城际动车组</a-select-option>
                    <a-select-option value="D">动车组</a-select-option>
                    <a-select-option value="Z">直达特快</a-select-option>
                    <a-select-option value="T">特快列车</a-select-option>
                    <a-select-option value="K">快速列车</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="出发站">
                  <a-input v-model="queryParam.departStation" placeholder="请输入出发站点名"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="到达站">
                  <a-input v-model="queryParam.arriveStation" placeholder="请输入到达站点名"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="出发时间">
                  <a-time-picker v-model="queryParam.departTime" style="width: 100%" placeholder="请输入出发时间" format="HH:mm"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="到达时间">
                  <a-time-picker v-model="queryParam.arriveTime" style="width: 100%" placeholder="请输入到达时间" format="HH:mm"/>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => this.queryParam = {}">重置</a-button>
                <a @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button>
        <a-button type="secondary" icon="clock-circle" @click="handleArrange" v-if="selectedRowKeys.length > 0">安排车次</a-button>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="key"
        :columns="columns"
        :data="loadData"
        :rowSelection="rowSelection"
        showPagination="auto"
      >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
        <span slot="status" slot-scope="text">
          <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
        </span>
        <span slot="station" slot-scope="text">
          <ellipsis :length="20" tooltip>{{ text }}</ellipsis>
        </span>

        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="modifyLine(record)">配置</a>
          </template>
        </span>
      </s-table>
    </a-card>
  </page-header-wrapper>
</template>

<script>
// 车站：预置；字段 车站编号 车站名 地址
// 线路：车次编号；列车类型；车厢数量与座位数量；车站数量-车站列表；各站出发与到达时间
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import { arrangeLine, getRoleList, getTrainList } from '@/api/manage'

const columns = [
  {
    title: '#',
    scopedSlots: { customRender: 'serial' }
  },
  {
    title: '列车班次',
    sorter: true,
    dataIndex: 'trainNo'
  },
  {
    title: '出发站-到达站',
    dataIndex: 'stationInfo',
    scopedSlots: { customRender: 'station' }
  },
  {
    title: '列车类型',
    dataIndex: 'trainType',
    customRender: (text) => text + ' 车'
  },
  {
    title: '车站数量',
    dataIndex: 'stationNum'
  },
  {
    title: '出发时间',
    dataIndex: 'departTime'
  },
  {
    title: '到达时间',
    dataIndex: 'arriveTime'
  },
  {
    title: '状态',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' }
  },
  {
    title: '操作',
    dataIndex: 'action',
    width: '150px',
    scopedSlots: { customRender: 'action' }
  }
]

const statusMap = {
  0: {
    status: 'default',
    text: '关闭'
  },
  1: {
    status: 'success',
    text: '正常'
  },
  2: {
    status: 'error',
    text: '暂停'
  }
}

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis
  },
  data () {
    this.columns = columns
    return {
      // create model
      visible: false,
      confirmLoading: false,
      mdl: null,
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        delete requestParameters.status
        if (requestParameters.departTime) {
          requestParameters.departTime = requestParameters.departTime._d.toTimeString().substring(0, 5)
        } else {
          delete requestParameters.departTime
        }
        if (requestParameters.arriveTime) {
          requestParameters.arriveTime = requestParameters.arriveTime._d.toTimeString().substring(0, 5)
        } else {
          delete requestParameters.arriveTime
        }
        console.log('loadData request parameters:', requestParameters)
        return getTrainList(requestParameters)
          .then(res => {
            console.log(res)
            return res.result
          })
      },
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  filters: {
    statusFilter (type) {
      return statusMap[type].text
    },
    statusTypeFilter (type) {
      return statusMap[type].status
    }
  },
  created () {
    getRoleList({ t: new Date() })
  },
  computed: {
    rowSelection () {
      return {
        selectedRowKeys: this.selectedRowKeys,
        onChange: this.onSelectChange
      }
    }
  },
  methods: {
    modifyLine (record) {
      const line = record.trainNo
      this.$router.push({ path: '/train/form/' + line })
    },
    handleAdd () {
      this.mdl = null
      this.visible = true
      this.$router.push({ path: '/train/form' })
    },
    handleEdit (record) {
      this.visible = true
      this.mdl = { ...record }
    },
    handleOk () {
      const form = this.$refs.createModal.form
      this.confirmLoading = true
      form.validateFields((errors, values) => {
        if (!errors) {
          console.log('values', values)
          if (values.id > 0) {
            // 修改 e.g.
            new Promise((resolve, reject) => {
              setTimeout(() => {
                resolve()
              }, 1000)
            }).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()

              this.$message.info('修改成功')
            })
          } else {
            // 新增
            new Promise((resolve, reject) => {
              setTimeout(() => {
                resolve()
              }, 1000)
            }).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()

              this.$message.info('安排车次成功')
            })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.visible = false

      const form = this.$refs.createModal.form
      form.resetFields() // 清理表单数据（可不做）
    },
    handleSub (record) {
      if (record.status !== 0) {
        this.$message.info(`${record.no} 订阅成功`)
      } else {
        this.$message.error(`${record.no} 订阅失败，规则已关闭`)
      }
    },
    handleArrange () {
      const { $notification } = this
      const arrangeNo = this.selectedRows.reduce((acc, cur, i) => {
        acc.push(cur.trainNo)
        return acc
      }, [])
      arrangeLine(arrangeNo).then(res => {
        console.log(arrangeNo)
        console.log(res)
        const result = res.result
        if (result.error) {
          $notification['error']({
            message: '错误',
            description: result.reason
          })
        } else {
          $notification['success']({
            message: '成功',
            description: result.reason
          })
          this.confirmLoading = false
          this.selectedRows.splice(0, this.selectedRows.length)
          this.selectedRowKeys.splice(0, this.selectedRowKeys.length)
          // 刷新表格
          this.$refs.table.refresh()
        }
      }).catch(() => {
        $notification['error']({
          message: '错误',
          description: '发送请求异常'
        })
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    }
  }
}
</script>
