<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="身份证号">
                <a-input v-model="queryParam.id" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="姓名">
                <a-input v-model="queryParam.name" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => this.queryParam = {}">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button>
        <a-button type="secondary" icon="delete" @click="handleDelete" v-if="selectedRowKeys.length > 0">删除</a-button>
      </div>
      <s-table
        ref="table"
        size="default"
        rowKey="key"
        :columns="columns"
        :data="loadData"
        :alert="true"
        :rowSelection="rowSelection"
        showPagination="auto"
      >
        <span slot="name" slot-scope="text">
          <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
        </span>
      </s-table>
      <create-form
        ref="createModal"
        :visible="visible"
        :loading="confirmLoading"
        :model="mdl"
        @cancel="handleCancel"
        @ok="handleOk"
      />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import { getCreditList, saveCredit, deleteCredit } from '@/api/manage'
import CreateForm from './CreateForm'

const columns = [
  {
    title: '身份证号',
    dataIndex: 'identity',
    customRender: (text) => text.toString().slice(0, -4) + '***' + text.toString().slice(-1),
    sorter: true
  },
  {
    title: '姓名',
    dataIndex: 'name',
    scopedSlots: { customRender: 'name' }
  }
]

export default {
  name: 'Credit',
  components: {
    STable,
    Ellipsis,
    CreateForm
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
        if (requestParameters.id === '') {
          delete requestParameters.id
        }
        if (requestParameters.name === '') {
          delete requestParameters.name
        }
        console.log('loadData request parameters:', requestParameters)
        return getCreditList(requestParameters)
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
    handleAdd () {
      this.mdl = null
      this.visible = true
    },
    handleCancel () {
      this.visible = false

      const form = this.$refs.createModal.form
      form.resetFields() // 清理表单数据（可不做）
    },
    handleOk () {
      const { $notification } = this
      const form = this.$refs.createModal.form
      this.confirmLoading = true
      form.validateFields((errors, values) => {
        if (!errors) {
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
            saveCredit(values).then(res => {
              const result = res.result
              if (result.error) {
                $notification['error']({
                  message: '错误',
                  description: result.reason
                })
                this.confirmLoading = false
              } else {
                $notification['success']({
                  message: '成功',
                  description: result.reason
                })
                this.visible = false
                this.confirmLoading = false
                // 重置表单数据
                form.resetFields()
                // 刷新表格
                this.$refs.table.refresh()
              }
            })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleDelete () {
      const { $notification } = this
      const deleteIdentities = this.selectedRows.reduce((acc, cur, i) => {
        acc.push(cur.identity)
        return acc
      }, [])
      deleteCredit(deleteIdentities).then(res => {
        console.log(deleteIdentities)
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
    }
  }
}
</script>

<style scoped>

</style>
