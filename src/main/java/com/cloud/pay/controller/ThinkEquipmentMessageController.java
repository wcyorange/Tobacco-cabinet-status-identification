package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.ThinkEquipmentCate;
import com.cloud.pay.entity.ThinkEquipmentMessage;
import com.cloud.pay.service.ThinkEquipmentCateService;
import com.cloud.pay.service.ThinkEquipmentMessageService;
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
public class ThinkEquipmentMessageController {

    @Autowired
    ThinkEquipmentMessageService thinkEquipmentMessageService;

    @RequestMapping("/equipmentMessage/list")
    public String list(){return "equipmentMessageList";}

    @RequestMapping("/equipmentMessage/listjson")
    @ResponseBody
    public JSONObject findAll(ThinkEquipmentMessage equipmentMessage, Integer page, Integer limit){
        List<ThinkEquipmentMessage> lives = thinkEquipmentMessageService.findByPage(equipmentMessage, page, limit);
        int count=thinkEquipmentMessageService.count(equipmentMessage);
        JSONObject json = new JSONObject();
        json.put("msg","");
        json.put("code","");
        json.put("data",lives);//list
        json.put("count",count);//count值
        return json;
    }

    @RequestMapping("/equipmentMessage/add")
    public String addone(){
        return "equipmentMessageAdd";
    }


    @RequestMapping("/equipmentMessage/insert")
    @ResponseBody
    public JSONObject insert(ThinkEquipmentMessage equipmentMessage){
        int ret = 0;
        JSONObject json = new JSONObject();
        equipmentMessage.setCreateTime(TimeUtil.getCurrentTime());
        equipmentMessage.setDeleteStatus((byte) 0);
        ret = thinkEquipmentMessageService.insert(equipmentMessage);
        if(ret > 0){
            json.put("status","succ");
            json.put("msg","新增成功");
        }else{
            json.put("status","error");
            json.put("msg","新增失败");
        }
        return json;
    }


    @RequestMapping("/equipmentMessage/delete")
    @ResponseBody
    public JSONObject delete(ThinkEquipmentMessage equipmentMessage,Integer id){
        int ret = 0;
        JSONObject json = new JSONObject();
        equipmentMessage.setId(id);
        equipmentMessage.setDeleteTime(TimeUtil.getCurrentTime());
        equipmentMessage.setDeleteStatus((byte) 1);
        ret = thinkEquipmentMessageService.delete(equipmentMessage);
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

    @RequestMapping("/equipmentMessage/editForm")
    public String updateForm(Integer id, Model model, HttpServletRequest request){
        ThinkEquipmentMessage equipmentMessage = thinkEquipmentMessageService.findById(id);
        request.getSession().setAttribute("id",equipmentMessage.getId());
        model.addAttribute("equipmentMessage",equipmentMessage);
        return "equipmentMessageEdit";
    }

    @RequestMapping("/equipmentMessage/edit")
    @ResponseBody
    public JSONObject update(ThinkEquipmentMessage equipmentMessage,Model model,HttpServletRequest request){
        int ret = 0;
        Integer id = (Integer) request.getSession().getAttribute("id");
        equipmentMessage.setId(id);
        equipmentMessage.setUpdateTime(TimeUtil.getCurrentTime());
        System.err.println(equipmentMessage.getUpdateTime());
        ret = thinkEquipmentMessageService.update(equipmentMessage);
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
