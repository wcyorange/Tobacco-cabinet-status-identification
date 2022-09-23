from nets.yolo3 import yolo_body
from keras.layers import Input
from yolo import YOLO
from PIL import Image
import numpy as np
import matplotlib.pyplot as plt 

yolo = YOLO()
image = Image.open('img/street.jpg')
answer = yolo.detect_image(image);
plt.imshow(answer);
plt.show()
# while True:
#     img = input('Input image filename:')
#     try:
#         image = Image.open('img/street.jpg')
#     except:
#         print('Open Error! Try again!')
#         continue
#     else:
#         r_image = yolo.detect_image(image)
#         r_image.show()
yolo.close_session()
