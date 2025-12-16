package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.entity.Admin;
import com.medical.entity.Prescription;
import com.medical.pojo.req.admin.AdminPage;
import com.medical.pojo.req.prescription.PrescriptionPage;
import com.medical.service.PrescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "开处方")
@RequestMapping("/manage/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;


    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Prescription>> page(@RequestBody PrescriptionPage req) {
        return R.ok(prescriptionService.queryPage(req));
    }


}
