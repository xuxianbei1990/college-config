package college.sms;

import java.util.List;

/**
 * Name 发送短信接口
 *
 * @author xuxb
 * Date 2018-12-10
 * VersionV1.0
 * @description
 */
public interface ISMS {

    /**
     * 发送注册短信
     *
     * @param phoneNum
     * @param code
     * @return
     */
    int sendSmsRegister(String phoneNum, String code);

    /**
     * 发送短信
     *
     * @param phoneNum      手机号码
     * @param templateCode  模板编码
     * @param templateParam 参数
     * @return
     */
    int sendSms(String phoneNum, String templateCode, List<String> templateParam);
}
