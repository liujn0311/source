package com.liugroup.springboot_base.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "TB_SYS_USER")
public class TbSysUser {

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "DEPT_CODE")
    private String deptCode;

    @Column(name = "JOIN_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "LABOR")
    private String labor;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "ENABLED")
    private String enabled;
}
