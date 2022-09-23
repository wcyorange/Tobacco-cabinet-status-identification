import cv2
import  numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np
import pandas as pd
from sklearn import svm
import numpy as np
import cv2
shd=np.array(Image.open("out1.jpg"))

def analyseTwoImage(im1,im2,shd2):
    maxx=(im1>im2)*im1+(im1<im2)*im2
    minn=(im1>im2)*im2+(im1<im2)*im1
    score = np.sum((maxx-minn)&shd2)
    if score!=0:
        score=score/np.sum((maxx-minn)&shd2>0)
    return score
def checkSobel(img):
    x=cv2.Sobel(img,cv2.CV_16S,1,0)
    y=cv2.Sobel(img,cv2.CV_16S,0,1)
    Scale_absX=cv2.convertScaleAbs(x,y)
    # cv2.imshow('absY', Scale_absX)
    # cv2.waitKey()
    return Scale_absX


# def inputImage(address,address1):
# 	return np.array(Image.open(address)),np.array(Image.open(address1));
# m=5
# i=1
# xlabel=[]
#
# while i<m:
#     a,b=inputImage(".\\newphoto\\add%d.bmp"%(i),".\\newphoto\\add%d.bmp"%(i+1))
#     xlabel.append(analyseTwoImage(checkSobel(a),checkSobel(b),shd))
#     i+=1


# 读取视频文件
def readvideo(filename):
    cap = cv2.VideoCapture(filename)
    L = []
    # 或者电影每秒的帧数
    fps = cap.get(cv2.CAP_PROP_FPS)
    c = 0
    while (cap.isOpened()):
        success,frame = cap.read()
        # 视频显示
        L.append(checkSobel(frame))
        cv2.waitKey(200)
        cv2.imshow('law', frame)
        # cv2.imwrite('image/' + str(c) + '.jpg', frame)  # 存储为图像
        c+=1
        # 设置窗口
        # cv2.resizeWindow('law', 512,288)
       # 判断退出条件
        if cv2.waitKey(int(1000//fps)) ==ord('q'):
            break
 # 清除缓存退出
    cv2.destroyAllWindows()
    return L
# def video_to_frames(filename):
#     """
#     输入：path(视频文件的路径)
#     """
#     videoCapture = cv2.VideoCapture(filename)
#     L = []
#     L1=[]
#     # 或者电影每秒的帧数
#     fps = videoCapture.get(cv2.CAP_PROP_FPS)
#     # VideoCapture视频读取类
#     # 帧率
#     # 总帧数
#     frames = videoCapture.get(cv2.CAP_PROP_FRAME_COUNT)
#     print("fps=", int(fps), "frames=", int(frames))
#     L1 = []
#     L2 = []
#     while videoCapture.isOpened():
#         success,frame = videoCapture.read()
#         # 视频显示
#         L1=L2
#         L2=frame
#         cv2.imshow('law', frame)
#         print(type(frame))
#         L1.append(frame[i].tolist())
#         print("添加%d"%(i))
#         if cv2.waitKey(int(1000//fps)) ==ord('q'):break
#     a=analyseTwoImage(checkSobel(L1[0]), checkSobel(L1[1]), shd)
#     L.append(a)
#         print(L)
#         frame.clear()
#         L1.clear()
#     # for i in range(1,len(frame),2):
#     #     analyseTwoImage(checkSobel(L[i]), checkSobel(L[i + 1], shd))
#     #     L1.append(analyseTwoImage(checkSobel(L[i]),checkSobel(L[i+1],shd)))
#
#     return L1


# print(video_to_frames('.//datasets/01.mp4'))

def getVideo(path):
    list = []
    # shd2=inputImage(shd)
    cap = cv2.VideoCapture(path)
    img_before = []
    img_now = []
    while(cap.isOpened()):
        c , frame = cap.read()
        img_before = img_now
        img_now = frame
        if len(img_before)==0:continue
        answer = analyseTwoImage(checkSobel(img_before),checkSobel(img_now),shd).astype(np.float8)
        list.append(answer)
        print(answer)
        cv2.imshow('image', frame)
        k = cv2.waitKey(10)
        #q键退出
        if (k & 0xff == ord('q')):
            break
    cap.release()
    cv2.destroyAllWindows()
    print(list)
    print(type(list))
    print(len(list))
    return list

L=getVideo('.//Content_detection/video1.mp4')





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
x = range(1,len(L)+1)#数据在x轴的位置，是一个可迭代对象
plt.plot(x,L)#传入xy轴
# plt.bar(x,xlabel)
plt.xlabel("num")
plt.ylabel("SCORE")
plt.show()#输出

#-------------------------------------------------
