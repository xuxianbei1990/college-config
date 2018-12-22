package college.common.result;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-21
 * VersionV1.0
 * @description
 */
public enum ReturnCodeEnum {
    SUCCESS(200, "请求成功"),
    FAIL(555, "请求失败");
    private int code;
    private String desc;

    ReturnCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
