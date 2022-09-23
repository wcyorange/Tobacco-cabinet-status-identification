from tensorflow.keras.preprocessing.image import ImageDataGenerator #图像增广技术  https://blog.csdn.net/jacke121/article/details/79245732
import matplotlib.pyplot as plt
from model import LeNet
import tensorflow as tf
import json
import os

gpus = tf.config.experimental.list_physical_devices('GPU')
for gpu in gpus:
    tf.config.experimental.set_memory_growth(gpu, True)

data_root = os.path.abspath(os.path.join(os.getcwd(), "../.."))  # get data root path
image_path = data_root + "/image_class-master/LeNet-5/data/"  # flower data set path
train_dir = image_path + "train" #训练集
validation_dir = image_path + "test"  #验证集

# create direction for saving weights
if not os.path.exists("save_weights"):
    os.makedirs("save_weights")

im_height = 512
im_width = 256
batch_size = 8
epochs = 100

# data generator with data augmentation -具有数据扩充功能的数据生成器
train_image_generator = ImageDataGenerator(rescale=1. / 255,
                                           horizontal_flip=True)
validation_image_generator = ImageDataGenerator(rescale=1. / 255)

#flow_from_directory(directory): 以文件夹路径为参数,生成经过数据提升/归一化后的数据,在一个无限循环中无限产生batch数据
#https://blog.csdn.net/mieleizhi0522/article/details/82191331
train_data_gen = train_image_generator.flow_from_directory(directory=train_dir,#目标文件夹路径,对于每一个类,该文件夹都要包含一个子文件夹.子文件夹中任何JPG、PNG、BNP、PPM的图片都会被生成器使用.
                                                           batch_size=batch_size,
                                                           shuffle=True, #是否打乱数据,默认为True
                                                           target_size=(im_height, im_width),# 整数tuple,默认为(256, 256). 图像将被resize成该尺寸
                                                           class_mode='categorical')#"categorical", "binary", "sparse"或None之一. 默认为"categorical. 该参数决定了返回的标签数组的形式, "categorical"会返回2D的one-hot编码标签,"binary"返回1D的二值标签."sparse"返回1D的整数标签,如果为None则不返回任何标签,
total_train = train_data_gen.n ## 训练集样本总数

# get class dict
class_indices = train_data_gen.class_indices

# transform value and key of dict
inverse_dict = dict((val, key) for key, val in class_indices.items())
# write dict into json file
json_str = json.dumps(inverse_dict, indent=4)
with open('class_indices.json', 'w') as json_file:
    json_file.write(json_str)

val_data_gen = validation_image_generator.flow_from_directory(directory=validation_dir,
                                                              batch_size=batch_size,
                                                              shuffle=False,
                                                              target_size=(im_height, im_width),
                                                              class_mode='categorical')
total_val = val_data_gen.n



model = LeNet(im_height=im_height, im_width=im_width, class_num=2)
# model = AlexNet_v2(class_num=5)
# model.build((batch_size, 224, 224, 3))  # when using subclass model
model.summary()

# using keras high level api for training
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.0005),
              loss=tf.keras.losses.CategoricalCrossentropy(from_logits=False),
              metrics=["accuracy"])

callbacks = [tf.keras.callbacks.ModelCheckpoint(filepath='./save_weights/myAlex.h5',
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


# history = model.fit_generator(generator=train_data_gen,
#                               steps_per_epoch=total_train // batch_size,
#                               epochs=epochs,
#                               validation_data=val_data_gen,
#                               validation_steps=total_val // batch_size,
#                               callbacks=callbacks)

# # using keras low level api for training
# loss_object = tf.keras.losses.CategoricalCrossentropy(from_logits=False)
# optimizer = tf.keras.optimizers.Adam(learning_rate=0.0005)
#
# train_loss = tf.keras.metrics.Mean(name='train_loss')
# train_accuracy = tf.keras.metrics.CategoricalAccuracy(name='train_accuracy')
#
# test_loss = tf.keras.metrics.Mean(name='test_loss')
# test_accuracy = tf.keras.metrics.CategoricalAccuracy(name='test_accuracy')
#
#
# @tf.function
# def train_step(images, labels):
#     with tf.GradientTape() as tape:
#         predictions = model(images, training=True)
#         loss = loss_object(labels, predictions)
#     gradients = tape.gradient(loss, model.trainable_variables)
#     optimizer.apply_gradients(zip(gradients, model.trainable_variables))
#
#     train_loss(loss)
#     train_accuracy(labels, predictions)
#
#
# @tf.function
# def test_step(images, labels):
#     predictions = model(images, training=False)
#     t_loss = loss_object(labels, predictions)
#
#     test_loss(t_loss)
#     test_accuracy(labels, predictions)
#
#
# best_test_loss = float('inf')
# for epoch in range(1, epochs+1):
#     train_loss.reset_states()        # clear history info
#     train_accuracy.reset_states()    # clear history info
#     test_loss.reset_states()         # clear history info
#     test_accuracy.reset_states()     # clear history info
#     for step in range(total_train // batch_size):
#         images, labels = next(train_data_gen)
#         train_step(images, labels)
#
#     for step in range(total_val // batch_size):
#         test_images, test_labels = next(val_data_gen)
#         test_step(test_images, test_labels)
#
#     template = 'Epoch {}, Loss: {}, Accuracy: {}, Test Loss: {}, Test Accuracy: {}'
#     print(template.format(epoch,
#                           train_loss.result(),
#                           train_accuracy.result() * 100,
#                           test_loss.result(),
#                           test_accuracy.result() * 100))
#     if test_loss.result() < best_test_loss:
#        model.save_weights("./save_weights/myAlex.ckpt", save_format='tf')
