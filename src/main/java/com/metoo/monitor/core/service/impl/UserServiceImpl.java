package com.metoo.monitor.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.metoo.monitor.core.dto.UserDto;
import com.metoo.monitor.core.entity.Role;
import com.metoo.monitor.core.entity.User;
import com.metoo.monitor.core.entity.UserRole;
import com.metoo.monitor.core.mapper.UserMapper;
import com.metoo.monitor.core.service.IRoleService;
import com.metoo.monitor.core.service.IUserRoleService;
import com.metoo.monitor.core.service.IUserService;
import com.metoo.monitor.core.utils.ShiroUserHolder;
import com.metoo.monitor.core.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;

    @Override
    public User findByUserName(String username) {
        return this.userMapper.findByUserName(username);
    }

    @Override
    public User findRolesByUserName(String username) {
        return null;
    }

    @Override
    public UserVo findUserUpdate(Long id) {
        return this.userMapper.findUserUpdate(id);
    }

    @Override
    public User findObjById(Long id) {
        return this.userMapper.selectPrimaryKey(id);
    }

    @Override
    public Page<UserVo> query(UserDto dto) {
        Page<UserVo> page = PageHelper.startPage(dto.getCurrentPage(), dto.getPageSize());
        this.userMapper.query(dto);
        return page;
    }


    @Override
    public boolean save(UserDto dto) {
        User user = null;
        if (dto.getId() == null) {
            user = new User();
            dto.setAddTime(new Date());
        } else {
            user = this.userMapper.selectPrimaryKey(dto.getId());
        }
        BeanUtils.copyProperties(dto, user);

        if (dto.getId() == null) {
            try {
                this.userMapper.insert(user);

                String roleName = "";
                // ??????????????????????????????
                if (dto.getRole_id() != null && dto.getRole_id().length > 0) {
                    List<Integer> idList = Arrays.asList(dto.getRole_id());
                    List<Role> roleList = this.roleService.findRoleByIdList(idList);
                    List<UserRole> userRoles = new ArrayList<UserRole>();
                    for (Role role : roleList) {
                        UserRole userRole = new UserRole();
                        userRole.setUser_id(user.getId());
                        userRole.setRole_id(role.getId());
                        userRoles.add(userRole);
                        roleName += role.getName() + ",";
                    }
                    roleName = roleName.substring(0, roleName.lastIndexOf(","));
                    this.userRoleService.batchAddUserRole(userRoles);
                }
                try {
                    user.setUserRole(roleName);
                    this.userMapper.update(user);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                String roleName = "";

                // ??????????????????????????????
                if (dto.getRole_id() != null && dto.getRole_id().length > 0) {
                    // ????????????????????????
                    this.userRoleService.deleteUserByRoleId(user.getId());
                    List<Integer> idList = Arrays.asList(dto.getRole_id());
                    List<Role> roleList = this.roleService.findRoleByIdList(idList);
                    List<UserRole> userRoles = new ArrayList<UserRole>();
                    for (Role role : roleList) {
                        UserRole userRole = new UserRole();
                        userRole.setUser_id(user.getId());
                        userRole.setRole_id(role.getId());
                        userRoles.add(userRole);
                        roleName += role.getName() + ",";
                    }
                    roleName = roleName.substring(0, roleName.lastIndexOf(","));
                    this.userRoleService.batchAddUserRole(userRoles);
                }
                user.setUserRole(roleName);
                this.userMapper.update(user);
                User currentUser = ShiroUserHolder.currentUser();


                // ??????????????? ????????????????????????
                // ??????????????????????????????????????????????????????????????????
                if (dto.isFlag() && currentUser.getId().equals(user.getId())) {
                    SecurityUtils.getSubject().logout();
                    // ????????????????????????????????????Subject???????????????
                    Subject subject = SecurityUtils.getSubject();
                    PrincipalCollection principalCollection = subject.getPrincipals();
                    String realmName = principalCollection.getRealmNames().iterator().next();
                    User userInfo = this.userMapper.findByUserName(user.getUsername());// ??????????????????????????????Subject???
                    PrincipalCollection newPrincipalCollection =
                            new SimplePrincipalCollection(userInfo, realmName);
                    subject.runAs(newPrincipalCollection);
                }

                //??????????????? ???????????????????????????

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean update(User user) {
        try {
            this.userMapper.update(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        // ??????????????????
        try {
            this.userRoleService.deleteUserByRoleId(user.getId());
            this.userMapper.delete(user.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean allocation(List<User> list) {
        try {
            this.userMapper.allocation(list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> findObjByIds(Long[] ids) {
        return this.userMapper.findObjByIds(ids);
    }
}


