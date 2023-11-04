package com.dlut.efmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlut.efmsystem.dao.AddDeclarationDao;
import com.dlut.efmsystem.pojo.AddDeclaration;
import com.dlut.efmsystem.pojo.FixLog;
import com.dlut.efmsystem.service.AddDeclarationService;
import com.dlut.efmsystem.service.FixLogService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class AddDeclarationServiceImpl implements AddDeclarationService {

    @Resource
    private AddDeclarationDao addDeclarationDao;
    @Resource
    private FixLogService fixLogService;

    @Override
    public int create(AddDeclaration addDeclaration) throws RuntimeException{
        /*
        判断当前维修单是否可以进行追加申报，包含维修状态与追加申报次数上限的判断
        创建后修改维修单状态
         */
        int state = fixLogService.getStateById(addDeclaration.getLogId());
        if (state == 2||state == 4||state==5||state==8) {//判断维修单状态是否可以申报
            LambdaQueryWrapper<AddDeclaration> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(addDeclaration.getLogId()!=0,AddDeclaration::getLogId,addDeclaration.getLogId());
            Long i=addDeclarationDao.selectCount(wrapper);//计算该维修单追加申报次数
            if (i < 5) {//追加申报次数未达上限，可以申报
                boolean flag=addDeclarationDao.insert(addDeclaration)>0;
                if (flag) {//创建追加申报单成果，修改维修单状态
                    FixLog fixLog= fixLogService.getOneLog(addDeclaration.getLogId());
                    fixLog.setState(3);
                    fixLogService.modify(fixLog);
                }
                return addDeclaration.getId();
            }
            else {
                throw new RuntimeException("追加申请已达上限，无法继续进行！");
            }
        }
        else {
            throw new RuntimeException("现在无法进行追加申报！请重新检查！");
        }
    }

    @Override
    public Boolean setApprovalInfo(AddDeclaration addDeclaration) throws RuntimeException{
        /*
        根据审查结果修改维修单状态
        修改维修单中的预算：目前预算=追加预算+之前的预算
        */
        AddDeclaration a = addDeclarationDao.selectById(addDeclaration.getId());//查询待审核的追加申报表
        int state = fixLogService.getStateById(a.getLogId());
        if(state==3) {//状态正确，可以进行审核
            boolean flag = addDeclarationDao.updateById(addDeclaration) > 0;
            if (flag) {//修改成功，更改维修单状态
                FixLog fixLog = fixLogService.getOneLog(a.getLogId());
                fixLog.setState(addDeclaration.getResult() == 2 ? 4 : 5);
                if(addDeclaration.getResult() == 2){
                    fixLog.setBudget(fixLog.getBudget().add(a.getAddBudget()));
                }
                fixLogService.modify(fixLog);
            }
            return flag;//修改追加申报表的审核结果和原因
        }
        else{
            throw new RuntimeException("维修单状态有误，请验证后再进行操作！");
        }
    }

    @Override
    public AddDeclaration getOneAddDeclaration(int id) {
        return addDeclarationDao.selectById(id);
    }


    @Override
    public IPage<AddDeclaration> getPage(int currentPage, int pageSize, AddDeclaration addDeclaration) {//查询操作
        IPage<AddDeclaration> page = new Page(currentPage,pageSize);
        LambdaQueryWrapper<AddDeclaration> lqw = new LambdaQueryWrapper<>();
        lqw.like(addDeclaration.getLogId()!=null, AddDeclaration::getLogId,addDeclaration.getLogId());
        lqw.like(Strings.isNotEmpty(addDeclaration.getCreateBy()),AddDeclaration::getCreateBy,addDeclaration.getCreateBy());
        lqw.like(Objects.nonNull(addDeclaration.getResult()),AddDeclaration::getResult,addDeclaration.getResult());
        addDeclarationDao.selectPage(page,lqw);
        return page;
    }

    @Override
    public IPage<AddDeclaration> getUnapproved(int currentPage, int pageSize, String id) {
        List<Integer> logIds=fixLogService.listIdByHead(id);
        IPage<AddDeclaration> page = new Page(currentPage, pageSize);
        LambdaQueryWrapper<AddDeclaration> lqw = new LambdaQueryWrapper<>();
        if(!logIds.isEmpty()){
            lqw.in(AddDeclaration::getLogId,logIds);
            lqw.eq(AddDeclaration::getResult,1);
            return addDeclarationDao.selectPage(page,lqw);
        }
        lqw.eq(AddDeclaration::getResult,0);
        return addDeclarationDao.selectPage(page,lqw);
    }
}
