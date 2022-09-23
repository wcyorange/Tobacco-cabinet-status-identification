from tensorflow.keras import layers, models, Model, Sequential
import tensorflow as tf
from tensorflow.keras import datasets, layers, optimizers, Sequential, metrics, losses

def LeNet(im_height=512, im_width=256, class_num=1000):
    # tensorflow中的tensor通道排序是NHWC
    input_image = layers.Input(shape=(im_height, im_width, 3), dtype="float32")  # output(None, 224, 224, 3)
    x = layers.ZeroPadding2D(((1, 2), (1, 2)))(input_image)                      # output(None, 227, 227, 3)
    x = layers.Conv2D(6, kernel_size=3, strides=1, activation="relu")(x)       # output(None, 55, 55, 48)
    x = layers.MaxPool2D(pool_size=2, strides=2)(x)                              # output(None, 27, 27, 48)
    x = layers.Conv2D(16, kernel_size=3, padding="same", activation="relu")(x)  # output(None, 27, 27, 128)
    x = layers.MaxPool2D(pool_size=2, strides=2)(x)                              # output(None, 13, 13, 128)

    x = layers.Flatten()(x)                         # output(None, 6*6*128)
    x = layers.Dropout(0.2)(x)
    x = layers.Dense(128, activation="relu")(x)    # output(None, 2048)
    x = layers.Dropout(0.2)(x)
    x = layers.Dense(64, activation="relu")(x)    # output(None, 2048)
    x = layers.Dense(class_num)(x)                  # output(None, 5)
    predict = layers.Softmax()(x)

    model = models.Model(inputs=input_image, outputs=predict)
    return model

