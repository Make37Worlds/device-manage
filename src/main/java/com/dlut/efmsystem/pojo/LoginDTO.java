package com.dlut.efmsystem.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class LoginDTO {
    private String userId;
    private String token;
    private String role;
    private List<Menu> menuList;
}
