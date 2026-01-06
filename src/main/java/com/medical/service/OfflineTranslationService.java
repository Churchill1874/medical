package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.OfflineTranslation;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationPage;

public interface OfflineTranslationService extends IService<OfflineTranslation> {

    IPage<OfflineTranslation> queryPage(OfflineTranslationPage dto);

    void addOfflineTranslation(OfflineTranslation dto);

    void updateStatus(Long id, String status);

    void deleteById(Long id);
}
