package com.jrmall.cloud.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jrmall.cloud.system.model.entity.SysUserNotice;
import com.jrmall.cloud.system.model.query.NoticePageQuery;
import com.jrmall.cloud.system.model.vo.NoticePageVO;
import com.jrmall.cloud.system.model.vo.UserNoticePageVO;

/**
 * 用户公告状态服务类
 *
 * @author youlaitech
 * @since 2024-08-28 16:56
 */
public interface SysUserNoticeService extends IService<SysUserNotice> {

    /**
     * 全部标记为已读
     *
     * @return 是否成功
     */
    boolean readAll();

    /**
     * 分页获取我的通知公告
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return 我的通知公告分页列表
     */
    IPage<UserNoticePageVO> getMyNoticePage(Page<NoticePageVO> page, NoticePageQuery queryParams);
}
