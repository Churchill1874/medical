package com.medical.controller.player;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlineConsultation;
import com.medical.entity.OnlinePrescription;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationAdd;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionAdd;
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

@Slf4j
@RestController
@Api(tags = "在线咨询处方药")
@RequestMapping("/player/onlinePrescription")
public class OnlinePrescriptionApi {

    @Resource
    private OnlinePrescriptionService onlinePrescriptionService;

    @PostMapping("/addOnlinePrescription")
    @ApiOperation(value = "提交咨询处方药订单", notes = "提交咨询处方药订单")
    public R<Long> addOnlinePrescription(@RequestBody @Valid OnlinePrescriptionAdd req) {
        if(StringUtils.isBlank(req.getPhone())){
            return R.failed("请填写联系电话");
        }
        Long id = onlinePrescriptionService.addOnlinePrescription(req);
        return R.ok(id);
    }

    @PostMapping("/queryPage")
    @ApiOperation(value = "分页", notes = "分页")
    public R<IPage<OnlinePrescription>> queryPage(@RequestBody @Valid OnlineConsultationPage req) {
        IPage<OnlinePrescription> iPage = onlinePrescriptionService.queryPage(req, TokenTools.getPlayerToken(true).getId());
        return R.ok(iPage);
    }

}
