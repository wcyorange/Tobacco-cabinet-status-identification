#!/usr/bin/env python
# coding: utf-8

# In[18]:


import json
def put(key="",key2=""):
    s = {"status":"","msg":""}
    s.update({'status': key})
    s.update({'msg': key2})
    #读取字符串并并将s保存为json文件输出
    jsonString=json.dumps(s,indent=4)
    return jsonString
#json文件读入转为字典，在进行查询
def get(jsonString,key):
    data = json.loads(jsonString)
    data_key = key
    for key in data.keys():
        if key == data_key:
            print (data[key])
            return data[key]


p=put(1,1)
print(get(p , "status"))
# print(p)

