package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.ThinkEquipmentMessage;
import com.cloud.pay.entity.ThinkEquipmentVideo;
import com.cloud.pay.service.ThinkEquipmentMessageService;
import com.cloud.pay.service.ThinkEquipmentVideoService;
import com.cloud.pay.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 宁志洋
 */

@Controller
public class ThinkEquipmentVideoController {

    @Autowired
    ThinkEquipmentVideoService thinkEquipmentVideoService;

    @RequestMapping("/equipmentVideo/list")
    public String list(){return "equipmentVideoList";}

    @RequestMapping("/equipmentVideo/listjson")
    @ResponseBody
    public JSONObject findAll(ThinkEquipmentVideo equipmentVideo, Integer page, Integer limit){
        List<ThinkEquipmentVideo> lives = thinkEquipmentVideoService.findByPage(equipmentVideo, page, limit);
        int count=thinkEquipmentVideoService.count(equipmentVideo);
        JSONObject json = new JSONObject();
        json.put("msg","");
        json.put("code","");
        json.put("data",lives);//list
        json.put("count",count);//count值
        return json;
    }

    @RequestMapping("/equipmentVideo/add")
    public String addone(){
        return "equipmentVideoAdd";
    }


    @RequestMapping("/equipmentVideo/insert")
    @ResponseBody
    public JSONObject insert(ThinkEquipmentVideo equipmentVideo){
        int ret = 0;
        JSONObject json = new JSONObject();
        equipmentVideo.setCreateTime(TimeUtil.getCurrentTime());
        equipmentVideo.setDeleteStatus((byte) 0);
        ret = thinkEquipmentVideoService.insert(equipmentVideo);
        if(ret > 0){
            json.put("status","succ");
            json.put("msg","新增成功");
        }else{
            json.put("status","error");
            json.put("msg","新增失败");
        }
        return json;
    }


    @RequestMapping("/equipmentVideo/delete")
    @ResponseBody
    public JSONObject delete(ThinkEquipmentVideo equipmentVideo,Integer id){
        int ret = 0;
        JSONObject json = new JSONObject();
        equipmentVideo.setId(id);
        equipmentVideo.setDeleteTime(TimeUtil.getCurrentTime());
        equipmentVideo.setDeleteStatus((byte) 1);
        ret = thinkEquipmentVideoService.delete(equipmentVideo);
        if(ret>0){
            json.put("msg","");
            json.put("code","0");
            json.put("data","");//list
            json.put("count","");//count值
        }else{
            json.put("msg","");
            json.put("code","");
            json.put("data","");//list
            json.put("count","");//count值
        }
        return json;
    }

    @RequestMapping("/equipmentVideo/editForm")
    public String updateForm(Integer id, Model model, HttpServletRequest request){
        ThinkEquipmentVideo equipmentVideo = thinkEquipmentVideoService.findById(id);
        request.getSession().setAttribute("id",equipmentVideo.getId());
        model.addAttribute("equipmentVideo",equipmentVideo);
        return "equipmentVideoEdit";
    }

    @RequestMapping("/equipmentVideo/edit")
    @ResponseBody
    public JSONObject update(ThinkEquipmentVideo equipmentVideo,Model model,HttpServletRequest request){
        int ret = 0;
        Integer id = (Integer) request.getSession().getAttribute("id");
        equipmentVideo.setId(id);
        equipmentVideo.setUpdateTime(TimeUtil.getCurrentTime());
        ret = thinkEquipmentVideoService.update(equipmentVideo);
        request.getSession().invalidate();
        JSONObject json = new JSONObject();
        if (ret > 0){
            json.put("status","success");
        }else {
            json.put("status","error");
        }
        return json;
    }
}
