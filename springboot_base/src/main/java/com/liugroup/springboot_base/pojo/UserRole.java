package com.liugroup.springboot_base.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "USER_ROLE")
public class UserRole {

    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "USERNAME")
    private String username;
}
