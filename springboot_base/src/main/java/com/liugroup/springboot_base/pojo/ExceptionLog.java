package com.liugroup.springboot_base.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "exception_log")
@Data
public class ExceptionLog {
    @Id
    private String excId;

    private String excRequParam;

    private String excName;

    private String excMessage;

    private String excUserId;

    private String excUserName;

    private String excMethod;

    private String excUri;

    private String excIp;

    private Date excCreateTime;

    private String excVer;
}
