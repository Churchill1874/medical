package com.medical.common.tools;

import com.medical.common.exception.DataException;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class HttpTools {

    private static final Map<String, Integer> urlMap = new HashMap<>();

    static {
        //初始化url和平台的对应关系
        urlMap.put("127.0.0.1", 0);
        urlMap.put("localhost", 0);

    }

    private static Ip2regionSearcher ip2regionSearcher;

    @Autowired
    public void setIp2regionSearcher(Ip2regionSearcher ip2regionSearcher){
        HttpTools.ip2regionSearcher = ip2regionSearcher;
    }

    /**
     * 根据ip获取地址
     * @param ip
     * @return
     */
    public static String findAddressByIp(String ip){
        return ip2regionSearcher.getAddress(ip);
    }

    /**
     * 获取地址
     * @return
     */
    public static String getAddress(){
        return findAddressByIp(getIp());
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取域名所在平台
     *
     * @return
     */
    public static int getPlatform() {
        String url = getRequest().getServerName();
        Integer platform = urlMap.get(url);
        if (platform == null) {
            log.error("未获取到域名:{},的平台", url);
            throw new DataException("未找到该域名的平台");
        }
        return platform;
    }

    /**
     * 获取请求头中的token
     *
     * @return
     */
    public static String getHeaderToken() {
        return getRequest().getHeader("TOKEN-ID");
    }

    /**
     * 解析http请求ip
     *
     * @return
     */
    public static String getIp() {
        HttpServletRequest request = getRequest();

        // 1️⃣ 优先从 X-Forwarded-For 取（只取第一个）
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && xff.length() > 0 && !"unknown".equalsIgnoreCase(xff)) {
            return xff.split(",")[0].trim();
        }

        // 2️⃣ 再取 X-Real-IP
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && realIp.length() > 0 && !"unknown".equalsIgnoreCase(realIp)) {
            return realIp;
        }

        // 3️⃣ 最后兜底 RemoteAddr
        return request.getRemoteAddr();
    }


}
