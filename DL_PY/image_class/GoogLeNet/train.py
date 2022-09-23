from tensorflow.keras.preprocessing.image import ImageDataGenerator
import matplotlib.pyplot as plt
from model import GoogLeNet
import tensorflow as tf
import json
import os

data_root = os.path.abspath(os.path.join(os.getcwd(), "../.."))  # 根路径
# 此处要根据自己文件存放路径来改
image_path = data_root + "/image_class-master/GoogLeNet/data/"  # flower数据集路径
train_dir = image_path + "train"  # 训练集路径
validation_dir = image_path + "test"  # 验证集路径

# 创建文件save_weights用来存放训练好的模型
if not os.path.exists("save_weights"):
    os.makedirs("save_weights")

im_height = 224
im_width = 224
batch_size = 32
epochs = 20

# def pre_function(img):
#     # img = im.open('test.jpg')
#     # img = np.array(img).astype(np.float32)
#     img = img / 255.    # 归一化
#     img = (img - 0.5) * 2.0   # 标准化
#     return img

# 定义训练集图像生成器，并对图像进行预处理
train_image_generator = ImageDataGenerator(rescale=1. / 255,
                                           horizontal_flip=True)  # 水平翻转
# 定义验证集图像生成器，并对图像进行预处理
validation_image_generator = ImageDataGenerator(rescale=1. / 255)
# 使用图像生成器从文件夹train_dir中读取样本，默认对标签进行了one-hot编码
train_data_gen = train_image_generator.flow_from_directory(directory=train_dir,
                                                           batch_size=batch_size,
                                                           shuffle=True,
                                                           target_size=(im_height, im_width),
                                                           class_mode='categorical')  # 分类方式
total_train = train_data_gen.n  # 训练集样本数
class_indices = train_data_gen.class_indices  # 数字编码标签字典：{类别名称：索引}
inverse_dict = dict((val, key) for key, val in class_indices.items())  # 转换字典中键与值的位置
json_str = json.dumps(inverse_dict, indent=4)  # 将转换后的字典写入文件class_indices.json
with open('class_indices.json', 'w') as json_file:
    json_file.write(json_str)
# 使用图像生成器从验证集validation_dir中读取样本
val_data_gen = train_image_generator.flow_from_directory(directory=validation_dir,
                                                         batch_size=batch_size,
                                                         shuffle=True,
                                                         target_size=(im_height, im_width),
                                                         class_mode='categorical')
total_val = val_data_gen.n  # 验证集样本数
model = GoogLeNet(im_height=im_height, im_width=im_width, class_num=3, aux_logits=True)  # 实例化模型
# model.build((batch_size, 224, 224, 3))  # when using subclass model
model.summary()  # 每层参数信息

# using keras high level api for training
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.0005),
              loss=tf.keras.losses.CategoricalCrossentropy(from_logits=False),
              metrics=["accuracy"])

callbacks = [tf.keras.callbacks.ModelCheckpoint(filepath='./save_weights/myGoogLeNet.h5',
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