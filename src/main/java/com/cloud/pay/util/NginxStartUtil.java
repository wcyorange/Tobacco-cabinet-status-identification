package com.cloud.pay.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NginxStartUtil {

    /**
     * @return 1：代理重启成功 2：进程未开启 3：进程关闭时出错 4：进程启动时出错
     */
    public static int reStartProc() {

        int flag = 0;
        if (findProcess()) {
            // 进程没开启
            try {
                killProc();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("nginx.exe" + "线程开启失败");
            } finally {
                if (findProcess()) {
                    flag = 3;// 关闭失败
                }
            }

            try {
                startProc();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (findProcess()) {
                    flag = 1;// 开启成功
                } else {
                    flag = 4;// 启动失败
                }
            }

        } else {
            // 进程未开启
            flag = 2;
        }
        return flag;
    }

    /**
     * 关闭nginx进程
     *
     * @return 1：进程关闭成功 2：进程没有开启 3：进程关闭失败
     */
    public static int stop() {
        int flag = 0;

        if (findProcess()) {
            // 进程开启了
            try {
                killProc();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("nginx.exe" + "线程关闭失败");
            } finally {
                if (!findProcess()) {
                    flag = 1;// 关闭成功
                } else {
                    flag = 3;// 关闭失败
                }
            }
        } else {
            // 进程没开启
            flag = 2;
        }
        return flag;

    }

    /**
     * 跨平台关闭nginx
     *
     * @throws IOException
     */
    public static void killProc() throws IOException {
        if (judgeOs()) {
            KillWin();
        } else {
            killLinuxProc();
        }
    }

    /**
     * 关闭windows系统的nginx
     *
     * @throws IOException
     */
    public static void KillWin() throws IOException {
        executeCmd("taskkill /F /IM " + "nginx.exe");
    }

    /**
     * @desc 执行cmd命令
     */
    public static String executeCmd(String command) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd /c " + command);
        // Process process = runtime.exec( command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder build = new StringBuilder();
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            build.append(line);
        }
        return build.toString();
    }

    /**
     * @desc 执行cmd命令
     */
    public static String executeCmd2(String command) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder build = new StringBuilder();
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            build.append(line);
        }
        return build.toString();
    }

    /**
     * @desc 判断进程是否开启
     */
    public static boolean findProcess() {
        String processName = "nginx.exe";
        BufferedReader bufferedReader = null;
        try {
            Process proc = Runtime.getRuntime().exec("tasklist -fi " + '"' + "imagename eq " + processName + '"');
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * 关闭linux系统的nginx
     *
     * @throws IOException
     */
    private static void killLinuxProc() throws IOException {
        String command = "pkill -9 nginx";
        executeCmd(command);
    }

    /**
     * 打印进程的状态
     */
    public static void logStatus() {
        boolean flag = findProcess();
        if (flag) {
            System.out.println();
            System.err.println("nginx.exe" + "进程状态：开启");
            System.out.println();
        } else {
            System.out.println();
            System.err.println("nginx.exe" + "进程状态：关闭");
            System.out.println();
        }
    }

    /**
     * 开启linux的nginx
     *
     * @throws IOException
     */
    private static void startLinuxProc() throws IOException {
        System.out.println("开启进程:" + "nginx");
        String command1 = "/usr/local/nginx/sbin/nginx";

        String pro = executeCmd2(command1);
        System.out.println(pro);
    }

    /**
     * windows平台开启
     *
     * @throws IOException 1：开启成功 2：开启失败 3：已开启
     */
    public static int start() {
        int flag = 0;

        if (!findProcess()) {
            // 进程开启了
            try {
                startProc();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("nginx.exe" + "线程关闭失败");
            } finally {
                if (findProcess()) {
                    flag = 1;// 开启成功
                } else {
                    flag = 2;// 开启失败
                }
            }
        } else {
            // 进程已经开启
            flag = 3;
        }
        return flag;
    }

    /**
     * 跨平台启动nginx
     *
     * @throws IOException
     */
    private static void startProc() throws IOException {
        if (judgeOs()) {
            startWinProc();
        } else {
            startLinuxProc();
        }
    }

    /**
     * 开启windows系统的nginx
     *
     * @throws IOException
     */
    public static void startWinProc() throws IOException {
        String myExe = "cmd /c start nginx";
        String CONFPREFIXURL = "D:\\nginx-rtmp";
        System.err.println(CONFPREFIXURL);

        File dir = new File(CONFPREFIXURL);
        String[] str = new String[]{};
        // 执行命令
        Runtime.getRuntime().exec(myExe, str, dir);
    }

    /**
     * 判断操作系统是不是windows
     *
     * @return true：是win false：是Linux
     */
    public static boolean judgeOs() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os != null && os.startsWith("windows")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取项目部署环境的ip
     *
     * @return
     */
    public static String getIP() {
        String url = "";
        ;
        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
                NetworkInterface item = e.nextElement();
                for (InterfaceAddress address : item.getInterfaceAddresses()) {
                    if (item.isLoopback() || !item.isUp()) {
                        continue;
                    }
                    if (address.getAddress() instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address) address.getAddress();
                        url = inet4Address.getHostAddress();
                    }
                }
            }
            // url = InetAddress.getLocalHost().getHostAddress();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

    /**
     * 判断某个字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumer(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @return
     */
    public static int getPort() {
        String fileName = getConfAddr();
        int port = -1;
        File file = new File(fileName);
        BufferedReader reader = null;
        String lastLine = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (!tempString.trim().startsWith("#") && tempString.trim().length() != 0) {
                    // System.out.println(tempString.trim());
                    if ("server {".equals(lastLine.trim())) {
                        String content = tempString.trim();
                        if (content.startsWith("listen") && content.endsWith(";")) {
                            String number = content.replace("listen", "").replace(";", "").trim();
                            if (isNumer(number)) {
                                port = Integer.parseInt(number);
                            }
                        }
                    }
                    lastLine = tempString;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return port;
    }

    public static String getConfAddr() {
        String CONFPREFIXURL = HCNetSDKPath.DLL_PATH+ File.separator+ "nginx";
        if (judgeOs()) {
            CONFPREFIXURL = CONFPREFIXURL + "windows" + File.separator + "conf" + File.separator + "nginx.conf";
        } else {
            // linux的处理
        }
        return CONFPREFIXURL;
    }

    public static void main(String[] args) {

    }

}