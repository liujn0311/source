package com.liugroup.springboot_base.controller;

import com.alibaba.fastjson.JSONObject;
import com.gtja.link.service.Encrypt3DES;
import com.liugroup.springboot_base.annotation.OperLog;
import com.liugroup.springboot_base.bean.Role;
import com.liugroup.springboot_base.bean.User;
import com.liugroup.springboot_base.common.BaseResponse;
import com.liugroup.springboot_base.common.Constant;
import com.liugroup.springboot_base.exception.BaseBizException;
import com.liugroup.springboot_base.mapper.MenuMapper;
import com.liugroup.springboot_base.mapper.RoleMapper;
import com.liugroup.springboot_base.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

@RequestMapping("/base")
@RestController
public class BaseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    private Encrypt3DES encrype =new Encrypt3DES();

    //加密格式：createtime|returnurl|username|pwd|ip|type|redirecturl
    private static final String appid="EoUX93yvFiA=";//应用ID,Demo,其实也是加密的，值会事先按逻辑生成好，需要做成可配置的

    private static final String returnurl ="http://test.gtja.net:8080/testSSo.jsp";//返回的url，目前就是登录页面的url 为''

    private String createtime =null;//当前时间
    private StringBuffer url=null;//链接地址
    private static final String key="pZDKZozdW6fh7Nyp/wpFqDfMF+DGHrtp";
    private static final String iv = "GLqLe209hX4=";

    public static final String[] IMAGE_SUFFIX = {"jpg", "png"};

    @Value("${image.path}")
    private String IMAGE_PATH;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuMapper menuMapper;

    @RequestMapping("index/add")
    @OperLog(operModul="首页",operType="GET",operDesc="新增")
    public JSONObject add(){
        LOGGER.info("调用了首页新增接口");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        jsonObject.put("errorInfo", "成功");
        jsonObject.put("data", null);
        return jsonObject;
    }

    @RequestMapping("/index/delete")
    @OperLog(operModul="首页",operType="GET",operDesc="删除")
    public JSONObject delete(){
        LOGGER.info("调用了首页删除接口");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        jsonObject.put("errorInfo", "成功");
        jsonObject.put("data", null);
        return jsonObject;
    }

    @RequestMapping("/pv/update")
    @OperLog(operModul="访问量统计",operType="GET",operDesc="修改")
    public JSONObject update(){
        LOGGER.info("调用了访问量统计修改接口");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        jsonObject.put("errorInfo", "成功");
        jsonObject.put("data", null);
        return jsonObject;
    }

    @RequestMapping("/pv/select")
    @OperLog(operModul="访问量统计",operType="GET",operDesc="查询")
    public JSONObject select(){
        LOGGER.info("调用了访问量统计查询接口");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        jsonObject.put("errorInfo", "成功");
        jsonObject.put("data", null);
        return jsonObject;
    }

    @RequestMapping("/user/add")
    public JSONObject addUser(@Validated @RequestBody User user){
        System.out.println(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        jsonObject.put("errorInfo", "成功");
        jsonObject.put("data", null);
        return jsonObject;
    }

    public enum ImageSuffix{
        jpg,
        png;
    }

    @RequestMapping("/uploadImage")
    public JSONObject uploadImage(@RequestParam("file") MultipartFile file){

        // 判断文件是否为空
        if (file.isEmpty()) {
            throw new BaseBizException(Constant.ErrorCode.FILE_NULL, Constant.ErrorMsg.FILE_NULL);
        }

        String originalFilename = file.getOriginalFilename();
        String imageSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        boolean flag = true;
        // 校验图片后缀
        for (String suffix : IMAGE_SUFFIX) {
            if (suffix.equalsIgnoreCase(imageSuffix)){
                flag = false;
                break;
            }
        }
        if (flag){
            throw new BaseBizException(Constant.ErrorCode.FILE_SUFFIX, Constant.ErrorMsg.FILE_SUFFIX);
        }
        // 校验图片大小
        long size = file.getSize();
        if (size > (500 * 1024)){
            throw new BaseBizException(Constant.ErrorCode.FILE_MAX, Constant.ErrorMsg.FILE_MAX);
        }
        // 生成新文件名(当前时间的时间戳+文件的后缀名）
        String newFileName = System.currentTimeMillis() + originalFilename;
        File filePath = new File(IMAGE_PATH, newFileName);
        // 如果目录不存在则创建目录
        if(!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录：" + filePath);
        }
        // 图片上传
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 响应
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        jsonObject.put("errorInfo", "成功");
        jsonObject.put("data", null);
        return jsonObject;
    }

    @RequestMapping("/getRole")
    public JSONObject getRole (){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        jsonObject.put("errorInfo", "成功");
        jsonObject.put("data", roleMapper.selectAll());
        return jsonObject;
    }

    @RequestMapping("/addRole")
    public JSONObject addRole(@RequestBody Role role){
        roleMapper.insertSelective(role);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", "0");
        jsonObject.put("errorInfo", "成功");
        jsonObject.put("data", null);
        return jsonObject;
    }

    @RequestMapping("/getMenu")
    public BaseResponse getMenu(){
        return BaseResponse.success(menuMapper.getMenuByUserName());
    }

    @RequestMapping("/getCookie")
    public void getCookie(HttpServletRequest request){
        String cookie = request.getHeaders("cookie").nextElement();
        System.out.println(cookie);
    }


//    @RequestMapping("/getUserInfo")
//    public BaseResponse getUserInfo(String token){
//        // token不为空，从token中取出用户信息，
//        if (Optional.ofNullable(token).isPresent()){
//
//        }else {
//            // 调国泰君安SSO SEVER验证
//
//        }
//
//    }

    @RequestMapping("/getIp")
    public BaseResponse getIp(HttpServletRequest request){

        String remoteIp = IpUtils.getRemoteIp(request);
        return BaseResponse.success(remoteIp);

    }

    @RequestMapping("/getMACAddress")
    public String getMACAddress(HttpServletRequest request) {

        String macAddress = "";

        String ip = getRemoteIp(request);

        Properties props = System.getProperties();

        if(props.get("os.name").toString().contains("Window")){//判断操作系统

            if(ip.equals("127.0.0.1")){//本机的IP地址查询不了，要转成192.xxx.xxx.xxx的形式

                InetAddress addr = null;

                try {

                    addr = InetAddress.getLocalHost();

                    ip=addr.getHostAddress().toString();

                } catch (UnknownHostException e) {

                    ip = "127.0.0.1";

                }

            }

            String str = "";

            try {

                Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);

                InputStreamReader ir = new InputStreamReader(p.getInputStream());

                LineNumberReader input = new LineNumberReader(ir);

                for (int i = 1; i < 100; i++) {

                    str = input.readLine();

                    if (str != null) {

                        if (str.contains("MAC Address")) {

                            macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());

                            break;

                        }else if(str.contains("MAC 地址")){//有的机器会显示中文

                            macAddress = str.substring(str.indexOf("MAC 地址") + 9, str.length());

                            break;

                        }

                    }

                }

            } catch (IOException e) {

                return "";

            }

        } else {

            try {

                Runtime.getRuntime().exec("ping -c1 " + ip);

                Process p = Runtime.getRuntime().exec(

                        new String[] { "/bin/sh", "-c", "arp | grep " + ip + " | awk '{print $3}'" });

                InputStreamReader ir = new InputStreamReader(p.getInputStream());

                LineNumberReader input = new LineNumberReader(ir);

                macAddress = input.readLine();

            } catch (IOException e) {

                return "";

            }

        }

        return macAddress;

    }


    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip = request.getRemoteAddr();
        ip = ip.indexOf("0:0:0:0:0:0:0:1") > -1 ? "127.0.0.1" : ip;
        return ip;
    }

    @GetMapping("testParam")
    public String testParam(String a, int b){
        System.out.println("a = " + a + ",b = " + b);
        return "fixbug";
    }

    @PostMapping("postParam")
    public String postParam(HttpServletRequest  request){
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username + ", age = " + age);

        new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            String username1 = request.getParameter("username");
            String age1 = request.getParameter("age");
        }).start();
        return "success";
    }

}
