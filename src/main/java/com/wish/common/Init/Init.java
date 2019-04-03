package com.wish.common.Init;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger sysLogger =  Logger.getLogger(Init.class);

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        sysLogger.info("项目启动完成");
    }
}
