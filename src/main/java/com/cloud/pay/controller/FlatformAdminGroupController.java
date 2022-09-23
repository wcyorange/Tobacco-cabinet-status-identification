package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.*;
import com.cloud.pay.service.FlatformAccountService;
import com.cloud.pay.service.FlatformAdminGroupService;
import com.cloud.pay.service.FlatformAdminMenuService;
import com.cloud.pay.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FlatformAdminGroupController {

    @Autowired
    private FlatformAdminGroupService flatformAdminGroupService;
    @Autowired
    private FlatformAdminMenuService flatformAdminMenuService;
    @Autowired
    private FlatformAccountService flatformAccountService;
    /**
    *@Descritpion:角色列表
    *@Param:[]
    *@return:java.lang.String
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("group/list")
    public String grouplist(){
        return "grouplist";
    }
    /**
    *@Description:新增平台页面
    *@Param:
    *@return:
    *@Author:丁宁
    *@Data:2019/8/9
    *
    **/
    @RequestMapping("group/addaccount")
    public String groupadd(){
        return "groupaddaccount";
    }
    /**
     *@Description:新增代理商页面
     *@Param:
     *@return:
     *@Author:丁宁
     *@Data:2019/8/9
     *
     **/
    @RequestMapping("group/addagent")
    public String groupagent(){
        return "groupaddagent";
    }
    /**
    *@Description:
    *@Param:
    *@return:
    *@Author:丁宁
    *@Data:2019/8/9
    *
    **/
    @RequestMapping("group/listjson")
    @ResponseBody
    public JSONObject listjson(FlatformAdminGroup flatformAdminGroup, Integer page, Integer limit){

        List<FlatformAdminGroup> flatformAdminGroups = flatformAdminGroupService.findByPage(flatformAdminGroup,page,limit);
        int count = flatformAdminGroupService.count(flatformAdminGroup);
        JSONObject json = new JSONObject();
        json.put("msg","");
        json.put("code","");
        json.put("data",flatformAdminGroups);//list
        json.put("count",count);//count值
        return json;

    }
    /**
    *@Descritpion:单个角色返回
    *@Param:[model]
    *@return:java.lang.String
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("group/echodata")
    public String echodata(Integer id,Model model){
        FlatformAdminGroup flatformAdminGroup  = flatformAdminGroupService.findById(id);
        model.addAttribute("flatformAdminGroup",flatformAdminGroup);
        return "groupedit";
    }
    /**
    *@Descritpion:角色新增接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("group/insert")
    @ResponseBody
    public JSONObject insert(Authentication authentication, FlatformAdminGroup flatformAdminGroup){
        int ret = 0;
        JSONObject json = new JSONObject();
        FlatformAccount flatformAccount = flatformAccountService.findByAccount(authentication.getName());
        flatformAdminGroup.setuId(flatformAccount.getuId());
        flatformAdminGroup.setCreateTime(TimeUtil.getCurrentTime());
        ret = flatformAdminGroupService.insert(flatformAdminGroup);
        if(ret>0){
            json.put("status","succ");
        }else{
            json.put("status","error");
        }
        return json;
    }
    /**
    *@Descritpion:角色更新接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("group/update")
    @ResponseBody
    public JSONObject update(FlatformAdminGroup flatformAdminGroup){
        JSONObject json = new JSONObject();
        int ret = 0;
        ret = flatformAdminGroupService.update(flatformAdminGroup);
        if(ret>0){
            json.put("status","succ");
        }else{
            json.put("status","error");
        }
        return json;
    }
    /**
    *@Descritpion:角色删除接口
    *@Param:[]
    *@return:com.alibaba.fastjson.JSONObject
    *@Author:丁宁
    *@Data:2019/7/31
    **/
    @RequestMapping("group/delete")
    @ResponseBody
    public JSONObject delete(FlatformAdminGroup flatformAdminGroup){

        JSONObject json = new JSONObject();
        int ret = 0;
        flatformAdminGroup.setDeleteStatus(1);
        flatformAdminGroup.setDeleteTime(TimeUtil.getCurrentTime());
        ret = flatformAdminGroupService.delete(flatformAdminGroup);
        if(ret>0){
            json.put("status","succ");
        }else{
            json.put("status","error");
        }
        return json;
    }
    /**
    *@Description:查找个人权限
    *@Param:[id]
    *@return:
    *@Author:丁宁
    *@Data:2019/8/9
    *
    **/

    @RequestMapping("group/testjson")
    @ResponseBody
    public JSONObject testjson(Integer id,String auType){
        JSONObject json = new JSONObject();
        FlatformAdminGroup flatformAdminGroup = flatformAdminGroupService.findById(id);
        String auth = flatformAdminGroup.getAgAuth();
        String[] authlist = auth.split(",");
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if(authlist!=null&&authlist.length>0)
            for(int i = 0;i<authlist.length;i++){
                arrayList.add(Integer.parseInt(authlist[i]));
            }
        Tree tree = new Tree();
        List<MenuTree> menuTreelist = new ArrayList<>();
        List<FlatformAdminMenu> flatformAdminMenu = flatformAdminMenuService.findByType(auType);
        if(flatformAdminMenu!=null&&flatformAdminMenu.size()>0) {
            for (int i = 0; i < flatformAdminMenu.size(); i++) {
                MenuTree menuTree = new MenuTree();
                menuTree.setId(flatformAdminMenu.get(i).getAuId());
                menuTree.setName(flatformAdminMenu.get(i).getAuTitle());
                menuTree.setPid(flatformAdminMenu.get(i).getAuPId());
                menuTreelist.add(menuTree);
            }
        }
        tree.setList(menuTreelist);
        tree.setCheckedId(arrayList);
        json.put("code","0");
        json.put("msg","获取成功");
        json.put("data",tree);
        return json;
    }
    /**
    *@Description:查找全部权限
    *@Param:[id]
    *@return:
    *@Author:丁宁
    *@Data:2019/8/9
    *
    **/

    @RequestMapping("group/treeAll")
    @ResponseBody
    public JSONObject treeAll(Integer id,String auType){
        JSONObject json = new JSONObject();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Tree tree = new Tree();
        List<MenuTree> menuTreelist = new ArrayList<>();
        List<FlatformAdminMenu> flatformAdminMenu = flatformAdminMenuService.findByType(auType);
        if(flatformAdminMenu!=null&&flatformAdminMenu.size()>0)
        for(int i = 0;i<flatformAdminMenu.size();i++) {
            MenuTree menuTree = new MenuTree();
            menuTree.setId(flatformAdminMenu.get(i).getAuId());
            menuTree.setName(flatformAdminMenu.get(i).getAuTitle());
            menuTree.setPid(flatformAdminMenu.get(i).getAuPId());
            menuTreelist.add(menuTree);
        }
        tree.setList(menuTreelist);
        tree.setCheckedId(arrayList);
        json.put("code","0");
        json.put("msg","获取成功");
        json.put("data",tree);
        return json;
    }
}
