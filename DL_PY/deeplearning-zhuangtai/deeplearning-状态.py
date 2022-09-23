import numpy as np
import os
import matplotlib.pyplot as plt
import tensorflow as tf
# from tensorflow.keras.layers import Conv2D, BatchNormalization, Activation, MaxPool2D, Dropout, Flatten, Dense
# from tensorflow.keras import Model
# 定义数据读取函数
# 读取指定数据目录下的图片文件信息，返回文件名列表和标签列表
def read_image_filenames(data_dir):  # data_dir  ->  数据目录
    empty_dir = data_dir + 'empty/'
    full_dir = data_dir + 'full/'
    have_dir = data_dir + 'have/'

    # 构建特征数据集，值为对应的图片文件名
    empty_filenames = tf.constant([empty_dir + fn for fn in os.listdir(empty_dir)])  # 通过os.listdir() 将该文件夹下所有文件名称导出，形成列表
    full_filenames = tf.constant([full_dir + fn for fn in os.listdir(full_dir)])
    have_filenames = tf.constant([have_dir + fn for fn in os.listdir(have_dir)])
    filenames = tf.concat([empty_filenames, full_filenames,have_filenames], axis=-1)  # 通过concat() 将这两个列表连接起来，前半部分都是猫，后半部分都是狗，要打散

    # 构建标签数据集，empty 为0， full 为1, have 为2
    labels = tf.concat([
        tf.zeros(empty_filenames.shape, dtype=tf.int32),
        tf.ones(full_filenames.shape, dtype=tf.int32),
        tf.ones(have_filenames.shape, dtype=tf.int32)*2],
        axis=-1)

    return filenames, labels

#定义解码图片和调整图片大小的函数
#读取图片文件并解码，调整图片的大小并标准化
def decode_image_and_resize(filename,label):
    image_string = tf.io.read_file(filename) #读取原始文件。tf.io.read_file()只是将图片读取出来形成字符串
    image_decoded = tf.image.decode_jpeg(image_string) #解码JPEG文件
    #调整图像大小，要和后面的模型输入要求一致，并进行标准化
    image_resized = tf.image.resize(image_decoded,[224,224])/255.0  #/255.0是进行标准化，缩放到0-1区间
    return image_resized,label


# #建立Dataset 数据集
# train_data_dir = './data1/'
# #dataset 的数据缓冲器大小，和数据集大小及规律有关
# buffer_size = 322
# #dataset 的数据批次大小，每批次多少个样本数
# batch_size = 4
# #定义超参数
# training_epochs = 100
#
# # dataset_train = prepare_dataset(train_data_dir,buffer_size,batch_size)
#
# filenames,labels = read_image_filenames(train_data_dir)
# dataset = tf.data.Dataset.from_tensor_slices((filenames,labels))

# #取前几个数据
# sub_dataset = dataset.take(3)
#
# for a,b in sub_dataset:
#     print("filename:",a.numpy(),"label:",b.numpy())

# #读取图像数据并处理
# dataset = dataset.map(
#         map_func = decode_image_and_resize,
#         num_parallel_calls=tf.data.experimental.AUTOTUNE)
# #
# #数据处理后在查看前几个数据
# sub_dataset = dataset.take(3)
# for a,b in sub_dataset:
#     print("feature shape:",a.shape,"label shape:",b.shape)

# #查看具体值
# sub_dataset = dataset.take(3)
#
# for a,b in sub_dataset:
#     print("features:",a.numpy(),"label:",b.numpy())
#
# # 显示图像和标签
# for x,y in sub_dataset:
#     plt.title(y.numpy())
#     plt.imshow(x.numpy())
#     plt.axis("off") #取消坐标轴
#     plt.show()

# '''乱序: dataset.shuffle(buffer_size):  将数据集打乱（设定一个固定大小的缓冲区（buffer），取出前buffer_size
#           个元素放入，并从缓冲区中随机采样，采样后的数据用后续数据替换）'''
# buffer_size = 30
# dataset = dataset.shuffle(buffer_size)
#
# '''分批：dataset.batch(batch_size): 将数据集分成批次，即对每batch_size个元素，在第0维合并成一个元素'''
# batch_size = 8
# dataset = dataset.batch(batch_size)


# 定义准备Dataset 数据集的函数(组合成一个函数)

def prepare_dataset(data_dir, buffle_size=322, batch_size=4):
    filenames, labels = read_image_filenames(data_dir)
    dataset = tf.data.Dataset.from_tensor_slices((filenames, labels))
    dataset = dataset.map(
        map_func=decode_image_and_resize, #解码并调整图像大小
        num_parallel_calls=tf.data.experimental.AUTOTUNE)
    dataset = dataset.shuffle(buffer_size)
    dataset = dataset.batch(batch_size)
    dataset = dataset.prefetch(tf.data.experimental.AUTOTUNE)  # 预取出数据集中的size个元素。AUTOTUNE系统自动调整

    return dataset


# 定义VGG-16 模型类
def vgg16_model(input_shape=(224, 224, 3)):
    vgg16 = tf.keras.applications.vgg16.VGG16(include_top=False,  # 选择不包含顶层（全连接层和输出层）
                                              weights="imagenet",  # 通过imagenet 样本库做的权重训练
                                              input_shape=input_shape)

    # 冻结掉前面的卷积和池化层让其不参与训练
    for layer in vgg16.layers:
        layer.trainable = False  # 设置VGG-16 预训练模型不可训练

    last = vgg16.output  # 将其输出为last

    # 加入剩下未经训练的全连接层
    x = tf.keras.layers.Flatten()(last)  # 在last后面加上一个平坦层，把原来模型输出都压成一维的
    x = tf.keras.layers.Dense(128, activation="relu")(x)  # 加上全连接层，
    x = tf.keras.layers.Dropout(0.3)(x)  # 防止过拟合，给他加上丢失率为0.3
    x = tf.keras.layers.Dense(32, activation="relu")(x)
    x = tf.keras.layers.Dropout(0.3)(x)
    x = tf.keras.layers.Dense(3, activation="softmax")(x)

    # 建立新的模型
    # 利用keras.models 中的Model类来建复用vgg-16模型的新的模型对象
    model = tf.keras.models.Model(inputs=vgg16.input, outputs=x)  # 输入采用vgg-16原来的输入，输出就是最后的x

    # model.summary()

    return model

#新建模型
model = vgg16_model()

#模型设置
model.compile(optimizer='adam', #优化器
              loss = 'sparse_categorical_crossentropy', #损失函数
              metrics=['accuracy']) #尺标度量,准确率

#建立Dataset 数据集
train_data_dir = './data1/'
#dataset 的数据缓冲器大小，和数据集大小及规律有关
buffer_size = 322
#dataset 的数据批次大小，每批次多少个样本数
batch_size = 4
#定义超参数
training_epochs = 2

dataset_train = prepare_dataset(train_data_dir,buffer_size,batch_size)

filenames,labels = read_image_filenames(train_data_dir)
dataset = tf.data.Dataset.from_tensor_slices((filenames,labels))

#通过model.fit()进行训练。 verbose表示训练过程指标的显示情况
train_history = model.fit(dataset_train,epochs=training_epochs,verbose=1)


# 模型存储：需要安排pyyaml包，将模型结构和模型权重参数分开存储  (相当于断点续寻)
# 模型结构存储在  .yaml  文件中
yaml_string = model.to_yaml()
with open('./models/zhuangtai.yaml', 'w') as model_file:
    model_file.write(yaml_string)
# 模型权重参数存储在 .h5 文件中
model.save_weights('./models/zhuangtai.h5')

# print(filenames)