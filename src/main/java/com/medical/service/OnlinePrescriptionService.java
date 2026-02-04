package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.OnlinePrescription;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionAdd;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionPage;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionUpdateStatus;

public interface OnlinePrescriptionService extends IService<OnlinePrescription> {

    IPage<OnlinePrescription> queryPage(OnlinePrescriptionPage onlinePrescriptionPage, Long userId);

    OnlinePrescription addOnlinePrescription(OnlinePrescriptionAdd onlinePrescriptionAdd);

    void updateStatus(OnlinePrescriptionUpdateStatus onlinePrescriptionUpdateStatus);

    void deleteOnlinePrescription(Long id);

    int unfinishedCount(Long userId);
}
