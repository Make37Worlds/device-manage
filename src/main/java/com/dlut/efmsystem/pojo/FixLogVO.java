package com.dlut.efmsystem.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
public class FixLogVO {
    private Integer id;
    private String facId;
    private int facOffice;
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

    public FixLogVO(FixLog fixLog,int facOffice){
        this.id=fixLog.getId();
        this.facId=fixLog.getFacId();
        this.createBy=fixLog.getCreateBy();
        this.createTime=fixLog.getCreateTime();
        this.faultCondition=fixLog.getFaultCondition();
        this.fixBy=fixLog.getFixBy();
        this.fixTime=fixLog.getFixTime();
        this.faultReason=fixLog.getFaultReason();
        this.head= fixLog.getHead();
        this.actualCost=fixLog.getActualCost();
        this.budget=fixLog.getBudget();
        this.state=fixLog.getState();
        this.facOffice=facOffice;
    }

}
