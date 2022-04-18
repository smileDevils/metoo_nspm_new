package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.entity.Policy;

public interface PolicyMapper {

    Policy getObjById(Long id);
    int save(Policy policy);
}
