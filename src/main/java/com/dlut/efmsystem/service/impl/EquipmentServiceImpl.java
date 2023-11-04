package com.dlut.efmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlut.efmsystem.dao.EquipmentDao;
import com.dlut.efmsystem.pojo.Equipment;
import com.dlut.efmsystem.service.EquipmentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    // // 生成设施编号（日期yyyymmdd+当日新增序号xxx），用于新增设施
    @Resource
    private EquipmentDao equipmentdao;
    private static Supplier<String> uniqueId = new Supplier<String>() {
        private LocalDate today = LocalDate.now();
        private Integer order = 1;
        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        @Override
        public synchronized String get() {
            LocalDate now = LocalDate.now();
            if (!now.equals(today)) {
                today = now;
                order = 1;
            }
            return String.format("%s%03d", today.format(dateTimeFormatter), order++);
        }
    };

    public String getId(){
        return uniqueId.get();
    }

    @Override
    public String create(Equipment equ) {
        if(equipmentdao.insert(equ.setId(uniqueId.get())) > 0){
            return equ.getId();
        }
        return "";
    }

    @Override
    public Boolean remove(String id) {
        return equipmentdao.deleteById(id) > 0;
    }

    @Override
    public Boolean modify(Equipment equ) {
        return equipmentdao.updateById(equ) > 0;
    }

    @Override
    public IPage<Equipment> getPage(int current, int num, Equipment equ) {
        LambdaQueryWrapper<Equipment> lqw = new LambdaQueryWrapper<Equipment>();
        lqw.like(Strings.isNotEmpty(equ.getName()), Equipment::getName, equ.getName());
        lqw.like(Strings.isNotEmpty(equ.getId()), Equipment::getId, equ.getId());
        IPage<Equipment> page = new Page(current, num);
        return equipmentdao.selectPage(page, lqw);
    }

    // 由设施编号获取办公室编号
    @Override
    public int getOfficeById(String id) {
        LambdaQueryWrapper<Equipment> lqw = new LambdaQueryWrapper<Equipment>();
        lqw.eq(Equipment::getId, id);
        return equipmentdao.selectOne(lqw).getOffice();
    }

    // 由设施编号修改设施状态
    @Override
    public Boolean setStateById(String id, int state) {
        Equipment e = equipmentdao.selectById(id);
        e.setState(state);
        return equipmentdao.updateById(e) > 0;
    }

    // 由设施编号获取设施状态
    @Override
    public int getStateById(String id) {
        return equipmentdao.selectById(id).getState();
    }

    @Override
    public Equipment getOneEquipment(String id) {
        return equipmentdao.selectById(id);
    }
}