package com.medical.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.common.tools.CodeTools;
import com.medical.common.tools.HttpTools;
import com.medical.entity.UserInfo;
import com.medical.mapper.UserInfoMapper;
import com.medical.pojo.req.player.UserInfoPage;
import com.medical.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public IPage<UserInfo> queryPage(UserInfoPage dto) {
        IPage<UserInfo> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(dto.getUsername()), UserInfo::getUsername, dto.getUsername())
                .eq(StringUtils.isNotBlank(dto.getPhone()), UserInfo::getPhone, dto.getPhone())
                .eq(StringUtils.isNotBlank(dto.getAddress()), UserInfo::getAddress, dto.getAddress())
                .eq(StringUtils.isNotBlank(dto.getEmail()), UserInfo::getEmail, dto.getEmail())
                .eq(dto.getGender() != null, UserInfo::getGender, dto.getGender())
                .eq(dto.getAge() != null, UserInfo::getAge, dto.getAge())
                .orderByDesc(UserInfo::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfo) {
        userInfo.setPassword(CodeTools.md5AndSalt(userInfo.getPassword()));
        userInfo.setCreateName("系统");
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setStatus(1);
        userInfo.setAddress(HttpTools.getAddress());
        save(userInfo);

        return userInfo;
    }

    @Override
    public UserInfo findByAccount(String username) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername, username);
        return getOne(queryWrapper);
    }

}
