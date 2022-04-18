package com.metoo.monitor.core.entity;

import io.swagger.annotations.ApiModelProperty;

public class License {

    @ApiModelProperty("申请码,系统唯一序列号")
    private String systemSN;

    @ApiModelProperty("过期时间")
    private String expireTime;

    @ApiModelProperty("授权码")
    private String liecnes;

}
