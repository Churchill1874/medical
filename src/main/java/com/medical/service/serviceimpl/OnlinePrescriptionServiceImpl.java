package com.medical.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlinePrescription;
import com.medical.entity.Prescription;
import com.medical.mapper.OnlinePrescriptionMapper;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionAdd;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionPage;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionUpdateStatus;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.OnlinePrescriptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OnlinePrescriptionServiceImpl extends ServiceImpl<OnlinePrescriptionMapper, OnlinePrescription> implements OnlinePrescriptionService {

    @Override
    public IPage<OnlinePrescription> queryPage(OnlinePrescriptionPage onlinePrescriptionPage, Long userId) {
        IPage<OnlinePrescription> iPage = new Page<>(onlinePrescriptionPage.getPageNum(), onlinePrescriptionPage.getPageSize());
        LambdaQueryWrapper<OnlinePrescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(userId != null, OnlinePrescription::getUserId, userId)
                .eq(onlinePrescriptionPage.getStatus() != null, OnlinePrescription::getStatus, onlinePrescriptionPage.getStatus())
                .eq(StringUtils.isNotBlank(onlinePrescriptionPage.getRealName()), OnlinePrescription::getRealName, onlinePrescriptionPage.getRealName())
                .orderByDesc(OnlinePrescription::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public OnlinePrescription addOnlinePrescription(OnlinePrescriptionAdd onlinePrescriptionAdd) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        OnlinePrescription onlinePrescription = BeanUtil.toBean(onlinePrescriptionAdd, OnlinePrescription.class);
        onlinePrescription.setCreateName(playerTokenResp.getRealName());
        onlinePrescription.setCreateTime(LocalDateTime.now());
        onlinePrescription.setUserId(playerTokenResp.getId());
        onlinePrescription.setAccount(playerTokenResp.getUsername());
        onlinePrescription.setStatus(0);
        save(onlinePrescription);
        return onlinePrescription;
    }

    @Override
    public void updateStatus(OnlinePrescriptionUpdateStatus onlinePrescriptionUpdateStatus) {
        LambdaUpdateWrapper<OnlinePrescription> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(OnlinePrescription::getStatus, onlinePrescriptionUpdateStatus.getStatus())
                .set(OnlinePrescription::getUpdateTime, LocalDateTime.now())
                .set(OnlinePrescription::getUpdateName, TokenTools.getAdminToken(true).getName())
                .set(OnlinePrescription::getReason, onlinePrescriptionUpdateStatus.getReason())
                .eq(OnlinePrescription::getId, onlinePrescriptionUpdateStatus.getId());
        update(updateWrapper);
    }

    @Override
    public void deleteOnlinePrescription(Long id) {
        removeById(id);
    }

    @Override
    public int unfinishedCount(Long userId) {
        LambdaQueryWrapper<OnlinePrescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(OnlinePrescription::getStatus, 2);
        queryWrapper.eq(userId != null, OnlinePrescription::getUserId, userId);
        return count(queryWrapper);
    }

}
