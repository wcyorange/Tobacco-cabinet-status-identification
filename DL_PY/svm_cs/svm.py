import matplotlib.pyplot as plt 
from PIL import Image
import numpy as np
import pandas as pd
from sklearn import svm
from mpl_toolkits.mplot3d import Axes3D
import cv2

SHADOW_ADDRESS1 = 'out1.jpg'
def getData(address):
	return np.array(pd.read_table(address,header = None));

def show3dPoint(data1,data2):
	fig = plt.figure()
	ax1 = plt.axes(projection='3d')
	ax1.scatter3D(data1[0:200,0],data1[0:200,1],data1[0:200,2],c='red')
	ax1.scatter3D(data2[0:200,0],data2[0:200,1],data2[0:200,2],c='green')
	plt.show()

def svmClassification(datax1,datax2):
	datay1 = np.zeros(len(datax1))+1
	datay2 = np.zeros(len(datax2))+2
	classifier=svm.SVC(kernel='linear',gamma=0.8,decision_function_shape='ovo',C=1)
	classifier.fit(np.vstack((datax1,datax2)),np.hstack((datay1,datay2)))
	b=classifier.intercept_
	w=classifier.coef_
	#所求结果
	print("[%s,%s,%s],%s"%(w[0][0],w[0][1],w[0][2],b[0]))
	return w,b

def inputImage(address):
	return np.array(Image.open(address));

def verification(img,w,b):
    shd1 = inputImage(SHADOW_ADDRESS1)
    answer = (img*w[0]).reshape((-1,3))
    answer = ((answer[:,0]+answer[:,1]+answer[:,2]+b)>0).reshape((len(img),len(img[0])))*shd1[:,:,0]
    plt.subplot(2,2,1)
    plt.imshow(img)
    plt.subplot(2,2,2)
    plt.imshow(answer)
    plt.subplot(2,2,3)
    plt.imshow(shd1)
    plt.subplot(2,2,4)
    plt.imshow(img&shd1)
    plt.show()


# w,b=[-0.019825691273629584,-0.0033972686490600828,0.020465644927100086],0.9713817413133342
#示例
#在三维空间显示两类训练集的————g_k.txt为传送带(红色点)，y.txt为烟叶(绿色点)
show3dPoint(getData('g_k.txt'),getData('y.txt'))
# #计算svm的权重和偏移量
w,b = svmClassification(getData('g_k.txt'),getData('y.txt'));
# #可视化验证权重和偏移量
verification(inputImage('03.bmp'),w,b)



#注意路径
#彩色、黑白
#超平面












