package com.dlut.efmsystem.pojo;

import lombok.Data;

@Data
public class DispatchVO {
    private int logId;
    private String fixBy;

    public DispatchVO(int logId,String fixBy){
        this.fixBy=fixBy;
        this.logId=logId;
    }

}
