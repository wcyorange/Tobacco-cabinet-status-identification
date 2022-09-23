import xml.etree.ElementTree as ET
from os import getcwd
import urllib.request

r = urllib.request.urlopen('http://www.njust.edu.cn')
con = r.read()
dec = con.decode('gbk', 'ignore')

# sets=[('2007', 'train'), ('2007', 'val'), ('2007', 'test')]

# classes = ["aeroplane", "bicycle", "bird", "boat", "bottle", "bus", "car", "cat", "chair", "cow", "diningtable", "dog", "horse", "motorbike", "person", "pottedplant", "sheep", "sofa", "train", "tvmonitor"]


sets=[('2020', 'train'), ('2020', 'val'), ('2020', 'test')]

classes = ["saoba","tuoba","human"]


def convert_annotation( image_id, list_file):
    in_file = open('VOCdevkit/VOC2020/Annotations/%s.xml'%( image_id))
    tree=ET.parse(in_file)
    root = tree.getroot()
    # print(image_id)
    for obj in root.iter('object'):
        difficult = obj.find('difficult').text
        cls = obj.find('name').text
        if cls not in classes or int(difficult)==1:
            continue
        cls_id = classes.index(cls)
        xmlbox = obj.find('bndbox')
        b = (int(xmlbox.find('xmin').text), int(xmlbox.find('ymin').text), int(xmlbox.find('xmax').text), int(xmlbox.find('ymax').text))
        list_file.write(" " + ",".join([str(a) for a in b]) + ',' + str(cls_id))
        # print(" " + ",".join([str(a) for a in b]) + ',' + str(cls_id))

wd = getcwd()
# for year, image_set in sets:
image_ids = open('VOCdevkit/VOC2020/ImageSets/Main/train.txt').read().strip().split('\n')
# print(image_ids)
list_file = open('2020_train.txt', 'w')
# print(image_set)
for image_id in image_ids:
    list_file.write("%s/VOCdevkit/VOC2020/JPEGImages/%s.bmp"%(wd, image_id))
    convert_annotation( image_id, list_file)
    list_file.write('\n')
list_file.close()













