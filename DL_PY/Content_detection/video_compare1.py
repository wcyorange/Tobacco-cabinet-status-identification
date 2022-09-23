import cv2
import  numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np
import pandas as pd
from sklearn import svm
import numpy as np
import cv2
from datetime import datetime

shd=np.array(Image.open("out2.jpg"))
def analyseTwoImage(im1,im2,shd2):
    t1 = datetime.now()
    maxx=(im1>im2)*im1+(im1<im2)*im2
    minn=(im1>im2)*im2+(im1<im2)*im1
    score = np.sum((maxx-minn)&shd2)
    if score!=0:
        score = score/np.sum((maxx-minn)&shd2>0)
    # return (im1&im2&shd2)
    t2 = datetime.now()
    print("Time cost = ", (t2 - t1))
    print("SUCCEED !!!")
    # return np,sum(((im1+1)&(im2+1))&shd2)
    return score
def checkSobel(img):
    # grey = cv2.createimg(cv2.GetSize(img), cv2.IPL_DEPTH_8U, 1)
    # cv2.cvtColor(img, grey, cv2.CV_BGR2GRAY)
    # img4 = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    # grey = cv2.imread(img, cv2.IMREAD_GRAYSCALE)
    x=cv2.Sobel(img,cv2.CV_16S,1,0)
    y=cv2.Sobel(img,cv2.CV_16S,0,1)
    Scale_absX=cv2.convertScaleAbs(x,y)
    # cv2.imshow('absY', Scale_absX)
    # cv2.waitKey()
    return Scale_absX
def laplacian(img):#拉普拉斯算法
    laplacian = cv2.Laplacian(img, cv2.CV_16S, ksize=3)
    dst = cv2.convertScaleAbs(laplacian)
    # cv2.imshow('laplacian', dst)
    return dst
def scharr(img):
    x = cv2.Sobel(img, cv2.CV_16S, 1, 0, ksize=-1)
    # y = cv2.Sobel(img, cv2.CV_16S, 0, 1, ksize=-1)
    # ksize=-1 Scharr算子
    # cv2.convertScaleAbs(src[, dst[, alpha[, beta]]])
    # 可选参数alpha是伸缩系数，beta是加到结果上的一个值，结果返回uint类型的图像
    Scharr_absX = cv2.convertScaleAbs(x)  # convert 转换  scale 缩放
    return Scharr_absX
    # Scharr_absY = cv2.convertScaleAbs(y)
    # result = cv2.addWeighted(Scharr_absX, 0.5, Scharr_absY, 0.5, 0)
    # cv2.imshow('img', img)
    # cv2.imshow('Scharr_absX', Scharr_absX)
    # cv2.imshow('Scharr_absY', Scharr_absY)
    # cv2.imshow('Scharr_result', result)
def checkcanny(img):
    # grey = cv2.createimg(cv2.GetSize(img), cv2.IPL_DEPTH_8U, 1)
    # cv2.cvtColor(img, grey, cv2.CV_BGR2GRAY)
    # img4 = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    # grey = cv2.imread(img, cv2.IMREAD_GRAYSCALE)
    imgray = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    blur = cv2.GaussianBlur(imgray, (3, 3), 0)  # 用高斯滤波处理原图像降噪
    canny = cv2.Canny(blur, 50, 150)  # 50是最小阈值,150是最大阈值
    # cv2.imshow('canny', canny)
    # cv2.imshow('absY', Scale_absX)
    # cv2.waitKey()
    return canny
def checkerzhi(img):
    imgray = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    thresh = 120
    ret, binary = cv2.threshold(imgray, thresh, 255, cv2.THRESH_BINARY)  # 输入灰度图，输出二值图
    cv2.imshow('binary', binary)
    binary1 = cv2.bitwise_not(binary)  # 取反
    cv2.imshow('binary1', binary1)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# def checkSobel(img):
# def inputImage(address,address1):
# 	return np.array(Image.open(address)),np.array(Image.open(address1))
# m=5
# i=1
# xlabel=[]
#
# while i<m:
#     a,b=inputImage(".\\newphoto\\add%d.bmp"%(i),".\\newphoto\\add%d.bmp"%(i+1))
#     xlabel.append(analyseTwoImage(checkSobel(a),checkSobel(b),shd))
#     i+=1
# # 读取视频文件
# # def readvideo(filename):
# #     cap = cv2.VideoCapture(filename)
# #     L = []
# #     # 或者电影每秒的帧数
# #     fps = cap.get(cv2.CAP_PROP_FPS)
# #     c = 0
# #     while (cap.isOpened()):
# #         success,frame = cap.read()
# #         # 视频显示
# #         L.append(checkSobel(frame))
# #         cv2.waitKey(200)
# #         cv2.imshow('law', frame)
# #         # cv2.imwrite('image/' + str(c) + '.jpg', frame)  # 存储为图像
# #         c+=1
# #         # 设置窗口
# #         # cv2.resizeWindow('law', 512,288)
# #        # 判断退出条件
# #         if cv2.waitKey(int(1000//fps)) ==ord('q'):
# #             break
# #  # 清除缓存退出
# #     cv2.destroyAllWindows()
# #     return L
# # def video_to_frames(filename):
# #     """
# #     输入：path(视频文件的路径)
# #     """
# #     videoCapture = cv2.VideoCapture(filename)
# #     L = []
# #     L1=[]
# #     # 或者电影每秒的帧数
# #     fps = videoCapture.get(cv2.CAP_PROP_FPS)
# #     # VideoCapture视频读取类
# #     # 帧率
# #     # 总帧数
# #     frames = videoCapture.get(cv2.CAP_PROP_FRAME_COUNT)
# #     print("fps=", int(fps), "frames=", int(frames))
# #     L1 = []
# #     L2 = []
# #     while videoCapture.isOpened():
# #         success,frame = videoCapture.read()
# #         # 视频显示
# #         L1=L2
# #         L2=frame
# #         cv2.imshow('law', frame)
# #         print(type(frame))
# #         L1.append(frame[i].tolist())
# #         print("添加%d"%(i))
# #         if cv2.waitKey(int(1000//fps)) ==ord('q'):break
# #     a=analyseTwoImage(checkSobel(L1[0]), checkSobel(L1[1]), shd)
# #     L.append(a)
# #         print(L)
# #         frame.clear()
# #         L1.clear()
# #     # for i in range(1,len(frame),2):
# #     #     analyseTwoImage(checkSobel(L[i]), checkSobel(L[i + 1], shd))
# #     #     L1.append(analyseTwoImage(checkSobel(L[i]),checkSobel(L[i+1],shd)))
# #
# #     return L1
#
#
# # print(video_to_frames('.//datasets/01.mp4'))
# def inputImage(address):
#     return np.array(Image.open(address))
#对摄像头图像进行二值化处理
# def binaryThreshold(Image, threshold):
#     grey = cv2.CreateImage(cv2.GetSize(img),cv2.IPL_DEPTH_8U, 1)
#     out = cv2.CreateImage(cv2.GetSize(img),cv2.IPL_DEPTH_8U, 1)
#
#     cv2.CvtColor(Image,grey,cv2.CV_BGR2GRAY)
#     cv2.Threshold(grey, out , threshold , 255 ,cv2.CV_THRESH_BINARY)
def getVideo1(path):
    list = []
    cap = cv2.VideoCapture(path)
    img_before = []
    img_now = []
    amount = 0
    i=0;
    while(cap.isOpened()):
        c , frame = cap.read()
        img_before = img_now
        img_now = frame
        if len(img_before)==0:continue
        answer = analyseTwoImage(checkSobel(img_before),checkSobel(img_now),shd)#sobel 算子
        # answer = analyseTwoImage(checkcanny(img_before),checkcanny(img_now),shd)#canny 算子
        list.append(answer)
        # amount = amount+1
        # if amount==3*60*24:
        #     break
        print(answer)
        cv2.imshow('image', frame)
        cv2.resizeWindow('law', 512, 288)
        k = cv2.waitKey(10)
        print("训练第%d"%(i))
        i+=1

        if (k & 0xff == ord('q')):
            break
    cap.release()
    cv2.destroyAllWindows()
    print(list)
    print(type(list))
    print(len(list))
    return list
def getVideo(path,c):
    list = []
    cap = cv2.VideoCapture(path)
    img_before = []
    img_now = []
    fiwu=[]
    amount = 0
    i=-1;
    while(cap.isOpened()):
        c , frame = cap.read()
        fiwu=frame
        i=i+1
        print(len(img_before),len(img_now))
        if i%25==0:#训练帧数可以进行调节，调q节此处进行操作每隔多少帧进行一次算子操作
            img_before = img_now
            img_now = frame
            if len(img_before) == 0: continue
            answer = analyseTwoImage(checkSobel(img_before),checkSobel(img_now),shd)#sobel 算子
            # answer = analyseTwoImage(checkcanny(img_before),checkcanny(img_now),shd)#canny 算子
            list.append(answer)
            print(answer)
            print("训练第%d" % (i/25))
            print(i)
        cv2.imshow('image', frame)
        cv2.resizeWindow('law', 512, 288)
        k = cv2.waitKey(10)
        if (k & 0xff == ord('q')):
            break
        if i/25>40:
            break
    cap.release()
    cv2.destroyAllWindows()
    print(list)
    print(type(list))
    print(len(list))
    return list
L=getVideo('.//datasets/yundong01.mp4',5)#动态图
L1=getVideo('.//datasets/half.mp4',5)#静态图

# t1 = datetime.now()
#     video_to_frames("D:\\Pyproject\\video\\video01.mp4")
#     t2 = datetime.now()
#     print("Time cost = ", (t2 - t1))
#     print("SUCCEED !!!")


#min->   7.0》7.0说明加料开始   max-> 4.5说明
# #------------------------------------------------
# a=readvideo('.//datasets/01.mp4')
# L=[]
# for i in range(0,len(a),2):
#     L.append(analyseTwoImage(a[i],a[i+1],shd))
#
# print(readvideo('.//datasets/01.mp4'))
# print(xlabel)
# print(L)
#------------------------------------------
print("L：min=,max=",min(L),max(L))
print("L1：min=,max=",min(L1),max(L1))
x = range(1,len(L)+1)#数据在x轴的位置，是一个可迭代对象
plt.plot(x,L,label="move")#传入xy轴
plt.plot(x,L1,label="quit")#传入xy轴
# plt.bar(x,xlabel)
plt.xlabel("num")
plt.ylabel("SCORE")
plt.legend()
plt.grid(alpha=0.3)
plt.show()#输出

#-------------------------------------------------
