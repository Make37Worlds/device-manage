package com.dlut.efmsystem.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class RoleMenu{

    @JsonSerialize(using = ToStringSerializer.class)
    private Integer id;
    private Integer roleId;
    private Integer menuId;

    public RoleMenu(){}

    public RoleMenu(Integer roleId,Integer MenuId){
        this.roleId = roleId;
        this.menuId = MenuId;
    }

}
