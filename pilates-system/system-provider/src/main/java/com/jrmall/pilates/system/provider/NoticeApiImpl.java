package com.jrmall.pilates.system.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.system.api.NoticeApi;
import com.jrmall.pilates.system.model.form.NoticeForm;
import com.jrmall.pilates.system.model.query.NoticePageQuery;
import com.jrmall.pilates.system.model.vo.NoticeDetailVO;
import com.jrmall.pilates.system.model.vo.NoticePageVO;
import com.jrmall.pilates.system.model.vo.UserNoticePageVO;
import com.jrmall.pilates.system.service.SysNoticeService;
import com.jrmall.pilates.system.service.SysUserNoticeService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 系统通知接口实现
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/16 17:56
 */
@DubboService
public class NoticeApiImpl implements NoticeApi {

    @Resource
    private SysNoticeService noticeService;

    @Resource
    private SysUserNoticeService userNoticeService;

    @Override
    public IPage<NoticePageVO> getNoticePage(NoticePageQuery queryParams) {
        return noticeService.getNoticePage(queryParams);
    }

    @Override
    public NoticeForm getNoticeFormData(Long id) {
        return noticeService.getNoticeFormData(id);
    }

    @Override
    public boolean saveNotice(NoticeForm formData) {
        return noticeService.saveNotice(formData);
    }

    @Override
    public boolean updateNotice(Long id, NoticeForm formData) {
        return noticeService.updateNotice(id, formData);
    }

    @Override
    public boolean deleteNotices(String ids) {
        return noticeService.deleteNotices(ids);
    }

    @Override
    public boolean publishNotice(Long id) {
        return noticeService.publishNotice(id);
    }

    @Override
    public boolean revokeNotice(Long id) {
        return noticeService.revokeNotice(id);
    }

    @Override
    public NoticeDetailVO getNoticeDetail(Long id) {
        return noticeService.getNoticeDetail(id);
    }

    @Override
    public IPage<UserNoticePageVO> getMyNoticePage(NoticePageQuery queryParams) {
        return noticeService.getMyNoticePage(queryParams);
    }

    @Override
    public boolean readAll() {
        return userNoticeService.readAll();
    }

    @Override
    public IPage<UserNoticePageVO> getMyNoticePage(Page<NoticePageVO> page, NoticePageQuery queryParams) {
        return userNoticeService.getMyNoticePage(page, queryParams);
    }
}
