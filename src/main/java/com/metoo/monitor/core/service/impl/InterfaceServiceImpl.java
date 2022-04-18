package com.metoo.monitor.core.service.impl;

import com.metoo.monitor.core.mapper.InterfaceMapper;
import com.metoo.monitor.core.service.IInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InterfaceServiceImpl implements IInterfaceService {

    @Autowired
    private InterfaceMapper interfaceMapper;

    @Override
    public int batchInsert(List<Map> list) {
        return this.interfaceMapper.batchInsert(list);
    }
}
