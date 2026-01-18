package com.medical.controller.player;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.exception.AccountOrPasswordException;
import com.medical.common.exception.DataException;
import com.medical.common.tools.CheckReqTools;
import com.medical.common.tools.CodeTools;
import com.medical.common.tools.GenerateTools;
import com.medical.common.tools.HttpTools;
import com.medical.entity.Admin;
import com.medical.entity.UserInfo;
import com.medical.pojo.req.player.UserInfoAdd;
import com.medical.pojo.req.player.UserLoginReq;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.AdminService;
import com.medical.service.EhcacheService;
import com.medical.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@RestController
@Api(tags = "登录相关")
@RequestMapping("/player/login")
public class LoginApi {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private EhcacheService ehcacheService;

    @Resource
    private AdminService adminService;


    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册")
    public R<PlayerTokenResp> register(@RequestBody @Valid UserInfoAdd req) {
        log.info("玩家注册入参:{}", JSONUtil.toJsonStr(req));
        checkVerificationCode(req.getVerifyCode());
        CheckReqTools.password(req.getPassword());

        //校验重复账号
        UserInfo userInfo = userInfoService.findByAccount(req.getUsername());
        if(userInfo != null){
            throw new DataException("账号已存在");
        }
        Admin admin = adminService.findByAccount(req.getUsername());
        if(admin != null){
            throw new DataException("账号已存在");
        }

        userInfo = BeanUtil.toBean(req, UserInfo.class);
        userInfo.setIp(HttpTools.getIp());
        userInfo = userInfoService.addUserInfo(userInfo);

        PlayerTokenResp playerTokenResp = createLoginToken(userInfo);
        return R.ok(playerTokenResp);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<PlayerTokenResp> login(@RequestBody @Valid UserLoginReq req) {
        log.info("玩家登录入参:{}", JSONUtil.toJsonStr(req));
        checkVerificationCode(req.getVerificationCode());

        //根据登录方式查询账号
        UserInfo userInfo = userInfoService.findByAccount(req.getUsername());
        if (userInfo == null) {
            throw new AccountOrPasswordException();
        }
        if (!userInfo.getPassword().equals(CodeTools.md5AndSalt(req.getPassword()))) {
            throw new AccountOrPasswordException();
        }

        userInfo.setAddress(HttpTools.getAddress());
        userInfo.setLastLoginTime(LocalDateTime.now());
        userInfo.setIp(HttpTools.getIp());
        userInfoService.updateById(userInfo);
        return R.ok(createLoginToken(userInfo));
    }

    public PlayerTokenResp createLoginToken(UserInfo userInfo) {
        String tokenId = GenerateTools.getUUID();
        PlayerTokenResp playerTokenResp = BeanUtil.toBean(userInfo, PlayerTokenResp.class);
        playerTokenResp.setId(userInfo.getId());
        playerTokenResp.setTokenId(tokenId);
        playerTokenResp.setLoginTime(LocalDateTime.now());
        playerTokenResp.setUsername(userInfo.getUsername());
        ehcacheService.playerTokenCache().put(tokenId, playerTokenResp);
        return playerTokenResp;
    }

    //校验验证码
    private void checkVerificationCode(String reqVerificationCode) {
        String verificationCode = ehcacheService.verificationCache().get(HttpTools.getIp());
        if (verificationCode == null) {
            throw new DataException("验证码有误或已过期");
        }
        if (!verificationCode.equalsIgnoreCase(reqVerificationCode)) {
            throw new DataException("验证码错误");
        }
    }


}
