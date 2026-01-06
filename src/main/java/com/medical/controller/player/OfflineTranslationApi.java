package com.medical.controller.player;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OfflineTranslation;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationAdd;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationPage;
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

@Slf4j
@RestController
@Api(tags = "线下陪同翻译")
@RequestMapping("/player/offlineTranslation")
public class OfflineTranslationApi {

    @Resource
    private OfflineTranslationService offlineTranslationService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<OfflineTranslation>> page(@RequestBody OfflineTranslationPage req) {
        req.setUserId(TokenTools.getPlayerToken(true).getId());
        return R.ok(offlineTranslationService.queryPage(req));
    }

    @PostMapping("/addOfflineTranslation")
    @ApiOperation(value = "添加请求", notes = "添加请求")
    public R addOfflineTranslation(@RequestBody @Valid OfflineTranslationAdd req) {
        OfflineTranslation offlineTranslation = BeanUtil.toBean(req, OfflineTranslation.class);
        offlineTranslationService.addOfflineTranslation(offlineTranslation);
        return R.ok(null);
    }



}
