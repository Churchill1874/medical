package com.medical.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.common.exception.IpException;
import com.medical.common.tools.HttpTools;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Blacklist;
import com.medical.mapper.BlacklistMapper;
import com.medical.pojo.req.blacklist.BlacklistPageReq;
import com.medical.service.BlacklistService;
import com.medical.service.EhcacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements BlacklistService {

    @Autowired
    private EhcacheService ehcacheService;

    @Override
    public IPage<Blacklist> queryPage(BlacklistPageReq req) {
        IPage<Blacklist> iPage = new Page<>(req.getPageNum(), req.getPageSize());

        LambdaQueryWrapper<Blacklist> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Blacklist::getIp, req.getIp())
                .eq(Blacklist::getAddress, req.getAddress())
                .orderByDesc(Blacklist::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public Blacklist findByIp(String ip) {
        LambdaQueryWrapper<Blacklist> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blacklist::getIp, ip);
        return getOne(queryWrapper);
    }

    @Override
    public Set<String> getBlacklistIpAll() {
        Set<String> ipSet = ehcacheService.getBlacklistIpSetCache();
        if (CollectionUtils.isNotEmpty(ipSet)) {
            return new HashSet<>(ipSet);
        }

        synchronized (this) {
            ipSet = ehcacheService.getBlacklistIpSetCache();
            if (CollectionUtils.isNotEmpty(ipSet)) {
                return new HashSet<>(ipSet);
            }

            QueryWrapper<Blacklist> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().select(Blacklist::getIp);
            List<Blacklist> list = list(queryWrapper);

            ipSet = CollectionUtils.isEmpty(list)
                    ? Collections.emptySet()
                    : list.stream().map(Blacklist::getIp).collect(Collectors.toSet());

            ehcacheService.setBlacklistIpSetCache(ipSet);
            return new HashSet<>(ipSet);
        }
    }


    @Override
    public void checkIp(String ip) {
        try {
            ehcacheService.checkIp3SecondsClick(ip, 100, "ip暴力访问:" + ip);
        } catch (IpException e) {
            Blacklist blacklist = new Blacklist();
            blacklist.setIp(ip);
            blacklist.setAddress(HttpTools.getAddress());
            blacklist.setRemark("暴力请求超过限制");
            blacklist.setCreateName("系统");
            blacklist.setCreateTime(LocalDateTime.now());
            save(blacklist);
            ehcacheService.clearIpCache();

            throw e;
        }
    }

    @Override
    public void addBlacklist(Blacklist blacklist) {
        blacklist.setCreateTime(LocalDateTime.now());
        blacklist.setCreateName(TokenTools.getAdminToken(true).getCreateName());
        save(blacklist);
    }

    @Override
    public void unlockBlacklist() {
        QueryWrapper<Blacklist> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .isNotNull(Blacklist::getUnlockingTime)
                .le(Blacklist::getUnlockingTime, LocalDateTime.now());
        remove(queryWrapper);

    }


}
