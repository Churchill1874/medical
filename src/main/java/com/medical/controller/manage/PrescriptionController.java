package com.medical.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.medical.entity.OnlineConsultation;
import com.medical.entity.Prescription;
import com.medical.pojo.req.prescription.PrescriptionPage;
import com.medical.pojo.req.prescription.PrescriptionUpdateStatus;
import com.medical.pojo.resp.onlineconsulation.OnlineConsultationReport;
import com.medical.pojo.resp.prescription.PrescriptionReport;
import com.medical.service.PrescriptionService;
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

    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改处理状态", notes = "修改处方药处理状态")
    public R updateStatus(@RequestBody @Valid PrescriptionUpdateStatus req) {
        prescriptionService.updateStatus(req.getId(), req.getStatus(), req.getRemark());
        return R.ok(null);
    }

    @PostMapping("/report")
    @ApiOperation(value = "报表数据", notes = "报表数据")
    public R<PrescriptionReport> report() {
        PrescriptionReport report = new PrescriptionReport();

        List<Prescription> list = prescriptionService.list();
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

        for (Prescription prescription : list) {
            //当前月统计
            if (!prescription.getCreateTime().isBefore(currentMonthStart)
                    && now.isAfter(prescription.getCreateTime())) {
                report.setThisMonth(report.getThisMonth() + 1);
            }
            //上个月统计
            if (!prescription.getCreateTime().isBefore(lastMonthStart)
                    && lastMonthEnd.isAfter(prescription.getCreateTime())) {
                report.setLastMonth(report.getLastMonth() + 1);
            }
            //上上个月统计
            if (!prescription.getCreateTime().isBefore(beforeLastMonthStart)
                    && beforeLastMonthEnd.isAfter(prescription.getCreateTime())) {
                report.setBeforeLastMonth(report.getBeforeLastMonth() + 1);
            }
            //当年统计
            if (!prescription.getCreateTime().isBefore(currentYearStart)
                    && now.isAfter(prescription.getCreateTime())) {
                report.setThisYear(report.getThisYear() + 1);
            }
            //去年统计
            if (!prescription.getCreateTime().isBefore(lastYearStart)
                    && currentYearStart.isAfter(prescription.getCreateTime())) {
                report.setLastYear(report.getLastYear() + 1);
            }
        }

        return R.ok(report);
    }


}
