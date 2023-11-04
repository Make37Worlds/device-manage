package com.dlut.efmsystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class Role {

    @TableId(value="id",type= IdType.AUTO)
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

    public Role(){};

    public Role(Integer id,String name,String authority,Integer sort,Integer state, String postscript,
                Timestamp updateTime,String updateBy,Timestamp createTime, String createBy){
        this.id = id;
        this.name = name;
        this.authority = authority;
        this.sort = sort;
        this.state = state;
        this.postscript = postscript;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.createTime = createTime;
        this.createBy = createBy;
    }

    public Role(Integer id,String name,String authority,Integer sort,Integer state, String postscript,
         Timestamp updateTime,String updateBy){
        this.id = id;
        this.name = name;
        this.authority = authority;
        this.sort = sort;
        this.state = state;
        this.postscript = postscript;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
    }

    public Role(String name,String authority,Integer sort,Integer state, String postscript,
                Timestamp createTime, String createBy){
        this.name = name;
        this.authority = authority;
        this.sort = sort;
        this.state = state;
        this.postscript = postscript;
        this.createTime = createTime;
        this.createBy = createBy;
    }

    public Role(String name,String authority,Integer sort,Integer state, String postscript){
        this.name = name;
        this.authority = authority;
        this.sort = sort;
        this.state = state;
        this.postscript = postscript;
    }

    public Role(Integer id,String name,String authority,Integer sort,Integer state, String postscript){
        this.id = id;
        this.name = name;
        this.authority = authority;
        this.sort = sort;
        this.state = state;
        this.postscript = postscript;
    }
}
