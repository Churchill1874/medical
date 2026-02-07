package com.medical.controller.manage;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.tools.CodeTools;
import com.medical.common.tools.GenerateTools;
import com.medical.common.tools.HttpTools;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Admin;
import com.medical.pojo.req.admin.AdminLogin;
import com.medical.pojo.req.admin.AdminPage;
import com.medical.pojo.resp.admin.UnfinishCountReport;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.pojo.resp.verification.VerificationCodeResp;
import com.medical.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "管理员")
@RequestMapping("/manage/admin")
public class AdminController {

    @Resource
    private NewMessageService newMessageService;
    @Resource
    private OfflineTranslationService offlineTranslationService;
    @Resource
    private OnlinePrescriptionService onlinePrescriptionService;
    @Resource
    private OnlineConsultationService onlineConsultationService;
    @Resource
    private PrescriptionService prescriptionService;

    @Autowired
    private AdminService adminService;
    @Autowired
    private EhcacheService ehcacheService;

    @PostMapping("/unfinishCount")
    @ApiOperation(value = "统计未完成的订单数量", notes = "统计未完成的订单数量")
    public R<UnfinishCountReport> unfinishCount() {
        UnfinishCountReport unfinishCountReport = new UnfinishCountReport();
        unfinishCountReport.setPrescriptionCount(prescriptionService.unfinishedCount(null));
        unfinishCountReport.setOnlineConsultationCount(onlineConsultationService.unfinishedCount(null));
        unfinishCountReport.setOffTranslationCount(offlineTranslationService.unfinishedCount(null));
        unfinishCountReport.setOnlinePrescriptionCount(onlinePrescriptionService.unfinishedCount(null));
        return R.ok(unfinishCountReport);
    }


    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Admin>> page(@RequestBody AdminPage dto) {
        return R.ok(adminService.page(dto));
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<String> login(@RequestBody @Valid AdminLogin dto) {
        String verifyCode = ehcacheService.verificationCache().get(HttpTools.getIp());
        if (StringUtils.isEmpty(verifyCode) || !dto.getVerifyCode().equalsIgnoreCase(verifyCode)) {
            return R.failed("验证码有误或已过期");
        }
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


    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public R logout() {
        Admin admin = TokenTools.getAdminToken(true);

        Cache<String, Admin> cache = ehcacheService.getAdminTokenCache();
        cache.remove(admin.getTokenId());

        return R.ok(null);
    }


}
