package com.liugroup.springboot_base.mapper;


import com.liugroup.springboot_base.pojo.Menu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<Menu> {

    @Select("SELECT T1.* FROM MENU T1 \n" +
            "INNER JOIN PERMISSION T2 ON T2.MENU_ID = T1.ID\n" +
            "INNER JOIN ROLE_PERMISSION T3 ON T3.PERMISSION_ID = T2.ID\n" +
            "INNER JOIN USER_ROLE t4 ON T4.ROLE_ID = T3.ROLE_ID\n")
    List<Menu> getMenuByUserName();
}
