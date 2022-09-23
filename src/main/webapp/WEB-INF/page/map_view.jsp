<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
    <base href="<%=basePath %>" />
    <base lay-href="<%=basePath %>" />
    <meta charset="UTF-8">
    <title>青岛卷烟厂视频监控系统</title>
    <link rel="stylesheet" href="layuiadmin/layui/css/layui.css" media="all">

</head>
<style>
    * {
        margin: 0px;
        padding: 0px;
        list-style: none;
        text-decoration: none;
        vertical-align: baseline;
        font-family: "Microsoft Yahei";
    }

    html {
        height: 100%;
        overflow: hidden ;
    }

    img {
        border: none;
    }
    ::-webkit-scrollbar {
        width: 0.2em;
    }
    ::-webkit-scrollbar:horizontal{
        height: 0.2em;
    }
    li{list-style:none;}

    @keyframes rotating{
        from{transform:rotate(0)}
        to{transform:rotate(360deg)}
    }

    .xuanzhuan{
        animation:rotating 1.2s linear infinite
    }

</style>
<body style="width:1920px;height:1080px;">
<div class="transform_container print_hide" style="overflow:hidden;width:1920px;height:1080px;background:url(layuiadmin/img/main_bg.png) 0% 0% / 100% 100%;transform-origin:left top 0px;">
    <div style="position:absolute;left:50px;top:40px;color:#fff;font-size:18px;" id="time_show"></div>
    <!--头部信息-->
    <div style="left: 580px; top: 0px; width: 800px; height: 90px; z-index: 41;position:absolute;">
        <div style="width: 800px; height: 90px; display: flex; align-items: center; justify-content: center; color: rgb(255, 255, 255); font-weight: lighter; font-family:Microsoft Yahei; font-size: 36px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">
            青岛卷烟厂视频监控系统
        </div>
    </div>
    <div style="left: 540px; top: 60px; width: 880px; height: 70px; z-index: 53;position:absolute;">
        <div style="width: 880px; height: 70px;">
            <div style="width: 100%; height: 100%; background-image: url(layuiadmin/img/top_bg.gif); background-size: 100%; background-repeat: no-repeat; background-position: center center;"></div>
        </div>
    </div>
    <div style="left: 40px; top: 98px; width: 1850px; height: 16px; z-index: 6;position:absolute;">
        <div style="width: 1850px; height: 16px; background-image: url(layuiadmin/img/top_line_bg.png); background-repeat: no-repeat; background-size: 100% 100%;"></div>
    </div>
    <div onclick="onCheck();" style="position:absolute;right:50px;top:40px;color:#fff;font-size:18px;display: flex;flex-direction: row;align-items: center;" id="">
        <i class="layui-icon layui-icon-refresh-3"></i>
        <p id="check" style="font-size: 20px">刷新</p>
    </div>
    <!-- 头部信息结束 -->
    <!--左侧排名-->
    <div style="left: 10px; top: 130px; width: 400px; height: 935px; position:absolute;">
        <div style="width: 400px;overflow-y: auto; height: 935px;border-radius: 0px; border: none; background-color: rgba(0, 0, 0, 0); background-image: url(layuiadmin/img/menu_bg_c.png); background-repeat: no-repeat; background-size: 100% 100%;">
            <div style="width: 400px; height: 40px;padding-top:15px;padding-bottom:15px;line-height:40px;text-align: center; color: #ffffff;font-weight: lighter; font-size: 26px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">
                预警消息列表
            </div>
            <div id="insert_message_html" style="display: flex;flex-direction: column;align-items: center;">
            </div>
        </div>
    </div>
    <!--左侧排名END-->
    <div style="left: 420px;top: 142px;position:absolute;width: 4px; height: 80px; border: none; background: rgb(0, 94, 255); border-radius: 0px;"></div>
    <!-- 地图左下的图标信息 -->
    <div  style="left: 420px; top: 142px;position:absolute; width: 1450px; height: 160px; z-index: 51;border-bottom:10px solid rgba(0, 123, 255, 0.4);background-image: url(layuiadmin/img/map_bg.png); background-repeat: no-repeat; background-size: 100% 100%;">
        <div style="height:160px;width:1450px;" >
            <ul>
                <c:forEach items="${equipmentCates}" var="equipmentCate">
                <li class="menu_item" style="float:left;color:#fff;height:80px;line-height:80px;text-align:center;">
                    <a href="<%=request.getContextPath()%>/map_view?id=${equipmentCate.id}" style="color: #F8F8F8" id="menu_item_warning${equipmentCate.id}"> <p  onclick="getEquipmentByCate(${equipmentCate.id},this)" style="cursor:pointer;padding-left:20px;padding-right:20px;margin-left:20px;font-size:20px;font-weight:bold;border-radius:10px;height:60px;line-height:60px;margin-top:10px;background:rgba(13, 57, 146, 0.3);">${equipmentCate.name} </p></a>
                </li>
                </c:forEach>
                <c:forEach items="${customizeGroups}" var="c">
                    <li class="menu_item" style="float:left;color:#fff;height:80px;line-height:80px;text-align:center;">
                        <a href="<%=request.getContextPath()%>/map_view?id=${c.id}" style="color: #F8F8F8" id="menu_item_warning${c.id}"> <p  onclick="getGroupById(${c.id},this)" style="cursor:pointer;padding-left:20px;padding-right:20px;margin-left:20px;font-size:20px;font-weight:bold;border-radius:10px;height:60px;line-height:60px;margin-top:10px;background:rgba(13, 57, 146, 0.3);">${c.groupName} </p></a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <!-- 地图左下的图标信息 -->
    <!--右侧图标信息-->

    <div  id="divPlugin" class="video_item" style="left:420px;top:320px;position:absolute; width: 1450px; border-radius: 0px; border: none; background-color: rgba(0, 0, 0, 0);">

    </div>
    <!--最右侧背景-->
    <div style="left: 1880px; top: 127px;position:absolute;  width: 17px; height: 764px;background-image: url(layuiadmin/img/right_bg.png); background-repeat: no-repeat; background-size: 100% 100%;"></div>
    <!--右侧图标信息END-->
</div>
<script src="layuiadmin/js/jquery.min.js"></script>
<script src="layuiadmin/layer/layer.js"></script>
<script src="layuiadmin/js/webVideoCtrl.js"></script>
<script type="text/javascript">
     $(document).ready(function(){
         //alert(${id});
         getEquipmentByCate(${id});
     });
    function init_HK(){
        if (-1 == WebVideoCtrl.I_CheckPluginInstall()) {
            alert("您还未安装过插件，双击开发包目录里的WebComponents.exe安装！");
            return;
        }
        // 初始化插件参数及插入插件
        WebVideoCtrl.I_InitPlugin(1450, 740, {
            iWndowType:2
        });
        WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin");
        // 检查插件是否最新
        if (-1 == WebVideoCtrl.I_CheckPluginVersion()) {
            alert("检测到新的插件版本，双击开发包目录里的WebComponents.exe升级！");
            return;
        }
    }

    /**
     * [getEquipmentByCate 获取设备列表根据]
     * @param  {[type]} cate [description]
     * @return {[type]}      [description]
     */
    function getEquipmentByCate(cate){
        init_HK();
        $.ajax({
            //请求方式
            type : "GET",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "map_view/equipment?cate="+cate,
            //请求成功
            success : function(result) {
                var html="";
                for (var k in result){

                    $.each(result[k],function(index,item){
                        clickLogin(item.ip,item.port,item.username,item.password,index);
                        clickRemoteConfig(item.ip,554);
                        if (item.id != null){
                            var status = "";
                            if(item.status === -100){
                                status = "异常";
                                color = "red";
                            }else{
                                status = "正常";
                                color = "green";
                            }
                           if(index === 0){
                               html += "<div style=\"left:960px;padding:5px;top:360px;position:absolute;background-color: white;z-index:9999;\">\n" +
                                   "        <p>设备名称:"+item.name+"</p>\n" +
                                   "        <p id='"+item.id+"' style='color: "+color+"'>设备状态:"+status+"</p>\n" +
                                   "    </div>";
                           }else if (index === 1){
                               html += "<div style=\"left:1690px;padding:5px;top:360px;position:absolute;background-color: white;z-index:9999;\">\n" +
                                   "        <p>设备名称:"+item.name+"</p>\n" +
                                   "        <p id='"+item.id+"' style='color: "+color+"'>设备状态:"+status+"</p>\n" +
                                   "    </div>";
                           }else if (index === 2){
                               html += "<div style=\"left:960px;padding:5px;top:730px;position:absolute;background-color: white;z-index:9999;\">\n" +
                                   "        <p>设备名称:"+item.name+"</p>\n" +
                                   "        <p id='"+item.id+"' style='color: "+color+"'>设备状态:"+status+"</p>\n" +
                                   "    </div>";
                           }else if (index === 3){
                               html += "<div style=\"left:1690px;padding:5px;top:730px;position:absolute;background-color: white;z-index:9999;\">\n" +
                                   "            <p>设备名称:"+item.name+"</p>\n" +
                                   "            <p id='"+item.id+"' style='color: "+color+"'>设备状态:"+status+"</p>\n" +
                                   "    </div>";
                           }

                            $("body").append(html);
                        }
                    });
                }
            },

            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    }
    function getGroupById(id){
         init_HK();
         $.ajax({
             //请求方式
             type : "GET",
             //请求的媒体类型
             contentType: "application/json;charset=UTF-8",
             //请求地址
             url : "map_view/group?id="+id,
             //请求成功
             success : function(result) {

                 for (var k in result){
                     $.each(result[k],function(index,item){
                         clickLogin(item.ip,item.port,item.username,item.password,index);
                         clickRemoteConfig(item.ip,554);
                     });
                 }
             },

             //请求失败，包含具体的错误信息
             error : function(e){
                 console.log(e.status);
                 console.log(e.responseText);
             }
         });
     }
    function clickLogin(szIP,szPort,szUsername,szPassword,iWndIndex) {
        if ("" == szIP || "" == szPort) {
            return;
        }
        var iRet = WebVideoCtrl.I_Login(szIP, 1, szPort, szUsername, szPassword, {
            async:true,
            success: function (xmlDoc) {
                clickStartRealPlay(szIP,iWndIndex);
            },
            error: function () {
            }
        });
    }

    // 退出
    function clickLogout(szip) {
        var szIP = szip;

        if (szIP == "") {
            return;
        }

        WebVideoCtrl.I_Logout(szIP);

    }
     // 远程配置库
     function clickRemoteConfig(ip,iDevicePort) {
         var szIP = ip,
             iDevicePort = parseInt(iDevicePort, 10) || "";

         if ("" == szIP) {
             return;
         }

         WebVideoCtrl.I_RemoteConfig(szIP, {
             iDevicePort: iDevicePort,
             iLan: 1
         });

     }

    // 开始预览
    function clickStartRealPlay(szIP,iWndIndex) {
        var oWndInfo = WebVideoCtrl.I_GetWindowStatus(iWndIndex);

        if ("" == szIP) {
            return;
        }

        if (oWndInfo != null) {// 已经在播放了，先停止
            WebVideoCtrl.I_Stop();
        }
        var iRet = WebVideoCtrl.I_StartRealPlay(szIP,{iWndIndex:iWndIndex});
        if (0 == iRet) {
            console.log("1");
        } else {
            szInfo = "开始预览失败！";
        }
    }
</script>
<script type="text/javascript">
    $(document).ready(function (){
        setTimeout(function(){
            fajax(null,show_status);
        }, 3000);
    });

    function fajax(data, callback) {
        $.ajax({
            url: "map_view/status",
            type: "post",
            dataType: "json",
            data: data,
            success: callback
        });
    }

    function onCheck(){
            var index = layer.confirm("确定刷新吗？", function () {
                layer.close(index);
                window.location.reload();
            })
    }

    $("body").on("mouseenter", ".x", function() {
        $(this).addClass('xuanzhuan')
    });
    $("body").on("mouseleave", ".x", function() {
        $(this).removeClass('xuanzhuan')
    });

    function show_status(ret){
        var data=ret.data;
        var html="";
        for (var k in ret){
        $.each(ret[k],function (index,entry) {
          if(entry.status == -100) {
              <%--html += "<a style='color: #cc0000' href='<%=request.getContextPath()%>/map_view?id="+entry.cate+"'>"+entry.cate+"-"+entry.name+"-"+index+"发现异常"+"</a>";--%>
              $("#menu_item_warning"+entry.cate).attr("style","color:red");
              html += "<div id='warn"+entry.id+"' style=\"margin-bottom:14px;position:relative;display: flex;flex-direction: row;width: 96%;height: 86px;background-color: #FFDDE1;border-radius: 8px;\">\n" +
                  "<div style=\"background-color: #DC2C37;border-radius: 8px;width: 36%;height: 100%;display: flex;align-items: center;justify-content: center;\">\n" +
                  "<img style=\"height: 40px;width: 40px;\" src=\"<%=request.getContextPath()%>/layuiadmin/img/help2.png\">\n" +
                  "</div>\n" +
                  "<div  style=\"width: 74%;height: 56px;display: flex;flex-direction: column;align-items: center;justify-content: space-between;padding-top: 14px;padding-bottom: 16px;\">\n" +
                  "    <p style=\"width:86%;color: #DC2C37;font-size: 20px;\">"+"<a href='<%=request.getContextPath()%>/map_view?id="+entry.cate+"'>"+entry.cate+"-"+entry.name+"-"+index+"发现异常"+"</a>"+"</p>\n" +
                  "</div>\n" +
                  "<img onclick=\"remove_x("+entry.id+","+entry.status+");\" class=\"x\" style=\"position: absolute;top: 8px;right: 8px;width: 12px;height: 12px;\" src=\"<%=request.getContextPath()%>/layuiadmin/img/circle.png\"></div>";

          }
              $("#insert_message_html").append(html);
        });
        }
        setTimeout(function(){fajax(null,show_status,false);},3000);
    }
</script>
<script type="text/javascript">
    function remove_x(id,status) {
        layer.confirm("确定移除该条预警信息吗？",function () {
            var status_a = "";
            if (status === -100){
                status_a = 1;
            }
            $.post("map_view/delAbNormal",{id:id,status:status_a},function (result) {
               if(result === "success"){
                   layer.msg("已移除！");
                   location.reload();
               }else{
                   console.log(response.body);
               }
            });
        });
        event.stopPropagation();
    }
</script>
<script type="text/javascript">
    $(document).ready(function () {
        var width = $(window).width();
        var height = $(window).height();
        $(".transform_container").css("transform", "scale(" + width / 1920 + "," + height / 1080 + " )").css("left",width / 1920*300).css("top",height/1080*150);
        $("#container").css("width",width / 1920*740).css("height",height / 1080*740).css("left",width / 1920*285).css("top",height / 1080*135);
    })
    $(window).resize(function () {
        var width = window.innerWidth;
        var height = window.innerHeight;
        $(".transform_container").css("transform", "scale(" + width / 1920 + "," + height / 1080 + " )").css("left",width / 1920*300).css("top",height/1080*150);
        $("#container").css("width",width / 1920*740).css("height",height / 1080*740).css("left",width / 1920*285).css("top",height / 1080*135);
    })
</script>
<script type="text/javascript">
    $(function () {
        setInterval("startTime()", 1000);
    });
    function startTime() {
        var today = new Date();
        var y = today.getFullYear();
        var M = today.getMonth() + 1;
        var d = today.getDate();
        var w = today.getDay();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        var week = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
        // add a zero in front of numbers<10
        m = checkTime(m);
        s = checkTime(s);
        $('#time_show').html('现在是：' + h + ':' + m + ':' + s + ' ' + y + '年' + M + '月' + d + "日 " + week[w]);//可改变格式
    }
    function checkTime(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }
</script>
</body>
</html>
