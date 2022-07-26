package com.liugroup.springboot_base.pojo;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "VISITS_LOG")
public class VisitsLog {

    private Integer id;


}
