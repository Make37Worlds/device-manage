package com.dlut.efmsystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Employee {
    private String id;
    private String password;
    private String name;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date birth;
    private char sex;
    private String position;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;
    private Date timeIn;
    private Integer depId;
    private String phoneNumber;

    public Employee(String id,String password,String name,Date birth,char sex,String position,
                    Long role_id,Date time_in,Integer dep_id,String phone_number){
        this.id = id;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.roleId = role_id;
        this.timeIn = time_in;
        this.depId = dep_id;
        this.phoneNumber = phone_number;
        this.position = position;
    }
}
