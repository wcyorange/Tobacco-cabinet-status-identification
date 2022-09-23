from tensorflow.keras.preprocessing.image import ImageDataGenerator
import matplotlib.pyplot as plt
from model import resnet101
import tensorflow as tf
import json
import os
import PIL.Image as im
import numpy as np

data_root = os.path.abspath(os.path.join(os.getcwd(), "../.."))  # 获得根路径
image_path = data_root + "/image_class-master/ResNet-101/data/"
train_dir = image_path + "train"
validation_dir = image_path + "test"

im_height = 224
im_width = 224
batch_size = 16
epochs = 20

_R_MEAN = 123.68
_G_MEAN = 116.78
_B_MEAN = 103.94

def pre_function(img):  # 图像预处理
    img = img - [_R_MEAN, _G_MEAN, _B_MEAN]
    return img

# 训练集准备：将图片载入、数据增强、预处理，然后转换成张量形式
train_image_generator = ImageDataGenerator(horizontal_flip=True,
                                           preprocessing_function=pre_function)
train_data_gen = train_image_generator.flow_from_directory(directory=train_dir,
                                                           batch_size=batch_size,
                                                           shuffle=True,
                                                           target_size=(im_height, im_width),
                                                           class_mode='categorical')
total_train = train_data_gen.n  # 训练集样本总数

# 验证集准备：将图片载入、数据增强、预处理，然后转换成张量形式
validation_image_generator = ImageDataGenerator(preprocessing_function=pre_function)
val_data_gen = validation_image_generator.flow_from_directory(directory=validation_dir,
                                                              batch_size=batch_size,
                                                              shuffle=False,
                                                              target_size=(im_height, im_width),
                                                              class_mode='categorical')
# img, _ = next(train_data_gen)
total_val = val_data_gen.n  # 验证集样本总数

# 获得类别字典
class_indices = train_data_gen.class_indices
# 转换类别字典中键和值的位置
inverse_dict = dict((val, key) for key, val in class_indices.items())
# 将数字标签字典写入json文件：class_indices.json
json_str = json.dumps(inverse_dict, indent=4)
with open('class_indices.json', 'w') as json_file:
    json_file.write(json_str)

feature = resnet101(num_classes=3, include_top=False)
# feature.build((None, 224, 224, 3))  # when using subclass model
feature.load_weights('pretrain_weights.ckpt')  # 加载预训练模型
feature.trainable = False  # 训练时冻结与训练模型参数
feature.summary()  # 打印预训练模型参数

# 在原模型后加入两个全连接层，进行自定义3分类
model = tf.keras.Sequential([feature,
                             tf.keras.layers.GlobalAvgPool2D(),
                             tf.keras.layers.Dropout(rate=0.5),
                             tf.keras.layers.Dense(1024),
                             tf.keras.layers.Dropout(rate=0.5),
                             tf.keras.layers.Dense(3),
                             tf.keras.layers.Softmax()])
# model.build((None, 224, 224, 3))
model.summary()  # 打印增加层的参数


# using keras high level api for training
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.0005),
              loss=tf.keras.losses.CategoricalCrossentropy(from_logits=False),
              metrics=["accuracy"])

callbacks = [tf.keras.callbacks.ModelCheckpoint(filepath='./save_weights/myResNet.h5',
                                                save_best_only=True,
                                                save_weights_only=True,
                                                monitor='val_loss')]

# tensorflow2.1 recommend to using fit
history = model.fit(x=train_data_gen,
                    steps_per_epoch=total_train // batch_size,
                    epochs=epochs,
                    validation_data=val_data_gen,
                    validation_steps=total_val // batch_size,
                    callbacks=callbacks)

# plot loss and accuracy image
history_dict = history.history
train_loss = history_dict["loss"]
train_accuracy = history_dict["accuracy"]
val_loss = history_dict["val_loss"]
val_accuracy = history_dict["val_accuracy"]

# figure 1
plt.figure()
plt.plot(range(epochs), train_loss, label='train_loss')
plt.plot(range(epochs), val_loss, label='val_loss')
plt.legend()
plt.xlabel('epochs')
plt.ylabel('loss')

# figure 2
plt.figure()
plt.plot(range(epochs), train_accuracy, label='train_accuracy')
plt.plot(range(epochs), val_accuracy, label='val_accuracy')
plt.legend()
plt.xlabel('epochs')
plt.ylabel('accuracy')
plt.show()