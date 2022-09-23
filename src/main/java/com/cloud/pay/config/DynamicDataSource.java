package com.cloud.pay.config;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
*@Description:
*@Param:
*@return:
*@Author:丁宁
*@Data:2019/7/28
*
**/

public class DynamicDataSource extends AbstractRoutingDataSource {
        /*
         * 根据Key返回targetDataSources
         */
        @Override
        protected Object determineCurrentLookupKey() {
            return DynamicDataSourceHolder.getDB();
        }
}
