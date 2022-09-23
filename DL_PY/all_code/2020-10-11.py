from PIL import Image
import flask
import numpy as np
import cv2
import json
from flask import request
from datetime import datetime
# import request/
SHADOW_ADDRESS1 = "test_imgs/out1.jpg"
SHADOW_ADDRESS2 = "test_imgs/out2.jpg"
SHADOW_ADDRESS3 = "test_imgs/out3.jpg"

def putJson(id_,code_,status_,message_):
    s = {"id_":"","code_":"","status_":"","message_":""}
    s.update({'id': id_})
    s.update({'code': code_})
    s.update({'status': status_})
    s.update({'message': message_})
    jsonString=json.dumps(s,indent=4)
    return jsonString
def getJson(jsonString,key):
    data = json.loads(jsonString)
    return data[key]
def inputImage(address):
    return np.array(Image.open(address));
def analyseTwoImage(im1,im2,shd2):
    maxx = (im1>im2)*im1 + (im1<im2)*im2;
    minn = (im1>im2)*im2 + (im1<im2)*im1;
    score = np.sum((maxx-minn) & shd2);
    if score != 0:
        score = score / np.sum(((maxx-minn) & shd2)>0);
    return score;
def writeData(id__,score):
    with open(str(id__)+".txt",mode='a') as file_name:
        file_name.write(str(score)+'\n')
def checkSobel(img):
    x=cv2.Sobel(img,cv2.CV_16S,1,0)
    y=cv2.Sobel(img,cv2.CV_16S,0,1)
    Scale_absX=cv2.convertScaleAbs(x,y)
    # cv2.imshow('absY', Scale_absX)
    # cv2.waitKey()
    return Scale_absX
def checkCapacity(img,id__,shd1,shd2):
    maxx_color= [100000000,100000000,58000000,2910000,92000000,67000000,108000000,40000000]#8
    minn_color = [19000000,26000000,41000000,41000000,11000000,22000000,11000000,14000000]#8
    maxx_black = [24000000,53000000,56000000,18000000,18000000,12000000,7800000,21000000]
    minn_black = [10000000,10000000,7800000,17000000,10000000,11000000,7800000,13000000]#5 6
    sminn_color = [5400000,16000000,5400000,7000000,7800000,800000,12000000,85218507]#梯形模板满料最小 8
    sminn_black = [2500000,7200000,6400000,3100000,2000000,2300000,2000000,3500000]#梯形模板满料最小 7
    x = cv2.Sobel(img, cv2.CV_16S, 1, 0)
    y = cv2.Sobel(img, cv2.CV_16S, 0, 1)
    Scale_absX = cv2.convertScaleAbs(x)
    r = img.reshape(-1,3)[:,0]
    g = img.reshape(-1,3)[:,1]
    score = np.sum(Scale_absX & shd1)
    score2 = np.sum(Scale_absX & shd2)
    # writeData(id__,score)
    if np.sum(r-g)<100000000:#判断是否是彩色
        if score>maxx_black[int(id__)-1] and score2>sminn_black[int(id__)-1]: return 1
        elif score<minn_black[int(id__)-1] : return 0
        else:return 2
        # return (score-minn_black[id__-1])/(maxx_black[id__-1]-minn_black[id__-1])
    else:
        if score>maxx_color[int(id__)-1] and score2>sminn_color[int(id__)-1]: return 1
        if score<minn_color[int(id__)-1] : return 0
        else:return 2

        # return (score-minn_color[id__-1])/(maxx_color[id__-1]-minn_color[id__-1])
# def checkAdd(img,shd2):
#     score = 0;
#     for i in range(5):
#         for j in range(i+1,5):
#             score = score + analyseTwoImage(img[i],img[j],shd2);
#     score = score/10;
#     return score
def checkAdd(img,shd2):

    # t1 = datetime.now()
    score=analyseTwoImage(img[0],img[4],shd2)
    # t2 = datetime.now()
    # print("Time cost = ", (t2 - t1),"秒")
    # print("SUCCEED !!!")
    return score
def checkYolo(img,
                label_path='./cfg/yolov3.names',
                config_path='./cfg/yolov3.cfg',
                weights1_path='./cfg/yolov3_d.weights',
                weights2_path='./cfg/yolov3_n.weights',
                confidence_thre=0.5,
                nms_thre=0.3,
                jpg_quality=80):
    LABELS = open(label_path).read().strip().split("\n")
    (H, W) = img.shape[:2]
    r = img.reshape(-1,3)[:,0]
    g = img.reshape(-1,3)[:,1]
    b = img.reshape(-1,3)[:,2]
    if np.sum(r-g)<100000000: #灰度图
        weights_path= weights2_path
        # return weights_p9ath
    else:  #彩图
        r=r * 0.3 + g * 0.59 + b * 0.11
        g=r * 0.3 + g * 0.59 + b * 0.11
        b=r * 0.3 + g * 0.59 + b * 0.11
        weights_path = weights2_path
        # return weights_path
    net = cv2.dnn.readNetFromDarknet(config_path, weights_path)
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
    str1 = str1.replace('0', '扫把')
    str1 = str1.replace('1', '拖把')
    str1 = str1.replace('2', '人')
    if len(str1) > 0:
        return str1
    else:
        return ""
def checkStatus(pathIn,id,shd1,shd2):

    imgGroup = [0,0,0,0,0]
    imgGroup[0] = inputImage(pathIn[0])
    imgGroup[1] = inputImage(pathIn[1])
    imgGroup[2] = inputImage(pathIn[2])
    imgGroup[3] = inputImage(pathIn[3])
    imgGroup[4] = inputImage(pathIn[4])
    '''异物状态'''
    current_abnormal = checkYolo(imgGroup[0]);
    '''加料状态'''
    current_add = checkAdd(imgGroup,shd2)
    '''烟柜状态'''
    current_capacity = checkCapacity(imgGroup[0],id,shd1,shd3)

    '''逻辑判断'''
    status_ID = 0
    status_message = ""
    if len(current_abnormal)>0 :
        status_ID = 10005
        status_message = current_abnormal
    elif current_add>4.6:
        status_ID = 10002
        status_message = "加料状态"
    elif current_capacity==1:
        status_ID = 10003
        status_message = "满料状态"
    elif current_capacity==0:
        status_ID = 10001
        status_message = "空料状态"
    elif current_capacity==2:
        status_ID = 10004
        status_message = "有料状态"
    return status_ID,status_message
server = flask.Flask(__name__)
shd1 = inputImage(SHADOW_ADDRESS1)
shd2 = inputImage(SHADOW_ADDRESS2)
shd3 = inputImage(SHADOW_ADDRESS3)

@server.route('/CheckCamera/Main', methods=['post'])
def Main():
    # print("已收到")
    # request.get()
    # print(jsonString)
    id_ = request.form.get('id')
    code_ = request.form.get('code')
    address_ = request.form.get('address')
    # id_ = getJson(json_string,'id')
    # code_ = getJson(json_string,'code');
    # address_ = getJson(json_string,'address');
    current_status,current_message = checkStatus(address_.split(','),id_,shd1,shd2);
    return putJson(id_,code_,current_status,current_message);

if __name__ == '__main__':
    server.run(debug=True, port=5000)  #  指定访问端口、host
