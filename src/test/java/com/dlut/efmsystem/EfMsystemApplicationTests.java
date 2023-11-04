package com.dlut.efmsystem;

import com.dlut.efmsystem.dao.RoleMenuDao;
import com.dlut.efmsystem.pojo.Employee;
import com.dlut.efmsystem.pojo.Role;
import com.dlut.efmsystem.service.EmployeeService;
import com.dlut.efmsystem.service.EquipmentService;
import com.dlut.efmsystem.service.RoleManageService;
import com.dlut.efmsystem.service.impl.EmployeeServiceImpl;
import com.dlut.efmsystem.service.impl.EquipmentServiceImpl;
import com.dlut.efmsystem.service.impl.RoleManageServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest
class EfMsystemApplicationTests {

    @Autowired
    RoleManageService roleManageService = new RoleManageServiceImpl();
    @Autowired
    EmployeeService employeeService = new EmployeeServiceImpl();

    @Autowired
    EquipmentServiceImpl equipmentService;

    @Test
    void addRole(){
        Date d = new Date();
        Timestamp t = new Timestamp(d.getTime());
        Role r = new Role("测试人员","test",10,1,"测试插入角色功能");
        roleManageService.create(r);
    }

    @Test
    void deleteRole(){
        roleManageService.remove(8);
    }

    @Test
    void test1(){
        System.out.println(employeeService.getRoleById("20170702002"));
    }

    @Test
    void test3(){
        int i = 10;
        while(i>0){
            double d =Math.random()*2;
            System.out.print(d);
            System.out.print("-");
            System.out.println((int)d);
            i--;
        }

    }

}
