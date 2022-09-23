'''异物状态'''
    current_abnormal = checkYolo(imgGroup[0]);
    '''加料状态'''
    current_add = checkAdd(imgGroup,shd2)
    '''烟柜状态'''
    current_capacity = checkCapacity(imgGroup[0],id,shd1)
    '''逻辑判断'''
    status_ID = 0
    status_message = ""
    if len(current_abnormal)>0 : 
        status_ID = 10005
        status_message = current_abnormal
    elif current_add>8.2:
        status_ID = 10002
        status_message = "加料状态"
    elif current_capacity:
        status_ID = 10003
        status_message = "满料状态"
    else :
        status_ID = 10001
        status_message = "空料状态"
    return status_ID,status_message