package com.medical.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.entity.Dialogue;
import com.medical.mapper.DialogueMapper;
import com.medical.pojo.req.dialogue.DialoguePage;
import com.medical.pojo.req.dialogue.OnlineConsultationDialogueSend;
import com.medical.service.DialogueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DialogueServiceImpl extends ServiceImpl<DialogueMapper, Dialogue> implements DialogueService {

    private final SimpMessagingTemplate messagingTemplate;

    public DialogueServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public IPage<Dialogue> queryPage(DialoguePage dto) {
        IPage<Dialogue> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());

        LambdaQueryWrapper<Dialogue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(dto.getOnlineConsultationId() != null, Dialogue::getOnlineConsultationId, dto.getOnlineConsultationId())
                .eq(dto.getIsRead() != null, Dialogue::getIsRead, dto.getIsRead())
                .eq(dto.getBusiness() != null, Dialogue::getBusiness, dto.getBusiness())
                .orderByDesc(Dialogue::getCreateTime);

        //如果搜索了用户id
        if (dto.getUserId() != null) {
            queryWrapper
                    .and(
                            q -> q
                                    .eq(dto.getUserId() != null, Dialogue::getSendId, dto.getUserId())
                                    .or()
                                    .eq(dto.getUserId() != null, Dialogue::getReceiveId, dto.getUserId())
                    );
        }

        //如果搜搜了用户名
        if (StringUtils.isNotBlank(dto.getUsername())) {
            queryWrapper
                    .and(
                            q -> q
                                    .eq(StringUtils.isNotBlank(dto.getUsername()), Dialogue::getSendName, dto.getUsername())
                                    .or()
                                    .eq(StringUtils.isNotBlank(dto.getUsername()), Dialogue::getReceiveName, dto.getUsername())
                    );
        }

        return page(iPage, queryWrapper);
    }

    @Override
    public void sendDialogue(Dialogue dto, Boolean isAdmin, Long sendId, String sendName, Long receiveId, String receiveName, int business) {
        Dialogue dialogue = BeanUtil.toBean(dto, Dialogue.class);
        dialogue.setIsRead(Boolean.FALSE);
        dialogue.setIsAdmin(isAdmin);
        dialogue.setSendName(sendName);
        dialogue.setReceiveName(receiveName);
        dialogue.setCreateTime(LocalDateTime.now());
        dialogue.setCreateName(sendName);
        dialogue.setSendId(sendId);
        dialogue.setBusiness(business);
        dialogue.setReceiveId(receiveId);
        save(dialogue);

        messagingTemplate.convertAndSendToUser(receiveName, "/queue/private", dialogue);
    }

    @Async
    @Override
    public void updateStatusById(Long id) {
        LambdaUpdateWrapper<Dialogue> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(Dialogue::getIsRead, Boolean.TRUE)
                .set(Dialogue::getReadTime, LocalDateTime.now())
                .eq(Dialogue::getId, id);
        update(updateWrapper);
    }

    @Override
    public void deleteById(Long id) {
        removeById(id);
    }


}
