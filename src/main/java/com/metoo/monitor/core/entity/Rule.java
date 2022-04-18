package com.metoo.monitor.core.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Rule extends IdEntity {


    private String ruleListName;
    @ApiModelProperty("行号")
    private String lineNum;
    @ApiModelProperty("策略Id")
    private Long policyId;
    @ApiModelProperty("策略名称")
    private String policyName;
    @ApiModelProperty("源域")
    private String srcDomain;
    @ApiModelProperty("源Ip")
    private String srcIp;
    @ApiModelProperty("源掩码")
    private String srcMask;
    @ApiModelProperty("源端口")
    private String srcPort1;
    @ApiModelProperty("源端口")
    private String srcPort2;
    @ApiModelProperty("目的Ip")
    private String dstIp;
    @ApiModelProperty("目的掩码")
    private String dstMask;
    @ApiModelProperty("目的端口")
    private String dstPort1;
    @ApiModelProperty("目的端口")
    private String dstPort2;
    @ApiModelProperty("目的域")
    private String dstDomain;
    @ApiModelProperty("服务")
    private String service;
    @ApiModelProperty("时间")
    private String time;
    @ApiModelProperty("过期时间")
    private String expireTime;
    @ApiModelProperty("")
    private String action;

}
