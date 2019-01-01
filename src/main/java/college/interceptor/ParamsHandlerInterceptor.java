package college.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Name 参数处理器
 *
 * @author xuxb
 * Date 2018-12-08
 * VersionV1.0
 * @description  参数拦截
 */
@Slf4j
public class ParamsHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder("操作日志:");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
            sb.append("-----------").append(sdf.format(new Date())).append("------------\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("Method: ").append(h.getMethod().getName()).append("\n");
            sb.append("Params: ");
            BufferedReader bufferedReader = request.getReader();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str);
            }
            sb.append(getParamString(request.getParameterMap()));
            sb.append("\n");
            sb.append("URL: ").append(request.getRequestURI()).append("\n");
            sb.append("-------------------------------------------------------------------------------");
            log.info(sb.toString());
        }
        return true;
    }

    private Object getParamString(Map<String, String[]> parameterMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> e : parameterMap.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }
}
