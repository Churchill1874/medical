package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.Prescription;
import com.medical.pojo.req.prescription.PrescriptionPage;

public interface PrescriptionService extends IService<Prescription> {

    IPage<Prescription> queryPage(PrescriptionPage dto);

    void addPrescription(Prescription prescription);

    void updateStatus(Long id, Integer status, String remark);

}
