package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.Dialogue;
import com.medical.pojo.req.dialogue.DialoguePage;
import com.medical.pojo.req.dialogue.OnlineConsultationDialogueSend;

public interface DialogueService extends IService<Dialogue> {

    IPage<Dialogue> queryPage(DialoguePage dto);

    void sendDialogue(Dialogue dto, Boolean isAdmin, Long sendId, String sendName, Long receiveId, String receiveName, int business);

    void updateStatusById(Long id);

    void deleteById(Long id);

}
