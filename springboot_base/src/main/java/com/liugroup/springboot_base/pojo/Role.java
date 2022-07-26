package com.liugroup.springboot_base.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "ROLE")
public class Role {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ROLE_KEY")
    private String roleKey;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "ROLE_REMARK")
    private String roleRemark;

    @Column(name = "ROLE_TYPE")
    private Integer roleType;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "RANK")
    private Integer rank;
}
