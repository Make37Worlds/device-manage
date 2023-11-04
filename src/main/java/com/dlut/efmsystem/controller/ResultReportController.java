package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.ResultReport;
import com.dlut.efmsystem.service.ResultManageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/resultreport")
public class ResultReportController {

    @Resource
    private ResultManageService resultManageService;

    @GetMapping("{currentPage}/{size}")
    public Result showPage(@PathVariable int currentPage, @PathVariable int size,ResultReport resultReport){
        IPage<ResultReport> page = resultManageService.getPage(currentPage,size,resultReport);
        if(currentPage>page.getPages()){
            page=resultManageService.getPage((int)page.getPages(),size,resultReport);
        }
        return  new Result(true,page);
    }


    @GetMapping("{id}")
    public Result getResultReport(@PathVariable int id){
        ResultReport resultReport = resultManageService.getOneResultReport(id);
        if(resultReport == null){
            return new Result(false,null,"成果单信息获取失败，请稍后再试");
        }else {
            return new Result(true,resultReport,"成果单信息获取成功");
        }
    }
}
