package college.sms;

import college.utils.SpringContextUtil;

import java.util.Map;

/**
 * Name 发送短信工具类
 *
 * @author xuxb
 * Date 2018-12-10
 * VersionV1.0
 * @description 这样我作为调用方，根本不用关心谁实现这个接口。
 */
public enum SmsUtil {
    GETINSTANCE;
    private ISMS isms;
    private FactorySMS factorySMS;

    SmsUtil() {
        factorySMS = new FactorySMS();
    }

    /**
     * 真正发送短信工具类
     *
     * @return 但是有个问题。会出现多个实例的问题。实际上只需要一个实例就行了。而这个变化应该由工厂模式控制
     */
    public int sendSmsRegister1_0() {
        isms = factorySMS.GetInstance("aliyun");
        if (isms.sendSmsRegister("110", "110") == 0) {
            isms = factorySMS.GetInstance("lanchuan");
            isms.sendSmsRegister("11", "1");
        }
        return 1;
    }

    /**
     * 真正发送短信工具类
     *
     * @return 不足：虽然线程安全，也是单例，如果新增一个类，我必须知道修改工厂模式
     * 但是已经和调用方没有关系了，真正做到了低耦合
     */
    public int sendSmsRegister2_0() {
        isms = factorySMS.GetInstanceOne("aliyun");
        if (isms.sendSmsRegister("110", "110") == 0) {
            isms = factorySMS.GetInstanceOne("lanchuan");
            isms.sendSmsRegister("11", "1");
        }
        return 1;
    }

    /**
     * 线程安全 3.0 而且是自动注册的，单例
     * https://www.cnblogs.com/heaveneleven/p/9125228.html
     * 缺点：依赖spring IOC
     */
    public int sendSmsRegister3_0() {

        Map<String, ISMS> map = SpringContextUtil.getBeansOfType(ISMS.class);
        if (map.isEmpty()) {
            return 0;
        }
        // 完美，每个类以后只要关心自己就行了
        for (Map.Entry<String, ISMS> item : map.entrySet()) {
            ISMS executor = item.getValue();
            if (executor.sendSmsRegister("110", "110") > 0) {
                return 1;
            }
        }
        return 0;
    }
}
