package college.sms;

/**
 * Name 发送短信工厂
 *
 * @author xuxb
 * Date 2018-12-10
 * VersionV1.0
 * @description 接口和实现隔离
 */
public class FactorySMS {

    public static final String ALIYUN = "aliyun";
    public static final String SMS253 = "Sms253";

    private static ISMS aliyunSms;
    private static ISMS sms253Sms;

    /**
     * 获取实例 1.0
     *
     * @param type
     * @return 但是有个问题。会出现多个实例的问题。实际上只需要一个实例就行了
     */
    @Deprecated
    public ISMS GetInstance(String type) {
        if (type.equals(ALIYUN)) {
            return new AliyunSmsUtil();
        } else if (type.equals(SMS253)) {
            return new Sms253Util();
        } else {
            return null;
        }
    }

    /**
     * 线程安全 2.0
     *
     * @param type
     * @return 不足：虽然线程安全，也是单例，如果新增一个类，我必须知道修改工厂模式
     */
    @Deprecated
    public ISMS GetInstanceOne(String type) {
        if (type.equals(ALIYUN)) {
            synchronized (FactorySMS.class) {
                if (aliyunSms == null) {
                    aliyunSms = new AliyunSmsUtil();
                }
            }
            return aliyunSms;
        } else if (type.equals(SMS253)) {
            synchronized (FactorySMS.class) {
                if (sms253Sms == null) {
                    sms253Sms = new Sms253Util();
                }
            }
            return sms253Sms;
        } else {
            return null;
        }
    }

}
