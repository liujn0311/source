package com.liugroup.springboot_base.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "PERMISSION")
public class Permission {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PERMISSION_KEY")
    private String permissionKey;

    @Column(name = "PERMISSION_NAME")
    private String permissionName;

    @Column(name = "MENU_ID")
    private Integer menuId;

    @Column(name = "PERMISSION_TYPE")
    private Integer permissionType;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "RANK")
    private Integer rank;

}
