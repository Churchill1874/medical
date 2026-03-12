package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.UserInfo;
import com.medical.pojo.req.player.UserInfoPage;

public interface UserInfoService extends IService<UserInfo> {

    IPage<UserInfo> queryPage(UserInfoPage dto);

    UserInfo addUserInfo(UserInfo userInfo);

    UserInfo findByAccount(String username);

}
