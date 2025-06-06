<!-- 授权成功重定向客户端页面，带有code，根据code获取token -->
<template>加载中...</template>

<script setup lang="ts">
import {getQueryParam} from '@/utils/index'
import {v4 as uuidv4} from 'uuid';
import {getTokenByCode} from "@/api/auth";


let state: string = uuidv4()

// 获取授权码 code
const code = getQueryParam('code')

if (code) {
  // 校验state，防止CSRF攻击
  const urlState = getQueryParam('state')
  if (state !== urlState) {
    ElMessage.error("state校验失败");
  } else {

    getTokenByCode({
      grant_type: 'authorization_code',
      code: code,
      state: state,
      client_id: import.meta.env.VITE_CLIENT_ID,
      client_secret: import.meta.env.VITE_CLIENT_SECRET,
      redirect_uri: import.meta.env.VITE_REDIRECT_URI
    }).then((res: any) => {
      localStorage.setItem("token", JSON.stringify(res))
    }).catch((e: any) => {
      ElMessage.error("获取token失败")
    })
  }
} else {
  // 未授权，跳转到授权页
  localStorage.setItem("state", state)
  window.location.href = `${import.meta.env.VITE_OAUTH_ISSUER}/oauth2/authorize?client_id=${
    import.meta.env.VITE_OAUTH_CLIENT_ID
  }&response_type=code&scope=&redirect_uri=${
    import.meta.env.VITE_OAUTH_REDIRECT_URI
  }&state=${state}`
}


</script>






