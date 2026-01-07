package com.medical.controller.player;

import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.tools.HttpTools;
import com.medical.service.VisitorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "访客")
@RequestMapping("/player/visitors")
public class VisitorsApi {

    @Resource
    private VisitorsService visitorsService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增访客记录", notes = "新增访客记录")
    public R insert() {
        visitorsService.insert(HttpTools.getIp(), HttpTools.getAddress());
        return R.ok(null);
    }


}
