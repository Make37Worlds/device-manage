package com.dlut.efmsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlut.efmsystem.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
}
