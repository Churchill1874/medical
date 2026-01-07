package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.annotation.AdminLoginCheck;
import com.medical.common.tools.TokenTools;
import com.medical.entity.OnlineConsultation;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationPage;
import com.medical.pojo.req.onlineconsultation.OnlineConsultationStatusUpdate;
import com.medical.pojo.resp.onlineconsulation.OnlineConsultationReport;
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
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

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

    @PostMapping("/report")
    @ApiOperation(value = "报表数据", notes = "报表数据")
    public R<OnlineConsultationReport> report() {
        OnlineConsultationReport report = new OnlineConsultationReport();

        List<OnlineConsultation> list = onlineConsultationService.list();
        if (CollectionUtils.isEmpty(list)) {
            return R.ok(report);
        }

        LocalDateTime now = LocalDateTime.now();

        // 当前月开始时间
        LocalDateTime currentMonthStart = now.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        // 上个月开始时间
        LocalDateTime lastMonthStart = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        // 上个月结束时间
        LocalDateTime lastMonthEnd = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
        // 上上个月开始
        LocalDateTime beforeLastMonthStart = currentMonthStart.minusMonths(2);
        // 上上个月结束（= 上个月开始）
        LocalDateTime beforeLastMonthEnd = currentMonthStart.minusMonths(1);
        // 当年开始时间
        LocalDateTime currentYearStart = currentMonthStart.with(TemporalAdjusters.firstDayOfYear()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        // 去年开始时间
        LocalDateTime lastYearStart = currentYearStart.minusYears(1);

        for (OnlineConsultation onlineConsultation : list) {
            //当前月统计
            if (!onlineConsultation.getCreateTime().isBefore(currentMonthStart)
                    && now.isAfter(onlineConsultation.getCreateTime())) {
                report.setThisMonth(report.getThisMonth() + 1);
            }
            //上个月统计
            if (!onlineConsultation.getCreateTime().isBefore(lastMonthStart)
                    && lastMonthEnd.isAfter(onlineConsultation.getCreateTime())) {
                report.setLastMonth(report.getLastMonth() + 1);
            }
            //上上个月统计
            if (!onlineConsultation.getCreateTime().isBefore(beforeLastMonthStart)
                    && beforeLastMonthEnd.isAfter(onlineConsultation.getCreateTime())) {
                report.setBeforeLastMonth(report.getBeforeLastMonth() + 1);
            }
            //当年统计
            if (!onlineConsultation.getCreateTime().isBefore(currentYearStart)
                    && now.isAfter(onlineConsultation.getCreateTime())) {
                report.setThisYear(report.getThisYear() + 1);
            }
            //去年统计
            if (!onlineConsultation.getCreateTime().isBefore(lastYearStart)
                    && currentYearStart.isAfter(onlineConsultation.getCreateTime())) {
                report.setLastYear(report.getLastYear() + 1);
            }
        }

        return R.ok(report);
    }

}
