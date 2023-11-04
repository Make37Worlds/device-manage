package com.dlut.efmsystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
public class ResultReport {

    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer logId;
    private BigDecimal actualCost;
    private String description;
    private Integer result;
    private String reason;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    private Timestamp fixTime;
    private Timestamp createTime;
    private String createBy;
    private String faultReason;

    public ResultReport(){}

    public ResultReport(Integer id,Integer logId,BigDecimal cost,String description,Integer result,String reason,
                        Timestamp fixTime,Timestamp time, String eid,String freason){
        this.id = id;
        this.logId = logId;
        this.actualCost = cost;
        this.description = description;
        this.result = result;
        this.reason = reason;
        this.fixTime=fixTime;
        this.createTime = time;
        this.createBy = eid;
        this.faultReason = freason;
    }
    public ResultReport(Integer id,Integer logId,BigDecimal cost,String description,Timestamp time,Timestamp fixTime,
                        String createBy,String freason){
        this.id = id;
        this.logId = logId;
        this.actualCost = cost;
        this.description = description;
        this.createTime = time;
        this.fixTime=fixTime;
        this.createBy = createBy;
        this.faultReason = freason;
    }

    public ResultReport(Integer result,String reason){
        this.result = result;
        this.reason = reason;
    }

}
