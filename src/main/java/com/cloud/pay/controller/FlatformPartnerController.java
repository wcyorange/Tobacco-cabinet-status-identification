package com.cloud.pay.controller;


import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.*;
import com.cloud.pay.service.*;
import com.cloud.pay.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("partner")
public class FlatformPartnerController {

    @Autowired
    private FlatformPartnerService flatformPartnerService;
    @Autowired
    private FlatformAccountService flatformAccountService;
    @Autowired
    private FlatformTaxTypeService flatformTaxTypeService;
    @Autowired
    private FlatformOrderService flatformOrderService;

    @RequestMapping("/list")
    public String PartnerList(){
        return "flatformPartnerList";
    }

    @RequestMapping("/listjson")
    @ResponseBody
    public JSONObject findAllAdvance(FlatformPartner flatformPartner, Integer page, Integer limit){
        List<FlatformPartner> flatformCostFlows = flatformPartnerService.findall(flatformPartner,page,limit);
        int count = flatformPartnerService.count(flatformPartner);
        JSONObject json = new JSONObject();
        json.put("msg","");
        json.put("code","");
        json.put("data",flatformCostFlows);//list
        json.put("count",count);//count值
        return json;
    }

    @RequestMapping("add")
    public String add(Model model){

        return null;
    }

    @RequestMapping("edit")
    public String edit(Model model,Integer id){
        FlatformPartner flatformPartner = flatformPartnerService.findById(id);
        model.addAttribute("flatformPartner",flatformPartner);
        return null;
    }

    @RequestMapping("insert")
    @ResponseBody
    public JSONObject insert(FlatformPartner flatformPartner){
        JSONObject json = new JSONObject();
        int ret = flatformPartnerService.insert(flatformPartner);
        if(ret>0){
            json.put("status","succ");
            json.put("msg","新增成功");
        }else{
            json.put("status","error");
            json.put("msg","新增失败");
        }
        return json;
    }

    @RequestMapping("update")
    @ResponseBody
    public JSONObject update(FlatformPartner flatformPartner){

        JSONObject json = new JSONObject();
        int ret = flatformPartnerService.update(flatformPartner);
        if(ret>0){
            json.put("status","succ");
            json.put("msg","更新成功");
        }else{
            json.put("status","error");
            json.put("msg","更新失败");
        }
        return json;
    }

    @RequestMapping("delete")
    @ResponseBody
    public JSONObject delete(Integer id){

        JSONObject json = new JSONObject();
        int ret = flatformPartnerService.delete(id);
        if(ret>0){
            json.put("status","succ");
            json.put("msg","删除成功");
        }else{
            json.put("status","error");
            json.put("msg","删除失败");
        }
        return json;
    }

    /**
     * 查看
     * @return
     */
    @RequestMapping("/findone")
    public String findone(Model model,Integer parId){
        FlatformPartner flatformPartner=flatformPartnerService.findById(parId);
        String uName=flatformAccountService.findById(flatformPartner.getuId()).getuName();
        if(flatformPartner.getpId()==0){
            model.addAttribute("parName","一级合伙人");
        }
        else{
            String parName=flatformPartnerService.findById(flatformPartner.getpId()).getParName();
            model.addAttribute("parName",parName);
        }
        model.addAttribute("uName",uName);
        model.addAttribute("flatformPartner",flatformPartner);
        return "checkPartner";
    }
    /**
     * 平台添加一级合伙人
     */
    @RequestMapping("/addPartner")
    @ResponseBody
    public JSONObject addPartner(HttpSession session, FlatformPartner flatformPartner){
        FlatformAccount agentAccount = (FlatformAccount) session.getAttribute("user");//获取当前登录用户信息
        JSONObject json = new JSONObject();
        int ret=0;
        if(flatformPartnerService.findByPhone(flatformPartner.getMobile())==null) {
            flatformPartner.setDeleteStatus(0);
            flatformPartner.setStatus(2);
            flatformPartner.setCreateTime(TimeUtil.getCurrentTime());
            flatformPartner.setuId(agentAccount.getuId());
            flatformPartner.setpId(0);
            ret = flatformPartnerService.insert(flatformPartner);
        }
        else{
            ret=-1;
        }
        if(ret>0){
            json.put("status","200");
            json.put("msg","添加成功");
        }else{
            json.put("status","error");
            json.put("msg","添加失败");
        }
        return json;
    }

    /**
     * 签订合同
     */
    @RequestMapping("signIn")
    @ResponseBody
    public JSONObject signIn(FlatformPartner flatformPartner){
        JSONObject json = new JSONObject();
        FlatformOrder flatformOrder =new FlatformOrder();
        int ret =0,ret1=0;
        FlatformPartner flatformPartner1=flatformPartnerService.findById(flatformPartner.getParId());
        if(flatformPartner1.getStatus()==1){
            ret = flatformPartnerService.update(flatformPartner);
            flatformOrder.setOrderId(String.valueOf(System.currentTimeMillis()));
            flatformOrder.setOrderName("添加合伙人");
            flatformOrder.setId(flatformPartner.getParId());
            flatformOrder.setOrderType(1);
            flatformOrder.setCreateTime(TimeUtil.getCurrentTime());
            if(flatformPartner1.getpId()!=0) {              //如果有上级合伙人
                flatformOrder.setParId(flatformPartner1.getpId());
                FlatformTaxType flatformTaxType=flatformTaxTypeService.findByType(1);
                flatformOrder.setMoney(flatformTaxType.getMoney()*flatformTaxType.getTax());
                flatformOrder.setDeleteStatus(0);
                flatformOrderService.insertOrder(flatformOrder);

            }else{                                          //如果没有上级合伙人
                flatformOrder.setParId(flatformPartner1.getParId());
                flatformOrder.setMoney((float) 0.00);
                flatformOrder.setDeleteStatus(0);
                flatformOrderService.insertOrder(flatformOrder);
            }


        }else{
            ret=0;
        }

        if(ret>0){
            json.put("status","succ");
            json.put("msg","更新成功");
        }else{
            json.put("status","error");
            json.put("msg","更新失败");
        }
        return json;
    }
}
