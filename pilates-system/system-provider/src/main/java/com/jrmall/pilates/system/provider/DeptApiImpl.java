package com.jrmall.pilates.system.provider;

import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.api.DeptApi;
import com.jrmall.pilates.system.model.form.DeptForm;
import com.jrmall.pilates.system.model.query.DeptQuery;
import com.jrmall.pilates.system.model.vo.DeptVO;
import com.jrmall.pilates.system.service.SysDeptService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * 部门接口实现
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/6 18:01
 */
@DubboService
public class DeptApiImpl implements DeptApi {

    @Resource
    private SysDeptService deptService;

    @Override
    public boolean deleteByIds(String ids) {
        return deptService.deleteByIds(ids);
    }

    @Override
    public List<DeptVO> listDepartments(DeptQuery queryParams) {
        return deptService.getDeptList(queryParams);
    }

    @Override
    public List<Option<Long>> listDeptOptions() {
        return deptService.listDeptOptions();
    }

    @Override
    public Long saveDept(DeptForm formData) {
        return deptService.saveDept(formData);
    }

    @Override
    public Long updateDept(Long deptId, DeptForm formData) {
        return deptService.updateDept(deptId, formData);
    }

    @Override
    public DeptForm getDeptForm(Long deptId) {
        return deptService.getDeptForm(deptId);
    }
}
