package com.medical.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.entity.Admin;
import com.medical.mapper.AdminMapper;
import com.medical.pojo.req.admin.AdminPage;
import com.medical.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public IPage<Admin> page(AdminPage dto) {
        IPage<Admin> page = new Page(dto.getPageNum(), dto.getPageSize());

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(dto.getStatus() != null, Admin::getStatus, dto.getStatus())
                .eq(StringUtils.isNotBlank(dto.getLoginIp()), Admin::getLoginIp, dto.getLoginIp())
                .orderByDesc(Admin::getCreateTime);

        return page(page, queryWrapper);
    }

    @Override
    public Admin findByAccount(String account) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(Admin::getAccount, account);
        return getOne(queryWrapper);
    }


}
