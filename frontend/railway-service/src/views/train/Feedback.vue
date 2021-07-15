<template>
  <page-header-wrapper>
    <a-card :bordered="false" title="反馈列表">
      <div slot="extra">
        <a-input-search style="margin-left: 16px; width: 272px;" v-model="queryParam.search" @search="$refs.table.refresh(true)"/>
      </div>
      <s-table
        ref="table"
        size="default"
        rowKey="key"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="detail" slot-scope="text">
          <ellipsis :length="20" tooltip>{{ text }}</ellipsis>
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="showDetail(record)">查看</a>
          </template>
        </span>
      </s-table>
      <show-feedback
        ref="createModal"
        :visible="visible"
        :loading="confirmLoading"
        :model="mdl"
        @cancel="handleCancel"
      />
    </a-card>
  </page-header-wrapper>
</template>

<script>
  import { STable, Ellipsis } from '@/components'
  import { getFeedbackList } from '@/api/manage'
  import ShowFeedback from './ShowFeedback'

  const columns = [
    {
      title: '用户名',
      dataIndex: 'username'
    },
    {
      title: '联系电话',
      dataIndex: 'tel'
    },
    {
      title: '反馈内容',
      dataIndex: 'detail',
      scopedSlots: { customRender: 'detail' }
    },
    {
      title: '操作',
      dataIndex: 'action',
      width: '150px',
      scopedSlots: { customRender: 'action' }
    }
  ]

  export default {
    name: 'Credit',
    components: {
      STable,
      Ellipsis,
      ShowFeedback
    },
    data () {
      this.columns = columns
      return {
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
          if (requestParameters.search === '') {
            delete requestParameters.search
          }
          console.log('loadData request parameters:', requestParameters)
          return getFeedbackList(requestParameters)
            .then(res => {
              return res.result
            })
        },
        selectedRowKeys: [],
        selectedRows: []
      }
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
      onSelectChange (selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      },
      showDetail (record) {
        this.mdl = record
        this.visible = true
      },
      handleCancel () {
        this.visible = false
      }
    }
  }
</script>

<style scoped>

</style>
