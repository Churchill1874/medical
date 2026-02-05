package com.medical.service.serviceimpl;

import com.medical.common.constant.CacheKeyConstant;
import com.medical.common.exception.DataException;
import com.medical.common.exception.IpException;
import com.medical.common.tools.GenerateTools;
import com.medical.entity.Admin;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.EhcacheService;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

/**
 * 该类对ehcache.xml配置文件里面已经配置的缓存容器进行实现获取，方便使用
 */
@Slf4j
@Service
public class EhcacheServiceImpl implements EhcacheService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void checkIp2SecondsClick(String ip, Integer limitCount, String remarks) {
        Cache<String, Integer> cache = lock3SecondCache();
        Integer reqCount = cache.get(ip);

        if (reqCount != null) {
            if (reqCount >= limitCount) {
                throw new IpException(ip);
            } else {
                cache.put(ip, reqCount + 1);
            }
        } else {
            cache.put(ip, 1);
        }
    }

    @Override
    public Cache<String, Admin> getAdminTokenCache() {
        return cacheManager.getCache(CacheKeyConstant.ADMIN_TOKEN, String.class, Admin.class);
    }

    @Override
    public Cache<String, Integer> lock3SecondCache() {
        return cacheManager.getCache(CacheKeyConstant.LOCK_3_SECOND, String.class, Integer.class);
    }

    @Override
    public void verification3SecondsRequest(String key) {
        Integer value = lock3SecondCache().get(key);
        if (value != null) {
            throw new DataException("操作过快,请稍后继续");
        }
        lock3SecondCache().put(key, 1);
    }

    @Override
    public Cache<String, String> verificationCache() {
        return cacheManager.getCache(CacheKeyConstant.VERIFICATION_CODE, String.class, String.class);
    }

    @Override
    public Cache<String, Integer> playerOnlineCount() {
        return cacheManager.getCache(CacheKeyConstant.PLAYER_ONLINE_COUNT, String.class, Integer.class);
    }


    @Override
    public String getVC(String key, Integer limitCount, String remarks) {
        //获取验证码
        String codeImageStream = null;
        String code = null;
        try {
            code = GenerateTools.getCaptchaText(5);
            codeImageStream = GenerateTools.getCaptchaImage(code);
        } catch (IOException e) {
            log.error("生成验证码异常:{}", e.getMessage());
            throw new DataException(e.getMessage());
        }

        verificationCache().put(key, code);
        return codeImageStream;
    }

    @Override
    public void clearIpCache() {
        Cache<String, Set<String>> cache =
                cacheManager.getCache(CacheKeyConstant.BLACKLIST, String.class, (Class<Set<String>>) (Class<?>) Set.class);

        if (cache != null) {
            cache.remove(CacheKeyConstant.BLACKLIST);
        }
    }


    @Override
    public Set<String> getBlacklistIpSetCache() {
        Cache<String, Set<String>> cache = cacheManager.getCache(CacheKeyConstant.BLACKLIST, String.class, (Class<Set<String>>) (Class<?>) Set.class);
        return cache.get(CacheKeyConstant.BLACKLIST);
    }

    @Override
    public void setBlacklistIpSetCache(Set<String> blacklistIpSet) {
        cacheManager.getCache(CacheKeyConstant.BLACKLIST, String.class, (Class<Set<String>>) (Class<?>) Set.class)
                .put(CacheKeyConstant.BLACKLIST, blacklistIpSet);
    }

    @Override
    public Cache<String, PlayerTokenResp> playerTokenCache() {
        return cacheManager.getCache(CacheKeyConstant.PLAYER_TOKEN, String.class, PlayerTokenResp.class);
    }

}
