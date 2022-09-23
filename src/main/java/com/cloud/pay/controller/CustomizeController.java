package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.CustomizeGroup;
import com.cloud.pay.entity.ThinkEquipment;
import com.cloud.pay.service.CustomizeGroupService;
import com.cloud.pay.service.FlatformAccountService;
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
 * @date 2020/10/31
 */

@Controller
public class CustomizeController {
    @Autowired
    private CustomizeGroupService customizeGroupService;
    @Autowired
    private FlatformAccountService flatformAccountService;
    @Autowired
    private ThinkEquipmentService thinkEquipmentService;

    @RequestMapping("/cg/list")
    public String list(){return "customizeGroupList";}

    @RequestMapping("/cg/listjson")
    @ResponseBody
    public JSONObject findAll(CustomizeGroup customizeGroup, Integer page, Integer limit,HttpServletRequest request){

        String u_name = (String) request.getSession().getAttribute("u_name");
        if (u_name.equals("admin")){
            List<CustomizeGroup> lives = customizeGroupService.findByPage(customizeGroup, page, limit);
            int count=customizeGroupService.count(customizeGroup);
            JSONObject json = new JSONObject();
            json.put("msg","");
            json.put("code","");
            json.put("data",lives);//list
            json.put("count",count);//count值
            return json;
        }else {
            customizeGroup.setuName(u_name);
            List<CustomizeGroup> list = customizeGroupService.findByuName(customizeGroup,page,limit);
            int count=customizeGroupService.count(customizeGroup);
            JSONObject json = new JSONObject();
            json.put("msg","");
            json.put("code","");
            json.put("data",list);//list
            json.put("count",count);//count值
            return json;
        }
    }

    @RequestMapping("/cg/addForm")
    public String addone(ThinkEquipment thinkEquipment,Model model){
        List<ThinkEquipment> list = thinkEquipmentService.findAll(thinkEquipment);
        model.addAttribute("list",list);
        return "customizeGroupAdd";
    }


    @RequestMapping("/cg/add")
    @ResponseBody
    public JSONObject insert(CustomizeGroup customizeGroup,HttpServletRequest request){
        int ret = 0;
        JSONObject json = new JSONObject();
        customizeGroup.setDeleteStatus(0);
        customizeGroup.setuName((String) request.getSession().getAttribute("u_name"));
        ret = customizeGroupService.insert(customizeGroup);
        if(ret > 0){
            json.put("status","succ");
            json.put("msg","新增成功");
        }else{
            json.put("status","error");
            json.put("msg","新增失败");
        }
        return json;
    }


    @RequestMapping("/cg/delete")
    @ResponseBody
    public JSONObject delete(CustomizeGroup customizeGroup,Integer id){
        int ret = 0;
        JSONObject json = new JSONObject();
        customizeGroup.setId(id);
        customizeGroup.setDeleteTime(TimeUtil.getCurrentTime());
        customizeGroup.setDeleteStatus(1);
        ret = customizeGroupService.delete(customizeGroup);
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

    @RequestMapping("/cg/updateForm")
    public String updateForm(Integer id, Model model, HttpServletRequest request,ThinkEquipment thinkEquipment){
        CustomizeGroup customizeGroup = customizeGroupService.findById(id);
        request.getSession().setAttribute("id",customizeGroup.getId());
        model.addAttribute("CustomizeGroup",customizeGroup);
        List<ThinkEquipment> thinkEquipments = thinkEquipmentService.findAll(thinkEquipment);
        model.addAttribute("thinkEquipments",thinkEquipments);
        return "customizeGroupEdit";
    }

    @RequestMapping("/cg/update")
    @ResponseBody
    public JSONObject update(CustomizeGroup customizeGroup,Model model,HttpServletRequest request){
        int ret = 0;
        Integer id = (Integer) request.getSession().getAttribute("id");
        customizeGroup.setId(id);
        ret = customizeGroupService.update(customizeGroup);
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
