package com.metoo.monitor.core.service;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.dto.ResDto;
import com.metoo.monitor.core.entity.Res;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IResService {

    /**
     * 根据角色id查询权限集合
     * @param id
     * @return
     */
    List<Res> findResByRoleId(Long id);

    /**
     * 根据权限ID查询权限对象
     * @param id
     * @return
     */
    Res findObjById(Long id);

    Res findObjByName(String name);

    Res findResUnitRoleByResId(Long id);

    Page<Res> query(ResDto dto);

    List<Res> findPermissionByJoin(Map map);

    List<Res> findPermissionByMap(Map map);

    List<Res> findResByResIds(List<Integer> ids);

    Collection<String> findPermissionByUserId(Long id);

    boolean save(ResDto instance);

    boolean delete(Long id);

}
