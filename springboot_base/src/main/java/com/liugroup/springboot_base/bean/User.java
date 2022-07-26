package com.liugroup.springboot_base.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class User {

    @NotBlank(message = "name不能为空")
    @Length(max = 20, message = "name最大不能超过20")
    private String name;

    private String address;

    private Integer age;

}
