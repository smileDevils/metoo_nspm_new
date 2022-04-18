package com.metoo.monitor.core.manager;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.dto.UserDto;
import com.metoo.monitor.core.entity.Role;
import com.metoo.monitor.core.service.IUserService;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.PageInfo;
import com.metoo.monitor.core.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/monitor/user")
@RestController
public class UserManagerController {

    @Autowired
    private IUserService userService;

    @ApiOperation("用户列表")
    @PostMapping("/list")
    public Object list(@RequestBody(required=false) UserDto dto){
        if(dto == null){
            dto = new UserDto();
        }
        Page<UserVo> page = this.userService.query(dto);
        int i = 0;
        if(page.getResult().size() > 0){
            return ResponseUtil.ok(new PageInfo<Role>(page));
        }
        return ResponseUtil.ok();
    }

}
