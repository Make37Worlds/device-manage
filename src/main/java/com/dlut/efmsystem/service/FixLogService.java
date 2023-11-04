package com.dlut.efmsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.pojo.FixLog;
import java.util.List;

public interface FixLogService {
    int create(FixLog fixLog);
    IPage<FixLog> getDispatchPage(int current, int num,String id);
    IPage<FixLog> getPage(int current,int num,FixLog fixLog);
    Boolean  modify(FixLog f);
    FixLog getOneLog(int id);
    int getStateById(int id);
    String getFacidById(int id);
    Boolean setDispatchInfo(int id,String fixBy);
    List<Integer> listIdByHead(String head);
}
