package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.dto.UserDto;
import com.metoo.monitor.core.entity.User;
import com.metoo.monitor.core.vo.UserVo;

import java.util.List;

public interface UserMapper {


    /**
     * 根据Username 查询一个User 对象
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 根据用户名查询所有角色
     * @param username
     * @return
     */
    User findRolesByUserName(String username);

    User selectPrimaryKey(Long id);

    List<UserVo> query(UserDto dto);

    /**
     * 根据用户ID查询用户、角色组、角色信息
     * @param id
     * @return
     */
    UserVo findUserUpdate(Long id);

    /**
     * 保存一个User对象
     * @param user
     */
    int insert(User user);

    int update(User user);

    /**
     * 根据UserID删除一个User对象
     * @param id
     * @return
     */
    int delete(Long id);

    boolean allocation(List<User> list);

    List<User> findObjByIds(Long[] ids) ;

}
