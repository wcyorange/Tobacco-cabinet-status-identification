from model import AlexNet_v1, AlexNet_v2
from PIL import Image
import numpy as np
import json
import matplotlib.pyplot as plt

im_height = 512
im_width = 256

# load image
img = Image.open("./data/train/have/have5.jpg")
# resize image to 224x224
img = img.resize((im_width, im_height))
plt.imshow(img)

# scaling pixel value to (0-1)
img = np.array(img) / 255.

# Add the image to a batch where it's the only member.
img = (np.expand_dims(img, 0))

# read class_indict
try:
    json_file = open('./class_indices.json', 'r')
    class_indict = json.load(json_file)
except Exception as e:
    print(e)
    exit(-1)

model = AlexNet_v1(class_num=3)
model.load_weights("./save_weights/myAlex.h5")
result = np.squeeze(model.predict(img))
predict_class = np.argmax(result)
# print(class_indict[str(predict_class)], result[predict_class])
# print(class_indict[str(predict_class)])
print(predict_class)
plt.show()


# # plot loss and accuracy image
# history_dict = history.history
# train_loss = history_dict["loss"]
# train_accuracy = history_dict["accuracy"]
# val_loss = history_dict["val_loss"]
# val_accuracy = history_dict["val_accuracy"]
#
# # figure 1
# plt.figure()
# plt.plot(range(epochs), train_loss, label='train_loss')
# plt.plot(range(epochs), val_loss, label='val_loss')
# plt.legend()
# plt.xlabel('epochs')
# plt.ylabel('loss')
#
# # figure 2
# plt.figure()
# plt.plot(range(epochs), train_accuracy, label='train_accuracy')
# plt.plot(range(epochs), val_accuracy, label='val_accuracy')
# plt.legend()
# plt.xlabel('epochs')
# plt.ylabel('accuracy')
# plt.show()