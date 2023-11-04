package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.AddDeclaration;
import com.dlut.efmsystem.pojo.ResultReport;
import com.dlut.efmsystem.service.AddDeclarationService;
import com.dlut.efmsystem.service.ResultManageService;
import com.dlut.efmsystem.service.impl.AddDeclarationServiceImpl;
import com.dlut.efmsystem.service.impl.ResultManageServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/adddeclaration")
public class AddDeclarationController {

    @Resource
    private AddDeclarationService addDeclarationService;

    @GetMapping("{currentPage}/{size}")
    public Result showPage(@PathVariable int currentPage, @PathVariable int size,AddDeclaration addDeclaration){
        IPage<AddDeclaration> page = addDeclarationService.getPage(currentPage,size,addDeclaration);
        if(currentPage>page.getPages()){
            page=addDeclarationService.getPage((int)page.getPages(),size,addDeclaration);
        }
        return  new Result(true,page);
    }

    @GetMapping("{id}")
    public Result getResultReport(@PathVariable int id){
        AddDeclaration addDeclaration = addDeclarationService.getOneAddDeclaration(id);
        if(addDeclaration == null){
            throw new RuntimeException("追加申报单信息获取失败，请确认信息无误");
        }else {
            return new Result(true,addDeclaration,"追加申报单信息获取成功");
        }
    }
}
