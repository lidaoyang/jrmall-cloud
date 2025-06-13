import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { CaptchaResult, LoginData, LoginResult } from "./types";

const AUTH_BASE_URL = "/auth-api/api/v1/auth";
/**
 * 登录API
 *
 * @param data {LoginData}
 * @returns
 */
export function loginApi(data: LoginData): AxiosPromise<LoginResult> {
  const formData = new FormData();
  formData.append("username", data.username);
  formData.append("password", data.password);
  formData.append("captchaId", data.captchaId as string);
  formData.append("captchaCode", data.captchaCode as string);
  return request({
    url: `${AUTH_BASE_URL}/login`,
    method: "post",
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}

/**
 * 获取验证码
 */
export function getCaptchaApi(): AxiosPromise<CaptchaResult> {
  return request({
    url: `${AUTH_BASE_URL}/captcha`,
    method: "get",
  });
}

/**
 * 注销API
 */
export function logoutApi() {
  return request({
    url: `${AUTH_BASE_URL}/logout`,
    method: "delete",
  });
}
