package com.liugroup.springboot_base.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.*;

@Data
@Table(name = "ROLE")
public class Role {

    @Id
    @Column(name = "ID")
    @KeySql(sql = "SELECT TEST_1.NEXTVAL AS ID FROM DUAL", order = ORDER.BEFORE)
    private Integer id;

    @Column(name = "ROLE_KEY")
    private String roleKey;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "ROLE_REMARK")
    private String roleRemark;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "RANK")
    private Integer rank;


}
