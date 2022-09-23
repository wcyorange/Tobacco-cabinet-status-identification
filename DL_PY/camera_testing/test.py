#coding: utf-8
from PIL import Image
import matplotlib.pyplot as plt 
import matplotlib.image as pli
import numpy as np
import collections
import pylab
import imageio
PICTURE_ADDRESS = "烟柜截图\\"
SHADOW_ADDRESS1 = "out1.jpg"
SHADOW_ADDRESS2 = "out2.jpg"
HANG = 1440;
LIE = 2560;
#绘制出下方梯形框
def drawOutLine1():
	xArray = [1439,600,600,1439];
	yArray = [500,1200,1300,2000];
	plt.plot(yArray,xArray);
#绘制出上方矩形框
def drawOutLine2():
	xArray = [300,300,600,600,300];
	yArray = [500,2000,2000,500,500];
	plt.plot(yArray,xArray);
#读取一幅图像
#输入：address为图像路径
#输出：返回矩阵形式
def inputImage(address):
	return np.array(Image.open(address));
#分析两帧图像之间的波段差
#输入：
#	1、img1，前一帧图像
#	2、img2，后一帧图像
#	3、shd1，梯形区域
#	4、shd2，矩形区域
#输出：
#	1、score1，梯形区域波段震荡幅度
#	2、score2，矩形区域波段震荡幅度
def analyseTwoImage(im1,im2,shd1,shd2):
	maxx = (im1>im2)*im1 + (im1<im2)*im2;
	minn = (im1>im2)*im2 + (im1<im2)*im1;
	score2 = np.sum((maxx-minn) & shd2);
	score1 = np.sum((maxx-minn) & shd1);
	if score1 != 0:
		score1 = score1 / np.sum(((maxx-minn) & shd1)>0);
	if score2 != 0:
		score2 = score2 / np.sum(((maxx-minn) & shd2)>0);
	return score1,score2;
#计算烟柜空余量
#输入：
#	1、img，图像
#	2、shd1，梯形框
#	3、k，分类阈值
def caculateCapacity(img,shd1,k):
	return np.sum((img<30)*shd1)/np.sum(shd1);
#主函数
#输入：
#	1、img， 输入的图像数组（5帧图像）
#	2、shd1，梯形区域
#	3、shd2，矩形区域
#输出：返回烟柜目前的状态
def check(img,shd1,shd2):
	#分别计算5帧内相邻两帧的震荡幅度
	score1 = score2 = 0;
	for i in range(5):
		for j in range(i+1,5):
			score1_1,score2_1 = analyseTwoImage(img[i],img[j],shd1,shd2);
			score1 = score1 + score1_1;
			score2 = score2 + score2_1;
	score1 = score1/10;
	score2 = score2/10;
	if score2>3 : return '加料状态';
	if score1>10 : return '异物状态';
	capacity = 0;
	for i in img:
		capacity = capacity + caculateCapacity(i,shd1,30);
	capacity = capacity/5;
	if capacity>0.9 : return '空料状态';
	if capacity>0.1 : return '送料状态';
	return '满料状态';

#读取素材
img1 = inputImage(PICTURE_ADDRESS+"1.jpg");
img2 = inputImage(PICTURE_ADDRESS+"2.jpg");
img3 = inputImage(PICTURE_ADDRESS+"3.jpg");
img4 = inputImage(PICTURE_ADDRESS+"4.jpg");
img5 = inputImage(PICTURE_ADDRESS+"5.jpg");
shd1 = np.array(Image.open(SHADOW_ADDRESS1));
shd2 = np.array(Image.open(SHADOW_ADDRESS2));
#示例
print(check([img1,img2,img3,img4,img5],shd1,shd2))

