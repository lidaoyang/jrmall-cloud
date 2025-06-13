<!-- 订单管理 -->
<script setup lang="ts">
defineOptions({
  name: "Order",
  inheritAttrs: false,
});

import { getOrderPage, getOrderDetail } from "@/api/oms/order";
import { ref, reactive, toRefs, onMounted } from "vue";
import { dayjs, ElForm } from "element-plus";
import { Order, OrderQuery } from "@/api/oms/order/types";

const queryFormRef = ref(ElForm);

const orderSourceMap = {
  1: "微信小程序",
  2: "APP",
  3: "PC",
};

const orderStatusMap = {
  101: "待付款",
  102: "用户取消",
  103: "系统取消",
  201: "已付款",
  202: "申请退款",
  203: "已退款",
  301: "待发货",
  401: "已发货",
  501: "用户收货",
  502: "系统收货",
  901: "已完成",
};

const payTypeMap = {
  1: "支付宝",
  2: "微信",
  3: "会员余额",
};

const state = reactive({
  loading: false,
  ids: [],
  single: true,
  multiple: true,
  dateRange: [] as any,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
  } as OrderQuery,
  orderList: [] as Order[],
  total: 0,
  dialog: {
    title: "订单详情",
    visible: false,
  } as DialogType,
  dialogVisible: false,
  orderDetail: {
    order: {
      refundAmount: undefined,
      refundType: undefined,
      refundNote: undefined,
      gmtRefund: undefined,
      confirmTime: undefined,
      gmtDelivery: undefined,
      shipSn: undefined,
      shipChannel: undefined,
      gmtPay: undefined,
      integralPrice: undefined,
      payChannel: undefined,
      skuPrice: undefined,
      couponPrice: undefined,
      freightPrice: undefined,
      orderPrice: undefined,
      orderSn: "", // 添加 orderSn 字段
      memberId: "", // 添加 memberId 字段
      source: 0, // 添加 source 字段
      status: 0, // 添加 status 字段
      totalAmount: 0, // 添加 totalAmount 字段
      paymentAmount: 0, // 添加 payAmount 字段
      paymentMethod: 0, // 添加 paymentMethod 字段
      createTime: "", // 添加 createTime 字段
    },
    member: {},
    orderItems: [],
  },
  orderSourceMap,
  orderStatusMap,
  payTypeMap,
});

const { loading, queryParams, orderList, total, dateRange } = toRefs(state);

function handleQuery() {
  state.loading = true;
  getOrderPage(state.queryParams).then(({ data }) => {
    state.orderList = data.list;
    state.total = data.total;
    state.loading = false;
  });
}

function resetQuery() {
  queryFormRef.value.resetFields();
  handleQuery();
}

function viewDetail(row: any) {
  state.dialog.visible = true;
  getOrderDetail(row.id)
    .then((response: any) => {
      if (response.data && typeof response.data === "object") {
        state.orderDetail = {
          ...state.orderDetail,
          ...response.data,
          order: {
            ...state.orderDetail.order,
            ...(response.data.order || {}),
            createTime: dayjs(response.data.order.createTime).format(
              "YYYY/MM/DD HH:mm:ss"
            ),
          },
          member: response.data.member || {},
          orderItems: response.data.orderItems || [],
        };
      } else {
        // 如果 API 返回无效数据，保持当前状态不变或设置默认值
        console.error(
          "Invalid data received from getOrderDetail:",
          response.data
        );
      }
    })
    .catch((error: any) => {
      console.error("Error fetching order detail:", error);
    });
}

onMounted(() => {
  handleQuery();
});
</script>

<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <div class="search-container">
      <el-form ref="queryFormRef" :model="queryParams" :inline="true">
        <el-form-item prop="orderSn">
          <el-input v-model="queryParams.orderSn" placeholder="订单号" />
        </el-form-item>

        <el-form-item>
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </el-form-item>

        <el-form-item>
          <el-select
            v-model="queryParams.status"
            class="filter-item"
            placeholder="订单状态"
          >
            <el-option
              v-for="(key, value) in orderStatusMap"
              :key="key"
              :label="key"
              :value="value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery"
            ><i-ep-search />查询</el-button
          >
          <el-button @click="resetQuery"><i-ep-refresh />重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table ref="dataTable" v-loading="loading" :data="orderList" border>
      <el-table-column type="expand" width="100" label="订单商品">
        <template #default="scope">
          <el-table :data="scope.row.orderItems" border>
            <el-table-column label="序号" type="index" width="100" />
            <el-table-column label="商品编号" align="center" prop="skuSn" />
            <el-table-column label="商品规格" align="center" prop="skuName" />
            <el-table-column label="图片" prop="picUrl">
              <template #default>
                <img :src="scope.row.picUrl" width="40" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="单价" prop="price">
              <template #default>{{ scope.row.price }}</template>
            </el-table-column>
            <el-table-column align="center" label="数量" prop="count">
              <template #default>{{ scope.row.count }}</template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="orderSn" label="订单编号" />

      <el-table-column align="center" prop="memberId" label="会员ID" />

      <el-table-column align="center" label="订单来源">
        <template #default="scope">
          <el-tag>{{ scope.row.sourceType }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单状态">
        <template #default="scope">
          <el-tag>{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="orderPrice" label="订单金额">
        <template #default="scope">
          {{ scope.row.totalAmount }}
        </template>
      </el-table-column>

      <el-table-column align="center" prop="payPrice" label="支付金额">
        <template #default="scope">
          {{ scope.row.payAmount }}
        </template>
      </el-table-column>

      <el-table-column align="center" label="支付方式">
        <template #default="scope">
          <el-tag>{{ scope.row.payType }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="createTime" label="创建时间" />

      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button @click="viewDetail(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页工具条 -->
    <pagination
      v-if="total > 0"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      :total="total"
      @pagination="handleQuery"
    />
    <!-- 订单详情弹窗 -->
    <el-dialog
      v-model="state.dialog.visible"
      :title="state.dialog.title"
      width="80%"
      destroy-on-close
    >
      <div>
        <h3>订单信息</h3>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="订单编号">{{
            state.orderDetail.order.orderSn
          }}</el-descriptions-item>
          <el-descriptions-item label="会员ID">{{
            state.orderDetail.order.memberId
          }}</el-descriptions-item>
          <el-descriptions-item label="订单来源">{{
            orderSourceMap[state.orderDetail.order.source] || "未知来源"
          }}</el-descriptions-item>
          <el-descriptions-item label="订单状态">{{
            orderStatusMap[state.orderDetail.order.status] || "未知状态"
          }}</el-descriptions-item>
          <el-descriptions-item label="订单金额">{{
            state.orderDetail.order.totalAmount
          }}</el-descriptions-item>
          <el-descriptions-item label="支付金额">{{
            state.orderDetail.order.paymentAmount
          }}</el-descriptions-item>
          <el-descriptions-item label="支付方式">{{
            payTypeMap[state.orderDetail.order.paymentMethod] || "未知"
          }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{
            state.orderDetail.order.createTime
          }}</el-descriptions-item>
        </el-descriptions>

        <h3>订单商品</h3>
        <el-table :data="state.orderDetail.orderItems" border>
          <el-table-column label="序号" type="index" width="100" />
          <el-table-column label="商品编号" align="center" prop="skuSn" />
          <el-table-column label="商品规格" align="center" prop="skuName" />
          <el-table-column label="图片" prop="picUrl">
            <template #default="scope">
              <img :src="scope.row.picUrl" width="40" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="单价" prop="price">
            <template #default="scope">{{ scope.row.price }}</template>
          </el-table-column>
          <el-table-column align="center" label="数量" prop="quantity">
            <template #default="scope">{{ scope.row.quantity }}</template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>
