package com.cloud.pay.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;



/**
*@Description:
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/28
*
**/        

@Service
public class UrlAccessDecisionManager implements AccessDecisionManager {

    /**
    *@Description:判断当前的url是否在权限中
    *@Param:[authentication, object, configAttributes]
    *@return:
    *@Author:丁宁
    *@Data:2019/7/28
    *
    **/


    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String url, method;
        if ("anonymousUser".equals(authentication.getPrincipal())
                || matchers("/images/**", request)
                || matchers("/js/**", request)
                || matchers("/css/**", request)
                || matchers("/fonts/**", request)
                || matchers("/layuiadmin/**", request)
                || matchers("/index", request)
                || matchers("/favicon.ico", request)
                || matchers("/login", request)
                || matchers("/static/**", request)
                || matchers("/mould/**", request)
                || matchers("/imgC/**", request)
                || matchers("/imgP/**", request)
                || matchers("/imgBL/**", request)
                || matchers("/imgIC/**", request)
                || matchers("/imgAgentLogo/**", request)
                || matchers("/imgAgreement/**", request)
                || matchers("/getKaptchaImage",request)
                || matchers("/group/testjson",request )
                || matchers("/group/treeAll",request )
                || matchers("/erp/listjson",request )
                || matchers("/regist",request )
                || matchers("/registagent",request )
                || matchers("/checkagentphone",request )
                || matchers("/checkagentemail",request )
                || matchers("/address/city",request )
                || matchers("/static/**",request )) {
            return;
        } else {
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (matchers(ga.getAuthority(), request)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }


    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
    *@Description:匹配当前的url与请求域中的是否匹配
    *@Param:[url, request]
    *@return:
    *@Author:丁宁
    *@Data:2019/7/28
    *
    **/

    private boolean matchers(String url, HttpServletRequest request) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
        if (matcher.matches(request)) {
            return true;
        }
        return false;
    }
}
