package com.medical.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Prescription;
import com.medical.mapper.PrescriptionMapper;
import com.medical.pojo.req.prescription.PrescriptionPage;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.PrescriptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PrescriptionServiceImpl extends ServiceImpl<PrescriptionMapper, Prescription> implements PrescriptionService {

    @Override
    public IPage<Prescription> queryPage(PrescriptionPage dto) {
        IPage<Prescription> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<Prescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(dto.getStatus() != null, Prescription::getStatus, dto.getStatus())
                .eq(dto.getReadStatus() != null, Prescription::getReadStatus, dto.getReadStatus())
                .eq(dto.getUserId() != null, Prescription::getUserId, dto.getUserId())
                .eq(StringUtils.isNotBlank(dto.getUsername()), Prescription::getUsername, dto.getUsername())
                .eq(StringUtils.isNotBlank(dto.getMedicalName()),Prescription::getMedicalName, dto.getMedicalName())
                .eq(StringUtils.isNotBlank(dto.getMobile()),Prescription::getMobile, dto.getMobile())
                .eq(StringUtils.isNotBlank(dto.getAccount()),Prescription::getAccount, dto.getAccount())
                .ge(dto.getStartTime() != null, Prescription::getCreateTime, dto.getStartTime())
                .le(dto.getEndTime() != null, Prescription::getCreateTime, dto.getEndTime())
                .orderByAsc(Prescription::getStatus)
                .orderByDesc(Prescription::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void addPrescription(Prescription prescription) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        prescription.setAccount(playerTokenResp.getUsername());
        prescription.setStatus(0);
        prescription.setReadStatus(false);
        prescription.setCreateName(playerTokenResp.getUsername());
        prescription.setCreateTime(LocalDateTime.now());
        prescription.setUserId(playerTokenResp.getId());
        save(prescription);
    }

    @Override
    public void updateStatus(Long id, Integer status, String remark) {
        UpdateWrapper<Prescription> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .lambda()
                .set(Prescription::getUpdateName, TokenTools.getAdminToken(true).getName())
                .set(Prescription::getUpdateTime, LocalDateTime.now())
                .set(Prescription::getRemark, remark)
                .set(Prescription::getStatus, status)
                .eq(Prescription::getId, id);
        update(updateWrapper);
    }


}
