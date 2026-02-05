package com.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.NewMessage;
import com.medical.pojo.resp.admin.UnfinishCountReport;

import java.util.List;
import java.util.Set;

public interface NewMessageService extends IService<NewMessage> {

    void addNewMessage(Integer type, String medicalType, String content, Long orderId, Long playerId, String createName);

    Set<Long> existingNewMessageSet(Integer type, List<String> medicalTypeList, List<Long> orderIdList, Long userId);

    void deleteNewMessage(Integer type, String medicalType, Long orderId, Long userId);

    UnfinishCountReport unfinishCountReport(Long userId);
}
