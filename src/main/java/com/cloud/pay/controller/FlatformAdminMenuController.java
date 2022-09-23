package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.entity.FlatformAdminMenu;
import com.cloud.pay.service.FlatformAccountService;
import com.cloud.pay.service.FlatformAdminMenuService;
import com.cloud.pay.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;


@Controller
public class FlatformAdminMenuController {

    @Autowired
    private FlatformAdminMenuService flatformAdminMenuService;
    @Autowired
    private FlatformAccountService flatformAccountService;
    /**
    *@Descritpion:菜单列表
    *@Param:[]
    *@return:java.lang.String
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("menu/list")
    public String list(){
        return "menulist";
    }
    /**
     *@Description:分页查询json接口
     *@Param:[FlatformAdminMenu]
     *@return:
     *@Author:丁宁
     *@Data:2019/8/7
     *
     **/
    @RequestMapping("/menu/listjson")
    @ResponseBody
    public JSONObject listjson(FlatformAdminMenu flatformAdminMenu, Integer page, Integer limit){
        List<FlatformAdminMenu> flatformAccounts = flatformAdminMenuService.findByPage(flatformAdminMenu,page,limit);
        int count = flatformAdminMenuService.count(flatformAdminMenu);
        JSONObject json = new JSONObject();
        json.put("msg","");
        json.put("code","");
        json.put("data",flatformAccounts);//list
        json.put("count",count);//count值
        return json;
    }
    /**
     *@Descritpion:新增菜单
     *@Param:[]
     *@return:java.lang.String
     *@Author:丁宁
     *@Data:2019/8/7
     **/
    @RequestMapping("/menu/add")
    public String menu(){
        return "menuadd";
    }
    /**
    *@Descritpion:菜单更新前回显
    *@Param:[]
    *@return:java.lang.String
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("menu/echodata")
    public String echodata(Integer id, Model model){
        FlatformAdminMenu flatformAdminMenu = flatformAdminMenuService.findById(id);
        model.addAttribute("flatformAdminMenu",flatformAdminMenu);
        return "menuedit";
    }
    /**
    *@Descritpion:菜单新增接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("menu/insert")
    @ResponseBody
    public JSONObject insert(Authentication authentication,FlatformAdminMenu flatformAdminMenu){
        int ret = 0;
        if(flatformAdminMenu.getAuClickable()==null){
            flatformAdminMenu.setAuClickable(1);
        }
        if(flatformAdminMenu.getAuState()==null){
            flatformAdminMenu.setAuState(1);
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        FlatformAccount flatformAccount = flatformAccountService.findByAccount(authentication.getName());
        flatformAdminMenu.setuId(flatformAccount.getuId());
        flatformAdminMenu.setCreateTime(TimeUtil.getCurrentTime());
        ret = flatformAdminMenuService.insert(flatformAdminMenu);
        if(ret>0){
            map.put("status","succ");
        }else{
            map.put("status","error");
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(map);
        return json;
    }
    /**
    *@Descritpion:菜单更新接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("menu/update")
    @ResponseBody
    public JSONObject update(FlatformAdminMenu flatformAdminMenu){
        if(flatformAdminMenu.getAuClickable()==null){
            flatformAdminMenu.setAuClickable(1);
        }
        if(flatformAdminMenu.getAuState()==null){
            flatformAdminMenu.setAuState(1);
        }
        int ret = 0;
        HashMap<String, Object> map = new HashMap<String, Object>();
        ret = flatformAdminMenuService.update(flatformAdminMenu);
        if(ret>0){
            map.put("status","succ");
        }else{
            map.put("status","error");
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(map);
        return json;
    }
    /**
    *@Descritpion:菜单删除接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("menu/delete")
    @ResponseBody
    public JSONObject delete(FlatformAdminMenu flatformAdminMenu){
        int ret = 0;
        JSONObject json = new JSONObject();
        flatformAdminMenu.setDeleteStatus(1);
        flatformAdminMenu.setDeleteTime(TimeUtil.getCurrentTime());
        ret = flatformAdminMenuService.delete(flatformAdminMenu);
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
    /**
    *@Descritpion:查询上级菜单名称
    *@Param:
    *@return:
    *@Author:丁宁
    *@Data:2019/8/7
    **/
    @RequestMapping("menu/findByPid")
    @ResponseBody
    public JSONObject findByPid(Integer auPId){
        JSONObject json = new JSONObject();
        FlatformAdminMenu flatformAdminMenu = flatformAdminMenuService.findByPid(auPId);
        if(flatformAdminMenu!=null){
            json.put("flatformAdminMenu",flatformAdminMenu);
            json.put("status","succ");
        }else{
            json.put("status","error");
        }
        return json;
    }
    /**
    *@Descritpion:查询一级菜单
    *@Param:
    *@return:
    *@Author:丁宁
    *@Data:2019/8/7
    **/
    @RequestMapping("menu/getMenuList")
    @ResponseBody
    public JSONObject findMenuList(Integer value){
        JSONObject json = new JSONObject();
        List<FlatformAdminMenu> flatformAdminMenus = flatformAdminMenuService.findByMenu(value);
        if(flatformAdminMenus!=null&&flatformAdminMenus.size()>0){
            json.put("flatformAdminMenus",flatformAdminMenus);
            json.put("status","succ");
        }else{
            json.put("status","error");
        }
        return json;
    }
}
