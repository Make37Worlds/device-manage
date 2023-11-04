package com.dlut.efmsystem.service;

import com.dlut.efmsystem.pojo.LoginDTO;
import com.dlut.efmsystem.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface LoginManagerService {
    LoginDTO login(String username, String password, String token);

    Boolean setPassword(String id,String oldPassword, String password);

    Boolean isLogin(String token);

    void logout(String token);
}
