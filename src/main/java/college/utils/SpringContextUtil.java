package college.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext; // Spring应用上下文环境

    /*

     * 实现了ApplicationContextAware 接口，必须实现该方法；

     *通过传递applicationContext参数初始化成员变量applicationContext

     */

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取某个类的所有实现
     *
     * @param var1
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> var1) {
        return applicationContext.getBeansOfType(var1);
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }


    public static void main(String[] args) {
        SpringContextUtil springContextUtil = new SpringContextUtil();
        System.out.println(springContextUtil.getClass().toString());

    }
}
