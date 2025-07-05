package com.xxl.job.admin.controller;

import com.xxl.job.admin.controller.annotation.PermissionLimit;
import com.xxl.job.admin.core.model.bo.XxlJobInfoBo;
import com.xxl.job.admin.service.XxlJobApiService;
import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.RegistryParam;
import com.xxl.job.core.biz.model.ReturnT;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * xxl-job api
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/30 11:07
 */
@RestController
@RequestMapping("/api/job")
public class JobApiController {

    @Resource
    private XxlJobApiService jobApiService;

    @PostMapping("/add")
    @PermissionLimit(limit = false)
    public ReturnT<String> add(@RequestBody XxlJobInfoBo jobInfo) {
        return jobApiService.add(jobInfo);
    }

    @PostMapping("/remove")
    @PermissionLimit(limit = false)
    public ReturnT<String> remove(@RequestParam("id") int id) {
        return jobApiService.remove(id);
    }

    @PostMapping("/stop")
    @PermissionLimit(limit = false)
    public ReturnT<String> pause(@RequestParam("id") int id) {
        return jobApiService.stop(id);
    }

    @PostMapping("/start")
    @PermissionLimit(limit = false)
    public ReturnT<String> start(@RequestParam("id") int id) {
        return jobApiService.start(id);
    }

}
