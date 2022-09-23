package com.cloud.pay.util;

import com.cloud.pay.ClientDemo.ClientDemo;
import com.cloud.pay.ClientDemo.HCNetSDK;
import com.cloud.pay.entity.Camera;
import com.sun.jna.NativeLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Administrator
 */

public class PreviewUtil {

    private static HCNetSDK hcNetSDK = HCNetSDK.INSTANCE;
    private Logger logger = LoggerFactory.getLogger(PreviewUtil.class);
    private NativeLong lUserID;//用户句柄
    public NativeLong channel = new NativeLong(1);;

    public void getHkwsCamera(Camera camera, int passage) {

        NativeLong canLong = new NativeLong(passage);

        if (!hcNetSDK.NET_DVR_Init()) {
            logger.warn("hksdk(预览)-海康sdk初始化失败!");
            return;
        }
        //设备信息
        HCNetSDK.NET_DVR_DEVICEINFO_V30 deviceinfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();

        //注册设备
        lUserID = hcNetSDK.NET_DVR_Login_V30(camera.getIp(), camera.getPort(), camera.getUsername(), camera.getPassword(), deviceinfo);

        if (lUserID.intValue() < 0) {
            logger.warn("hkvs(预览)-HK设备注册失败,错误码:" + hcNetSDK.NET_DVR_GetLastError());
            return;
        }
        //获取设备工作状态进行调试
        HCNetSDK.NET_DVR_WORKSTATE_V30 dvr_workstate_v30 = new HCNetSDK.NET_DVR_WORKSTATE_V30();

        if (!hcNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, dvr_workstate_v30)) {
            logger.info("hkvs(预览)-设备状态：失败");
        }

        //启动实时预览功能  创建clientInfo对象赋值预览参数

        HCNetSDK.NET_DVR_CLIENTINFO clientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();

        clientInfo.lChannel=channel;  //设置通道号
        clientInfo.lLinkMode=new NativeLong(0);  //TCP取流
        clientInfo.sMultiCastIP=null;                   //不启动多播模式

        //创建窗口句柄
        clientInfo.hPlayWnd=null;
        ClientDemo clientDemo = new ClientDemo();
        ClientDemo.FRealDataCallBack fRealDataCallBack = (ClientDemo.FRealDataCallBack) ClientDemo.FRealDataCallBack;

        //开启实时预览
        NativeLong key = hcNetSDK.NET_DVR_RealPlay_V30(lUserID, clientInfo,fRealDataCallBack, null,true);

        //判断是否预览成功
        if(key.intValue()==-1){
            System.out.println("预览失败   错误代码为:  "+hcNetSDK.NET_DVR_GetLastError());
            hcNetSDK.NET_DVR_Logout(lUserID);
            hcNetSDK.NET_DVR_Cleanup();
        }

        // 如果没有文件则创建 保存在 D://realData/result.mp4 中
//        File file = new File("D:\\HkvsDir");
//        if (!file.exists()) {
//            file.mkdir();
//        }

        //预览成功后 调用接口使视频资源保存到文件中
        if(!hcNetSDK.NET_DVR_SaveRealData(key, "D:\\HkvsDir\\"+camera.getIp()+"-"+System.currentTimeMillis()+"-abnormal.mp4")){
            System.out.println("保存到文件失败 错误码为:  "+hcNetSDK.NET_DVR_GetLastError());
            hcNetSDK.NET_DVR_StopRealPlay(key);
            hcNetSDK.NET_DVR_Logout(lUserID);
            hcNetSDK.NET_DVR_Cleanup();
        }
        try {
            logger.info("当前正在录像中..........");
            Thread.sleep(1000*600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //上面设置的睡眠时间可以当做拍摄时长来使用,然后调用结束预览,注销用户,释放资源就可以了
        hcNetSDK.NET_DVR_StopRealPlay(key);
        hcNetSDK.NET_DVR_Logout(lUserID);
        hcNetSDK.NET_DVR_Cleanup();
        // 程序运行完毕退出阻塞状态
        System.exit(0);
    }

    public static void main(String[] args) {
        Camera camera = new Camera();
        camera.setIp("192.168.1.64");
        camera.setPort((short) 8000);
        camera.setUsername("admin");
        camera.setPassword("zscj123a");
        camera.setDefaultID(0);
        PreviewUtil preview = new PreviewUtil();
        preview.getHkwsCamera(camera,1);
    }

}
