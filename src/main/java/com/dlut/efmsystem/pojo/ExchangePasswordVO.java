package com.dlut.efmsystem.pojo;

import lombok.Data;

@Data
public class ExchangePasswordVO {

    private String id;
    private String oldPassword;
    private String newPassword;

}
