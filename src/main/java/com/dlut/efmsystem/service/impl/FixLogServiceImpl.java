package com.dlut.efmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlut.efmsystem.dao.FixLogDao;
import com.dlut.efmsystem.pojo.Employee;
import com.dlut.efmsystem.pojo.Equipment;
import com.dlut.efmsystem.pojo.FixLog;
import com.dlut.efmsystem.service.EmployeeService;
import com.dlut.efmsystem.service.EquipmentService;
import com.dlut.efmsystem.service.FixLogService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class FixLogServiceImpl implements FixLogService {
    @Resource
    private FixLogDao fixLogDao;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EmployeeService employeeService;

    //用于随机选取负责人
    private String selectHead() throws RuntimeException{
        List<Employee> leaders = employeeService.listAllMaintenanceLeader();
        if(leaders.size()>0){
            double index=Math.random()*leaders.size();
            return leaders.get((int)index).getId();
        }else{
            throw new RuntimeException("当前无负责人");
        }
    }

    @Override
    public int create(FixLog fixLog) throws RuntimeException {
        Equipment e = equipmentService.getOneEquipment(fixLog.getFacId());
        if(Objects.isNull(e)){
            throw new RuntimeException("该设施不存在，请检查设施编号");
        }
        int state = equipmentService.getStateById(fixLog.getFacId());
        if (state==1) {
            fixLog.setHead(selectHead());
            if(fixLogDao.insert(fixLog)>0){
                equipmentService.setStateById(fixLog.getFacId(),2);
                return fixLog.getId();
            }else{
                return 0;
            }
        }else {
            throw new RuntimeException("该设施不可维修");
        }
    }

    @Override
    public IPage<FixLog> getDispatchPage(int current, int num,String id) {
        IPage<FixLog> page = new Page<>(current, num);
        LambdaQueryWrapper<FixLog> lqw = new LambdaQueryWrapper<>();
        lqw.eq(FixLog::getState,1);
        lqw.eq(Strings.isNotEmpty(id),FixLog::getHead,id);
        return fixLogDao.selectPage(page,lqw);
    }

    @Override
    public Boolean modify(FixLog fixLog) {
        return fixLogDao.updateById(fixLog) > 0;
    }

    @Override
    public FixLog getOneLog(int id) {
        return fixLogDao.selectById(id);
    }

    @Override
    public int getStateById(int id){
        return fixLogDao.selectById(id).getState();
    }
    
    @Override
    public String getFacidById(int id){
        return fixLogDao.selectById(id).getFacId();
    }

    @Override
    public Boolean setDispatchInfo(int id, String fixBy) throws RuntimeException {
        FixLog fixLog = fixLogDao.selectById(id);
        if (fixLog.getState()==1){
            fixLog.setState(2);
            fixLog.setFixBy(fixBy);
            return fixLogDao.updateById(fixLog) > 0;
        }else {
            throw new RuntimeException("该维修单不可派遣");
        }
    }


    @Override
    public IPage<FixLog> getPage(int current, int num,FixLog fixLog) {
        IPage<FixLog> page = new Page(current,num);

        LambdaQueryWrapper<FixLog> lqw = new LambdaQueryWrapper<FixLog>();
        lqw.eq(Objects.nonNull(fixLog.getId()),FixLog::getId,fixLog.getId());
        lqw.orderBy(true,true,FixLog::getId);
        if(Strings.isNotEmpty(fixLog.getFixBy())){
            lqw.and((lqw2)->{
                lqw2.like(FixLog::getFixBy,fixLog.getFixBy())
                        .or().like(FixLog::getCreateBy,fixLog.getCreateBy())
                        .or().like(FixLog::getHead,fixLog.getHead());
            });
        }
        return fixLogDao.selectPage(page,lqw);
    }

    public List<Integer> listIdByHead(String head){
        return fixLogDao.getIdByHead(head);
    }
}
