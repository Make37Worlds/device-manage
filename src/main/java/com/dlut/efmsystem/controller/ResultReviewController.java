package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.FixLog;
import com.dlut.efmsystem.pojo.ResultReport;
import com.dlut.efmsystem.pojo.ResultReportVO;
import com.dlut.efmsystem.service.FixLogService;
import com.dlut.efmsystem.service.ResultManageService;
import com.dlut.efmsystem.service.impl.FixLogServiceImpl;
import com.dlut.efmsystem.service.impl.ResultManageServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/resultreview")
public class ResultReviewController {

    @Resource
    ResultManageService resultManageService ;
    @Resource
    FixLogService fixLogService;

    @GetMapping("{currentPage}/{size}/{id}")
    public Result getUnaudited(@PathVariable int currentPage, @PathVariable int size,@PathVariable String id){
        IPage<ResultReport> page = resultManageService.getUnreviewed(currentPage,size,id);
        if(currentPage>page.getPages()){
            page = resultManageService.getUnreviewed((int)page.getPages(),size,id);
        }
        return new Result(true,page,"待审核结果获取成功");
    }

    @GetMapping("{id}")
    public Result getResultReportVO(@PathVariable int id){
        ResultReportVO resultReportVO = new ResultReportVO();
        ResultReport resultReport = resultManageService.getOneResultReport(id);
        FixLog fixLog = fixLogService.getOneLog(resultReport.getLogId());
        if(fixLog!=null && resultReport!=null){
            resultReportVO.setResultReport(resultReport);
            resultReportVO.setFixLog(fixLog);
        }else{
            return new Result(false,null,"信息获取失败，请稍后再试");
        }
        return new Result(true,resultReportVO,"信息获取成功");
    }
    @PutMapping
    public Result reviewResult(@RequestBody ResultReport resultReport) throws RuntimeException{
      if(resultManageService.setReviewResult(resultReport)){
          return new Result(true,"审核成功");
      }else{
          return new Result(false,"审核操作失败，请稍后重试");
      }
    }


}
