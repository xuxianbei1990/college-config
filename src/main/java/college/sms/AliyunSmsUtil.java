package college.sms;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Name  阿里云发送短信
 *
 * @author xuxb
 * Date 2018-12-10
 * VersionV1.0
 * @description
 */
@Component
public class AliyunSmsUtil implements ISMS {
    @Override
    public int sendSmsRegister(String phoneNum, String code) {
        System.out.println("阿里发送短信" + phoneNum + code);
        return 0;
    }

    @Override
    public int sendSms(String phoneNum, String templateCode, List<String> templateParam) {
        System.out.println("阿里发送短信" + phoneNum + templateCode + templateParam.toArray());
        return 0;
    }
}
