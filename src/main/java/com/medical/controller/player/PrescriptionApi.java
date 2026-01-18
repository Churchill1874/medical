package com.medical.controller.player;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Prescription;
import com.medical.pojo.req.prescription.PrescriptionAdd;
import com.medical.pojo.req.prescription.PrescriptionPage;
import com.medical.service.PrescriptionService;
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
@Api(tags = "处方药")
@RequestMapping("/player/prescription")
public class PrescriptionApi {

    @Resource
    private PrescriptionService prescriptionService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Prescription>> page(@RequestBody PrescriptionPage req) {
        req.setUserId(TokenTools.getPlayerToken(true).getId());
        return R.ok(prescriptionService.queryPage(req));
    }

    @PostMapping("/add")
    @ApiOperation(value = "提交下单", notes = "提交下单")
    public R add(@RequestBody @Valid PrescriptionAdd req) {
        Prescription prescription = BeanUtil.toBean(req, Prescription.class);
        prescriptionService.addPrescription(prescription);
        return R.ok(null);
    }

}
