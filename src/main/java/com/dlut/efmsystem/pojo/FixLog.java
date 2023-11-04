package com.dlut.efmsystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;

@Data
public class FixLog{

    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String facId;
    private String createBy;
    private Timestamp createTime;
    private String faultCondition;
    private String fixBy;
    private Timestamp fixTime;
    private String faultReason;
    private String head;
    private BigDecimal actualCost;
    private BigDecimal budget;
    private Integer state;

    public FixLog() {
    }

    public FixLog(Integer id, String fac_id, String create_by, Timestamp create_time, String fault_condition, String fix_by,
                  Timestamp fix_time, String fault_reason, String head, BigDecimal cost, BigDecimal budget, Integer state){
        this.id = id;
        this.facId = fac_id;
        this.createTime =create_time;
        this.createBy = create_by;
        this.faultCondition = fault_condition;
        this.fixBy = fix_by;
        this.fixTime = fix_time;
        this.faultReason = fault_reason;
        this.head = head;
        this.actualCost = cost;
        this.budget = budget;
        this.state = state;
    }
}