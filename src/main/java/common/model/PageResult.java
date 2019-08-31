package common.model;

/**
 * @author 周林
 * @Description 分页结果
 * @email prometheus@noask-ai.com
 * @date 2019/8/30
 */
public class PageResult<T> {
    /** 当前页码 **/
    private Integer index;
    /** 每页大小 **/
    private Integer size;
    /** 数据 **/
    private Iterable<T> data;
    /** 总数 **/
    private Long total;
    /** 总页数 **/
    private Long totalPage;

    public PageResult(Integer index, Integer size, Iterable<T> data, Long total) {
        this.index = index;
        this.size = size;
        this.data = data;
        this.total = total;
        this.totalPage = (total / size) + (total % size == 0 ? 0 : 1);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Iterable<T> getData() {
        return data;
    }

    public void setData(Iterable<T> data) {
        this.data = data;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
