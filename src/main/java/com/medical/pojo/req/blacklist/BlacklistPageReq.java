package com.medical.pojo.req.blacklist;

import com.medical.pojo.req.PageBase;
import lombok.Data;

import java.io.Serializable;

@Data
public class BlacklistPageReq extends PageBase implements Serializable {
    private static final long serialVersionUID = 947609775046161163L;

    private String ip;

    private String address;


}
