package college.common.result;

import com.github.pagehelper.Page;

/**
 * Name 返回结果
 *
 * @author xuxb
 * Date 2018-12-21
 * VersionV1.0
 * @description 依赖pagehelper组件
 */
public class ResultVO {
    private int code = 200;

    private String msg = "";

    private Object results;

    public ResultVO(int code, String msg) {
        this(code, msg, null);
    }

    public ResultVO(int code, Object results) {
        this(code, null, results);
    }

    public ResultVO(int code, String msg, Object results) {
        this.code = code;
        this.msg = msg;
        this.results = results;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        if (results instanceof Page) {
            Page page = (Page) results;
            PageResult pageResult = new PageResult();
            pageResult.setPage(new college.common.result.Page());
            college.common.result.Page newPage = pageResult.getPage();
            newPage.setPageNum(page.getPageNum());
            newPage.setPageSize(page.getPageSize());
            newPage.setTotal(page.getTotal());
            pageResult.setResult(results);
            this.results = pageResult;
        } else {
            this.results = results;
        }
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", results=" + results +
                '}';
    }
}
