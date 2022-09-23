package com.cloud.pay.util;

import cc.eguid.commandManager.CommandManager;
import cc.eguid.commandManager.CommandManagerImpl;
import cc.eguid.commandManager.data.CommandTasker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:宁志洋
 * @Date:2020/10/8 21:42
 */
public class FfpegUtil {

    private static CommandManager manager = new CommandManagerImpl();

    public static String rtmpN() throws InterruptedException {
        String username = "admin";
        String password = "zscj123a";
        String ip = "192.168.1.64";
        String identifier = "GS01";
        CommandManager manager = new CommandManagerImpl();

        // -rtsp_transport tcp
        //测试多个任何同时执行和停止情况
        //默认方式发布任务
        String rtmp = "rtmp://localhost:1935/live/room-" + identifier;
        manager.start("room" + identifier, "cmd /c start C:/Users/Administrator/Desktop/java/GS01.bat");

        /*System.out.println(manager.queryAll());
//        Thread.sleep(30000*2);
        CommandTasker info = manager.query("room" + identifier);
        System.out.println("---------"+info.getId());
        Thread.sleep(1000*120);
        manager.stop(info.getId());
        System.out.println("-------------------------close");*/
        String live_url = "http://10.100.100.75:8888/live?port=1935&app=live&stream=room-" + identifier;
        return live_url;
    }

    public static void main(String[] args) throws Exception {
        String a = FfpegUtil.rtmpN();
        System.out.println(a);
    }
}
