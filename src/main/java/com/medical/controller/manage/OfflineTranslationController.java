package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.annotation.AdminLoginCheck;
import com.medical.entity.OfflineTranslation;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationPage;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationUpdateStatus;
import com.medical.service.OfflineTranslationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "线下翻译陪同服务")
@RequestMapping("/manage/offlineTranslation")
public class OfflineTranslationController {

    @Autowired
    private OfflineTranslationService offlineTranslationService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<OfflineTranslation>> page(@RequestBody OfflineTranslationPage req) {
        return R.ok(offlineTranslationService.queryPage(req));
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改状态", notes = "修改状态")
    public R updateStatus(@RequestBody OfflineTranslationUpdateStatus req) {
        offlineTranslationService.updateStatus(req.getId(), req.getStatus());
        return R.ok(null);
    }

    @PostMapping("/deleteById")
    @ApiOperation(value = "删除", notes = "删除")
    @AdminLoginCheck
    public R deleteById(@RequestBody @Valid IdBase req) {
        offlineTranslationService.deleteById(req.getId());
        return R.ok(null);
    }

}
