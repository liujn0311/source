package com.liugroup.springboot_base.util;

import java.util.Date;

public class Test {

    public static void main(String[] args) {
        long time = new Date().getTime();
        System.out.println(time);
        System.out.println(System.currentTimeMillis());
    }

    // 1600328950
    // 1658195212197
}
