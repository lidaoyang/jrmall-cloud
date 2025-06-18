/**
 * 响应码枚举
 */
export const enum ResultEnum {
  /**
   * 成功
   */
  SUCCESS = "00000",
  /**
   * 错误
   */
  ERROR = "B0001",

  /**
   * 访问令牌无效或过期
   */
  ACCESS_TOKEN_INVALID = "A0230",

  /**
   * token已被禁止访问
   */
  TOKEN_ACCESS_FORBIDDEN = "A0231",
}
