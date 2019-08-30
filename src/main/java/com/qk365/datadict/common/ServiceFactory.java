package com.qk365.datadict.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 保存批量数据工厂类
 *
 * @author zhaoge
 * @ClassName: CacheModeFactory
 * @Description: TODO
 * @date 2018年8月16日下午3:55:30
 */
public class ServiceFactory {

    private static Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    private static ApplicationContext applicationContext;

    static {
        if (applicationContext == null) {
            applicationContext = SpringUtil.getApplicationContext();
        }
    }

    public static DataSourceItem createItem(String serviceName) {
        DataSourceItem t = null;
        try {
            t = applicationContext.getBean(serviceName, DataSourceItem.class);
        } catch (Exception e) {
        }
        if (t == null) {
            logger.info("未获取到PettyItemBill对应模型name:{}，已加载默认的", serviceName);
            t = applicationContext.getBean("DataSourceItem_default", DataSourceItem.class);
        }
        return t;
    }

}
