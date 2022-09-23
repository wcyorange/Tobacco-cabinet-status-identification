from code2 import check
import numpy as np
from PIL import Image
import matplotlib.pyplot as plt 
pathIn = '11.bmp'
img = check(np.array(Image.open(pathIn)))
plt.imshow(img)
plt.show()







































