package com.metoo.monitor.core.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface InterfaceMapper {

    int batchInsert(List<Map> list);
}
