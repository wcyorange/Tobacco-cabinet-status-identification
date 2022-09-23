import cv2
import  numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np
import pandas as pd
from sklearn import svm

SHADOW_ADDRESS2 = "out2.jpg"

def inputImage(address):
	return np.array(Image.open(address));

def sobel(img):
    x=cv2.Sobel(img,cv2.CV_16S,1,0)
    y=cv2.Sobel(img,cv2.CV_16S,0,1)
    Scale_absX=cv2.convertScaleAbs(x,y)
    # cv2.imshow('absY', Scale_absX)
    # cv2.waitKey()
    return Scale_absX

def analyseTwoImage(im1,im2,shd2):
    maxx = (im1>im2)*im1 + (im1<im2)*im2;
    minn = (im1>im2)*im2 + (im1<im2)*im1;
    return (maxx-minn)
    # score = np.sum((maxx-minn) & shd2);
    # if score != 0:
    #     score = score / np.sum(((maxx-minn) & shd2)>0);

# #显示原视频
# def getVideo():
# 	shd2=inputImage(SHADOW_ADDRESS2)
# 	cap = cv2.VideoCapture('video1.mp4')
# 	img_before = []
# 	img_now = []
# 	while(cap.isOpened()):
# 		c , frame = cap.read()
# 		img_before = img_now
# 		img_now = frame
# 		if len(img_before)==0:continue
# 		# answer = analyseTwoImage(sobel(img_before),sobel(img_now),shd2).astype(np.uint8)
# 		# print(answer)
# 		frame = cv2.resize(frame,(256*5,144*5))
# 		cv2.imshow('image', frame)
# 		k = cv2.waitKey(20)
# 		#q键退出
# 		if (k & 0xff == ord('q')):
# 			break
# 	cap.release()
# 	cv2.destroyAllWindows()

#显示边缘检测的视频
def getVideo():
	List = []
	shd2=inputImage(SHADOW_ADDRESS2)
	cap = cv2.VideoCapture('video1.mp4')
	img_before = []
	img_now = []
	while(cap.isOpened()):
		c , frame = cap.read()
		img_before = img_now
		img_now = frame
		if len(img_before)==0:continue
		answer = analyseTwoImage(sobel(img_before),sobel(img_now),shd2).astype(np.uint8)
		List.append(np.sum(answer))
		# print(answer)
		frame = cv2.resize(answer,(256*5,144*5))
		cv2.imshow('image', frame)
		k = cv2.waitKey(10)
		# q键退出
		if (k & 0xff == ord('q')):
			break


	cap.release()
	cv2.destroyAllWindows()
	return List

list = getVideo()

print(list)
plt.plot(range(len(list)),list)#传入xy轴
plt.show()#输出