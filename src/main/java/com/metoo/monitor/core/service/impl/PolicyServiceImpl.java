package com.metoo.monitor.core.service.impl;

import com.metoo.monitor.core.entity.Policy;
import com.metoo.monitor.core.mapper.PolicyMapper;
import com.metoo.monitor.core.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PolicyServiceImpl implements IPolicyService {

    @Autowired
    private PolicyMapper policyMapper;

    @Override
    public Policy getObjById(Long id) {
        return this.policyMapper.getObjById(id);
    }

    @Override
    public int save(Policy policy) {
        return this.policyMapper.save(policy);
    }
}
