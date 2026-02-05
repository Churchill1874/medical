package com.medical.controller.player;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlineConsultation;
import com.medical.entity.OnlinePrescription;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationAdd;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionAdd;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionPage;
import com.medical.pojo.resp.onlineconsulation.OnlineConsultationResp;
import com.medical.pojo.resp.onlineprescription.OnlinePrescriptionResp;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.NewMessageService;
import com.medical.service.OnlinePrescriptionService;
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
@Api(tags = "在线咨询处方药")
@RequestMapping("/player/onlinePrescription")
public class OnlinePrescriptionApi {

    @Resource
    private NewMessageService newMessageService;
    @Resource
    private OnlinePrescriptionService onlinePrescriptionService;

    @PostMapping("/findById")
    @ApiOperation(value = "查询详情并删除新消息状态", notes = "查询详情并删除新消息状态")
    public R<OnlinePrescription> findById(@RequestBody @Valid IdBase req) {
        return R.ok(onlinePrescriptionService.findById(req.getId(), TokenTools.getPlayerToken(true).getId()));
    }


    @PostMapping("/addOnlinePrescription")
    @ApiOperation(value = "提交咨询处方药订单", notes = "提交咨询处方药订单")
    public R<OnlinePrescription> addOnlinePrescription(@RequestBody @Valid OnlinePrescriptionAdd req) {
        if(StringUtils.isBlank(req.getPhone())){
            return R.failed("请填写联系电话");
        }
        OnlinePrescription onlinePrescription = onlinePrescriptionService.addOnlinePrescription(req);
        return R.ok(onlinePrescription);
    }

    @PostMapping("/queryPage")
    @ApiOperation(value = "分页", notes = "分页")
    public R<IPage<OnlinePrescriptionResp>> queryPage(@RequestBody @Valid OnlinePrescriptionPage req) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        IPage<OnlinePrescription> onlinePrescriptionIPage = onlinePrescriptionService.queryPage(req, playerTokenResp.getId());

        IPage<OnlinePrescriptionResp> iPage = new Page<>(req.getPageNum(), req.getPageSize());
        iPage.setPages(onlinePrescriptionIPage.getPages());
        iPage.setTotal(onlinePrescriptionIPage.getTotal());

        List<OnlinePrescription> onlinePrescriptionList = onlinePrescriptionIPage.getRecords();
        if(CollectionUtils.isEmpty(onlinePrescriptionList)){
            return R.ok(iPage);
        }

        List<OnlinePrescriptionResp> list = new ArrayList<>();

        List<Long> orderIdList = onlinePrescriptionList.stream().map(OnlinePrescription::getId).collect(Collectors.toList());
        Set<Long> existingMessage = newMessageService.existingNewMessageSet(3, null, orderIdList, playerTokenResp.getId());

        for(OnlinePrescription onlinePrescription : onlinePrescriptionList){
            OnlinePrescriptionResp onlinePrescriptionResp = BeanUtil.toBean(onlinePrescription, OnlinePrescriptionResp.class);
            if(existingMessage.contains(onlinePrescriptionResp.getId())){
                onlinePrescriptionResp.setNewMessage(true);
            }
            list.add(onlinePrescriptionResp);
        }

        iPage.setRecords(list);
        return R.ok(iPage);
    }

}
