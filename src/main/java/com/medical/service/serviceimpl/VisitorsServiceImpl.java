package com.medical.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.common.tools.TimeTools;
import com.medical.entity.Visitors;
import com.medical.mapper.VisitorsMapper;
import com.medical.pojo.req.PageBase;
import com.medical.pojo.resp.visitors.VisitorsStatisticsResp;
import com.medical.service.VisitorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
@Slf4j
@Service
public class VisitorsServiceImpl extends ServiceImpl<VisitorsMapper, Visitors> implements VisitorsService {

    @Override
    public IPage<Visitors> page(PageBase req) {
        IPage<Visitors> iPage = new Page<>(req.getPageNum(), req.getPageSize());

        QueryWrapper<Visitors> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Visitors::getCreateTime);

        return page(iPage, queryWrapper);
    }

    @Async
    @Override
    public void insert(String ip, String address) {
        log.info("将要插入访客记录的ip是:{}-{}",ip,address);
        //当天访问过的ip 则不再次记录
        if(!CollectionUtils.isEmpty(findByIpToday(ip))){
            return;
        }

        Visitors visitors = new Visitors();
        visitors.setCreateTime(LocalDateTime.now());
        visitors.setCreateName("访客");
        visitors.setIp(ip);
        visitors.setAddress(address);
        save(visitors);
    }

    @Override
    public List<Visitors> findByIpToday(String ip) {
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<Visitors> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .select(Visitors::getCreateTime)
                .eq(Visitors::getIp, ip)
                .ge(Visitors::getCreateTime, now.toLocalDate().atStartOfDay())
                .le(Visitors::getCreateTime, now);
        return list(queryWrapper);
    }

    @Override
    public VisitorsStatisticsResp statistics() {
        VisitorsStatisticsResp resp = new VisitorsStatisticsResp();

        LocalDateTime now = LocalDateTime.now();

        // 查询最近 3 个月访问记录
        QueryWrapper<Visitors> qw = new QueryWrapper<>();
        qw.lambda()
                .ge(Visitors::getCreateTime, TimeTools.getBeforeTimeFrom1DayStart(3))
                .le(Visitors::getCreateTime, now);
        List<Visitors> list = list(qw);

        if (CollectionUtils.isEmpty(list)) {
            return resp;
        }

        // ===== 时间边界 =====
        LocalDate today = now.toLocalDate();

        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime yesterdayStart = today.minusDays(1).atStartOfDay();
        LocalDateTime beforeYesterdayStart = today.minusDays(2).atStartOfDay();

        // 周：以周一为一周开始
        LocalDateTime weekStart = today.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime lastWeekStart = weekStart.minusWeeks(1);
        LocalDateTime lastWeekEnd = weekStart.minusSeconds(1);

        // 月：自然月
        LocalDateTime monthStart = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime lastMonthStart = monthStart.minusMonths(1);
        LocalDateTime lastMonthEnd = monthStart.minusSeconds(1);
        LocalDateTime beforeLastMonthStart = monthStart.minusMonths(2);
        LocalDateTime beforeLastMonthEnd = lastMonthStart.minusSeconds(1);

        // ===== 去重用的 Set（按 IP 去重）=====
        Set<String> todayIpSet = new HashSet<>();
        Set<String> yesterdayIpSet = new HashSet<>();
        Set<String> beforeYesterdayIpSet = new HashSet<>();

        Set<String> thisWeekIpSet = new HashSet<>();
        Set<String> lastWeekIpSet = new HashSet<>();

        Set<String> thisMonthIpSet = new HashSet<>();
        Set<String> lastMonthIpSet = new HashSet<>();
        Set<String> beforeMonthIpSet = new HashSet<>();

        // 地址去重集合
        Set<String> todayAddrSet = new LinkedHashSet<>();
        Set<String> yesterdayAddrSet = new LinkedHashSet<>();
        Set<String> beforeYesterdayAddrSet = new LinkedHashSet<>();

        // ===== 遍历一次，完成所有统计 =====
        for (Visitors v : list) {
            LocalDateTime t = v.getCreateTime();
            String ip = v.getIp();
            String addr = v.getAddress();

            if (ip == null) {
                continue;
            }

            // ---------- 日维度 ----------
            // 今日
            if (!t.isBefore(todayStart)) {
                if (todayIpSet.add(ip) && addr != null) {
                    todayAddrSet.add(addr);
                }
            }
            // 昨天
            else if (!t.isBefore(yesterdayStart) && t.isBefore(todayStart)) {
                if (yesterdayIpSet.add(ip) && addr != null) {
                    yesterdayAddrSet.add(addr);
                }
            }
            // 前天
            else if (!t.isBefore(beforeYesterdayStart) && t.isBefore(yesterdayStart)) {
                if (beforeYesterdayIpSet.add(ip) && addr != null) {
                    beforeYesterdayAddrSet.add(addr);
                }
            }

            // ---------- 周维度 ----------
            // 本周
            if (!t.isBefore(weekStart)) {
                thisWeekIpSet.add(ip);
            }
            // 上周
            else if (!t.isBefore(lastWeekStart) && !t.isAfter(lastWeekEnd)) {
                lastWeekIpSet.add(ip);
            }

            // ---------- 月维度 ----------
            // 本月
            if (!t.isBefore(monthStart)) {
                thisMonthIpSet.add(ip);
            }
            // 上月
            else if (!t.isBefore(lastMonthStart) && !t.isAfter(lastMonthEnd)) {
                lastMonthIpSet.add(ip);
            }
            // 上上个月
            else if (!t.isBefore(beforeLastMonthStart) && !t.isAfter(beforeLastMonthEnd)) {
                beforeMonthIpSet.add(ip);
            }
        }

        // ===== 填充 IP 数量（去重后的数量）=====
        resp.setToday(todayIpSet.size());
        resp.setYesterday(yesterdayIpSet.size());
        resp.setBeforeYesterday(beforeYesterdayIpSet.size());

        resp.setThisWeek(thisWeekIpSet.size());
        resp.setLastWeek(lastWeekIpSet.size());

        resp.setThisMonth(thisMonthIpSet.size());
        resp.setLastMonth(lastMonthIpSet.size());
        resp.setBeforeMonth(beforeMonthIpSet.size());

        // ===== 填充地址列表（去重后的地址）=====
        resp.setTodayAddress(todayAddrSet);
        resp.setYesterdayAddress(yesterdayAddrSet);
        resp.setBeforeYesterdayAddress(beforeYesterdayAddrSet);

        return resp;
    }


}
