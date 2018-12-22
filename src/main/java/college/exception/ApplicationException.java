package college.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Name 应用异常
 *
 * @author xuxb
 * Date 2018-12-11
 * VersionV1.0
 * @description 处理没有捕捉的异常
 */
@RestControllerAdvice
@EnableWebMvc
@Slf4j
public class ApplicationException {

    /**
     * 拦截消息不可读异常
     *
     * @param ex 异常实体
     * @return 指定格式的 result bean
     */
    @ExceptionHandler(Exception.class)
    public String applicationException(Exception ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        // 打印堆栈信息
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] elements = ex.getStackTrace();
        for (StackTraceElement element : elements) {
            sb.append("\n");
            sb.append(element.toString());
        }
        log.error(sb.toString());
        return "统一返回错误，翻车啦";
    }

}
