package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.annotation.AdminLoginCheck;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlineConsultation;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationStatusUpdate;
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
@RequestMapping("/manage/onlineConsultation")
public class OnlineConsultationController {

    @Resource
    private OnlineConsultationService onlineConsultationService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<OnlineConsultation>> page(@RequestBody OnlineConsultationPage req) {
        return R.ok(onlineConsultationService.queryPage(req, null));
    }

    @PostMapping("/updateStatusById")
    @ApiOperation(value = "修改状态", notes = "修改状态")
    public R updateStatusById(@RequestBody OnlineConsultationStatusUpdate req) {
        onlineConsultationService.updateStatusById(req.getId(), req.getStatus(), TokenTools.getAdminToken(true).getName());
        return R.ok(null);
    }

    @PostMapping("/deleteById")
    @ApiOperation(value = "删除", notes = "删除")
    @AdminLoginCheck
    public R deleteById(@RequestBody @Valid IdBase req) {
        onlineConsultationService.deleteById(req.getId());
        return R.ok(null);
    }

}
