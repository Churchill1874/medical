package com.medical.controller.player;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Prescription;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.prescription.PrescriptionAdd;
import com.medical.pojo.req.prescription.PrescriptionPage;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.pojo.resp.prescription.PrescriptionResp;
import com.medical.service.NewMessageService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "处方药")
@RequestMapping("/player/prescription")
public class PrescriptionApi {

    @Resource
    private NewMessageService newMessageService;
    @Resource
    private PrescriptionService prescriptionService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<PrescriptionResp>> page(@RequestBody PrescriptionPage req) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken(true);
        req.setUserId(playerTokenResp.getId());

        IPage<Prescription> prescriptionIPage = prescriptionService.queryPage(req);
        IPage<PrescriptionResp> iPage = new Page<>(req.getPageNum(), req.getPageSize());

        List<Prescription> prescriptionIdList = prescriptionIPage.getRecords();

        if (CollectionUtils.isEmpty(prescriptionIdList)) {
            return R.ok(iPage);
        }

        List<Long> orderIdList = prescriptionIdList.stream().map(Prescription::getId).collect(Collectors.toList());

        Set<Long> set = newMessageService.existingNewMessageSet(2, null, orderIdList, playerTokenResp.getId());

        List<PrescriptionResp> list = new ArrayList<>();

        for(Prescription prescription : prescriptionIdList) {
            PrescriptionResp prescriptionResp = BeanUtil.toBean(prescription, PrescriptionResp.class);
            if(set.contains(prescriptionResp.getId())) {
                prescriptionResp.setNewMessage(true);
            }
            list.add(prescriptionResp);
        }

        iPage.setRecords(list);
        return R.ok(iPage);
    }

    @PostMapping("/findById")
    @ApiOperation(value = "查找详情并删除新消息未读状态", notes = "查找详情并删除新消息未读状态")
    public R<Prescription> findById(@RequestBody @Valid IdBase req) {
        return R.ok(prescriptionService.findById(req.getId(), TokenTools.getPlayerToken(true).getId()));
    }

    @PostMapping("/add")
    @ApiOperation(value = "提交下单", notes = "提交下单")
    public R add(@RequestBody @Valid PrescriptionAdd req) {
        Prescription prescription = BeanUtil.toBean(req, Prescription.class);
        prescriptionService.addPrescription(prescription);
        return R.ok(null);
    }


}
