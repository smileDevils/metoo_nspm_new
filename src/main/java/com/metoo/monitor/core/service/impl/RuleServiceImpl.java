package com.metoo.monitor.core.service.impl;

import com.metoo.monitor.core.entity.Rule;
import com.metoo.monitor.core.mapper.RuleMapper;
import com.metoo.monitor.core.service.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RuleServiceImpl implements IRuleService {

    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public int save(Rule instance) {
        return this.ruleMapper.save(instance);
    }
}
