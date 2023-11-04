package com.dlut.efmsystem.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RoleVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer id;
    private String name;
    private String authority;
    private Integer sort;
    private Integer state;
    private String postscript;
    private Timestamp createTime;
    private String createBy;
    private Timestamp updateTime;
    private String updateBy;
    private List<Integer> menus;

    public RoleVO(String name,String authority,Integer sort,Integer state,
                  String postscript,Timestamp createTime,String createBy,List<Integer> menus){
        this.name=name;
        this.authority=authority;
        this.sort=sort;
        this.createBy=createBy;
        this.createTime=createTime;
        this.postscript=postscript;
        this.state=state;
        this.menus=menus;
    }

    public RoleVO(Integer id,String name,String authority,Integer sort,Integer state,
                  String postscript,Timestamp updateTime,String updateBy,List<Integer> menus){
        this.id=id;
        this.name=name;
        this.authority=authority;
        this.sort=sort;
        this.updateBy=updateBy;
        this.updateTime=updateTime;
        this.postscript=postscript;
        this.menus=menus;
    }

    public RoleVO(Role r,List<Integer> menus){
        this.id=r.getId();
        this.name=r.getName();
        this.authority=r.getAuthority();
        this.sort=r.getSort();
        this.state=r.getState();
        this.postscript=r.getPostscript();
        this.menus=menus;
    }

    public Role getRole(){
        Role role;
        if(this.id==null){
            role= new Role(this.name,this.authority,this.sort,this.state,this.postscript,this.createTime,this.createBy);
        }
        else{
            role= new Role(this.id,this.name,this.authority,this.sort,this.state,this.postscript,this.updateTime,this.updateBy);
        }
        return role;
    }

}
