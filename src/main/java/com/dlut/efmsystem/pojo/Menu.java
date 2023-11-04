package com.dlut.efmsystem.pojo;

import lombok.Data;

@Data
public class Menu {

    private Integer id;
    private String name;
    private Integer orderNum;
    private String url;

    public Menu(Integer id,String name,Integer orderNumber,String url){
        this.id = id;
        this.name = name;
        this.orderNum = orderNumber;
        this.url = url;
    }
}
