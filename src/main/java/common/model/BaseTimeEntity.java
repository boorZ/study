package common.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 周林
 * @Description 基础时间实体
 * @email prometheus@noask-ai.com
 * @date 2019/8/30
 */
@MappedSuperclass
public class BaseTimeEntity extends BaseEntity {
    @Column(name = "`create_at`")
    private Date createAt;
    @Column(name = "`create_by`")
    private Long createBy;
    @Column(name = "`update_at`")
    private Date updateAt;
    @Column(name = "`update_by`")
    private Long updateBy;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
