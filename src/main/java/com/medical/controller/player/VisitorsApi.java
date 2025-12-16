package com.medical.controller.player;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "访客")
@RequestMapping("/player/visitors")
public class VisitorsApi {
/*

    @Resource
    private VisitorsService visitorsService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增访客记录", notes = "新增访客记录")
    public R insert() {
        visitorsService.insert(HttpTools.getIp(), HttpTools.getAddress());
        return R.ok(null);
    }
*/


}
