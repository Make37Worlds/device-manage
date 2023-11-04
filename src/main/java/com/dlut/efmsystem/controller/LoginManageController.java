package com.dlut.efmsystem.controller;

import com.alibaba.druid.util.StringUtils;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.ExchangePasswordVO;
import com.dlut.efmsystem.pojo.LoginDTO;
import com.dlut.efmsystem.service.LoginManagerService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 50844
 * @date 2022/11/17 21:19
 */
@RestController
@RequestMapping("/loginManage")
public class LoginManageController {
    @Resource
    private LoginManagerService loginManagerService;

    @GetMapping("/login")
    public Result loginIn(String id,String password,@RequestHeader("token") String token){
        if (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(password)){
            try {
                LoginDTO loginDTO = loginManagerService.login(id, password,token);
                return new Result(true,loginDTO,"登录成功！");
            } catch (Exception e) {
                return new Result(false,"登录失败,"+e.getMessage());
            }
        }else{
            return new Result(false,"登录失败,账号密码不能为空！");
        }
    }
    @PostMapping("/exChange")
    public Result exChange(@RequestBody ExchangePasswordVO exchangePasswordVO,@RequestHeader("token") String token){
        if(Strings.isEmpty(token)){
            return new Result(false,"用户未登录");
        }
        if (!StringUtils.isEmpty(exchangePasswordVO.getId())&&!StringUtils.isEmpty(exchangePasswordVO.getNewPassword()) && !StringUtils.isEmpty(exchangePasswordVO.getOldPassword())){
            try {
                loginManagerService.setPassword(exchangePasswordVO.getId(),exchangePasswordVO.getOldPassword(), exchangePasswordVO.getNewPassword());
                return new Result(true,"修改成功！");
            } catch (Exception e) {
                return new Result(false,"修改失败,"+e.getMessage());
            }
        }else{
            return new Result(false,"修改失败,账号密码不能为空！");
        }
    }

    @DeleteMapping("/logout/{id}")
    public Result logout(@PathVariable String id,@RequestHeader("token") String token){
        if(Strings.isEmpty(token)){
            return new Result(false,"用户未登录");
        }
        loginManagerService.logout(id);
        return new Result(true,"退出登录成功");
    }
}
