import request from "@/utils/request";
import {AxiosPromise} from "axios";
import {CaptchaResult, LoginData, LoginResult} from "./types";
import {base64Str} from '@/utils/pkce'

/**
 * 登录API(密码模式)
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
    formData.append("grant_type", "captcha");
    return request({
        url: "/youlai-auth/oauth2/token",
        method: "post",
        data: formData,
        headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Basic ${base64Str(`${import.meta.env.VITE_OAUTH_CLIENT_ID}:${import.meta.env.VITE_OAUTH_CLIENT_SECRET}`)}`, // 客户端信息Base64明文：mall-admin:123456
        },
    });
}

/**
 * 获取验证码
 */
export function getCaptchaApi(): AxiosPromise<CaptchaResult> {
    return request({
        url: "/youlai-auth/api/v1/auth/captcha",
        method: "get",
    });
}

/**
 * 注销API
 */
export function logoutApi() {
    return request({
        url: "/youlai-system/api/v1/users/logout",
        method: "delete",
    });
}


export function getTokenByCode(data:any) {

    return request({
        url: "/youlai-auth/oauth2/token",
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            Authorization: `Basic ${base64Str(`${data.client_id}:${data.client_secret}`)}`, // 客户端信息Base64明文：messaging-client:123456
        },
    });
}



export function oauth2Login(data:any): AxiosPromise<LoginResult> {
return request({
        url: import.meta.env.VITE_OAUTH_ISSUER + "/login", // http://127.0.0.1/login
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
    });
}

