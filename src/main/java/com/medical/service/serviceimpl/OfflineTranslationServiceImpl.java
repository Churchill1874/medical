package com.medical.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.common.exception.DataException;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Admin;
import com.medical.entity.Dialogue;
import com.medical.entity.OfflineTranslation;
import com.medical.entity.OnlineConsultation;
import com.medical.mapper.DialogueMapper;
import com.medical.mapper.OfflineTranslationMapper;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationPage;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.NewMessageService;
import com.medical.service.OfflineTranslationService;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class OfflineTranslationServiceImpl extends ServiceImpl<OfflineTranslationMapper, OfflineTranslation> implements OfflineTranslationService {

    @Resource
    private NewMessageService newMessageService;

    @Override
    public IPage<OfflineTranslation> queryPage(OfflineTranslationPage dto) {
        IPage<OfflineTranslation> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<OfflineTranslation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(dto.getMedicalType()), OfflineTranslation::getMedicalType, dto.getMedicalType())
                .eq(StringUtils.isNotBlank(dto.getProjectType()), OfflineTranslation::getProjectType, dto.getProjectType())
                .eq(dto.getGender() != null, OfflineTranslation::getGender, dto.getGender())
                .eq(dto.getAge() != null, OfflineTranslation::getAge, dto.getAge())
                .eq(StringUtils.isNotBlank(dto.getRealName()), OfflineTranslation::getRealName, dto.getRealName())
                .eq(StringUtils.isNotBlank(dto.getEmail()), OfflineTranslation::getEmail, dto.getEmail())
                .eq(StringUtils.isNotBlank(dto.getPhone()), OfflineTranslation::getPhone, dto.getPhone())
                .eq(StringUtils.isNotBlank(dto.getStatus()), OfflineTranslation::getStatus, dto.getStatus())
                .eq(StringUtils.isNotBlank(dto.getWechat()), OfflineTranslation::getWechat, dto.getWechat())
                .eq(dto.getUserId() != null,  OfflineTranslation::getUserId, dto.getUserId())
                .eq(StringUtils.isNotBlank(dto.getUsername()), OfflineTranslation::getUsername, dto.getUsername())
                .orderByDesc(OfflineTranslation::getCreateTime);
        return page(iPage,queryWrapper);
    }

    @Override
    public void addOfflineTranslation(OfflineTranslation dto) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        dto.setUserId(playerTokenResp.getId());
        dto.setUsername(playerTokenResp.getUsername());
        dto.setStatus("待受理");
        dto.setCreateName(playerTokenResp.getRealName());
        dto.setCreateTime(LocalDateTime.now());
        save(dto);
    }

    @Override
    public void updateStatus(Long id, String status) {
        Admin admin = TokenTools.getAdminToken(true);
        LambdaUpdateWrapper<OfflineTranslation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OfflineTranslation::getId, id)
                .set(OfflineTranslation::getStatus, status)
                .set(OfflineTranslation::getUpdateTime, LocalDateTime.now())
                .set(OfflineTranslation::getUpdateName, admin.getName());
        update(updateWrapper);

        OfflineTranslation offlineTranslation = getById(id);

        String content = offlineTranslation.getMedicalType() + "线下陪同订单状态变化" + status;
        newMessageService.addNewMessage(4, offlineTranslation.getMedicalType(), content , id, offlineTranslation.getUserId(), admin.getName());
    }


    @Override
    public void deleteById(Long id) {
        removeById(id);
    }

    @Override
    public int unfinishedCount(Long userId) {
        LambdaQueryWrapper<OfflineTranslation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(OfflineTranslation::getStatus, 2);
        queryWrapper.eq(userId != null, OfflineTranslation::getUserId, userId);
        return count(queryWrapper);
    }

    @Override
    public OfflineTranslation findById(Long id, Long userId) {
        OfflineTranslation offlineTranslation = getById(id);
        newMessageService.deleteNewMessage(4, offlineTranslation.getMedicalType(), id, userId);
        return offlineTranslation;
    }


}
