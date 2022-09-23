from PIL import Image
import flask
from flask import Flask, session
import numpy as np
import cv2
import json
from flask import request
import datetime
import tensorflow as tf
from tensorflow.keras.preprocessing import image
from model import vgg
import gc
import signal
import os
import cv2
os.environ["CUDA_VISIBLE_DEVICES"] = "-1"
# import request/
SHADOW_ADDRESS1 = "D:\\allcode02\\test_imgs\\out1.jpg"
SHADOW_ADDRESS2 = "D:\\allcode02\\test_imgs\\out2.jpg"
SHADOW_ADDRESS3 = "D:\\allcode02\\test_imgs\\out3.jpg"

times = 1


def inputImage(address):
    return np.array(Image.open(address));


app = flask.Flask(__name__)
shd1 = inputImage(SHADOW_ADDRESS1)
shd2 = inputImage(SHADOW_ADDRESS2)
shd3 = inputImage(SHADOW_ADDRESS3)


class Yanchang():

    def __init__(self):
        self.load_vgg_model()
        # self.load_yolo_model()

    def load_vgg_model(self):
        self.vgg_model = vgg("vgg16", 224, 224, 3)
        self.vgg_weights_path = "D:\\allcode02\\save_weights\\myvgg-16_10.h5"
        self.vgg_model.load_weights(self.vgg_weights_path)

        self.class_indices_path = "D:\\allcode02\\class_indices.json"
        self.class_index_json = json.load(open(self.class_indices_path, "r"))

    # def load_yolo_model(self):
    #     self.yolo_label_path = "D:\\allcode02\\cfg\\yolov3.names"
    #     self.yolo_cfg_path = "D:\\allcode02\\cfg\\yolov3.cfg"
    #     self.yolo_weights_path = "D:\\allcode02\\cfg\\yolov3.weights"
    #     self.yolo_confidence_threshold = 0.85
    #     self.nms_threshold = 0.3
    #     self.jpg_quality = 80
    #
    #     # self.labels = []
    #     # for line in open(self.yolo_label_path, "r"):
    #     #     self.labels.append(line.strip())
    #     self.LABELS = open(self.yolo_label_path).read().strip().split("\n")
    #
    #     self.yolo_net = cv2.dnn.readNetFromDarknet(self.yolo_cfg_path, self.yolo_weights_path)

    def make_vgg_prediction(self, path, image_size=(224, 224)):
        img = image.load_img(path)
        img = img.resize(image_size)

        # scaling pixel value to (0-1)
        img = np.array(img) / 255.

        # Add the image to a batch where it's the only member.
        img = (np.expand_dims(img, 0))

        prediction = self.vgg_model.predict(img)
        predicted_class = np.argmax(prediction)

        if predicted_class == 0:
            return 0
        elif predicted_class == 1:
            return 1
        else:
            return 2

    # def make_yolo_prediction(self, img):
    #     (H, W) = img.shape[:2]
    #     r = img.reshape(-1, 3)[:, 0]
    #     g = img.reshape(-1, 3)[:, 1]
    #     b = img.reshape(-1, 3)[:, 2]
    #     # if np.sum(r - g) < 100000000:  # 灰度图
    #     #     weights_path = weights2_path
    #     #     # return weights_p9ath
    #     # else:  # 彩图
    #     #     r = r * 0.3 + g * 0.59 + b * 0.11
    #     #     g = r * 0.3 + g * 0.59 + b * 0.11
    #     #     b = r * 0.3 + g * 0.59 + b * 0.11
    #     #     weights_path = weights2_path
    #     #     # return weights_path
    #     if np.sum(r - g) > 100000000:  # 灰度图
    #         r = r * 0.3 + g * 0.59 + b * 0.11
    #         g = r * 0.3 + g * 0.59 + b * 0.11
    #         b = r * 0.3 + g * 0.59 + b * 0.11
    #
    #
    #     blob = cv2.dnn.blobFromImage(img, 1 / 255.0, (416, 416), swapRB=True, crop=False)
    #     self.yolo_net.setInput(blob)
    #     layerOutputs = self.yolo_net.forward([
    #         self.yolo_net.getLayerNames()[i[0] - 1] for i in self.yolo_net.getUnconnectedOutLayers()])
    #
    #     boxes = []
    #     confidences = []
    #     classIDs = []
    #     confidencesmost = 0.0
    #
    #     for output in layerOutputs:
    #         for detection in output:
    #             scores = detection[5:]
    #             classID = np.argmax(scores)
    #             confidence = scores[classID]
    #
    #             if confidence > self.yolo_confidence_threshold:
    #                 confidencesmost = confidence
    #                 (centerX, centerY, width, height) = (detection[0:4] * np.array([W, H, W, H])).astype("int")
    #                 boxes.append([int(centerX - (width / 2)), int(centerY - (height / 2)), int(width), int(height)])
    #                 confidences.append(float(confidence))
    #                 classIDs.append(classID)
    #
    #     classIDs = np.unique(classIDs)
    #     num = [str(i) for i in classIDs]
    #     str1 = ","
    #     str1 = str1.join(num)
    #     # str1 = str1.replace('0', 'broom')
    #     # str1 = str1.replace('1', 'mop')
    #     # str1 = str1.replace('2', 'people')
    #     str1 = str1.replace('0', '扫把')
    #     str1 = str1.replace('1', '拖把')
    #     str1 = str1.replace('2', '人')
    #
    #     if len(str1) > 0:
    #         return str1, confidencesmost
    #     else:
    #         return ","


    def putJson(self, id_, code_, status_, message_):
        s = {"id_": "", "code_": "", "status_": "", "message_": ""}
        s.update({'id': id_})
        s.update({'code': code_})
        s.update({'status': status_})
        s.update({'message': message_})
        jsonString = json.dumps(s, indent=4)
        return jsonString

    def getJson(self, jsonString, key):
        data = json.loads(jsonString)
        return data[key]

    def analyseTwoImage(self, im1, im2, shd2):
        maxx = (im1 > im2) * im1 + (im1 < im2) * im2;
        minn = (im1 > im2) * im2 + (im1 < im2) * im1;
        score = np.sum((maxx - minn) & shd2);
        if score != 0:
            score = score / np.sum(((maxx - minn) & shd2) > 0);
        return score;
    def analyseTwoImage1(self,img1,img2,shd2):
        img1=img1*shd2
        img2=img2*shd2
        hist=cv2.calcHist(img1,[0],None,[256],[0.0,255.0])
        hist1 = cv2.calcHist(img2, [0], None, [256], [0.0, 255.0])
        hist2=abs(hist1-hist)
        return hist2.sum()
    def writeData(self, id__, score):
        with open(str(id__) + ".txt", mode='a') as file_name:
            file_name.write(str(score) + '\n')

    def checkSobel(self, img):
        x = cv2.Sobel(img, cv2.CV_16S, 1, 0)
        y = cv2.Sobel(img, cv2.CV_16S, 0, 1)
        Scale_absX = cv2.convertScaleAbs(x, y)
        # cv2.imshow('absY', Scale_absX)
        # cv2.waitKey()
        return Scale_absX

    # model = vgg("vgg16", 224, 224, 3)
    # model.load_weights("D:\\allcode02\\save_weights\\myvgg-16_10.h5")
    # 定义预测函数
        # 定义预测函数
    # def image_predict(self, path, image_size=(224, 224)):
    #
    #     # load image
    #     img = image.load_img(path)
    #     # resize image to 224x224
    #     img = img.resize(image_size)
    #     # plt.imshow(img)
    #
    #     # scaling pixel value to (0-1)
    #     img = np.array(img) / 255.
    #
    #     # Add the image to a batch where it's the only member.
    #     img = (np.expand_dims(img, 0))
    #
    #     # read class_indict
    #     try:
    #         json_file = open('D:\\allcode02\\class_indices.json', 'r')
    #         class_indict = json.load(json_file)
    #         json_file.close()
    #     except Exception as e:
    #         print(e)
    #         exit(-1)
    #
    #     model = vgg("vgg16", 224, 224, 3)
    #     model.load_weights("D:\\allcode02\\save_weights\\myvgg-16_10.h5")
    #     result = np.squeeze(model.predict(img))
    #     predict_class = np.argmax(result)
    #     del model
    #     del result
    #     del img
    #
    #     if predict_class == 0:
    #         return 0
    #     elif predict_class == 1:
    #         return 1
    #     else:
    #         return 2

    def checkAdd(self, img, shd2):
        # t1 = datetime.now()
        score = self.analyseTwoImage1(img[0], img[4], shd2)
        # t2 = datetime.now()
        # print("Time cost = ", (t2 - t1),"秒")
        # print("SUCCEED !!!")
        return score

    def checkYolo(self, img,
                  label_path='D:\\allcode02\\cfg\\yolov3.names',
                  config_path='D:\\allcode02\\cfg\\yolov3.cfg',
                  weights1_path='D:\\allcode02\\cfg\\yolov3.weights',
                  weights2_path='D:\\allcode02\\cfg\\yolov3.weights',
                  confidence_thre=0.85,
                  nms_thre=0.3,
                  jpg_quality=80):
        LABELS = open(label_path).read().strip().split("\n")
        (H, W) = img.shape[:2]
        r = img.reshape(-1, 3)[:, 0]
        g = img.reshape(-1, 3)[:, 1]
        b = img.reshape(-1, 3)[:, 2]
        if np.sum(r - g) < 100000000:  # 灰度图
            weights_path = weights2_path
            # return weights_p9ath
        else:  # 彩图
            r = r * 0.3 + g * 0.59 + b * 0.11
            g = r * 0.3 + g * 0.59 + b * 0.11
            b = r * 0.3 + g * 0.59 + b * 0.11
            weights_path = weights2_path
            # return weights_path
        net = cv2.dnn.readNetFromDarknet(config_path, weights_path)
        blob = cv2.dnn.blobFromImage(img, 1 / 255.0, (416, 416), swapRB=True, crop=False)
        net.setInput(blob)
        layerOutputs = net.forward([net.getLayerNames()[i[0] - 1] for i in net.getUnconnectedOutLayers()])
        boxes = []
        confidences = []
        classIDs = []
        confidencesmost = 0.0
        for output in layerOutputs:
            for detection in output:
                scores = detection[5:]
                classID = np.argmax(scores)
                confidence = scores[classID]
                if confidence > confidence_thre:
                    confidencesmost = confidence
                    (centerX, centerY, width, height) = (detection[0:4] * np.array([W, H, W, H])).astype("int")
                    boxes.append([int(centerX - (width / 2)), int(centerY - (height / 2)), int(width), int(height)])
                    confidences.append(float(confidence))
                    classIDs.append(classID)
        # 0 -> saoba ; 1 -> tuoba; 2->human
        classIDs = np.unique(classIDs)
        num = [str(i) for i in classIDs]
        str1 = ","
        str1 = str1.join(num)
        str1 = str1.replace('0', '扫把')
        str1 = str1.replace('1', '拖把')
        str1 = str1.replace('2', '人')
        del boxes
        del classIDs
        del net
        if len(str1) > 0:
            return str1, confidencesmost
        else:
            return "", ""

    def checkStatus(self, pathIn, id, shd1, shd2):
        imgGroup = [0, 0, 0, 0, 0]
        imgGroup[0] = inputImage(pathIn[0])
        imgGroup[1] = inputImage(pathIn[1])
        imgGroup[2] = inputImage(pathIn[2])
        imgGroup[3] = inputImage(pathIn[3])
        imgGroup[4] = inputImage(pathIn[4])
        '''异物状态'''
        try:
            current_abnormal, confidence = self.checkYolo(imgGroup[0]);
        except Exception as e:
            print("yolo部分出现错误",e)
            return 10003, 'yoloERROR',0.0, ' '
        '''加料状态'''
        try:
            current_add = self.checkAdd(imgGroup, shd2)
        except Exception as e:
            print("状态判断加料部分出现错误",e)
            return 10003, 'ADDERROR',0.0,' '

        '''烟柜状态'''
        try:
            current_capacity = self.make_vgg_prediction(pathIn[0])
        except Exception as e:
            print("状态判断部分出现错误",e)
            return 10003, 'VGGERROR', 0.0,' '
        '''逻辑判断'''
        status_ID = 0
        status_message = ""
        if len(current_abnormal) > 0:
            status_ID = 10005
            status_message = current_abnormal
        elif current_add > 950:
            status_ID = 10002
            status_message = "加料状态"
        elif current_capacity == 1:
            status_ID = 10003
            status_message = "满料状态"
        elif current_capacity == 0:
            status_ID = 10001
            status_message = "空料状态"
        elif current_capacity == 2:
            status_ID = 10004
            status_message = "有料状态"
        del imgGroup
        return status_ID, status_message, current_add, confidence

# detector = yolo_detector()
yc = Yanchang()


@app.route('/CheckCamera/Main', methods=['POST'])
def Main():
    try:
        t1 = datetime.datetime.now()
        # print("已收到")
        # request.get()
        # print(jsonString)
        id_ = request.form.get('id')
        code_ = request.form.get('code')
        address_ = request.form.get('address')
        # id_ = getJson(json_string,'id')
        # code_ = getJson(json_string,'code');
        # address_ = getJson(json_string,'address');
        current_status, current_message, add, confidence = yc.checkStatus(address_.split(','), id_, shd1, shd2);
        t2 = datetime.datetime.now()

        print('耗时: ', t2 - t1)
        print('结果：', id_, code_, current_status, current_message, add, confidence, "运行次数", times)
        gc.collect()
        return yc.putJson(id_, code_, current_status, current_message);
    except Exception as e:
        print("ERROR：", e)
        os.system('D:\\allcode02\\restart.bat')
        os._exit(0)
        return yc.putJson(id_, code_, 10003, "未知状态");


if __name__ == '__main__':
    app.run(debug=False, use_reloader=True, port=5001, threaded=True, processes=True)  # 指定访问端口、host
