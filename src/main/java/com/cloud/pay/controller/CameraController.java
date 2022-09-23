package com.cloud.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.pay.entity.*;
import com.cloud.pay.service.CustomizeGroupService;
import com.cloud.pay.service.ThinkEquipmentCateService;
import com.cloud.pay.service.ThinkEquipmentMessageService;
import com.cloud.pay.service.ThinkEquipmentService;
import com.cloud.pay.util.FileMoveUtil;
import com.cloud.pay.util.MyThread;
import com.cloud.pay.util.NginxStartUtil;
import com.cloud.pay.util.SnapShot;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:宁志洋
 * @Date:2020/9/19 19:49
 */

@Controller
public class CameraController {

    @Autowired
    private ThinkEquipmentMessageService thinkEquipmentMessageService;
    @Autowired
    private ThinkEquipmentService thinkEquipmentService;
    @Autowired
    private ThinkEquipmentCateService thinkEquipmentCateService;
    @Autowired
    private CustomizeGroupService customizeGroupService;

    @RequestMapping("/map_view")
    public String map_view(Model model, ThinkEquipmentCate thinkEquipmentCate, Integer id, HttpServletRequest request,CustomizeGroup customizeGroup) {
        List<ThinkEquipmentCate> equipmentCates = thinkEquipmentCateService.findAll(thinkEquipmentCate);
        model.addAttribute("equipmentCates", equipmentCates);
        model.addAttribute("id", id);
        String u_name = (String) request.getSession().getAttribute("u_name");
        if (u_name.equals("admin")){
            List<CustomizeGroup> customizeGroups = customizeGroupService.findByPage(customizeGroup,1,10);
            model.addAttribute("customizeGroups",customizeGroups);
        }else{
            customizeGroup.setuName(u_name);
            List<CustomizeGroup> customizeGroups = customizeGroupService.findByuName(customizeGroup,1,10);
            model.addAttribute("customizeGroups",customizeGroups);
        }
        return "map_view";
    }

    @ResponseBody
    @RequestMapping("/map_view/equipment")
    public JSONObject equipment(Integer cate, Model model) {
        JSONObject json = new JSONObject();
        List<ThinkEquipment> thinkEquipments = thinkEquipmentService.findAllByCate(cate);
        model.addAttribute("thinkEquipments", thinkEquipments);
        json.put("data", thinkEquipments);
        return json;
    }

    @ResponseBody
    @RequestMapping("/map_view/group")
    public JSONObject group(Integer id, Model model,CustomizeGroup customizeGroup) {
        JSONObject json = new JSONObject();
        customizeGroup = customizeGroupService.findById(id);
        String[] a = customizeGroup.getEqId().split(",");
        List<ThinkEquipment> thinkEquipments = new ArrayList<>();
        for (String s : a) {
            ThinkEquipment thinkEquipment = thinkEquipmentService.findOneByCode(s);
            thinkEquipments.add(thinkEquipment);
        }
        json.put("data", thinkEquipments);
        return json;
    }

    @ResponseBody
    @RequestMapping("/map_view/updateStatus")
    public JSONObject updateStatus(ThinkEquipment equipment, Model model) {
        JSONObject json = new JSONObject();
        int ret = thinkEquipmentService.updateStatus(equipment);
        if (ret > 0) {
            json.put("result", "success");
        } else {
            json.put("result", "fail");
        }
        return json;
    }

    @ResponseBody
    @RequestMapping("/map_view/status")
    public JSONObject status(Model model, ThinkEquipment thinkEquipment) {
        JSONObject json = new JSONObject();
        List<ThinkEquipment> Equipments = thinkEquipmentService.findAll(thinkEquipment);
        model.addAttribute("Equipments", Equipments);
        json.put("result", Equipments);
        return json;
    }

    @RequestMapping("/single_view")
    public String single_view(Model model, ThinkEquipmentCate thinkEquipmentCate) {
        List<ThinkEquipmentCate> thinkEquipmentCates = thinkEquipmentCateService.findAll(thinkEquipmentCate);
        model.addAttribute("thinkEquipmentCates", thinkEquipmentCates);
        return "single_view";
    }

    @ResponseBody
    @RequestMapping("/single_view/findName")
    public JSONObject findName(Integer cate) {
        JSONObject json = new JSONObject();
        List<ThinkEquipment> Equipments = thinkEquipmentService.findAllByCate(cate);
        json.put("result", Equipments);
        return json;
    }

    @ResponseBody
    @RequestMapping("/startPic")
    public void ss1(String msg, String code, ThinkEquipment thinkEquipment) {

        ThinkEquipment thinkEquipment1 = new ThinkEquipment();
        if (msg.equals(String.valueOf(10009))) {
            thinkEquipment1.setCode(code);
            thinkEquipment1.setStatus((byte) 1);
            thinkEquipmentService.updateStatusByCode(thinkEquipment1);
        } else {
            thinkEquipment1.setCode(code);
            thinkEquipment1.setStatus((byte) -100);
            thinkEquipmentService.updateStatusByCode(thinkEquipment1);
        }

        int i = 0;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("pic_thread")) {
                System.out.println(t.getName() + i);
                t.stop();
                i++;
            }
        }

        List<ThinkEquipment> Equipments = thinkEquipmentService.findAll(thinkEquipment);
        for (ThinkEquipment equipment : Equipments) {
            if (equipment.getStatus() == 1) {
                MyThread myThread = new MyThread();
                myThread.init(equipment.getId(), equipment.getIp(), equipment.getUsername(), equipment.getPassword(), equipment.getName(), equipment.getCode());
                Thread thread = new Thread(myThread);
                thread.setName("pic_thread");
                thread.start();
            }
        }

    }

    @ResponseBody
    @RequestMapping("/shutdownPic")
    public void ss2() throws InterruptedException {
        int i = 0;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("pic_thread")) {
                System.out.println(t.getName() + i);
                t.stop();
                i++;
            }
        }
    }

    @ResponseBody
    @RequestMapping("/map_view/delAbNormal")
    public String delAbNormal(ThinkEquipment thinkEquipment) {
        int ret = thinkEquipmentService.delAbNormalStatus(thinkEquipment);
        if (ret > 0) {
            return "success";
        } else {
            return "error";
        }
    }


    @ResponseBody
    @RequestMapping("/gainRtmp")
    public JSONObject live(String code) throws InterruptedException, IOException {

        if (NginxStartUtil.start() != 3) {
            NginxStartUtil.startWinProc();
        }
        //获取相应的bat打开
        String cmd = "cmd /c start D:/Java/bat/" + code + ".bat";// pass
        try {
            ThinkEquipment eq = thinkEquipmentService.findOneByCode(code);
            Process ps = Runtime.getRuntime().exec(cmd);
            ps.waitFor();
            ThinkEquipment equipment = new ThinkEquipment();
            equipment.setLiveUrl("开启");
            equipment.setCode(code);
            thinkEquipmentService.updateLiveStatusByCode(equipment);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        json.put("live_url", "http://10.100.100.182:8888/live?port=1935&app=live&stream=room-" + code);
        return json;
    }

    @ResponseBody
    @RequestMapping("/closeGainRtmp")
    public JSONObject killProgess(String code) throws InterruptedException, IOException {

        ThinkEquipment equipment_u = new ThinkEquipment();
        equipment_u.setLiveUrl("禁用");
        equipment_u.setCode(code);
        thinkEquipmentService.updateLiveStatusByCode(equipment_u);
        JSONObject json = new JSONObject();
        json.put("msg", "推流关闭成功！");
        json.put("boolean", "true");
        return json;
    }

}
