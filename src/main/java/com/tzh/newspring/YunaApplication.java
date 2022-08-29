package com.tzh.newspring;

/**
 * @author yiri
 */
public class YunaApplication {
    public static void main(String[] args) throws Exception{
        YunaApplicationContext context = new YunaApplicationContext(YunaConfig.class);
        UserService userService = (UserService)context.getBean("userService");
        userService.print();
        userService.getAutowiredField().print();
    }
}
