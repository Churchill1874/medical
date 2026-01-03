package com.medical.controller.player;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlineConsultation;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationAdd;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.service.OnlineConsultationService;
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
@Api(tags = "在线问诊")
@RequestMapping("/player/onlineConsultation")
public class OnlineConsultationApi {

    @Resource
    private OnlineConsultationService onlineConsultationService;

    @PostMapping("/addOnlineConsultation")
    @ApiOperation(value = "提交在线问诊表单", notes = "提交在线问诊表单")
    public R<Long> addOnlineConsultation(@RequestBody @Valid OnlineConsultationAdd req) {
        Long id = onlineConsultationService.addOnlineConsultation(req);
        return R.ok(id);
    }

    @PostMapping("/queryPage")
    @ApiOperation(value = "分页", notes = "分页")
    public R<IPage<OnlineConsultation>> queryPage(@RequestBody @Valid OnlineConsultationPage req) {
        IPage<OnlineConsultation> iPage = onlineConsultationService.queryPage(req, TokenTools.getPlayerToken(true).getId());
        return R.ok(iPage);
    }

}
