package com.metoo.monitor.core.service;


import com.metoo.monitor.core.entity.UserRole;

import java.util.List;

public interface IUserRoleService {

    int batchAddUserRole(List<UserRole> userRoles);

    boolean deleteUserByRoleId(Long id);
}
