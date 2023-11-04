package com.dlut.efmsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.pojo.Equipment;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface EquipmentService {
    String create(Equipment equ);
    Boolean remove(String id);
    Boolean modify(Equipment equ);
    IPage<Equipment> getPage(int current, int num, Equipment equ);
    int getOfficeById(String id);
    Boolean setStateById(String id,int state);
    int getStateById(String id);
    Equipment getOneEquipment(String id);
}