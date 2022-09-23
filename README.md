## 青岛卷烟厂烟柜状态识别系统

### 项目简介
为青岛卷烟厂做的实际项目，项目通过摄像头获取烟柜的视频流，使用CNN图像分类方法检测传送带烟丝状态；使用YOLOX与机器学习图像处理方法（如Sobel等）对烟柜异物状态与加料状态进行检测。最终将烟柜状态检测结果通过web页面展示。
![算法流程](https://github.com/wcyorange/Tobacco-cabinet-status-identification/tree/main/img/all.png)

### 数据介绍
该项目所有数据都是从工业摄像机进行采集，数据包括烟柜传送带状态数据（空料、满料、含料状态）、异物数据（人、扫把、拖把）、加料状态数据
![数据](https://github.com/wcyorange/Tobacco-cabinet-status-identification/tree/main/img/date1.png)

### 异物状态检测
异物状态检测在生产之前进行，主要检测烟柜内是否有人或者工人清洁后遗忘的扫把、拖把，若存在不仅会造成安全事故，还有可能导致该批次烟丝报废，造成巨大的经济损失。
![烟柜异物状态检测](https://github.com/wcyorange/Tobacco-cabinet-status-identification/tree/main/img/yiwu.png)

### 加料状态检测
若烟柜中不存在异物，则进行加料状态检测，加料状态时生产状态的重要过程，通过加料机将烟料送入烟柜中。
![烟柜异物状态检测](https://github.com/wcyorange/Tobacco-cabinet-status-identification/tree/main/img/yiwu.png)

### 烟草柜传送带状态检测
烟柜传送带状态检测主要分为光照条件或者灰度条件下的空料、满料、含料（送料）的状态检测，以往需要工人攀爬烟柜进行生产状态检测，存在巨大的安全 隐患及公司需要支出巨大的成本，
![烟柜传送带状态检测](https://github.com/wcyorange/Tobacco-cabinet-status-identification/tree/main/img/classification.png)
