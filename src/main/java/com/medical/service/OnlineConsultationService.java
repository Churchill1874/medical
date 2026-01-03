package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.OnlineConsultation;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationAdd;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;

public interface OnlineConsultationService extends IService<OnlineConsultation> {

    IPage<OnlineConsultation> queryPage(OnlineConsultationPage dto, Long userId);

    Long addOnlineConsultation(OnlineConsultationAdd dto);

    void updateStatusById(Long id, Integer status, String adminName);

    void deleteById(Long id);


}
