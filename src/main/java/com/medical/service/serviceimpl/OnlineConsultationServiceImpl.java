package com.medical.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlineConsultation;
import com.medical.entity.OnlinePrescription;
import com.medical.mapper.OnlineConsultationMapper;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationAdd;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.OnlineConsultationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class OnlineConsultationServiceImpl extends ServiceImpl<OnlineConsultationMapper, OnlineConsultation> implements OnlineConsultationService {

    @Override
    public IPage<OnlineConsultation> queryPage(OnlineConsultationPage dto, Long userId) {
        IPage<OnlineConsultation> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<OnlineConsultation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(userId != null, OnlineConsultation::getUserId, userId)
                .eq(dto.getStatus() != null, OnlineConsultation::getStatus, dto.getStatus())
                .eq(StringUtils.isNotBlank(dto.getRealName()), OnlineConsultation::getRealName, dto.getRealName());
        queryWrapper.orderByDesc(OnlineConsultation::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public Long addOnlineConsultation(OnlineConsultationAdd dto) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        OnlineConsultation onlineConsultation = BeanUtil.toBean(dto, OnlineConsultation.class);
        onlineConsultation.setStatus(0);
        onlineConsultation.setCreateName(playerTokenResp.getUsername());
        onlineConsultation.setUserId(playerTokenResp.getId());
        onlineConsultation.setCreateTime(LocalDateTime.now());
        this.save(onlineConsultation);

        return onlineConsultation.getId();
    }

    @Override
    public void updateStatusById(Long id, Integer status, String adminName) {
        UpdateWrapper<OnlineConsultation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(OnlineConsultation::getCreateName, adminName)
                .set(OnlineConsultation::getStatus, status)
                .eq(OnlineConsultation::getId, id);
        this.update(updateWrapper);
    }

    @Override
    public void deleteById(Long id) {
        removeById(id);
    }

    @Override
    public int unfinishedCount() {
        LambdaQueryWrapper<OnlineConsultation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(OnlineConsultation::getStatus, 2);
        return count(queryWrapper);
    }

}
