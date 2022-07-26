package com.liugroup.springboot_base.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "USER_PERMISSION")
public class UserPermission {

    @Column(name = "USERNAME")
    private Integer username;

    @Column(name = "PERMISSION_ID")
    private Integer permissionId;
}
