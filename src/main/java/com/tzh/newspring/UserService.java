package com.tzh.newspring;

import lombok.Data;

@Component("userService")
@Scope("singleton")
@Data
public class UserService implements InitializingBean {

    @Autowired
    public AutowiredField autowiredField;
    private String password;

    public void print() {
        System.out.println(this.getClass());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.password = "实例化过后从远程机获取秘钥";
        System.out.println(password);
    }
}
