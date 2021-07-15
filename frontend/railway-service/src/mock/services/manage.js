import Mock from 'mockjs2'
import { builder, getQueryParameters } from '../util'

const totalCount = 5701

const serverList = (options) => {
  const parameters = getQueryParameters(options)

  const result = []
  const pageNo = parseInt(parameters.pageNo)
  const pageSize = parseInt(parameters.pageSize)
  const totalPage = Math.ceil(totalCount / pageSize)
  const key = (pageNo - 1) * pageSize
  const next = (pageNo >= totalPage ? (totalCount % pageSize) : pageSize) + 1

  for (let i = 1; i < next; i++) {
    const tmpKey = key + i
    result.push({
      key: tmpKey,
      id: tmpKey,
      no: 'No ' + tmpKey,
      description: '这是一段描述',
      callNo: Mock.mock('@integer(1, 999)'),
      status: Mock.mock('@integer(0, 3)'),
      updatedAt: Mock.mock('@datetime'),
      editable: false
    })
  }

  return builder({
    pageSize: pageSize,
    pageNo: pageNo,
    totalCount: totalCount,
    totalPage: totalPage,
    data: result
  })
}

// const creditList = (options) => {
//   const parameters = getQueryParameters(options)
//
//   const result = []
//   const pageNo = parseInt(parameters.pageNo)
//   const pageSize = parseInt(parameters.pageSize)
//   const totalPage = Math.ceil(totalCount / pageSize)
//   const key = (pageNo - 1) * pageSize
//   const next = (pageNo >= totalPage ? (totalCount % pageSize) : pageSize) + 1
//
//   for (let i = 1; i < next; i++) {
//     const tmpKey = key + i
//     result.push({
//       key: tmpKey,
//       id: tmpKey,
//       identity: Mock.mock('@integer(100000000000000000, 999999999999999999)'),
//       name: Mock.mock({ 'regexp': /[A-Za-z]{2,10}/ }).regexp,
//       editable: false
//     })
//   }
//
//   return builder({
//     pageSize: pageSize,
//     pageNo: pageNo,
//     totalCount: totalCount,
//     totalPage: totalPage,
//     data: result
//   })
// }

// const trainList = (options) => {
//   const parameters = getQueryParameters(options)
//
//   const result = []
//   const pageNo = parseInt(parameters.pageNo)
//   const pageSize = parseInt(parameters.pageSize)
//   const totalPage = Math.ceil(totalCount / pageSize)
//   const key = (pageNo - 1) * pageSize
//   const next = (pageNo >= totalPage ? (totalCount % pageSize) : pageSize) + 1
//
//   for (let i = 1; i < next; i++) {
//     const tmpKey = key + i
//     const trainNo = Mock.mock({ 'regexp': /[A-Z]/ }).regexp + Mock.mock('@integer(1, 999)')
//     result.push({
//       key: tmpKey,
//       id: tmpKey,
//       trainNo: trainNo,
//       stationInfo: Mock.mock({ 'array|1': ['北京西', '南京', '南京南', '宋城路', '绿博园', '新郑机场', '南阳寨', '黄河景区',
//           '武陟', '修武西', '白城', '成都东', '东莞', '东莞东', '惠州', '济宁', '南充', '日照', '山海关', '汕头', '松原', '乌兰浩特',
//           '乌鲁木齐南', '乌鲁木齐', '岚山西', '日照西', '锦州', '锦州南'] }).array + ' - ' + Mock.mock({ 'array|1': ['北京西',
//           '南京', '南京南', '宋城路', '绿博园', '新郑机场', '南阳寨', '黄河景区', '武陟', '修武西', '白城', '成都东', '东莞', '东莞东',
//           '惠州', '济宁', '南充', '日照', '山海关', '汕头', '松原', '乌兰浩特', '乌鲁木齐南', '乌鲁木齐', '岚山西', '日照西', '锦州',
//           '锦州南'] }).array,
//       trainType: trainNo[0],
//       stationNum: Mock.mock('@integer(1, 20)'),
//       departTime: Mock.mock('@time("HH:mm")'),
//       arriveTime: Mock.mock('@time("HH:mm")'),
//       status: Mock.mock('@integer(0, 2)'),
//       editable: false
//     })
//   }
//
//   return builder({
//     pageSize: pageSize,
//     pageNo: pageNo,
//     totalCount: totalCount,
//     totalPage: totalPage,
//     data: result
//   })
// }

const stationList = () => {
  return builder(['北京西', '南京', '南京南', '宋城路', '绿博园', '新郑机场', '南阳寨', '黄河景区', '武陟', '修武西', '白城',
    '成都东', '东莞', '东莞东', '惠州', '济宁', '南充', '日照', '山海关', '汕头', '松原', '乌兰浩特', '乌鲁木齐南', '乌鲁木齐',
    '岚山西', '日照西', '锦州', '锦州南', '阿克苏', '阿克陶', '阿拉山口', '阿图什', '巴楚', '北屯市', '博乐', '布列开', '成都',
    '大庆', '大庆西', '福海', '哈密', '和静', '和什托洛盖', '和田', '霍尔果斯', '精河', '精河南', '喀什', '克拉玛依', '库车',
    '库尔勒', '奎屯', '兰州', '兰州西', '柳园', '柳园南', '轮台', '玛纳斯', '玛纳斯湖', '墨玉', '尼勒克', '皮山', '萍乡', '萍乡北',
    '齐齐哈尔', '沙湾县', '莎车', '鄯善', '鄯善北', '深圳北', '深圳', '深圳东', '深圳西', '沈阳北', '石河子', '疏勒', '吐哈',
    '吐鲁番', '吐鲁番北', '乌西', '五五', '新和', '焉耆', '叶城', '伊宁', '伊宁东', '宜春', '英吉沙', '泽普', '成都南', '柳园南',
    '和硕', '马兰', '大庆东', '齐齐哈尔南', '福利区', '西固', '兰州新区', '中川机场', '宾西北', '宾州', '胜利镇', '双龙湖', '方正',
    '得莫利', '高楞', '达连河', '依兰', '宏克力', '佳木斯西', '达坂城', '新香坊北', '阿城北', '帽儿山西', '尚志南', '一面坡北',
    '苇河西', '亚布力西', '横道河子东', '海林北', '乌苏', '昆玉', '四棵树', '塔城', '额敏', '铁厂沟', '博尔塔拉', '双河市',
    '图木舒克', '平阳', '苍南', '德清', '德清西', '奉化', '海宁', '海宁西', '湖州', '加格达奇', '嘉善南', '嘉兴', '嘉兴南', '金山北',
    '乐清', '临海', '漠河', '南昌', '南昌西', '讷河', '宁海', '青岛', '青岛北', '瑞安', '三门县', '上虞', '上虞北', '绍兴', '绍兴北',
    '绅坊', '沈阳', '沈阳南', '松江南', '台州', '桐乡', '温岭', '温州南', '雁荡山', '永嘉', '余杭', '余姚', '余姚北', '长兴',
    '长兴南', '庄桥', '安吉', '宁波', '温州', '缙云', '永康', '武义', '壶镇', '磐安南', '仙居南', '临海南', '杜桥', '头门港', '安塘',
    '巴彦高勒', '白壁关', '白音察干', '白云鄂博', '柏果', '查布嘎', '察素齐', '岔江', '柴沟堡', '楚雄南', '达拉特旗', '达拉特西',
    '大板', '大理', '大营', '代县', '岱岳', '德令哈', '甸心', '定襄', '东胜', '东胜西', '东淤地', '东镇', '东庄', '豆罗', '兑镇',
    '额济纳', '二连', '发耳', '繁峙', '汾阳', '丰镇', '风陵渡', '富源', '高村', '格尔木', '公庙子', '古东', '古交', '广安', '广水',
    '广通北', '海石湾', '河边', '河津', '鹤庆', '黑井', '红果', '洪洞', '洪洞西', '侯马', '侯马西', '化德', '怀仁', '怀仁东', '湟源',
    '霍州', '霍州东', '集宁南', '稷山', '建水', '江所田', '交城', '介休', '介休东', '晋中', '经棚', '开鲁', '岢岚', '昆阳', '拉萨',
    '老羊壕', '乐都', '丽江', '林东', '林西', '临河', '灵丘', '灵石', '灵石东', '柳林南', '鲁番', '陆良', '禄丰南', '罗平', '吕梁',
    '麻城', '麻城北', '茅草坪', '蒙自北', '明安', '那曲', '南宁', '尼木', '宁武', '盘关', '平安驿', '平关', '平社', '平田', '平旺',
    '平型关', '平遥', '平遥古城', '祁县', '祁县东', '旗下营', '秦家庄', '沁县', '清徐', '曲靖', '曲水县', '仁布', '日喀则', '萨拉齐',
    '赛汗塔拉', '三家寨', '桑根达来', '厦门', '厦门北', '厦门高崎', '山阴', '商都', '商丘', '商丘南', '神池', '神头', '师宗', '十堰',
    '石林', '寿阳', '朔州', '松河', '遂宁', '太谷', '太谷西', '太原北', '太原东', '天镇', '通海', '土贵乌拉', '土牧尔台', '万州',
    '威箐', '威舍', '文水', '闻喜', '闻喜西', '乌海', '乌海西', '乌拉特前旗', '五台山', '五原', '五寨', '武乡', '西斗铺', '西小召',
    '锡林浩特', '下社', '襄汾', '襄汾西', '祥云', '小雨谷', '孝南', '孝西', '忻州', '新绛', '信阳', '信阳东', '兴和西', '轩岗',
    '宣威', '延安', '阳高', '阳明堡', '阳曲', '阳泉曲', '宜良北', '营盘湾', '应县', '永济', '永济北', '榆次', '榆社', '雨格', '玉溪',
    '元谋', '原平', '月亮田', '枣林', '张兰', '赵城', '哲里木', '镇城底', '正镶白旗', '朱日和', '卓资东', '卓资山', '蒙自', '屏边',
    '河口北', '杨林', '小新街', '南宁东', '西宁', '民和南', '乐都南', '海东西', '大通西', '门源', '鄂尔多斯', '杭锦后旗', '汾河',
    '西乌旗', '白音华南', '茶卡', '苏尼特左旗', '阿巴嘎旗', '昆独仑召', '万州北', '广安南', '嵩明', '曲靖北', '富源北', '富宁',
    '广南县', '普者黑', '弥勒', '石林西', '树木岭', '香樟路', '湘府路', '洞井', '先锋', '芙蓉南', '暮云', '九郎山', '田心东', '大丰',
    '株洲南', '昭山', '荷塘', '板塘', '乌兰察布', '旗下营南', '准格尔', '东胜东', '娄烦', '岚县', '白文东', '蔡家崖', '楚雄', '南华',
    '云南驿', '祥云', '珠琳', '海宴', '托克托东', '十堰东', '元谋西', '永仁', '阳高南', '兴和北', '乌审旗', '花土沟', '太谷东',
    '榆社西', '武乡', '临沧', '云县', '巍山', '开封', '开封北', '兰考', '兰考南', '民权北', '民权', '宁陵县', '个旧', '天津北',
    '天津西', '天津', '林芝', '贡嘎', '扎囊', '山南', '桑日', '加查', '朗县', '米林', '岗嘎'])
}

// const lineStation = () => {
//   return builder([
//     {
//       stationName: '北京西',
//       arrive: '19:50',
//       depart: '19:50'
//     },
//     {
//       stationName: '浦东',
//       arrive: '22:30',
//       depart: '22:40'
//     },
//     {
//       stationName: '广州北',
//       arrive: '02:10',
//       depart: '02:10'
//     }
//   ])
// }

// const saveLine = (options) => {
//   const parameters = JSON.parse(options.body)
//   if (parameters.create) {
//     return builder({
//       error: false,
//       reason: 'Success'
//     })
//   } else {
//     return builder({
//       error: true,
//       reason: 'Fail'
//     })
//   }
// }

// const saveCredit = () => {
//   return builder({
//     error: false,
//     reason: 'Success'
//   })
//   // return builder({
//   //   error: true,
//   //   reason: 'Fail'
//   // })
// }

// const deleteCredit = () => {
//   return builder({
//     error: false,
//     reason: 'Success'
//   })
// }

const activity = () => {
  return builder([{
    id: 1,
    user: {
      nickname: '铁路控制',
      avatar: '@image(64x64)'
    },
    project: {
      name: '征信管理',
      action: '创建',
      event: '乘客信息'
    },
    time: '2021-06-23 14:47:00'
  },
  {
    id: 1,
    user: {
      nickname: '铁路控制',
      avatar: '@image(64x64)'
    },
    project: {
      name: '线路管理',
      action: '更新',
      event: '列车线路'
    },
    time: '2021-06-23 09:35:37'
  },
  {
    id: 1,
    user: {
      nickname: '铁路控制',
      avatar: '@image(64x64)'
    },
    project: {
      name: '线路管理',
      action: '创建',
      event: '列车线路'
    },
    time: '2017-05-27 00:00:00'
  },
  {
    id: 1,
    user: {
      nickname: '铁路控制',
      avatar: '@image(64x64)'
    },
    project: {
      name: '线路管理',
      action: '更新',
      event: '列车线路'
    },
    time: '2021-06-23 14:47:00'
  },
  {
    id: 1,
    user: {
      nickname: '铁路控制',
      avatar: '@image(64x64)'
    },
    project: {
      name: '征信管理',
      action: '删除',
      event: '乘客信息'
    },
    time: '2021-06-23 14:47:00'
  },
  {
    id: 1,
    user: {
      nickname: '铁路控制',
      avatar: '@image(64x64)'
    },
    project: {
      name: '线路管理',
      action: '创建',
      event: '列车线路'
    },
    time: '2021-06-23 14:47:00'
  }
  ])
}

const radar = () => {
  return builder([{
    item: '准点率',
    '高铁': 50,
    '动车': 70,
    '特快': 40
  },
  {
    item: '吞吐量',
    '高铁': 90,
    '动车': 70,
    '特快': 80
  },
  {
    item: '售出率',
    '高铁': 50,
    '动车': 90,
    '特快': 80
  },
  {
    item: '满意率',
    '高铁': 70,
    '动车': 70,
    '特快': 80
  },
  {
    item: '应答速度',
    '高铁': 70,
    '动车': 30,
    '特快': 40
  },
  {
    item: '安全指标',
    '高铁': 75,
    '动车': 80,
    '特快': 70
  }
  ])
}

Mock.mock(/\/service/, 'get', serverList)
// Mock.mock(/\/train\/lines/, 'get', trainList)
Mock.mock(/\/train\/stations/, 'get', stationList)
// Mock.mock(/\/train\/credit/, 'get', creditList)
// Mock.mock(/\/train\/line-station/, 'get', lineStation)
// Mock.mock(/\/train\/save-line/, 'post', saveLine)
// Mock.mock(/\/train\/save-credit/, 'post', saveCredit)
// Mock.mock(/\/train\/delete-credit/, 'post', deleteCredit)
Mock.mock(/\/workplace\/activity/, 'get', activity)
Mock.mock(/\/workplace\/radar/, 'get', radar)
