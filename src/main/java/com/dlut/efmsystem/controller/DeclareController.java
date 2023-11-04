package com.dlut.efmsystem.controller;

import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.FixLog;
import com.dlut.efmsystem.service.FixLogService;
import com.dlut.efmsystem.service.impl.FixLogServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/declare")
public class DeclareController {

    @Resource
    FixLogService fixLogService;

    @PostMapping
    public Result declare(@RequestBody FixLog fixLog){
        Integer logId=fixLogService.create(fixLog);
        if(logId!=0){
            return new Result(true,logId,"申报成功");
        }else{
            return new Result(false,"申报失败，请稍后再试");
        }
    }
}
