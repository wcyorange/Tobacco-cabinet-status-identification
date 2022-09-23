package com.cloud.pay.controller;


import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.FlatformCostFlow;
import com.cloud.pay.entity.FlatformOrder;
import com.cloud.pay.entity.FlatformPartner;
import com.cloud.pay.entity.FlatformTaxType;
import com.cloud.pay.service.FlatformCostFlowService;
import com.cloud.pay.service.FlatformOrderService;
import com.cloud.pay.service.FlatformPartnerService;
import com.cloud.pay.service.FlatformTaxTypeService;
import com.cloud.pay.util.PropertiesUtil;
import com.cloud.pay.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("costflow")
public class FlatformCostFlowController {

    @Autowired
    private FlatformCostFlowService flatformCostFlowService;
    @Autowired
    private FlatformOrderService flatformOrderService;
    @Autowired
    private FlatformPartnerService flatformPartnerService;
    @Autowired
    private FlatformTaxTypeService flatformTaxTypeService;


    @RequestMapping("/view")
    public String view(){

        return "costViewPartner";
    }

    /**
     * 查看消费信息列表
     * @param model
     * @param parId
     * @return
     */
    @RequestMapping("/viewlist")
    public String costViewList(Model model,Integer parId){
        model.addAttribute("parId",parId);
        return "flatformCostList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public JSONObject findAllAdvance(FlatformCostFlow flatformCostFlow, Integer page, Integer limit){
        List<FlatformCostFlow> flatformCostFlows = flatformCostFlowService.findall(flatformCostFlow,page,limit);
        HashMap<String,String> hashMap = PropertiesUtil.findAll();
        Iterator iter = hashMap.entrySet().iterator();
        for (int i=0;i<flatformCostFlows.size();i++) {
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (Integer.valueOf(String.valueOf(entry.getKey())) ==flatformCostFlows.get(i).getCfType()){
                    flatformCostFlows.get(i).setTypeName(String.valueOf(entry.getValue()));
                }
            }
        }
        int count = flatformCostFlowService.count(flatformCostFlow);
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
    public String edit(Integer id,Model model){
        FlatformCostFlow flatformCostFlow = flatformCostFlowService.findById(id);
        model.addAttribute("flatformCostFlow",flatformCostFlow);
        return null;
    }

    @RequestMapping("insert")
    @ResponseBody
    public JSONObject insert(FlatformCostFlow flatformCostFlow){
        JSONObject json = new JSONObject();
        int ret = flatformCostFlowService.insert(flatformCostFlow);
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
    public JSONObject update(FlatformCostFlow flatformCostFlow){

        JSONObject json = new JSONObject();
        int ret = flatformCostFlowService.update(flatformCostFlow);
        if(ret>0){
            json.put("status","succ");
            json.put("msg","更新成功");
        }else{
            json.put("status","error");
            json.put("msg","更新失败");
        }
        return json;
    }

    /**
     * 签订合同
     * @param flatformCostFlow
     * @return
     */
    @RequestMapping("signIn")
    @ResponseBody
    public JSONObject signInContract(FlatformCostFlow flatformCostFlow){
        int ret=0;
        JSONObject json = new JSONObject();
        FlatformOrder flatformOrder = new FlatformOrder();
        FlatformCostFlow flatformCostFlow1=flatformCostFlowService.findById(flatformCostFlow.getCfId());
        FlatformPartner flatformPartner=flatformPartnerService.findById(flatformCostFlow1.getParId());
        if(flatformCostFlow1.getStatus()==1) {
            ret = flatformCostFlowService.update(flatformCostFlow);
            flatformOrder.setOrderId(String.valueOf(System.currentTimeMillis()));
            flatformOrder.setOrderName(flatformCostFlow.getCusName() + "消费");
            flatformOrder.setCreateTime(TimeUtil.getCurrentTime());
            flatformOrder.setId(flatformCostFlow1.getParId());
            flatformOrder.setOrderType(2);
            if(flatformPartner.getpId()!=0){                    //有上级合伙人
                flatformOrder.setParId(flatformCostFlow1.getParId());
                FlatformTaxType flatformTaxType=flatformTaxTypeService.findByType(2);
                flatformOrder.setMoney(flatformTaxType.getMoney()*flatformTaxType.getTax());
                flatformOrder.setDeleteStatus(0);
                flatformOrderService.insertOrder(flatformOrder);

                flatformOrder.setOrderName("上级合伙人分润");
                flatformOrder.setOrderType(2);
                flatformOrder.setParId(flatformPartner.getpId());
                FlatformTaxType flatformTaxType1=flatformTaxTypeService.findByType(2);
                flatformOrder.setMoney(flatformTaxType.getMoney()*flatformTaxType.getpTax());
                flatformOrder.setDeleteStatus(0);
                flatformOrder.setOrderId(String.valueOf(System.currentTimeMillis()));
                flatformOrderService.insertOrder(flatformOrder);
            }else{                                              //无上级合伙人
                flatformOrder.setParId(flatformCostFlow1.getParId());
                FlatformTaxType flatformTaxType=flatformTaxTypeService.findByType(2);
                flatformOrder.setMoney(flatformTaxType.getMoney()*flatformTaxType.getTax());
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

    @RequestMapping("delete")
    @ResponseBody
    public JSONObject update(Integer id){

        JSONObject json = new JSONObject();
        int ret = flatformCostFlowService.delete(id);
        if(ret>0){
            json.put("status","succ");
            json.put("msg","删除成功");
        }else{
            json.put("status","error");
            json.put("msg","删除失败");
        }
        return json;
    }
}
