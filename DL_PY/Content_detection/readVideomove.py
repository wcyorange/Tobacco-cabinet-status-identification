import numpy as np
import cv2
import cv2 as cv
import numpy as np
import cv2
import matplotlib.pyplot as plt
from PIL import Image
import pandas as pd
from sklearn import svm
import numpy as np
import cv2
from datetime import datetime
i=350
a2=".\\newphotoStatic\\pic_"+str(i)+".jpg"
a3=".\\newphotoStatic\\pic_"+str(i+5)+".jpg"
a4=".\\newphotoStatic\\pic_"+str(i+10)+".jpg"
a5=".\\newphotoStatic\\pic_"+str(i+15)+".jpg"
a6=".\\newphotoStatic\\pic_"+str(i+20)+".jpg"
shd=np.array(Image.open("out2.jpg"))
p1=np.array(Image.open(a2))
p2=np.array(Image.open(a3))
p3=np.array(Image.open(a4))
p4=np.array(Image.open(a5))
p5=np.array(Image.open(a6))

L=[p1,p2,p3,p4,p5]

# 进行一次图像操作的时间
def readvideo():
    cap = cv2.VideoCapture('.//datasets/01.mp4')
    # 或者电影每秒的帧数
    fps = cap.get(cv2.CAP_PROP_FPS)
    # 判断视频是否一直打开
    while (cap.isOpened()):
        success,frame = cap.read()
        # 视频显示
        print(frame)

        print(cap)
        cv2.imshow('law', frame)
        # 设置窗口
        # cv2.resizeWindow('law', 512,288)
       # 判断退出条件
        if cv2.waitKey(int(1000//fps)) ==ord('q'):
            break
 # 清除缓存退出
    cv2.destroyAllWindows()
    return frame,cap

def checkSobel(img):
    x=cv2.Sobel(img,cv2.CV_16S,1,0)
    y=cv2.Sobel(img,cv2.CV_16S,0,1)
    Scale_absX=cv2.convertScaleAbs(x,y)
    # cv2.imshow('absY', Scale_absX)
    # cv2.waitKey()
    return Scale_absX
# def checkAdd(img,shd2):
#
#     t1 = datetime.now()
#     score= [];
#     for i in range(0,4):
#         score.append(analyseTwoImage(checkSobel(img[i]),checkSobel(img[i+1]),shd2))
#     t2 = datetime.now()
#     print("Time cost = ", (t2 - t1),"秒")
#     print("SUCCEED !!!")
#     return score
def checkAdd(img,shd2):

    t1 = datetime.now()
    score=analyseTwoImage(checkSobel(img[0]),checkSobel(img[4]),shd2)
    t2 = datetime.now()
    print("Time cost = ", (t2 - t1),"秒")
    print("SUCCEED !!!")
    return score
def analyseTwoImage(im1,im2,shd2):
    maxx=(im1>im2)*im1+(im1<im2)*im2
    minn=(im1>im2)*im2+(im1<im2)*im1
    score = np.sum((maxx-minn)&shd2)
    if score!=0:
        score = score/np.sum((maxx-minn)&shd2>0)
    # return (im1&im2&shd2)

    # return np,sum(((im1+1)&(im2+1))&shd2)
    return score
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
        if i%5==0:#训练帧数可以进行调节，调q节此处进行操作每隔多少帧进行一次算子操作
            img_before = img_now
            img_now = frame
            if len(img_before) == 0: continue
            answer = analyseTwoImage(checkSobel(img_before),checkSobel(img_now),shd)#sobel 算子
            # answer = analyseTwoImage(checkcanny(img_before),checkcanny(img_now),shd)#canny 算子
            list.append(answer)
            print(answer)
            print("训练第%d" % (i/5))
            print(i)
        cv2.imshow('image', frame)
        cv2.resizeWindow('law', 512, 288)
        k = cv2.waitKey(10)
        if (k & 0xff == ord('q')):
            break
        if i/5>100:
            break
    cap.release()
    cv2.destroyAllWindows()
    print(list)
    print(type(list))
    print(len(list))
    return list



def checkADDrate(L,a):#传入score列表和列表的应该的状态，返回成功的百分比如加料状态为1
    num=0
    num1=0
    '''加料状态'''
    for i in L:
        if i >= 8.2:
            num+=1
            print("加料状态")
        else:
            num1+=1
            print("非加料状态")
    if a==1:
        rate = num/len(L)
    elif a==2:
        rate = num1/len(L)
    return rate*100
# list = checkAdd(L, shd)
#     if list >= 8.2:
#         print("加料状态")
#     else:
#         print("非加料状态")
L=getVideo('.//datasets/yundong01.mp4',5)#动态图
print(str(checkADDrate(L,1))+"%")
# '''加料状态'''
    # current_add = checkAdd(imgGroup,shd2)
    # if current_add>8.2: return 2,""