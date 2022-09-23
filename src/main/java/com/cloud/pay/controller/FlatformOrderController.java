package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.FlatformCostFlow;
import com.cloud.pay.entity.FlatformOrder;
import com.cloud.pay.entity.FlatformPartner;
import com.cloud.pay.entity.FlatformSource;
import com.cloud.pay.service.FlatformCostFlowService;
import com.cloud.pay.service.FlatformOrderService;
import com.cloud.pay.service.FlatformPartnerService;
import com.cloud.pay.service.FlatformSourceService;
import com.cloud.pay.util.PropertiesUtil;
//import org.apache.xmlbeans.impl.piccolo.util.FactoryServiceFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("order")
public class FlatformOrderController {

    @Autowired
    private FlatformPartnerService flatformPartnerService;
    @Autowired
    private FlatformOrderService flatformOrderService;
    @Autowired
    private FlatformCostFlowService flatformCostFlowService;
    @Autowired
    private FlatformSourceService flatformSourceService;

    /**
     * 签订成功合伙人列表
     * @return
     */
    @RequestMapping("/list")
    public String signInPartnerList(){
        return "signInPartnerList";
    }

    /**
     * 签订成功合伙人列表json
     * @param flatformPartner
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/listjson")
    @ResponseBody
    public JSONObject findAllAdvance(FlatformPartner flatformPartner, Integer page, Integer limit) {
        flatformPartner.setStatus(3);
        List<FlatformPartner> flatformCostFlows = flatformPartnerService.findall(flatformPartner, page, limit);
        for(int i=0;i<flatformCostFlows.size();i++){
            flatformCostFlows.get(i).setDealNumbers(flatformOrderService.countByParId(flatformCostFlows.get(i).getParId()));;
        }
        int count = flatformPartnerService.count(flatformPartner);
        JSONObject json = new JSONObject();
        json.put("msg", "");
        json.put("code", "");
        json.put("data", flatformCostFlows);//list
        json.put("count", count);//count值
        return json;
    }

    /**
     * 签订成功合伙人(消费)列表json
     * @param flatformPartner
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/listjson1")
    @ResponseBody
    public JSONObject findAllAdvance1(FlatformPartner flatformPartner, Integer page, Integer limit) {
        flatformPartner.setStatus(3);
        List<FlatformPartner> flatformCostFlows = flatformPartnerService.findall(flatformPartner, page, limit);
        for(int i=0;i<flatformCostFlows.size();i++){
            flatformCostFlows.get(i).setDealNumbers(flatformCostFlowService.countByParId(flatformCostFlows.get(i).getParId()));;
        }
        int count = flatformPartnerService.count(flatformPartner);
        JSONObject json = new JSONObject();
        json.put("msg", "");
        json.put("code", "");
        json.put("data", flatformCostFlows);//list
        json.put("count", count);//count值
        return json;
    }


    /**
     * 订单列表查询
     */
    @RequestMapping("/listOrder")
    public String listOrder(Model model,Integer parId){
        model.addAttribute("parId",parId);
        return "flatformOrderList";
    }

    /**
     * 订单列表查询接口
     * @param flatformOrder
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/listOrderjson")
    @ResponseBody
    public JSONObject listOrderjson(FlatformOrder flatformOrder, Integer page, Integer limit) {
        List<FlatformOrder> flatformOrders = flatformOrderService.findall(flatformOrder, page, limit);
        for(int i=0;i<flatformOrders.size();i++){
            flatformOrders.get(i).setParName1(flatformPartnerService.findById(flatformOrders.get(i).getId()).getParName());
        }
        int count = flatformOrderService.count(flatformOrder);
        JSONObject json = new JSONObject();
        json.put("msg", "");
        json.put("code", "");
        json.put("data", flatformOrders);//list
        json.put("count", count);//count值
        return json;
    }

    /**
     * 消费信息查询findById
     */
    @RequestMapping("/findoneConsume")
    public String findoneConsume(Integer cfId,FlatformSource flatformSource,Model model){
        FlatformCostFlow flatformCostFlow=flatformCostFlowService.findById(cfId);
        String pname=flatformPartnerService.findById(flatformCostFlow.getParId()).getParName();
        String sname=flatformSourceService.findById(flatformCostFlow.getsId()).getsName();
        HashMap<String,String> hashMap = PropertiesUtil.findAll();

        model.addAttribute("flatformCostFlow",flatformCostFlow);
        model.addAttribute("list",hashMap);
        model.addAttribute("pname",pname);
        model.addAttribute("sname",sname);
        return "checkConsume";
    }
}
