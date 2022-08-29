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
        this.password = "ʵ���������Զ�̻���ȡ��Կ";
        System.out.println(password);
    }
}
