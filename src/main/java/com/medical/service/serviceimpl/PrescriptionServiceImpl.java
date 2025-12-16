package com.medical.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.entity.Prescription;
import com.medical.mapper.PrescriptionMapper;
import com.medical.pojo.req.prescription.PrescriptionPage;
import com.medical.service.PrescriptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

}
