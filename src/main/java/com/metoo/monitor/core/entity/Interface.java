package com.metoo.monitor.core.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class Interface {

    @ApiModelProperty("接口名称")
    private String name;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("接口状态")
    private String isShutdown;
    @ApiModelProperty("Ip地址")
    private String ip4Address;
    @ApiModelProperty("掩码")
    private String ip4MaskLength;

}
