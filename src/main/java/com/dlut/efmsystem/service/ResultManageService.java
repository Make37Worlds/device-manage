package com.dlut.efmsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.pojo.ResultReport;

public interface ResultManageService {
    IPage<ResultReport> getPage(int current,int num,ResultReport r);
    IPage<ResultReport> getUnreviewed(int current,int num,String id);
    ResultReport getOneResultReport(int id);
    int create(ResultReport r);
    Boolean setReviewResult(ResultReport resultReport);
}
