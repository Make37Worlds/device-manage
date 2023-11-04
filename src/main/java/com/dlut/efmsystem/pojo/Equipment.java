package com.dlut.efmsystem.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Equipment {
    private String id;
    private String name;
    private Integer state;
    private Integer depId;
    private Integer office;
    private Date buyTime;

    public Equipment(String id,String name,Integer state,Integer dep_id,Integer office,Date buy_time){
        this.id = id;
        this.name = name;
        this.state = state;
        this.depId = dep_id;
        this.office = office;
        this.buyTime = buy_time;
    }
}
