<template>
  <a-modal
    title="查看反馈"
    :width="960"
    :visible="visible"
    :confirmLoading="loading"
    :footer="null"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-list>
        <a-list-item>
          <a-list-item-meta :description="model.username">
            <a slot="title">用户名</a>
          </a-list-item-meta>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :description="model.tel">
            <a slot="title">联系电话</a>
          </a-list-item-meta>
        </a-list-item>
        <a-list-item>
          <a-list-item-meta :description="model.detail">
            <a slot="title">反馈详情</a>
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    </a-spin>
  </a-modal>
</template>

<script>

export default {
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    loading: {
      type: Boolean,
      default: () => false
    },
    model: {
      type: Object,
      default: () => null
    }
  }
}
</script>
