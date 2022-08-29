package com.tzh.newspring;

import cn.hutool.core.lang.ClassScanner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class YunaApplicationContext {
    private HashMap<String, Object> singletonMap = new HashMap<>();
    private HashMap<String, BeanDefinition> beanDefinitionHashMap = new HashMap<>();

    private Class configClass;
    private Set<Class<?>> classes;

    public YunaApplicationContext(Class configClass) {
        this.configClass = configClass;
        // É¨ÃèÀà, ²¢Ð´ÈëbeanDefiniton
        ComponentScan annotation = (ComponentScan)configClass.getAnnotation(ComponentScan.class);
        String path = annotation.value();
        classes = ClassScanner.scanPackage(path);

        classes.stream()
                .filter(c -> c.getAnnotation(Component.class)!=null)
                .forEach( c -> {
                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setClazz(c);
                    Scope scope = (Scope) configClass.getAnnotation(c);
                    beanDefinition.setScope(scope == null?"singleton" : scope.value());

                    Component component = c.getAnnotation(Component.class);
                    beanDefinitionHashMap.put(component.value(), beanDefinition);
                });
    }

    public Object getBean(String beanName) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionHashMap.get(beanName);
        if (beanDefinition.getScope().equals("singleton")) {
            Object object = singletonMap.get(beanName);
            if (object == null) {
                object = createBean(beanDefinition.getClazz());
                singletonMap.put(beanName, object);
            }
            return object;
        } else {
            return createBean(beanDefinition.getClazz());
        }
    }

    private Object createBean(Class clazz) throws Exception {
        Object object = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields)
                .filter(f -> f.isAnnotationPresent(Autowired.class))
                .forEach(f -> {
                    f.setAccessible(true);
                    try {
                        f.set(object, getBean(f.getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        if(object instanceof InitializingBean) {
            ((InitializingBean)object).afterPropertiesSet();
        }
        return object;
    }
}
