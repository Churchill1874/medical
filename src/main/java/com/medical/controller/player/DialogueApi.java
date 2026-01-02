package com.medical.controller.player;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.annotation.AdminLoginCheck;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Admin;
import com.medical.entity.Dialogue;
import com.medical.entity.UserInfo;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.dialogue.DialoguePage;
import com.medical.pojo.req.dialogue.DialogueSend;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.DialogueService;
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
@RequestMapping("/player/dialogue")
public class DialogueApi {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private DialogueService dialogueService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Dialogue>> page(@RequestBody DialoguePage req) {
        return R.ok(dialogueService.queryPage(req));
    }

    @PostMapping("/send")
    @ApiOperation(value = "发送", notes = "发送")
    public R send(@RequestBody @Valid DialogueSend req) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        UserInfo receiveUser = userInfoService.getById(req.getReceiveId());
        dialogueService.sendDialogue(req, false, playerTokenResp.getId(), playerTokenResp.getUsername(), receiveUser.getUsername());
        return R.ok(null);
    }

    @PostMapping("/updateStatusById")
    @ApiOperation(value = "修改成已读状态", notes = "修改成已读状态")
    @AdminLoginCheck
    public R updateStatusById(@RequestBody @Valid IdBase req) {
        dialogueService.updateStatusById(req.getId());
        return R.ok(null);
    }


}
