package com.zl.study.jdbc.bean;

import javax.persistence.Column;
import java.util.Date;

public class TRelationshipBO {
    /** relationship_id :  图谱id */
    @Column(name = "`atlas_id`")
    private Long atlasId;

    /** relationship_name :  关系实体名称 */
    @Column(name = "`relationship_name`")
    private String relationshipName;

    /** relationship_id :  关系实体id */
    @Column(name = "`relationship_id`")
    private Long relationshipId;

    /** to_node_id :  目标节点id */
    @Column(name = "`to_node_id`")
    private Long toNodeId;

    /** from_node_id :  源节点id */
    @Column(name = "`from_node_id`")
    private Long fromNodeId;

    @Column(name = "`create_time`")
    private Date createTime;

    public Long getAtlasId() {
        return atlasId;
    }

    public void setAtlasId(Long atlasId) {
        this.atlasId = atlasId;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Long getToNodeId() {
        return toNodeId;
    }

    public void setToNodeId(Long toNodeId) {
        this.toNodeId = toNodeId;
    }

    public Long getFromNodeId() {
        return fromNodeId;
    }

    public void setFromNodeId(Long fromNodeId) {
        this.fromNodeId = fromNodeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public TRelationshipBO() {
    }

    public TRelationshipBO(Long atlasId, Long toNodeId, Long fromNodeId) {
        this.atlasId = atlasId;
        this.toNodeId = toNodeId;
        this.fromNodeId = fromNodeId;
    }

    public TRelationshipBO(Long atlasId, String relationshipName, Long relationshipId, Long toNodeId, Long fromNodeId) {
        this.atlasId = atlasId;
        this.relationshipName = relationshipName;
        this.relationshipId = relationshipId;
        this.toNodeId = toNodeId;
        this.fromNodeId = fromNodeId;
    }

    @Override
    public String toString() {
        return "TRelationshipBO{" +
                "atlasId=" + atlasId +
                ", relationshipName='" + relationshipName + '\'' +
                ", relationshipId=" + relationshipId +
                ", toNodeId=" + toNodeId +
                ", fromNodeId=" + fromNodeId +
                ", createTime=" + createTime +
                '}';
    }
}