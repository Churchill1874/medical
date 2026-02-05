package com.medical.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.medical.entity.NewMessage;
import com.medical.mapper.NewMessageMapper;
import com.medical.service.NewMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewMessageServiceImpl extends ServiceImpl<NewMessageMapper, NewMessage> implements NewMessageService {

    @Async
    @Override
    public void addNewMessage(Integer type, String medicalType, String content, Long orderId, Long userId, String createName) {
        LambdaQueryWrapper<NewMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(NewMessage::getType, type)
                .eq(StringUtils.isNotBlank(medicalType), NewMessage::getMedicalType, medicalType)
                .eq(NewMessage::getUserId, userId)
                .eq(NewMessage::getOrderId, orderId);
        int count = count(queryWrapper);

        if (count > 0) {
            return;
        }

        NewMessage newMessage = new NewMessage();
        newMessage.setType(type);
        newMessage.setUserId(userId);
        newMessage.setMedicalType(medicalType);
        newMessage.setContent(content);
        newMessage.setOrderId(orderId);
        newMessage.setCreateName(createName);
        newMessage.setCreateTime(LocalDateTime.now());
        save(newMessage);
    }

    @Override
    public Set<Long> existingNewMessageSet(Integer type, List<String> medicalTypeList, List<Long> orderIdList, Long userId) {
        LambdaQueryWrapper<NewMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(NewMessage::getOrderId, orderIdList);
        queryWrapper.in(CollectionUtils.isNotEmpty(medicalTypeList), NewMessage::getMedicalType, medicalTypeList);
        queryWrapper.eq(NewMessage::getType, type);
        queryWrapper.eq(NewMessage::getUserId, userId);
        List<NewMessage> newMessageList = list(queryWrapper);

        if (CollectionUtils.isEmpty(newMessageList)) {
            return Sets.newHashSet();
        }

        return newMessageList.stream().map(NewMessage::getOrderId).collect(Collectors.toSet());
    }

    @Async
    @Override
    public void deleteNewMessage(Integer type, String medicalType, Long orderId, Long userId) {
        LambdaQueryWrapper<NewMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NewMessage::getOrderId, orderId);
        queryWrapper.eq(NewMessage::getType, type);
        queryWrapper.eq(StringUtils.isNotBlank(medicalType), NewMessage::getMedicalType, medicalType);
        remove(queryWrapper);
    }


}
