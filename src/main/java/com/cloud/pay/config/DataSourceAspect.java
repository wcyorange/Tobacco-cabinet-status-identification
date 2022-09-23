package com.cloud.pay.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Properties;


/**
*@Description:
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/28
*
**/

@Aspect
@Component
public class DataSourceAspect {

    /**
     *@Description:根据扫描每个类前的标签，动态切换数据源
     *@Param:
     *@return:
     *@Author:丁宁
     *@Data:2019/7/28
     *
     **/
    @Before("@annotation(TargetDataSource)")
    public void beforeSwitchDS(JoinPoint point){
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dataSource = DynamicDataSourceHolder.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@TargetDataSource注解
            if (method.isAnnotationPresent(TargetDataSource.class)) {
                TargetDataSource annotation = method.getAnnotation(TargetDataSource.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(dataSource);
        // 切换数据源
        DynamicDataSourceHolder.setDB(dataSource);
        if(dataSource.equals("one")){
            pageHelperone();
        }else {
            pageHelpertwo();
        }
    }
    @Bean
    public PageHelper pageHelperone(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("pay","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
    @Bean
    public PageHelper pageHelpertwo(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("erp","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
    /**
    *@Description:结束后关闭数据源
    *@Param:[point]
    *@return:
    *@Author:丁宁
    *@Data:2019/7/28
    *
    **/

    @After("@annotation(TargetDataSource)")
    public void afterSwitchDS(JoinPoint point){
        DynamicDataSourceHolder.clearDB();
    }
}