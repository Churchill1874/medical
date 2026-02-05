package com.medical.pojo.resp.offlinetranslation;

import com.medical.entity.OfflineTranslation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OfflineTranslationResp extends OfflineTranslation implements Serializable {
    private static final long serialVersionUID = -814159728822787461L;

    @ApiModelProperty("是否有新消息")
    private Boolean newMessage = false;

}
