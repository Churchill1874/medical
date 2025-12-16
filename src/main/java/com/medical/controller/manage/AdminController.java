package com.medical.controller.manage;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.tools.CodeTools;
import com.medical.common.tools.GenerateTools;
import com.medical.common.tools.HttpTools;
import com.medical.entity.Admin;
import com.medical.pojo.req.admin.AdminLogin;
import com.medical.pojo.req.admin.AdminPage;
import com.medical.pojo.resp.verification.VerificationCodeResp;
import com.medical.service.AdminService;
import com.medical.service.EhcacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "管理员")
@RequestMapping("/manage/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private EhcacheService ehcacheService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Admin>> page(@RequestBody AdminPage dto) {
        return R.ok(adminService.page(dto));
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<String> login(@RequestBody @Valid AdminLogin dto) {
        Admin admin = adminService.findByAccount(dto.getAccount());
        if(admin == null){
            return R.failed("账号或者密码错误");
        }
        if(!admin.getPassword().equals(CodeTools.md5AndSalt(dto.getPassword()))){
            return R.failed("账号或者密码错误");
        }

        String tokenId = GenerateTools.getUUID();
        admin.setTokenId(tokenId);
        admin.setLoginIp(HttpTools.getIp());
        admin.setAddress(HttpTools.getAddress());
        admin.setLastLoginTime(LocalDateTime.now());

        ehcacheService.getAdminTokenCache().put(tokenId, admin);
        adminService.updateById(admin);
        return R.ok(tokenId);
    }

    @PostMapping("/getVerification")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public synchronized R<VerificationCodeResp> getVerification() {
        String ip = HttpTools.getIp();
        log.info("ip:{}请求图片验证码", ip);

        String codeImageStream = ehcacheService.getVC(ip,30,"每3秒超过30次点击验证码");

        VerificationCodeResp verificationCodeResp = new VerificationCodeResp();
        verificationCodeResp.setCaptchaImage("data:image/png;base64," + codeImageStream);
        return R.ok(verificationCodeResp);
    }




}
