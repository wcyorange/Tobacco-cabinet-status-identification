from tensorflow.keras.preprocessing.image import ImageDataGenerator
import matplotlib.pyplot as plt
from model import vgg
import tensorflow as tf
import json
import os


data_root = os.path.abspath(os.path.join(os.getcwd(), "../.."))  # get data root path
image_path = data_root + "/image_class-master/vgg-19/data/"  # flower data set path
train_dir = image_path + "train"
validation_dir = image_path + "test"

# create direction for saving weights
if not os.path.exists("save_weights"):
    os.makedirs("save_weights")

im_height = 224
im_width = 224
batch_size = 32
epochs = 20

_R_MEAN = 123.68
_G_MEAN = 116.78
_B_MEAN = 103.94


# def pre_function(img):
#     # img = im.open('test.jpg')
#     # img = np.array(img).astype(np.float32)
#     img = img - [_R_MEAN, _G_MEAN, _B_MEAN]
#
#     return img


# data generator with data augmentation
train_image_generator = ImageDataGenerator(horizontal_flip=True,
                                           # preprocessing_function=pre_function，
                                           rescale=1. / 255)
validation_image_generator = ImageDataGenerator(rescale=1. / 255)
                                                 # preprocessing_function=pre_function)

train_data_gen = train_image_generator.flow_from_directory(directory=train_dir,
                                                           batch_size=batch_size,
                                                           shuffle=True,
                                                           target_size=(im_height, im_width),
                                                           class_mode='categorical')
total_train = train_data_gen.n

# get class dict
class_indices = train_data_gen.class_indices

# transform value and key of dict
inverse_dict = dict((val, key) for key, val in class_indices.items())
# write dict into json file
json_str = json.dumps(inverse_dict, indent=4)
with open('class_indices.json', 'w') as json_file:
    json_file.write(json_str)

val_data_gen = train_image_generator.flow_from_directory(directory=validation_dir,
                                                         batch_size=batch_size,
                                                         shuffle=True,
                                                         target_size=(im_height, im_width),
                                                         class_mode='categorical')
total_val = val_data_gen.n

model = vgg("vgg19", 224, 224, 3)
model.load_weights('./pretrain_weights.ckpt')
for layer_t in model.layers:
    if layer_t.name == 'feature':
        layer_t.trainable = False
        break

model.summary()

# using keras high level api for training
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.0001),
              loss=tf.keras.losses.CategoricalCrossentropy(from_logits=False),
              metrics=["accuracy"])

callbacks = [tf.keras.callbacks.ModelCheckpoint(filepath='./save_weights/myvgg-19_{epoch}.h5',
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