package com.liugroup.springboot_base.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "MENU")
public class Menu {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LEVEL")
    private Integer level;

    @Column(name = "CATALOG")
    private Integer catalog;

    @Column(name = "SORT")
    private Integer sort;

    @Column(name = "PERMISSION_ID")
    private Integer permissionId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "KEY")
    private String key;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "CREATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name = "LINK_TYPE")
    private Integer linkType;

    @Column(name = "LINK_URL")
    private String linkUrl;

    @Column(name = "SYSTEM_TYPE")
    private String systemType;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "IF_DEFAULT")
    private Integer ifDefault;
}
