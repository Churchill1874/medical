package com.medical.controller.player;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OfflineTranslation;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationAdd;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationPage;
import com.medical.pojo.resp.offlinetranslation.OfflineTranslationResp;
import com.medical.service.NewMessageService;
import com.medical.service.OfflineTranslationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Api(tags = "线下陪同翻译")
@RequestMapping("/player/offlineTranslation")
public class OfflineTranslationApi {

    @Resource
    private NewMessageService newMessageService;
    @Resource
    private OfflineTranslationService offlineTranslationService;

    @PostMapping("/findById")
    @ApiOperation(value = "查询详情并删除新消息状态", notes = "查询详情并删除新消息状态")
    public R<OfflineTranslation> findById(@RequestBody @Valid IdBase req) {
        return R.ok(offlineTranslationService.findById(req.getId(), TokenTools.getPlayerToken(true).getId()));
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<OfflineTranslationResp>> page(@RequestBody OfflineTranslationPage req) {
        req.setUserId(TokenTools.getPlayerToken(true).getId());

        IPage<OfflineTranslation> iPage = offlineTranslationService.queryPage(req);

        IPage<OfflineTranslationResp> offlineTranslationRespIPage = new Page<>(req.getPageNum(), req.getPageSize());
        offlineTranslationRespIPage.setPages(iPage.getPages());
        offlineTranslationRespIPage.setCurrent(iPage.getCurrent());
        offlineTranslationRespIPage.setSize(iPage.getSize());
        offlineTranslationRespIPage.setTotal(iPage.getTotal());

        List<OfflineTranslation> offlineTranslationList = iPage.getRecords();
        if(CollectionUtils.isEmpty(offlineTranslationList)){
            return R.ok(offlineTranslationRespIPage);
        }

        List<Long> offlineTranslationIdList = offlineTranslationList.stream().map(OfflineTranslation::getId).collect(Collectors.toList());
        List<String> medicalTypeList = new ArrayList<>();
        medicalTypeList.add("预防医疗");
        medicalTypeList.add("再生医疗");
        medicalTypeList.add("医疗美容");
        medicalTypeList.add("普通医疗");

        Set<Long> existingNewMessage = newMessageService.existingNewMessageSet(4, medicalTypeList, offlineTranslationIdList, req.getUserId());

        List<OfflineTranslationResp> list = new ArrayList<>();
        for(OfflineTranslation offlineTranslation : offlineTranslationList){
            OfflineTranslationResp offlineTranslationResp = BeanUtil.toBean(offlineTranslation, OfflineTranslationResp.class);
            if(existingNewMessage.contains(offlineTranslationResp.getId())){
                offlineTranslationResp.setNewMessage(true);
            }
            list.add(offlineTranslationResp);
        }

        offlineTranslationRespIPage.setRecords(list);
        return R.ok(offlineTranslationRespIPage);
    }

    @PostMapping("/addOfflineTranslation")
    @ApiOperation(value = "添加请求", notes = "添加请求")
    public R addOfflineTranslation(@RequestBody @Valid OfflineTranslationAdd req) {
        OfflineTranslation offlineTranslation = BeanUtil.toBean(req, OfflineTranslation.class);
        offlineTranslationService.addOfflineTranslation(offlineTranslation);
        return R.ok(null);
    }



}
