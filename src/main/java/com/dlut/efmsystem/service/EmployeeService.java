package com.dlut.efmsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.pojo.Employee;
import java.util.List;

public interface EmployeeService {
    String create(Employee emp);
    Boolean remove(String id);
    Boolean modify(Employee emp);
    IPage<Employee> getPage(int current, int num, Employee emp);
    String getPasswordById(String id);
    int getRoleById(String id);
    Employee getOneEmployee(String id);
    List<Employee> listAllMaintenanceStaff();
    List<Employee> listAllMaintenanceLeader();
    String getNameById(String id);
    Long getCountByRole(int roleId);
}