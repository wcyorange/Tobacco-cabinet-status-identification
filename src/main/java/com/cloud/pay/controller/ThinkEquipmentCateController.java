package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.ThinkEquipment;
import com.cloud.pay.entity.ThinkEquipmentCate;
import com.cloud.pay.service.ThinkEquipmentCateService;
import com.cloud.pay.service.ThinkEquipmentService;
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
public class ThinkEquipmentCateController {

    @Autowired
    ThinkEquipmentCateService thinkEquipmentCateService;

    @RequestMapping("/equipmentCate/list")
    public String list(){return "equipmentCateList";}

    @RequestMapping("/equipmentCate/listjson")
    @ResponseBody
    public JSONObject findAll(ThinkEquipmentCate equipmentCate, Integer page, Integer limit){
        List<ThinkEquipmentCate> lives = thinkEquipmentCateService.findByPage(equipmentCate, page, limit);
        int count=thinkEquipmentCateService.count(equipmentCate);
        JSONObject json = new JSONObject();
        json.put("msg","");
        json.put("code","");
        json.put("data",lives);//list
        json.put("count",count);//count值
        return json;
    }

    @RequestMapping("/equipmentCate/add")
    public String addone(){
        return "equipmentCateAdd";
    }


    @RequestMapping("/equipmentCate/insert")
    @ResponseBody
    public JSONObject insert(ThinkEquipmentCate equipmentCate){
        int ret = 0;
        JSONObject json = new JSONObject();
        equipmentCate.setCreatTime(TimeUtil.getCurrentTime());
        equipmentCate.setDeleteStatus((byte) 0);
        ret = thinkEquipmentCateService.insert(equipmentCate);
        if(ret > 0){
            json.put("status","succ");
            json.put("msg","新增成功");
        }else{
            json.put("status","error");
            json.put("msg","新增失败");
        }
        return json;
    }


    @RequestMapping("/equipmentCate/delete")
    @ResponseBody
    public JSONObject delete(ThinkEquipmentCate equipmentCate,Integer id){
        int ret = 0;
        JSONObject json = new JSONObject();
        equipmentCate.setId(id);
        equipmentCate.setDeleteTime(TimeUtil.getCurrentTime());
        equipmentCate.setDeleteStatus((byte) 1);
        ret = thinkEquipmentCateService.delete(equipmentCate);
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

    @RequestMapping("/equipmentCate/editForm")
    public String updateForm(Integer id, Model model, HttpServletRequest request){
        ThinkEquipmentCate equipmentCate = thinkEquipmentCateService.findById(id);
        request.getSession().setAttribute("id",equipmentCate.getId());
        model.addAttribute("equipmentCate",equipmentCate);
        return "equipmentCateEdit";
    }

    @RequestMapping("/equipmentCate/edit")
    @ResponseBody
    public JSONObject update(ThinkEquipmentCate thinkEquipmentCate,Model model,HttpServletRequest request){
        int ret = 0;
        Integer id = (Integer) request.getSession().getAttribute("id");
        thinkEquipmentCate.setId(id);
        thinkEquipmentCate.setUpdateTime(TimeUtil.getCurrentTime());
        System.err.println(thinkEquipmentCate.getUpdateTime());
        ret = thinkEquipmentCateService.update(thinkEquipmentCate);
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
