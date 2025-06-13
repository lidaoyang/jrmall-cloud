/**
 * 订单查询参数
 */
export interface OrderQuery extends PageQuery {
  orderSn: string | undefined;
  status: number | undefined;
  memberId: string | undefined;
}

/**
 * 订单分页列表项
 */
export interface Order {
  id: string;
  orderSn: string;
  totalAmount: string;
  payAmount: string;
  paymentMethod: number;
  status: number;
  totalQuantity: number;
  createTime: string;
  memberId: string;
  source: number;
  orderItems: OrderItem[];
}
export interface OrderItem {
  id: string;
  orderId: string;
  skuId: string;
  skuName: string;
  picUrl: string;
  skuSn: string;
  price: string;
  count: number;
  memberId: string;
  totalAmount: number;
}

/**
 * 订单分页
 */
export type OrderPageResult = PageResult<Order[]>;

/**
 * 订单表单
 */
export interface OrderDetail {
  id: number | undefined;
  title: string;
  picUrl: string;
  beginTime: string;
  endTime: string;
  status: number;
  sort: number;
  url: string;
  remark: string;
}

/**
 * 订单状态枚举
 */
export enum OrderStatusEnum {
  /**
   * 待付款
   */
  WAIT_PAY = 1,
  /**
   * 待发货
   */
  WAIT_SHIPPING = 2,
  /**
   * 已发货
   */
  SHIPPED = 3,

  /**
   * 已完成
   */
  FINISHED = 4,
  /**
   * 已关闭
   */
  CLOSED = 5,
  /**
   * 已取消
   */
  CANCELED = 6,
}
