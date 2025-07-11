import request from "@/utils/request";
import { MemberQuery, MemberPageResult } from "./types";

const UMS_BASE_URL = "/ums-api/api/v1/members";

/**
 * 获取会员分页列表
 *
 * @param queryParams
 */
export function getMemberPage( queryParams: MemberQuery ) {
  return request<any, MemberPageResult>({
    url: `${UMS_BASE_URL}`,
    method: "get",
    params: queryParams,
  });
}
/**
 * 获取会员详情
 *
 * @param id
 */
export function getMemberDetail(id: number) {
  return request({
    url: `${UMS_BASE_URL}` + id,
    method: "get",
  });
}

/**
 * 添加会员
 *
 * @param data
 */
export function addMember(data: object) {
  return request({
    url: `${UMS_BASE_URL}`,
    method: "post",
    data: data,
  });
}

/**
 * 添加会员
 *
 * @param id
 * @param data
 */
export function updateMember(id: number, data: object) {
  return request({
    url: `${UMS_BASE_URL}` + id,
    method: "put",
    data: data,
  });
}
