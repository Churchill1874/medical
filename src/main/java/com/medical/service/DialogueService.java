package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.Dialogue;
import com.medical.pojo.req.dialogue.DialoguePage;
import com.medical.pojo.req.dialogue.DialogueSend;

public interface DialogueService extends IService<Dialogue> {

    IPage<Dialogue> queryPage(DialoguePage dto);

    void sendDialogue(DialogueSend dto, Boolean isAdmin, Long sendId, String sendName, String receiveName);

    void updateStatusById(Long id);

    void deleteById(Long id);

}
