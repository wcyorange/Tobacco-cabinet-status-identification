package com.cloud.pay.security;

import com.cloud.pay.entity.FlatformAccount;
import com.cloud.pay.entity.FlatformAdminMenu;
import com.cloud.pay.service.FlatformAccountService;
import com.cloud.pay.service.FlatformAdminMenuService;
import com.cloud.pay.util.GetIp;
import com.cloud.pay.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
*@Description:登陆根据用户名去数据查找，并查找出权限，放进UserDetails中
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/28
*
**/

@Service
public class UrlUserService implements UserDetailsService {

    @Autowired
    private FlatformAccountService flatformAccountService;
    @Autowired
    private FlatformAdminMenuService flatformAdminMenuService;
    @Override
    public UserDetails loadUserByUsername(String username) { //重写loadUserByUsername 方法获得 userdetails 类型用户
        FlatformAccount flatformAccount = flatformAccountService.findByAccount(username);
        if(flatformAccount!=null){
            List<FlatformAdminMenu> flatformAdminMenuList = flatformAdminMenuService.findByUid(flatformAccount.getAgId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (FlatformAdminMenu flatformAdminMenuLis : flatformAdminMenuList) {
                if (flatformAdminMenuLis != null && flatformAdminMenuLis.getAuUrl()!=null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(flatformAdminMenuLis.getAuUrl());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(flatformAccount.getuLoginName(), flatformAccount.getuLoginPwd(), grantedAuthorities);
        }else{
            throw  new UsernameNotFoundException("账号不存在");
        }
    }
}