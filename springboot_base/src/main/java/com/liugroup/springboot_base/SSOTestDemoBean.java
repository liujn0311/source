package com.liugroup.springboot_base;

import com.gtja.link.service.Encrypt3DES;
import com.gtja.link.util.TSSDate;

public class SSOTestDemoBean {
    private Encrypt3DES encrype =new Encrypt3DES();
    //加密格式：createtime|returnurl|username|pwd|ip|type|redirecturl
    private static final String appid="EoUX93yvFiA=";//应用ID,Demo,其实也是加密的，值会事先按逻辑生成好，需要做成可配置的
    private static final String returnurl ="http://test.gtja.net:8080/testSSo.jsp";//返回的url，目前就是登录页面的url 为''

    private String createtime =null;//当前时间
    private StringBuffer url=null;//链接地址
    private static final String key="pZDKZozdW6fh7Nyp/wpFqDfMF+DGHrtp";
    private static final String iv = "GLqLe209hX4=";


    /**
     * 组合完成的访问地址参数
     * 直接用这个地址访问sso server得到返回状态
     * @return
     */
    public String getSSoAddr(){
        String encodeUrl=null;
        StringBuffer url =new StringBuffer("https://linktest.gtja.net/SSOServer/SSO.aspx?");
        createtime = TSSDate.shortDate();
        encodeUrl=createtime+"|"+returnurl;
        Encrypt3DES encrype =new Encrypt3DES();
        url.append("SSO="+java.net.URLEncoder.encode(appid+"$"+encrype.encode3DES(encodeUrl,key,iv)));
        return url.toString();
    }
    /**
     * 解析sso server返回的参数信息
     * @throws Exception
     */
    public String getRtmSS(String url) throws Exception{
        if(null!=url && !url.equals("")){
            url =encrype.decode3DES(url, key, iv);
        }
        return url;
    }


    public static void main(String[] args) throws Exception {
        SSOTestDemoBean ss=new SSOTestDemoBean();
        String url = ss.getSSoAddr();
        System.out.println(url);
    }


}
