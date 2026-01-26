package com.medical.controller.player;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Dialogue;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.dialogue.DialoguePage;
import com.medical.pojo.req.dialogue.OnlineConsultationDialogueSend;
import com.medical.pojo.req.dialogue.OnlinePrescriptionDialogueSend;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.AdminService;
import com.medical.service.DialogueService;
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
@RequestMapping("/player/dialogue")
public class DialogueApi {

    @Resource
    private AdminService adminService;
    @Resource
    private DialogueService dialogueService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Dialogue>> page(@RequestBody DialoguePage req) {
        if(req.getOnlineConsultationId() == null){
            return R.failed("为关联在线问诊订单");
        }
        return R.ok(dialogueService.queryPage(req));
    }

    @PostMapping("/send")
    @ApiOperation(value = "发送在线咨询疾病", notes = "发送在线咨询疾病")
    public R send(@RequestBody @Valid OnlineConsultationDialogueSend req) {
        if(req.getOnlineConsultationId() == null){
            return R.failed("缺少在线咨询订单id");
        }
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        Dialogue dialogue = BeanUtil.toBean(req, Dialogue.class);
        dialogueService.sendDialogue(dialogue, false, playerTokenResp.getId(), playerTokenResp.getUsername(),null, null,2);
        return R.ok(null);
    }

    @PostMapping("/sendPrescriptionMessage")
    @ApiOperation(value = "发送处方药咨询消息", notes = "发送处方药咨询消息")
    public R sendPrescriptionMessage(@RequestBody @Valid OnlinePrescriptionDialogueSend req) {
        if(req.getOnlinePrescriptionId() == null){
            return R.failed("缺少在线咨询处方药订单id");
        }
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        Dialogue dialogue = BeanUtil.toBean(req, Dialogue.class);
        dialogueService.sendDialogue(dialogue, false, playerTokenResp.getId(), playerTokenResp.getUsername(),null, null,1);
        return R.ok(null);
    }

    @PostMapping("/updateStatusById")
    @ApiOperation(value = "修改成已读状态", notes = "修改成已读状态")
    public R updateStatusById(@RequestBody @Valid IdBase req) {
        dialogueService.updateStatusById(req.getId());
        return R.ok(null);
    }


}
