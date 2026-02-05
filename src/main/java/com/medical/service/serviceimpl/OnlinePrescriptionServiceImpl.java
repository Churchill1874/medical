package com.medical.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Admin;
import com.medical.entity.OnlinePrescription;
import com.medical.mapper.OnlinePrescriptionMapper;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionAdd;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionPage;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionUpdateStatus;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.NewMessageService;
import com.medical.service.OnlinePrescriptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class OnlinePrescriptionServiceImpl extends ServiceImpl<OnlinePrescriptionMapper, OnlinePrescription> implements OnlinePrescriptionService {

    @Resource
    private NewMessageService newMessageService;

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
        Admin admin = TokenTools.getAdminToken(true);
        LambdaUpdateWrapper<OnlinePrescription> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(OnlinePrescription::getStatus, onlinePrescriptionUpdateStatus.getStatus())
                .set(OnlinePrescription::getUpdateTime, LocalDateTime.now())
                .set(OnlinePrescription::getUpdateName, admin.getName())
                .set(OnlinePrescription::getReason, onlinePrescriptionUpdateStatus.getReason())
                .eq(OnlinePrescription::getId, onlinePrescriptionUpdateStatus.getId());
        update(updateWrapper);

        OnlinePrescription onlinePrescription = getById(onlinePrescriptionUpdateStatus.getId());
        String statusStr = "在线咨询处方药订单状态变化" + onlinePrescriptionUpdateStatus.getStatus();
        newMessageService.addNewMessage(3,null, statusStr, onlinePrescription.getId(), onlinePrescription.getUserId(), admin.getName());
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

    @Override
    public OnlinePrescription findById(Long id, Long  userId) {
        OnlinePrescription onlinePrescription = getById(id);
        newMessageService.deleteNewMessage(3, null, onlinePrescription.getId(),userId);
        return onlinePrescription;
    }

}
