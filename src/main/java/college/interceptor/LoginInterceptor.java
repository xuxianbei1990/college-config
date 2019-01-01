package college.interceptor;

import college.common.result.ResultUtil;
import college.common.result.ResultVO;
import college.common.result.ReturnCodeEnum;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Name 登录拦截
 *
 * @author xuxb
 * Date 2018-12-24
 * VersionV1.0
 * @description 根据token来实现校验登录
 */
public class LoginInterceptor implements HandlerInterceptor {

    //登录token
    private static final String LOGINTOKEN = "";

    // 登录类型
    private static final String LOGINTYPE = "";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        httpServletResponse.reset();
        // 设置编码格式
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 获取请求头的 JWT
        String webToken = httpServletRequest.getHeader(LOGINTOKEN);
        // 用户类型
        String userType = httpServletRequest.getHeader(LOGINTYPE);

        // 用户类型为空
        if (userType == null || webToken == null) {
            // 获取响应画笔
            PrintWriter printWriter = httpServletResponse.getWriter();
            return printFailResult(printWriter);
        }
        return false;
    }

    private boolean printFailResult(PrintWriter printWriter) {
        ResultVO ajaxResult = ResultUtil.failedResult(ReturnCodeEnum.UNAUTHORIZED.getCode(),
                ReturnCodeEnum.UNAUTHORIZED.getDesc());
        printWriter.print(JSONObject.toJSONString(ajaxResult));
        printWriter.flush();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
