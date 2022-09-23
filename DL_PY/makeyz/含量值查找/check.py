#用来判断图像在空满情况下不同卡模板的值
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np
import os
import cv2
from PIL import Image
import os
def sobel(img):
    x = cv2.Sobel(img, cv2.CV_16S, 1, 0)
    # y = cv2.Sobel(img, cv2.CV_16S, 0, 1)
    # cv2.convertScaleAbs(src[, dst[, alpha[, beta]]])
    # 可选参数alpha是伸缩系数，beta是加到结果上的一个值，结果返回uint类型的图像
    Scale_absX = cv2.convertScaleAbs(x)  # convert 转换  scale 缩放
    # Scale_absY = cv2.convertScaleAbs(y)
    # result = cv2.addWeighted(Scale_absX, 1, Scale_absY, 1, 0)
    # print(len(result))
    return Scale_absX

def checkscore(img,shd1):
    # print(address, "     ", np.sum(sobel(img & shd1)))
    Scale_absX = sobel(img)
    score = np.sum(Scale_absX & shd1)
    score2 = np.sum(Scale_absX & shd3)

    print("     ", score,score2)
    return score,score2



def inputImage(address):
    return np.array(Image.open(address));
def getVideo(path,c):
    list = []
    list1=[]
    list2=[]
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
            # answer = analyseTwoImage(checkSobel(img_before),checkSobel(img_now),shd)#sobel 算子
            # gray = cv2.cvtColor(img_before, cv2.COLOR_BGR2GRAY)
            # gray1 = cv2.cvtColor(img_now, cv2.COLOR_BGR2GRAY)

            # answer = analyseTwoImage(img_before,img_now,shd)#sobel 算子
            answer=checkscore(img_now,shd)
            answer1=checkscore(img_now,shd3)
            # answer = analyseTwoImage(checkcanny(img_before),checkcanny(img_now),shd)#canny 算子
            list.append(answer)
            list1.append(answer1)
            list2.append(i)
            print(answer)
            print(answer1)
            print("训练第%d" % (i/25))
            print(i)
        cv2.imshow('image', frame)
        cv2.resizeWindow('law', 512, 288)
        k = cv2.waitKey(10)
        if (k & 0xff == ord('q')):
            break
        if i/25>60:
            break
    cap.release()
    cv2.destroyAllWindows()
    print(list)
    print(list1)
    print(list2)
    return list,list1
def checkscore(img,shd1):
    # print(address, "     ", np.sum(sobel(img & shd1)))
    Scale_absX = sobel(img)
    score = np.sum(Scale_absX & shd1)

    print("     ",score)
    return score

def drawOutLine1():
	xArray = [1439,600,600,1439];
	yArray = [500,1200,1300,2000];
	plt.plot(yArray,xArray);
def inputImage(address):
    print(address)
    return np.array(Image.open(address))
def checkkkkk(img):
    SHADOW_ADDRESS1 = '.\\out1.jpg'
    SHADOW_ADDRESS3 = '.\\out3.jpg'
    shd = np.array(Image.open(SHADOW_ADDRESS1))
    shd3 = np.array(Image.open(SHADOW_ADDRESS3))
    # print(address, "     ", np.sum(sobel(img & shd1)))
    Scale_absX = sobel(img)
    score = np.sum(Scale_absX & shd)
    score2 = np.sum(Scale_absX & shd3)
    print("图像", "     ", score,score2)
    return score,score2

def readall():
    list=[]
    list1=[]
    list2=[]
    # path = "E:\\pycharm workspace\\2020-08 青岛卷烟厂\\代码\\含量值查找"
    # print(all_images)
    shd1 = inputImage('.\\out1.jpg')
    fileList = os.listdir()
    for i in fileList:
        file_name_group = i.split('.')
        if file_name_group[len(file_name_group)-1]=='py':
            continue;
        if file_name_group[len(file_name_group)-2]=='out1':
            continue;
        if file_name_group[len(file_name_group)-2] == 'out3':
                continue;
        img = inputImage(i)
        score,score2 = checkkkkk(img)
        list.append(score)
        list1.append(score2)
        list2.append(i)
        plt.subplot(2,1,1)
        plt.imshow(img)
        drawOutLine1()
        plt.subplot(2,1,2)
        plt.imshow(img & shd1)
        plt.show()
    print(list2)
    print(list)
    print(min(list))
    print(max(list))
    print(list1)
    print(min(list1))
    print(max(list1))
readall()












