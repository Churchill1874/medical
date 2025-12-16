package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.Admin;
import com.medical.pojo.req.admin.AdminPage;

public interface AdminService extends IService<Admin> {

    IPage<Admin> page(AdminPage dto);

    Admin findByAccount(String account);

}
