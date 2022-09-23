import cv2
import  numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np
import pandas as pd
from sklearn import svm
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
    #显示sobel 检测后的图片
    # cv2.imshow('absY', Scale_absX)
    # cv2.waitKey()
    return Scale_absX


def inputImage(address,address1):
	return np.array(Image.open(address)),np.array(Image.open(address1));

m=3
i=1
xlabel=[]
while i<m:
    a,b=inputImage("%d.bmp"%(i),"%d.bmp"%(i+1))
    xlabel.append(analyseTwoImage(checkSobel(a),checkSobel(b),shd))
    i+=1







print(xlabel)

x = range(1,m)#数据在x轴的位置，是一个可迭代对象
# plt.plot(x,xlabel)#传入xy轴
plt.bar(x,xlabel)
plt.xlabel("num")
plt.ylabel("SCORE")
plt.show()#输出
