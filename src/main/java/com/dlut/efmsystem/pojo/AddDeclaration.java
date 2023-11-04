package com.dlut.efmsystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors
public class AddDeclaration {

    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer logId;
    private BigDecimal addBudget;
    private String description;
    private Integer result;
    private String reason;
    private Timestamp createTime;
    private String createBy;

    public AddDeclaration(Integer id, Integer logId, BigDecimal addBudget, String description, Integer result, String reason, Timestamp createTime, String createBy){
        this.id = id;
        this.logId = logId;
        this.addBudget = addBudget;
        this.description = description;
        this.result = result;
        this.reason = reason;
        this.createTime = createTime;
        this.createBy=createBy;
    }
}
