package com.cloud.pay.util;

import com.cloud.pay.ClientDemo.HCNetSDK;
import com.cloud.pay.entity.Camera;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;



/**
 * @author 宁志洋
 */

public class SnapShot {

    private static HCNetSDK hcNetSDK = HCNetSDK.INSTANCE;
    private Logger logger = LoggerFactory.getLogger(SnapShot.class);
    private NativeLong lUserID;//用户句柄

    /**
     * 抓拍图片
     */

    public void getHkwsPic(Camera camera, int passage, String imgPath){

        NativeLong canLong = new NativeLong(passage);

        if(!hcNetSDK.NET_DVR_Init()) {
            logger.warn("hksdk(抓图)-海康sdk初始化失败!");
            return ;
        }
        //设备信息
        HCNetSDK.NET_DVR_DEVICEINFO_V30 deviceinfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();

        //注册设备
        lUserID = hcNetSDK.NET_DVR_Login_V30(camera.getIp(),camera.getPort(),camera.getUsername(),camera.getPassword(),deviceinfo);

        if (lUserID.intValue() < 0) {
            logger.warn(camera.getInfo()+"-----hkvs(抓图)-HK设备注册失败,错误码:"+hcNetSDK.NET_DVR_GetLastError());
            return;
        }
        //获取设备工作状态进行调试
        HCNetSDK.NET_DVR_WORKSTATE_V30 dvr_workstate_v30 = new HCNetSDK.NET_DVR_WORKSTATE_V30();

        if(!hcNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID,dvr_workstate_v30)){
            logger.info("hkvs(抓图)-设备状态：失败");
        }

        //图片质量
        HCNetSDK.NET_DVR_JPEGPARA jpeg = new HCNetSDK.NET_DVR_JPEGPARA();
        //设置图片分辨率
        jpeg.wPicSize=2;
        //设置图片质量
        jpeg.wPicQuality=0;
        IntByReference a = new IntByReference();
        //设置图片大小
        ByteBuffer jpegBuffer = ByteBuffer.allocate(1024 * 1024);
        File file = new File(imgPath);
        // 抓图到内存，单帧数据捕获并保存成JPEG存放在指定的内存空间中
        //需要加入通道
        boolean is = hcNetSDK.NET_DVR_CaptureJPEGPicture_NEW(lUserID, canLong, jpeg, jpegBuffer, 1024 * 1024, a);
        if(is) {

            logger.info("hkvs(抓图)-结果状态值(0表示成功):"+hcNetSDK.NET_DVR_GetLastError());
            //存储到本地
            BufferedOutputStream outputStream = null;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(jpegBuffer.array(),0,a.getValue());
                outputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(outputStream!=null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            logger.info("hkvs(抓图)-抓取失败,错误码:"+hcNetSDK.NET_DVR_GetLastError());
        }
        //退出登录
        hcNetSDK.NET_DVR_Logout(lUserID);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Camera camera = new Camera();
        camera.setIp("192.168.1.64");
        camera.setPort((short) 8000);
        camera.setUsername("admin");
        camera.setPassword("zscj123a");
        camera.setDefaultID(0);
        SnapShot snapShot = new SnapShot();
        for(int i=0;i<5;i++){
            String jpgName = "E:\\HkvsDir\\HkvsPic\\" + System.currentTimeMillis() + "_" + i + ".jpg";
            snapShot.getHkwsPic(camera, 1, jpgName);
        }
    }
}