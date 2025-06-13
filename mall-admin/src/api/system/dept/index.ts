import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { DeptForm, DeptQuery, DeptVO } from "./types";

const DEPT_BASE_URL = "/sys-api/api/v1/dept";

/**
 * 部门树形表格
 *
 * @param queryParams
 */
export function listDepts(queryParams?: DeptQuery): AxiosPromise<DeptVO[]> {
  return request({
    url: `${DEPT_BASE_URL}`,
    method: "get",
    params: queryParams,
  });
}

/**
 * 部门下拉列表
 */
export function getDeptOptions(): AxiosPromise<OptionType[]> {
  return request({
    url: `${DEPT_BASE_URL}/options`,
    method: "get",
  });
}

/**
 * 获取部门详情
 *
 * @param id
 */
export function getDeptForm(id: number): AxiosPromise<DeptForm> {
  return request({
    url: `${DEPT_BASE_URL}/` + id + "/form",
    method: "get",
  });
}

/**
 * 新增部门
 *
 * @param data
 */
export function addDept(data: DeptForm) {
  return request({
    url: `${DEPT_BASE_URL}/add`,
    method: "post",
    data: data,
  });
}

/**
 *  修改部门
 *
 * @param id
 * @param data
 */
export function updateDept(id: number, data: DeptForm) {
  return request({
    url: `${DEPT_BASE_URL}/` + id,
    method: "put",
    data: data,
  });
}

/**
 * 删除部门
 *
 * @param ids
 */
export function deleteDept(ids: string) {
  return request({
    url: `${DEPT_BASE_URL}/` + ids,
    method: "delete",
  });
}
