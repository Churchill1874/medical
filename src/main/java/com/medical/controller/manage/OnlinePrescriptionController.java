package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.annotation.AdminLoginCheck;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlineConsultation;
import com.medical.entity.OnlinePrescription;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationStatusUpdate;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionPage;
import com.medical.pojo.req.onlineprescription.OnlinePrescriptionUpdateStatus;
import com.medical.service.OnlinePrescriptionService;
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
@Api(tags = "在线咨询处方药")
@RequestMapping("/manage/onlinePrescription")
public class OnlinePrescriptionController {

    @Resource
    private OnlinePrescriptionService onlinePrescriptionService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<OnlinePrescription>> page(@RequestBody OnlinePrescriptionPage req) {
        return R.ok(onlinePrescriptionService.queryPage(req, null));
    }

    @PostMapping("/updateStatusById")
    @ApiOperation(value = "修改状态", notes = "修改状态")
    public R updateStatusById(@RequestBody @Valid OnlinePrescriptionUpdateStatus req) {
        onlinePrescriptionService.updateStatus(req);
        return R.ok(null);
    }

    @PostMapping("/deleteById")
    @ApiOperation(value = "删除", notes = "删除")
    @AdminLoginCheck
    public R deleteById(@RequestBody @Valid IdBase req) {
        onlinePrescriptionService.deleteOnlinePrescription(req.getId());
        return R.ok(null);
    }

}
