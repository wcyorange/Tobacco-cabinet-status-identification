package com.cloud.pay.util;

import com.cloud.pay.entity.FlatformAdminGroup;
import com.cloud.pay.entity.FlatformAdminMenu;

import java.util.ArrayList;
import java.util.List;

/**
*@Descritpion:权限树
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/31
**/
public class MakeMenuTree {
    private List<FlatformAdminMenu> menuCommon;
    private List<FlatformAdminMenu> list =new ArrayList<FlatformAdminMenu>();
    public List<FlatformAdminMenu> menuList(List<FlatformAdminMenu> menu){
        this.menuCommon=menu;
        for(FlatformAdminMenu bean:menu){
            if(bean.getAuPId()==0){
                FlatformAdminMenu adminmenu=new FlatformAdminMenu();
                adminmenu.setAuClickable(bean.getAuClickable());
                adminmenu.setAuId(bean.getAuId());
                adminmenu.setAuLevel(bean.getAuLevel());
                adminmenu.setAuUrl(bean.getAuUrl());
                adminmenu.setAuPId(bean.getAuPId());
                adminmenu.setAuSort(bean.getAuSort());
                adminmenu.setAuTitle(bean.getAuTitle());
                adminmenu.setAuClass(bean.getAuClass());
                adminmenu.setAuInfo(bean.getAuInfo());
                adminmenu.setSub_list(menuChild(bean.getAuId()));
                list.add(adminmenu);
            }
        }
        return list;
    }

    private List<FlatformAdminMenu> menuChild(Integer auId) {
        // TODO Auto-generated method stub
        List<FlatformAdminMenu> sub_list =new ArrayList<FlatformAdminMenu>();
        for(FlatformAdminMenu bean:menuCommon){
            if(bean.getAuPId().equals(auId)){
                FlatformAdminMenu adminmenu=new FlatformAdminMenu();
                adminmenu.setAuClickable(bean.getAuClickable());
                adminmenu.setAuId(bean.getAuId());
                adminmenu.setAuLevel(bean.getAuLevel());
                adminmenu.setAuUrl(bean.getAuUrl());
                adminmenu.setAuPId(bean.getAuPId());
                adminmenu.setAuSort(bean.getAuSort());
                adminmenu.setAuTitle(bean.getAuTitle());
                adminmenu.setAuClass(bean.getAuClass());
                adminmenu.setAuInfo(bean.getAuInfo());
                adminmenu.setSub_list(menuChild(bean.getAuId()));
                sub_list.add(adminmenu);
            }
        }
        return sub_list;
    }

    /**
     * 生成权限列表
     * @param
     * @return
     */
    public List<FlatformAdminMenu>  makeAuthList(List<FlatformAdminMenu> menu_list, FlatformAdminGroup group_info) {
        // TODO Auto-generated method stub
        if(group_info==null){
            group_info=new FlatformAdminGroup();
            group_info.setAgAuth("0");
        }
        String auth = group_info.getAgAuth();
        String[] authlist = auth.split(",");
        for(int i=0;i<authlist.length;i++){
            makeChecked(menu_list,authlist[i]);
        }
        return menu_list;
    }

    /**
     * 修改每个权限的选中状态
     * @param list
     * @param list
     */
    public void makeChecked(List<FlatformAdminMenu> list, String au_id){
        //顶层的 p_id=0
        name:for(FlatformAdminMenu item:list){
            //子功能权限
            if(item.getAuId()==Integer.parseInt(au_id)){
                item.setChecked(2);
            }
            for(FlatformAdminMenu bean:item.getSub_list()){
               // System.out.println(bean.getAuTitle());
                if(bean.getAuId()==Integer.parseInt(au_id)&&bean.getAuClickable()==1){
               //     System.out.println(bean.getAuTitle()+"11111111111111    ");
                    bean.setChecked(2);
                    break name;
                }
            }
        }
        return;
    }
}

