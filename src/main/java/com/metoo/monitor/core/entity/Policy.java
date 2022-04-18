package com.metoo.monitor.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Policy extends IdEntity{

    private String name;
    private String natOnlyList;
    private String ruleListType;
    private String ruleTotal;
    private String uuid;
    private Integer type;

}
