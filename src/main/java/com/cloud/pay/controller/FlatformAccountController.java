package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.entity.FlatformAdminGroup;
import com.cloud.pay.service.FlatformAccountService;
import com.cloud.pay.service.FlatformAdminGroupService;
import com.cloud.pay.util.MD5Util;
import com.cloud.pay.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class FlatformAccountController {

    @Autowired
    private FlatformAccountService flatformAccountService;
    @Autowired
    private FlatformAdminGroupService flatformAdminGroupService;

    @RequestMapping("/index/bg_index")
    public String bg_index(){
        return "bg_index";
    }
    @RequestMapping("/user")
    @ResponseBody
    public JSONArray user(FlatformAccount flatformAccount, Integer currentPage, Integer pageSize){
        List<FlatformAccount> flatformAccountList = flatformAccountService.findByPage(flatformAccount,1,1);
        JSONArray json = (JSONArray) JSONArray.toJSON(flatformAccountList);
        return json;
    }
    /**
    *@Descritpion:用户列表
    *@Param:[model]
    *@return:java.lang.String
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("/user/list")
    public String userlist(FlatformAccount flatformAccount,Integer currentPage, Integer pageSize,Model model){
        List<FlatformAccount> flatformAccountList = flatformAccountService.findByPage(flatformAccount,currentPage,pageSize);
        model.addAttribute("flatformAccountList",flatformAccountList);
        return "list";
    }
    /**
     *@Description:分页查询json接口
     *@Param:[flatformAccount]
     *@return:
     *@Author:丁宁
     *@Data:2019/8/2
     *
     **/

    @RequestMapping("/user/listjson")
    @ResponseBody
    public JSONObject listjson(FlatformAccount flatformAccount, Integer page, Integer limit){
        List<FlatformAccount> flatformAccounts = flatformAccountService.findByPage(flatformAccount,page,limit);
        int count = flatformAccountService.count(flatformAccount);
        JSONObject json = new JSONObject();
        json.put("msg","");
        json.put("code","");
        json.put("data",flatformAccounts);//list
        json.put("count",count);//count值
        return json;
    }
    /**
    *@Descritpion:回显单个用户数据
    *@Param:[model]
    *@return:java.lang.String
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("/user/add")
    public String userechodata(FlatformAdminGroup flatformAdminGroup,Integer id,Model model) {
        List<FlatformAdminGroup> flatformAdminGroups = flatformAdminGroupService.findAll(flatformAdminGroup);
        model.addAttribute("flatformAdminGroups", flatformAdminGroups);

        return "add";
    }
    /**
    *@Descritpion:回显用户数据
    *@Param:
    *@return:
    *@Author:丁宁
    *@Data:2019/8/5
    **/
    @RequestMapping("/user/echodata")
    public String echodata(FlatformAdminGroup flatformAdminGroup,Integer uId,Model model){
        FlatformAccount flatformAccount = flatformAccountService.findById(uId);
        List<FlatformAdminGroup> flatformAdminGroups = flatformAdminGroupService.findAll(flatformAdminGroup);
        model.addAttribute("flatformAccount",flatformAccount);
        model.addAttribute("flatformAdminGroups",flatformAdminGroups);
        return "edit";
    }
    /**
    *@Descritpion:检测当前登录名是否被使用
    *@Param:
    *@return:
    *@Author:丁宁
    *@Data:2019/8/5
    **/
    @RequestMapping("user/check")
    @ResponseBody
    public boolean CheckUser(String account){
        FlatformAccount flatformAccount = flatformAccountService.findByAccount(account);
        if(flatformAccount!=null){
            return true;
        }else{
            return false;
        }
    }
    /**
    *@Descritpion:用户insert接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("/user/insert")
    @ResponseBody
    public JSONObject insert(FlatformAccount flatformAccount){
        if(flatformAccount.getStatus()==null){
            flatformAccount.setStatus(1);
        }
        int ret = 0;
        HashMap<String, Object> map = new HashMap<String, Object>();
        flatformAccount.setDeleteStatus(0);
        flatformAccount.setuLoginPwd(MD5Util.encode(flatformAccount.getuLoginPwd()));
        ret = flatformAccountService.insert(flatformAccount);
        if(ret>0){
            map.put("status","succ");
        }else{
            map.put("status","error");
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(map);
        return json;
    }
    /**
    *@Descritpion:用户update接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("/user/update")
    @ResponseBody
    public JSONObject update(FlatformAccount flatformAccount){
        if(flatformAccount.getStatus()==null){
            flatformAccount.setStatus(1);
        }
        int ret = 0;
        HashMap<String, Object> map = new HashMap<String, Object>();
        flatformAccount.setuLoginPwd(MD5Util.encode(flatformAccount.getuLoginPwd()));
        ret = flatformAccountService.update(flatformAccount);
        if(ret>0){
            map.put("status","succ");
        }else{
            map.put("status","error");
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(map);
        return json;
    }
    /**
    *@Descritpion:用户删除接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("/user/delete")
    @ResponseBody
    public JSONObject delete(Integer uId){
        int ret = 0;
        JSONObject json = new JSONObject();
        FlatformAccount flatformAccount = new FlatformAccount();
        flatformAccount.setuId(uId);
        flatformAccount.setDeleteTime(TimeUtil.getCurrentTime());
        ret = flatformAccountService.delete(flatformAccount);
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
    *@Description:查询parentId的区域
    *@Param:
    *@return:
    *@Author:丁宁
    *@Data:2019/8/13
    *
    **/
   /* @RequestMapping("user/city")
    @ResponseBody
    public JSONObject findcity(Integer parentId){

        JSONObject json = new JSONObject();
        List<FlatformEnterprise> flatformEnterprises = flatformEnterpriseService.findByParantId(parentId);
        json.put("flatformEnterprises",flatformEnterprises);
        return json;
    }*/
}
