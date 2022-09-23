package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.ThinkEquipment;
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
public class ThinkEquipmentController {

    @Autowired
    ThinkEquipmentService thinkEquipmentService;

    @RequestMapping("/equipment/list")
    public String list(){return "equipmentList";}

    @RequestMapping("/equipment/listjson")
    @ResponseBody
    public JSONObject findAll(ThinkEquipment equipment, Integer page, Integer limit){
        List<ThinkEquipment> lives = thinkEquipmentService.findByPage(equipment, page, limit);
        int count=thinkEquipmentService.count(equipment);
        JSONObject json = new JSONObject();
        json.put("msg","");
        json.put("code","");
        json.put("data",lives);//list
        json.put("count",count);//count值
        return json;
    }

    @RequestMapping("/equipment/add")
    public String addone(){
        return "equipmentAdd";
    }


    @RequestMapping("/equipment/insert")
    @ResponseBody
    public JSONObject insert(ThinkEquipment equipment){
        int ret = 0;
        JSONObject json = new JSONObject();
        equipment.setCreatTime(TimeUtil.getCurrentTime());
        equipment.setDeleteStatus((byte) 0);
        ret = thinkEquipmentService.insert(equipment);
        if(ret > 0){
            json.put("status","succ");
            json.put("msg","新增成功");
        }else{
            json.put("status","error");
            json.put("msg","新增失败");
        }
        return json;
    }


    @RequestMapping("/equipment/delete")
    @ResponseBody
    public JSONObject delete(ThinkEquipment equipment,Integer id){
        int ret = 0;
        JSONObject json = new JSONObject();
        equipment.setId(id);
        equipment.setDeleteTime(TimeUtil.getCurrentTime());
        equipment.setDeleteStatus((byte) 1);
        ret = thinkEquipmentService.delete(equipment);
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

    @RequestMapping("/equipment/editForm")
    public String updateForm(Integer id, Model model, HttpServletRequest request){
        ThinkEquipment equipment = thinkEquipmentService.findById(id);
        request.getSession().setAttribute("id",equipment.getId());
        model.addAttribute("equipment",equipment);
        return "equipmentEdit";
    }

    @RequestMapping("/equipment/edit")
    @ResponseBody
    public JSONObject update(ThinkEquipment equipment,Model model,HttpServletRequest request){
        int ret = 0;
        Integer id = (Integer) request.getSession().getAttribute("id");
        equipment.setId(id);
        equipment.setUpdateTime(TimeUtil.getCurrentTime());
        System.err.println(equipment.getUpdateTime());
        ret = thinkEquipmentService.update(equipment);
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
