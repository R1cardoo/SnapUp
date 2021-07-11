<template>
  <global-footer class="footer custom-render">
    <template v-slot:links>
      <span>铁路管理系统 ©2021</span>
    </template>
  </global-footer>
</template>

<script>
import { GlobalFooter } from '@ant-design-vue/pro-layout'

export default {
  name: 'ProGlobalFooter',
  components: {
    GlobalFooter
  }
}
</script>
