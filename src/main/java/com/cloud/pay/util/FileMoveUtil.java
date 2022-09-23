package com.cloud.pay.util;

import java.io.File;

/**
 * @Author 宁志洋
 * @create 2020/10/18 14:53
 */
public class FileMoveUtil {
    public static void fileMove(String jpgName){
        //    #源文件路径
        File startFile = new File("E:\\HkvsDir\\normal\\"+jpgName);

        //#目的目录路径
        File endDirection = new File("E:\\HkvsDir\\abnormal");
        //#如果目的目录路径不存在，则进行创建
        if(!endDirection.exists())

        {
            endDirection.mkdirs();
        }

        //#目的文件路径=目的目录路径+源文件名称
        File endFile = new File(endDirection + File.separator + startFile.getName());

        try
        {
        //	#调用File类的核心方法renameTo
            if (startFile.renameTo(endFile)) {
                System.out.println("文件移动成功！目标路径：{" + endFile.getAbsolutePath() + "}");
            } else {
                System.out.println("文件移动失败！起始路径：{" + startFile.getAbsolutePath() + "}");
            }
        }catch(
                Exception e)

        {
            System.out.println("文件移动出现异常！起始路径：{" + startFile.getAbsolutePath() + "}");
        }
    }

    public static void main(String[] args) {
        fileMove("a.txt");
    }
}
