package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.common.annotation.AdminLoginCheck;
import com.medical.entity.OfflineTranslation;
import com.medical.entity.OnlineConsultation;
import com.medical.pojo.req.IdBase;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationPage;
import com.medical.pojo.req.offlinetranslation.OfflineTranslationUpdateStatus;
import com.medical.pojo.resp.offlinetranslation.OfflineTranslationReport;
import com.medical.pojo.resp.onlineconsulation.OnlineConsultationReport;
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
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

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

    @PostMapping("/report")
    @ApiOperation(value = "报表数据", notes = "报表数据")
    public R<OfflineTranslationReport> report() {
        OfflineTranslationReport report = new OfflineTranslationReport();

        List<OfflineTranslation> list = offlineTranslationService.list();
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

        for (OfflineTranslation offlineTranslation : list) {
            //当前月统计
            if (!offlineTranslation.getCreateTime().isBefore(currentMonthStart)
                    && now.isAfter(offlineTranslation.getCreateTime())) {
                report.setThisMonth(report.getThisMonth() + 1);
            }
            //上个月统计
            if (!offlineTranslation.getCreateTime().isBefore(lastMonthStart)
                    && lastMonthEnd.isAfter(offlineTranslation.getCreateTime())) {
                report.setLastMonth(report.getLastMonth() + 1);
            }
            //上上个月统计
            if (!offlineTranslation.getCreateTime().isBefore(beforeLastMonthStart)
                    && beforeLastMonthEnd.isAfter(offlineTranslation.getCreateTime())) {
                report.setBeforeLastMonth(report.getBeforeLastMonth() + 1);
            }
            //当年统计
            if (!offlineTranslation.getCreateTime().isBefore(currentYearStart)
                    && now.isAfter(offlineTranslation.getCreateTime())) {
                report.setThisYear(report.getThisYear() + 1);
            }
            //去年统计
            if (!offlineTranslation.getCreateTime().isBefore(lastYearStart)
                    && currentYearStart.isAfter(offlineTranslation.getCreateTime())) {
                report.setLastYear(report.getLastYear() + 1);
            }
        }

        return R.ok(report);
    }
    
}
