from PIL import Image
import flask
import numpy as np
import cv2
import json
from flask import request

def inputImage(address):
	return np.array(Image.open(address));

#yolo检测
def checkYolo(img,
                label_path='./cfg/yolov3.names',
                config_path='./cfg/yolov3.cfg',
                weights_path='./cfg/yolov3.weights',
                confidence_thre=0.5,
                nms_thre=0.3,
                jpg_quality=80):
    LABELS = open(label_path).read().strip().split("\n")
    (H, W) = img.shape[:2]
    # #添加的
    # # 判断是灰度图还是彩图
    # r = img.reshape(-1,3)[:,0]
    # g = img.reshape(-1,3)[:,1]
    # if np.sum(r-g)<100000000: #灰度图
    #     weights_path= w1
    #     return weights_path
    # else:  #彩图
    #     weights_path = w2
    #     return weights_path
    #从磁盘加载YOLO文件后，并利用OpenCV中的cv2.dnn.readNetFromDarknet函数从中读取网络文件及权重参数，此函数需要两个参数configPath 和 weightsPath
    net = cv2.dnn.readNetFromDarknet(config_path, weights_path)
    #对图像进行预处理，包括减均值，比例缩放，裁剪，交换通道等，返回一个4通道的blob(blob可以简单理解为一个N维的数组，用于神经网络的输入)
    blob = cv2.dnn.blobFromImage(img, 1 / 255.0, (416, 416), swapRB=True, crop=False)
    net.setInput(blob)
    layerOutputs = net.forward([net.getLayerNames()[i[0] - 1] for i in net.getUnconnectedOutLayers()])
    boxes = []
    confidences = []
    classIDs = []
    for output in layerOutputs:
        for detection in output:
            scores = detection[5:]
            classID = np.argmax(scores)
            confidence = scores[classID]
            if confidence > confidence_thre:
                (centerX, centerY, width, height) = (detection[0:4] * np.array([W, H, W, H])).astype("int")
                boxes.append([int(centerX - (width / 2)), int(centerY - (height / 2)), int(width), int(height)])
                confidences.append(float(confidence))
                classIDs.append(classID)

    #0 -> saoba ; 1 -> tuoba; 2->human
    classIDs=np.unique(classIDs)
    num=[str(i) for i in classIDs]
    str1 = ","
    str1 = str1.join(num)
    str1 = str1.replace('0', 'saoba')
    str1 = str1.replace('1', 'tuoba')
    str1 = str1.replace('2', 'human')
    if len(str1) > 0:
        return str1
    # else:
    #     return "No foreign matter"

    # if len(classIDs) > 0:
    #     return classIDs
        # for num in classIDs:
        #     # return num
        #     if num==0:
        #         x1='saoba'
        #     else:
        #         x1=None
        #         # return 'saoba'
        #     if num==1:
        #         x2='tuoba'
        #     else:
        #         x2=None
        #         # return 'tuoba'
        #     # if num==2:
        #     #     x3='human'
        #     # else:
        #     #     x3=None
        #     #     # return 'human'
        # return x1,x2


    # if len(classIDs)>0:
    #     count1=count2=count3=0
    #     for i in len(classIDs):
    #         if classID==0:
    #             count1=count1+1
    #         if classID==1:
    #             count2=count2+1
    #         if classID==2:
    #             count3=count3+1
    #     return count1,count2,count3

    # if len(classIDs)>0:
        # if classID==0:
        #     return "saoba"
        # if classID==1:
        #     return "tuoba"
        # if classID==2:
        #     return  "human"


# address1="E:\pycharmnote\python\yanchang\all_code\4.bmp"
img1 = inputImage("4.bmp")
l1=checkYolo(img1)
print(l1)