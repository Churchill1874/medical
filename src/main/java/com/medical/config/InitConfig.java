package com.medical.config;

import com.medical.entity.Admin;
import com.medical.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Component
public class InitConfig {


    //超管管理员账号
    private static final String SUPER_ADMIN_ACCOUNT = "superadmin";

    private static final String PASSWORD = "111111a";

    //获取创建机器人开关
    @Value("${init.create.bot}")
    private boolean createBot;

    @Resource
    private AdminService adminService;

    /**
     * 项目启动时运行方法
     */
    @PostConstruct
    private void run() {
        Admin admin = adminService.findByAccount(SUPER_ADMIN_ACCOUNT);
        if (admin == null) {
            admin = new Admin();
            admin.setAccount(SUPER_ADMIN_ACCOUNT);
            admin.setPassword(PASSWORD);
            admin.setName("超级管理人");
            admin.setStatus(true);
            admin.setCreateName("系统");
            admin.setCreateTime(LocalDateTime.now());
            adminService.save(admin);
            log.info("系统初始化了superadmin管理员");
        }
    }


}
