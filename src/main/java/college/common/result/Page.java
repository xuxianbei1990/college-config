package college.common.result;


/**
 * Page
 *
 * @author Jason
 * @since v1.0
 */

public class Page {
    /**
     * 当前页
     */
    private Integer pageNum = 1;
    /**
     * 页数大小
     */
    private Integer pageSize = 10;

    /**
     * 总数
     */
    private Long total;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}