import cv2
import numpy as np
from PIL import Image
import matplotlib as plt

SHADOW_ADDRESS1 = 'out1.jpg'
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

def checkkkkk(address):
    img = inputImage(address)
    shd1 = inputImage(SHADOW_ADDRESS1)
    # print(address,"     ",np.sum(sobel(img & shd1)))
    Scale_absX = sobel(img)
    score = np.sum(Scale_absX & shd1)
    print(address, "     ", score)
	#answer = sobel(img) & shd1


def inputImage(address):
    return np.array(Image.open(address));


# img = inputImage('E:/newproject/8张图片/1.bmp')
checkkkkk('.//photo/04 1.bmp')