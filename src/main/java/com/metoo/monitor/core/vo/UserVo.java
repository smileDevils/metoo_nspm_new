package com.metoo.monitor.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    /**
     * AllArgsConstructor注解和NotNull注解配合使用，参数不为null
     */
    private Long id;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;

    private String username;

    private String password;

    @ApiModelProperty("用户年龄")
    private Integer age;

    @ApiModelProperty("用户性别")
    private Integer sex;

    @ApiModelProperty("用户性别")
    private String type;

    @ApiModelProperty("用户角色")
    private String userRole;

    @ApiModelProperty("角色ID集合")
    private List<Long> roleIds = new ArrayList<Long>();

}