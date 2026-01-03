package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.entity.Blacklist;
import com.medical.entity.UserInfo;
import com.medical.pojo.req.blacklist.LockUserReq;
import com.medical.pojo.req.player.UserInfoPage;
import com.medical.service.BlacklistService;
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
@Api(tags = "用户管理")
@RequestMapping("/manage/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private BlacklistService blacklistService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<UserInfo>> page(@RequestBody UserInfoPage req) {
        return R.ok(userInfoService.queryPage(req));
    }

    @PostMapping("/lock")
    @ApiOperation(value = "对用户ip上锁", notes = "对用户ip上锁")
    public R lock(@RequestBody @Valid LockUserReq req) {
        UserInfo userInfo = userInfoService.getById(req.getUserId());
        if(userInfo == null){
            return R.failed("未找到用户id:" + req.getUserId());
        }

        Blacklist blacklist = new Blacklist();
        blacklist.setIp(userInfo.getIp());
        blacklist.setAddress(userInfo.getAddress());
        blacklist.setRemark("后台对用户ip的封禁");
        blacklist.setUnlockingTime(LocalDateTime.now().plusDays(req.getDays()));
        blacklistService.addBlacklist(blacklist);

        return R.ok(null);
    }

}
