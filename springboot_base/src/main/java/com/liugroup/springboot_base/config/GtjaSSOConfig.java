package com.liugroup.springboot_base.config;

public class GtjaSSOConfig {

    private String appid;

    private String returnUrl;



//    private static final String appid="EoUX93yvFiA=";//应用ID,Demo,其实也是加密的，值会事先按逻辑生成好，需要做成可配置的
//    private static final String returnurl ="http://test.gtja.net:8080/testSSo.jsp";//返回的url，目前就是登录页面的url 为''

    private String createtime =null;//当前时间
    private StringBuffer url=null;//链接地址
    private static final String key="pZDKZozdW6fh7Nyp/wpFqDfMF+DGHrtp";
    private static final String iv = "GLqLe209hX4=";

}
