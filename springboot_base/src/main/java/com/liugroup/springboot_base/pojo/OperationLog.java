package com.liugroup.springboot_base.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "operation_log")
@Data
public class OperationLog {
    @Id
    private String operId;

    private String operModul;

    private String operType;

    private String operDesc;

    private String operRequParam;

    private String operRespParam;

    private String operUserId;

    private String operUserName;

    private String operMethod;

    private String operUri;

    private String operIp;

    private Date operCreateTime;

    private String operVer;
}
