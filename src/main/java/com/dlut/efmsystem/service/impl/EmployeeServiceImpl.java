package com.dlut.efmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlut.efmsystem.dao.EmployeeDao;
import com.dlut.efmsystem.pojo.Employee;
import com.dlut.efmsystem.pojo.Role;
import com.dlut.efmsystem.service.EmployeeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    // 生成人员编号（日期yyyymmdd+当日新增序号xxx），用于新增人员
    @Resource
    private EmployeeDao employeedao;
    private static Supplier<String> uniqueId = new Supplier<String>() {
        private LocalDate today = LocalDate.now();
        private Integer order = 0;
        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        @Override
        public synchronized String get() {
            LocalDate now = LocalDate.now();
            if (!now.equals(today)) {
                today = now;
                order = 0;
            }
            return String.format("%s%03d", today.format(dateTimeFormatter), order++);
        }
    };

    @Override
    public String create(Employee emp) {
        // 检测新建人员合法性
        if (emp.getBirth().after(emp.getTimeIn())) {
            throw new RuntimeException("生日或入职日期输入有误");
        }
        emp.setId(uniqueId.get());
        if(employeedao.insert(emp)>0){
            return emp.getId();
        }
        return "";
    }

    @Override
    public Boolean remove(String id) {
        return employeedao.deleteById(id) > 0;
    }

    @Override
    public Boolean modify(Employee emp) {
        return employeedao.updateById(emp) > 0;
    }

    @Override
    public IPage<Employee> getPage(int current, int num, Employee emp) {
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        lqw.like(Strings.isNotEmpty(emp.getName()), Employee::getName, emp.getName());
        lqw.like(Strings.isNotEmpty(emp.getId()), Employee::getId, emp.getId());
        IPage<Employee> page = new Page(current, num);
        return employeedao.selectPage(page, lqw);
    }

    // 由人员编号获取密码，用于登录
    @Override
    public String getPasswordById(String id) {
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        lqw.eq(Employee::getId, id);
        return employeedao.selectOne(lqw).getPassword();
    }

    // 由人员编号获取角色
    @Override
    public int getRoleById(String id) {
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        lqw.eq(Employee::getId, id);
        return employeedao.selectOne(lqw).getRoleId().intValue();
    }

    @Override
    public Employee getOneEmployee(String id) {
        return employeedao.selectById(id);
    }

    // 获取所有维修部员工，用于维修派遣选取人员
    public List<Employee> listAllMaintenanceStaff() {
        return listByRole(2);
    }

    // 获取所有维修部领导，用于选取负责人
    public List<Employee> listAllMaintenanceLeader() {
        return listByRole(1);
    }

    // 由角色编号获取所有此角色人员
    private List<Employee> listByRole(int roleId) {
        return employeedao.selectList(Wrappers.<Employee>lambdaQuery()
                .eq(Employee::getRoleId, roleId));
    }

    // 由人员编号获取姓名
    @Override
    public String getNameById(String id) {
        return employeedao.selectById(id).getName();
    }

    @Override
    public Long getCountByRole(int roleId) {
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Employee::getRoleId,roleId);
        return employeedao.selectCount(lqw);
    }
}