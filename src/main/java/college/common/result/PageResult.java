package college.common.result;


/**
 * Page
 *
 * @author Jason
 * @since v1.0
 */

public class PageResult<T> {
    /**
     * 分页
     */
    private Page page;
    /**
     * 数据
     */
    private T result;

    public PageResult() {
    }

    public PageResult(Page page, T result) {
        this.page = page;
        this.result = result;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}