package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.entity.Blacklist;
import com.medical.pojo.req.blacklist.BlacklistPageReq;
import com.medical.service.BlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "黑名单")
@RequestMapping("/manage/blacklist")
public class BlacklistController {

    @Resource
    private BlacklistService blackListService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<Blacklist>> page(@RequestBody BlacklistPageReq req) {
        return R.ok(blackListService.queryPage(req));
    }


}
