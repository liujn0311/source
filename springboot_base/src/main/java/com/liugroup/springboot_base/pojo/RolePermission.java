package com.liugroup.springboot_base.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ROLE_PERMISSION")
public class RolePermission {

    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "PERMISSION_ID")
    private Integer permissionId;
}
