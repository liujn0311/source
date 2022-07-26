package com.liugroup.springboot_base;

import com.liugroup.springboot_base.bean.Role;
import com.liugroup.springboot_base.mapper.*;
import com.liugroup.springboot_base.pojo.Menu;
import com.liugroup.springboot_base.pojo.Permission;
import com.liugroup.springboot_base.pojo.RolePermission;
import com.liugroup.springboot_base.pojo.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@Slf4j
class SpringbootBaseApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Test
    void applicationContext(){
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("application.yml");
        Object user = ioc.getBean("user");
    }

    @Test
    void contextLoads() {

        List<Menu> menus = menuMapper.selectAll();
        log.info(menus.toString());

        List<Permission> permissions = permissionMapper.selectAll();
        log.info(permissions.toString());

        List<Role> roles = roleMapper.selectAll();
        log.info(roles.toString());

        List<RolePermission> rolePermissions = rolePermissionMapper.selectAll();
        log.info(rolePermissions.toString());

        List<UserRole> userRoles = userRoleMapper.selectAll();
        log.info(userRoles.toString());

    }

    @Test
    void testK() throws SocketException, UnknownHostException {


        String macAddrByIp = getMacAddr("127.0.0.1");

        System.out.println(macAddrByIp);


    }

    public String getMACAddress(String ip) throws UnknownHostException, SocketException {
        String line = "";
        String macAddress = "";
        final String MAC_ADDRESS_PREFIX = "MAC_Address = ";
        final String LOOPBACK_ADDRESS = "127.0.0.1";

        if (LOOPBACK_ADDRESS.equals(ip)){
            InetAddress inetAddress =InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0){
                    sb.append("-");
                }
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            macAddress = sb.toString().trim().toUpperCase();
            return macAddress;
        }
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A" + ip);
            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while (null != (line = br.readLine())){
                int index = line.indexOf(MAC_ADDRESS_PREFIX);
                if (index != -1){
                    macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();
                }
            }
            isr.close();
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return macAddress;
    }


    public String getMacAddrByIp(String ip) {
        String macAddr = null;
        try {
            Process process = Runtime.getRuntime().exec("nbtstat -a " + ip);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            Pattern pattern = Pattern.compile("([A-F0-9]{2}-){5}[A-F0-9]{2}");
            Matcher matcher;
            for (String strLine = br.readLine(); strLine != null;
                 strLine = br.readLine()) {
                matcher = pattern.matcher(strLine);
                if (matcher.find()) {
                    macAddr = matcher.group();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(macAddr);
        return macAddr;
    }


    static String getMacAddr(String ip) {
        String macAddr = null;
        try {
            Process process = Runtime.getRuntime().exec("nbtstat -a " + ip);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            Pattern pattern = Pattern.compile("([A-F0-9]{2}-){5}[A-F0-9]{2}");
            Matcher matcher;
            for (String strLine = br.readLine(); strLine != null;
                 strLine = br.readLine()) {
                matcher = pattern.matcher(strLine);
                if (matcher.find()) {
                    macAddr = matcher.group();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(macAddr);
        return macAddr;
    }

    public static String getMACAddress(HttpServletRequest request) {

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

    public static String callCmd(String[] cmd) {
        String result = "" ;
        String line = "" ;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader (is);
            while ((line = br.readLine ()) != null ) {
                result += line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param cmd 第一个命令
     * @param another 第二个命令
     * @return  第二个命令的执行结果
     */
    public static String callCmd(String[] cmd,String[] another) {
        String result = "" ;
        String line = "" ;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.waitFor(); //已经执行完第一个命令，准备执行第二个命令
            proc = rt.exec(another);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader (is);
            while ((line = br.readLine ()) != null ) {
                result += line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    /**
     *
     * @param ip 目标ip,一般在局域网内
     * @param sourceString 命令处理的结果字符串
     * @param macSeparator mac分隔符号
     * @return mac地址，用上面的分隔符号表示
     */
    public static String filterMacAddress( final String ip, final String sourceString, final String macSeparator) {
        String result = "" ;
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})" ;
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while (matcher.find()){
            result = matcher.group( 1 );
            if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group( 1 ))) {
                break ; //如果有多个IP,只匹配本IP对应的Mac.
            }
        }
        return result;
    }



    /**
     *
     * @param ip 目标ip
     * @return  Mac Address
     *
     */
    public static String getMacInWindows( final String ip){
        String result = "" ;
        String[] cmd = {
                "cmd" ,
                "/c" ,
                "ping " + ip
        };
        String[] another = {
                "cmd" ,
                "/c" ,
                "arp -a"
        };

        String cmdResult = callCmd(cmd,another);
        result = filterMacAddress(ip,cmdResult, "-" );

        return result;
    }

    /**
     * @param ip 目标ip
     * @return  Mac Address
     *
     */
    public static String getMacInLinux( final String ip){
        String result = "" ;
        String[] cmd = {
                "/bin/sh" ,
                "-c" ,
                "ping " + ip + " -c 2 && arp -a"
        };
        String cmdResult = callCmd(cmd);
        result = filterMacAddress(ip,cmdResult, ":" );

        return result;
    }

    /**
     * 获取MAC地址
     * @return 返回MAC地址
     */
    public static String getMacAddress(String ip){
        String macAddress = "" ;
        macAddress = getMacInWindows(ip).trim();
        if (macAddress== null || "" .equals(macAddress)){
            macAddress = getMacInLinux(ip).trim();
        }
        return macAddress;
    }

    @Test
    public void testMAC(){

        System.out.println(getMacAddress( "192.168.0.117" ));
    }
}
