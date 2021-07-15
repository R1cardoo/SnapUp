<template>
  <a-modal
    title="安排车次"
    :width="640"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <a-form-item label="起始日期">
          <a-date-picker
            @change="changeNumber"
            v-decorator="['date', { initialValue: moment(new Date()) }]"
          ></a-date-picker>
        </a-form-item>
        <a-form-item label="持续天数">
          <a-input-number
            :min="1"
            @change="changeDate"
            v-decorator="['days', { initialValue: 1 }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'

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
  },
  data () {
    this.formLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      }
    }
    return {
      moment,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    changeNumber (number) {
      this.form.setFieldsValue('days', number)
    },
    changeDate (date) {
      this.form.setFieldsValue('date', date)
    }
  }
}
</script>
