package com.dlut.efmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlut.efmsystem.dao.ResultReportDao;
import com.dlut.efmsystem.pojo.FixLog;
import com.dlut.efmsystem.pojo.ResultReport;
import com.dlut.efmsystem.service.EquipmentService;
import com.dlut.efmsystem.service.FixLogService;
import com.dlut.efmsystem.service.ResultManageService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class ResultManageServiceImpl implements ResultManageService {

    @Resource
    private ResultReportDao resultreportdao;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private FixLogService fixLogService;

    @Override
    public IPage<ResultReport> getPage(int current, int num, ResultReport r) {
        IPage<ResultReport> page = new Page(current,num);
        LambdaQueryWrapper<ResultReport> lqw = new LambdaQueryWrapper<ResultReport>();
        lqw.like(r.getLogId()!=null, ResultReport::getLogId,r.getLogId());
        lqw.like(Strings.isNotEmpty(r.getCreateBy()),ResultReport::getCreateBy,r.getCreateBy());
        lqw.like(Objects.nonNull(r.getResult()),ResultReport::getResult,r.getResult());
        return resultreportdao.selectPage(page,lqw);
    }

    @Override
    public IPage<ResultReport> getUnreviewed(int current, int num, String id) {
        List<Integer> logIds=fixLogService.listIdByHead(id);
        IPage<ResultReport> page = new Page(current, num);
        LambdaQueryWrapper<ResultReport> lqw = new LambdaQueryWrapper<ResultReport>();
        if(!logIds.isEmpty()) {
            lqw.in(ResultReport::getLogId, logIds);
            lqw.eq(ResultReport::getResult, 1);
            return resultreportdao.selectPage(page, lqw);
        }
        lqw.eq(ResultReport::getResult, 0);
        return resultreportdao.selectPage(page, lqw);
    }

    @Override
    public ResultReport getOneResultReport(int id) {
        return resultreportdao.selectById(id);
    }

    @Override
    public int create(ResultReport r) throws RuntimeException {
        int state = fixLogService.getStateById(r.getLogId());
        if(state==2||state==4||state==5||state==8){ //状态正确才能进行上报
            if(resultreportdao.insert(r)>0) { //插入成功，修改维修单状态为“成果已上报”
                FixLog fixLog = new FixLog();
                fixLog.setId(r.getLogId());
                fixLog.setState(6);
                fixLogService.modify(fixLog);
                return r.getId();
            }else{
                throw new RuntimeException("成果上报失败，请稍后再试。");
            }
        }else{
            throw new RuntimeException("当前状态下，维修单不可进行成果上报。");
        }
    }

    @Override
    public Boolean setReviewResult(ResultReport resultReport) throws RuntimeException {
        ResultReport r = resultreportdao.selectById(resultReport.getId());
        //插入审核结果
        int state = fixLogService.getStateById(r.getLogId());
        if(state == 6){//若维修单状态正确，则进行审核结果的写入
            boolean flag = resultreportdao.updateById(resultReport)>0;
            if(flag) {//插入成功，对维修单和设施进行修改
                //根据审核结果修改维修单状态
                FixLog fixLog = fixLogService.getOneLog(r.getLogId());
                fixLog.setState(resultReport.getResult() == 2 ? 7 : 8);

                //审核通过，维修结束，故障原因、实际成本、维修时间写入维修单，设施状态改为运行中
                if (resultReport.getResult() == 2) {
                    fixLog.setActualCost(r.getActualCost());
                    fixLog.setFaultReason(r.getFaultReason());
                    if(r.getFixTime().before(fixLog.getCreateTime())){
                        throw new RuntimeException("维修时间早于申报时间，填写错误，请检查。");
                    }
                    fixLog.setFixTime(r.getFixTime());
                    equipmentService.setStateById(fixLogService.getFacidById(r.getLogId()), 1);
                }
                fixLogService.modify(fixLog);
            }
            return flag;
        }else{
            throw new RuntimeException("维修单状态有误，审核失败。");
        }
    }
}
