package com.medical.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.annotation.AdminLoginCheck;
import com.medical.common.exception.DataException;
import com.medical.common.tools.TokenTools;
import com.medical.entity.*;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.dialogue.DialoguePage;
import com.medical.pojo.req.dialogue.OnlineConsultationDialogueSend;
import com.medical.pojo.req.dialogue.OnlinePrescriptionDialogueSend;
import com.medical.service.DialogueService;
import com.medical.service.OnlineConsultationService;
import com.medical.service.OnlinePrescriptionService;
import com.medical.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "对话")
@RequestMapping("/manage/dialogue")
public class DialogueController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private DialogueService dialogueService;
    @Resource
    private OnlineConsultationService onlineConsultationService;
    @Resource
    private OnlinePrescriptionService onlinePrescriptionService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Dialogue>> page(@RequestBody DialoguePage req) {
        return R.ok(dialogueService.queryPage(req));
    }

    @PostMapping("/send")
    @ApiOperation(value = "发送", notes = "发送")
    public R send(@RequestBody @Valid OnlineConsultationDialogueSend req) {
        if(req.getOnlineConsultationId() == null){
            return R.failed("缺少在线咨询订单id");
        }
        Admin admin = TokenTools.getAdminToken(true);
        OnlineConsultation onlineConsultation = onlineConsultationService.getById(req.getOnlineConsultationId());
        if(onlineConsultation == null){
            throw new DataException("未找到在线问诊订单");
        }

        Dialogue dialogue = BeanUtil.toBean(req, Dialogue.class);
        dialogueService.sendDialogue(dialogue, true, admin.getId(), admin.getName(),onlineConsultation.getUserId(), onlineConsultation.getRealName(), 2);
        return R.ok(null);
    }

    @PostMapping("/sendPrescriptionMessage")
    @ApiOperation(value = "发送处方药咨询消息", notes = "发送处方药咨询消息")
    public R sendPrescriptionMessage(@RequestBody @Valid OnlinePrescriptionDialogueSend req) {
        if(req.getOnlinePrescriptionId() == null){
            return R.failed("缺少在线咨询处方药订单id");
        }
        Admin admin = TokenTools.getAdminToken(true);
        OnlinePrescription onlinePrescription = onlinePrescriptionService.getById(req.getOnlinePrescriptionId());
        if(onlinePrescription == null){
            throw new DataException("未找到在线咨询处方药订单");
        }

        Dialogue dialogue = BeanUtil.toBean(req, Dialogue.class);
        dialogueService.sendDialogue(dialogue, true, admin.getId(), admin.getName(), onlinePrescription.getUserId(), onlinePrescription.getRealName(),1);
        return R.ok(null);
    }

    @PostMapping("/updateStatusById")
    @ApiOperation(value = "修改成已读状态", notes = "修改成已读状态")
    @AdminLoginCheck
    public R updateStatusById(@RequestBody @Valid IdBase req) {
        dialogueService.updateStatusById(req.getId());
        return R.ok(null);
    }

    @PostMapping("/deleteById")
    @ApiOperation(value = "删除", notes = "删除")
    @AdminLoginCheck
    public R deleteById(@RequestBody @Valid IdBase req) {
        dialogueService.deleteById(req.getId());
        return R.ok(null);
    }

}
