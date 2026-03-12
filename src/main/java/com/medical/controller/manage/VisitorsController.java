package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.entity.Visitors;
import com.medical.pojo.req.PageBase;
import com.medical.pojo.resp.visitors.VisitorsStatisticsResp;
import com.medical.service.VisitorsService;
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
@Api(tags = "访客")
@RequestMapping("/manage/visitors")
public class VisitorsController {

    @Autowired
    private VisitorsService visitorsService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Visitors>> page(@RequestBody PageBase dto) {
        return R.ok(visitorsService.page(dto));
    }

    @PostMapping("/statistics")
    @ApiOperation(value = "访客访问统计数据", notes = "访客访问统计数据")
    public R<VisitorsStatisticsResp> statistics() {
        return R.ok(visitorsService.statistics());
    }


}






