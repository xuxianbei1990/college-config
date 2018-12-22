package college.common.result;

import java.util.List;

/**
 * Name 结果工具类
 *
 * @author xuxb
 * Date 2018-12-21
 * VersionV1.0
 * @description 对于常用结果返回进行总结
 */
public class ResultUtil {

    // 后续可能升级到注解方式
    public static void listToPage(List list, com.github.pagehelper.Page result) {
        com.github.pagehelper.Page oldPage = ((com.github.pagehelper.Page) list);
        result.setTotal(oldPage.getTotal());
        result.setPageNum(oldPage.getPageNum());
        result.setPageSize(oldPage.getPageSize());
    }

    public static ResultVO successResult(Object object) {
        return new ResultVO(ReturnCodeEnum.SUCCESS.getCode(), object);
    }

    public static ResultVO failedResult() {
        return new ResultVO(ReturnCodeEnum.FAIL.getCode(), ReturnCodeEnum.FAIL.getDesc());
    }

    public static ResultVO failedResult(int code, String errorMsg) {
        return new ResultVO(code, errorMsg);
    }

    public static ResultVO failedResult(String errorMsg) {
        return failedResult(ReturnCodeEnum.FAIL.getCode(), errorMsg);
    }

    /**
     * 有值就成功
     *
     * @param object
     * @return
     */
    public static ResultVO simpleResult(Object object) {
        return object == null ? successResult(object) : failedResult();
    }

    /**
     * 大于1 就成功
     *
     * @param object
     * @return
     */
    public static ResultVO simpleResult(int object) {
        return object > 0 ? successResult(object) : failedResult();
    }

    /**
     * 大于1 就成功
     *
     * @param object
     * @return
     */
    public static ResultVO simpleResult(int object, String errorMsg) {
        return object > 0 ? successResult(object) : failedResult(errorMsg);
    }

    /**
     * 大于1 就成功  规则0： 对应 errorMsgs[0] ; -1 对应 errorMsgs[1];
     * -2 对应 errorMsgs[2]
     *
     * @param object
     * @return
     */
    public static ResultVO simpleResult(int object, String... errorMsgs) {
        if (object > 0) {
            return successResult(object);
        } else {
            return failedResult(errorMsgs[Math.abs(object)]);
        }
    }

    public static void main(String[] args) {
        System.out.println(Math.abs(-1));
    }
}
