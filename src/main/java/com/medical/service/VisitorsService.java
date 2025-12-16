package com.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.entity.Visitors;
import com.medical.pojo.req.PageBase;
import com.medical.pojo.resp.visitors.VisitorsStatisticsResp;

import java.util.List;

public interface VisitorsService extends IService<Visitors> {

    IPage<Visitors> page(PageBase req);

    void insert(String ip, String address);

    List<Visitors> findByIpToday(String ip);

    VisitorsStatisticsResp statistics();

}
