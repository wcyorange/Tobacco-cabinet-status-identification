import numpy as np
import matplotlib as plt
import imageio as pli
from PIL import Image
from PIL import ImageEnhance
import cv2
import numpy as np
HANG=1440
LIE=2560
def outputshdow1():
    mode=np.zeros((HANG,LIE,3))
    for i in range(HANG):
        for j in range(LIE):
            if i<1439 and i>1150 and j*839<1426800-700*i and  j*839>1200000-700*i :
                mode[i][j]=[1,1,1]
            else:
                mode[i][j]=[0,0,0]
    pli.imsave("out3.jpg",mode)
outputshdow1()