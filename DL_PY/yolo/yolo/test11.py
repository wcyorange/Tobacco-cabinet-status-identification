from code2 import check
import numpy as np
from PIL import Image
import matplotlib.pyplot as plt
pathIn = '7hao.bmp'
img = np.array(Image.open(pathIn))
r = img.reshape(-1, 3)[:, 0]
g = img.reshape(-1, 3)[:, 1]
b = img.reshape(-1, 3)[:, 2]
# img = img[:, :, 0] * 0.3 + img[:, :, 1] * 0.59 + img[:, :, 2] * 0.11
# img = r * 0.3 + g * 0.59 + b * 0.11
r=r * 0.3 + g * 0.59 + b * 0.11
g=r * 0.3 + g * 0.59 + b * 0.11
b=r * 0.3 + g * 0.59 + b * 0.11
img1=check(img)
plt.imshow(img1)
plt.show()
