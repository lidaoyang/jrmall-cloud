import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { RoleQuery, RolePageResult, RoleForm } from "./types";

const ROLE_BASE_URL = "/sys-api/api/v1/roles";

/**
 * 获取角色分页数据
 *
 * @param queryParams
 */
export function getRolePage(
  queryParams?: RoleQuery
): AxiosPromise<RolePageResult> {
  return request({
    url: `${ROLE_BASE_URL}/page`,
    method: "get",
    params: queryParams,
  });
}

/**
 * 获取角色下拉数据
 *
 * @param queryParams
 */
export function getRoleOptions(
  queryParams?: RoleQuery
): AxiosPromise<OptionType[]> {
  return request({
    url: `${ROLE_BASE_URL}/options`,
    method: "get",
    params: queryParams,
  });
}

/**
 * 获取角色的菜单ID集合
 *
 * @param queryParams
 */
export function getRoleMenuIds(roleId: number): AxiosPromise<number[]> {
  return request({
    url: `${ROLE_BASE_URL}/` + roleId + "/menuIds",
    method: "get",
  });
}

/**
 * 分配菜单权限给角色
 *
 * @param queryParams
 */
export function updateRoleMenus(
  roleId: number,
  data: number[]
): AxiosPromise<any> {
  return request({
    url: `${ROLE_BASE_URL}/` + roleId + "/menus",
    method: "put",
    data: data,
  });
}

/**
 * 获取角色详情
 *
 * @param id
 */
export function getRoleForm(id: number): AxiosPromise<RoleForm> {
  return request({
    url: `${ROLE_BASE_URL}/` + id + "/form",
    method: "get",
  });
}

/**
 * 添加角色
 *
 * @param data
 */
export function addRole(data: RoleForm) {
  return request({
    url: `${ROLE_BASE_URL}/add`,
    method: "post",
    data: data,
  });
}

/**
 * 更新角色
 *
 * @param id
 * @param data
 */
export function updateRole(id: number, data: RoleForm) {
  return request({
    url: `${ROLE_BASE_URL}/` + id,
    method: "put",
    data: data,
  });
}

/**
 * 批量删除角色，多个以英文逗号(,)分割
 *
 * @param ids
 */
export function deleteRoles(ids: string) {
  return request({
    url: `${ROLE_BASE_URL}/` + ids,
    method: "delete",
  });
}
