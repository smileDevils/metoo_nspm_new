package com.metoo.monitor.core.service;

import com.metoo.monitor.core.entity.Policy;

public interface IPolicyService {

    Policy getObjById(Long id);
    int save(Policy policy);
}
