package com.metoo.monitor.core.service;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.dto.UserDto;
import com.metoo.monitor.core.entity.User;
import com.metoo.monitor.core.vo.UserVo;

import java.util.List;

public interface IUserService {

    User findByUserName(String username);

    User findRolesByUserName(String username);

    UserVo findUserUpdate(Long id);

    User findObjById(Long id);

    Page<UserVo> query(UserDto dto);

    boolean save(UserDto dto);

    boolean update(User user);

    boolean delete(User id);

    boolean allocation(List<User> list);

    List<User> findObjByIds(Long[] ids) ;

}
