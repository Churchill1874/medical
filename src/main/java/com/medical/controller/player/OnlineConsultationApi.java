package com.medical.controller.player;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlineConsultation;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationAdd;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.pojo.resp.onlineconsulation.OnlineConsultationResp;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.NewMessageService;
import com.medical.service.OnlineConsultationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "在线问诊")
@RequestMapping("/player/onlineConsultation")
public class OnlineConsultationApi {

    @Resource
    private NewMessageService newMessageService;
    @Resource
    private OnlineConsultationService onlineConsultationService;

    @PostMapping("/findById")
    @ApiOperation(value = "查询并删除新消息未读状态", notes = "查询并删除新消息未读状态")
    public R<OnlineConsultation> findById(@RequestBody @Valid IdBase req) {
        return R.ok(onlineConsultationService.findById(req.getId(),TokenTools.getPlayerToken(true).getId()));
    }

    @PostMapping("/addOnlineConsultation")
    @ApiOperation(value = "提交在线问诊表单", notes = "提交在线问诊表单")
    public R<OnlineConsultation> addOnlineConsultation(@RequestBody @Valid OnlineConsultationAdd req) {
        if(StringUtils.isEmpty(req.getPhone()) && StringUtils.isEmpty(req.getWechat())){
            return R.failed("请输入微信号或者手机号");
        }
        OnlineConsultation onlineConsultation = onlineConsultationService.addOnlineConsultation(req);
        return R.ok(onlineConsultation);
    }

    @PostMapping("/queryPage")
    @ApiOperation(value = "分页", notes = "分页")
    public R<IPage<OnlineConsultationResp>> queryPage(@RequestBody @Valid OnlineConsultationPage req) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        IPage<OnlineConsultation> onlineConsultationIPage = onlineConsultationService.queryPage(req, playerTokenResp.getId());

        IPage<OnlineConsultationResp> iPage = new Page<>(req.getPageNum(), req.getPageSize());
        iPage.setTotal(onlineConsultationIPage.getTotal());
        iPage.setPages(onlineConsultationIPage.getPages());

        if(CollectionUtils.isEmpty(onlineConsultationIPage.getRecords())) {
            return R.ok(iPage);
        }

        List<Long> orderIdList = onlineConsultationIPage.getRecords().stream().map(OnlineConsultation::getId).collect(Collectors.toList());
        Set<Long> existingNewMessage = newMessageService.existingNewMessageSet(5, null, orderIdList, playerTokenResp.getId());

        List<OnlineConsultationResp> list = new ArrayList<>();
        for(OnlineConsultation onlineConsultation: onlineConsultationIPage.getRecords()){
            OnlineConsultationResp onlineConsultationResp = BeanUtil.toBean(onlineConsultation, OnlineConsultationResp.class);
            if(existingNewMessage.contains(onlineConsultationResp.getId())){
                onlineConsultationResp.setNewMessage(true);
            }
            list.add(onlineConsultationResp);
        }

        iPage.setRecords(list);
        return R.ok(iPage);
    }

}
