package com.cloud.pay.controller;


import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.entity.FlatformAdminGroup;
import com.cloud.pay.entity.FlatformAdminMenu;
import com.cloud.pay.service.FlatformAccountService;
import com.cloud.pay.service.FlatformAdminGroupService;
import com.cloud.pay.service.FlatformAdminMenuService;
import com.cloud.pay.util.GetIp;
import com.cloud.pay.util.MakeMenuTree;
import com.cloud.pay.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private FlatformAccountService flatformAccountService;
    @Autowired
    private FlatformAdminGroupService flatformAdminGroupService;
    @Autowired
    private FlatformAdminMenuService flatformAdminMenuService;

    /**
    *@Descritpion:登录页面
    *@Param:[model]
    *@return:java.lang.String
    *@Author:丁宁
    *@Data:2019/7/30
    **/
    @RequestMapping("/login")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/regist")
    public String register(Model model){
        return "regsit";
    }
    /**
    *@Description:权限树生成
    *@Param:[authentication, mdoel]
    *@return:
    *@Author:丁宁
    *@Data:2019/7/28
    *
    **/
    @RequestMapping("index")
    public String index(Authentication authentication, Model mdoel, HttpSession session, HttpServletRequest request){
        FlatformAccount flatformAccount = flatformAccountService.findByAccount(authentication.getName());
        flatformAccount.setLastLoginIp(GetIp.getIp(request));
        flatformAccount.setLastLoginTime(TimeUtil.getCurrentTime());
        flatformAccountService.update(flatformAccount);
        FlatformAdminGroup group_info = flatformAdminGroupService.findById(flatformAccount.getAgId());
        MakeMenuTree menuTree = new MakeMenuTree();
        List<FlatformAdminMenu> menu_list = flatformAdminMenuService.findAll();
        menu_list=menuTree.menuList(menu_list);
        List<FlatformAdminMenu> auth_list = menuTree.makeAuthList(menu_list,group_info);
        session.setAttribute("SESSIONAUTHLIST",auth_list);
        session.setAttribute("user",flatformAccount);
        return "index";
    }
    /**
    *@Descritpion:注销
    *@Param:[]
    *@return:java.lang.String
    *@Author:丁宁
    *@Data:2019/7/30
    **/
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(){
        return "login";
    }
}
