package com.zl.study.jdbc;

import com.zl.study.jdbc.bean.TRelationshipBO;
import com.zl.study.jdbc.bean.ToAndFromListBO;
import com.zl.study.jdbc.c3p0.C3P0Utils;
import org.apache.commons.collections4.map.LinkedMap;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/9/23
 */
public class Main {

    public static <T> List<T> getDuplicateElements(List<T> list) {
        return list.stream()
                // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                // Set<Entry>转换为Stream<Entry>
                .entrySet().stream()
                // 过滤出元素出现次数大于 1 的 entry
                .filter(entry -> entry.getValue() > 1)
                // 获得 entry 的键（重复元素）对应的 Stream
                .map(Map.Entry::getKey)
                // 转化为 List
                .collect(Collectors.toList());
    }

    private static Map<Long, List<Long>> getFromNodeIdList (List<TRelationshipBO> atlasValueList) {
        Map<Long, List<Long>> map = new HashMap<>();
        // 根据目标节点分组
        Map<Long, List<TRelationshipBO>> toNodeIdMap = atlasValueList.stream()
                .collect(Collectors.groupingBy(TRelationshipBO::getToNodeId));
        for (Map.Entry<Long, List<TRelationshipBO>> toNodeId : toNodeIdMap.entrySet()) {
            // 提取源节点IdList
            List<Long> fromNodeIdList = toNodeId.getValue().stream().map(TRelationshipBO::getFromNodeId).collect(Collectors.toList());
            if (fromNodeIdList.size() > 1) {
                List<Long> duplicateElements = getDuplicateElements(fromNodeIdList);
                if (duplicateElements.size() > 0) {
                    map.put(toNodeId.getKey(), duplicateElements);
                }
            }
        }
        return map;
    }
    public static void main(String[] args) throws PropertyVetoException, SQLException {
        List<TRelationshipBO> list = new ArrayList<>();

        List<TRelationshipBO> relatList = getRelatList();
        Map<Long, List<TRelationshipBO>> atlasIdMap = relatList.stream().collect(Collectors.groupingBy(TRelationshipBO::getAtlasId));
        List<List<TRelationshipBO>> reListList = new ArrayList<> ();
        List<List<TRelationshipBO>> reListList2 = new ArrayList<> ();
        for (Map.Entry<Long, List<TRelationshipBO>> atlasMap : atlasIdMap.entrySet()) {
            for (TRelationshipBO relationship : atlasMap.getValue()) {
                Long atlasId = relationship.getAtlasId();
                Long toNodeId = relationship.getToNodeId();
                Long fromNodeId = relationship.getFromNodeId();
                List<TRelationshipBO> reList = new ArrayList<>();
                for (TRelationshipBO relationship2 : atlasMap.getValue()) {
                    Long newAtlasId = relationship.getAtlasId();
                    Long newToNodeId = relationship2.getToNodeId();
                    Long newFromNodeId = relationship2.getFromNodeId();
                    if (newAtlasId.equals(atlasId) && newToNodeId.equals(toNodeId) && newFromNodeId.equals(fromNodeId)) {
                        reList.add(relationship2);
                    }
                }
                if (reList.size() > 1) {
                    reListList.add(reList);
                }
                if (reList.size() > 2) {
                    reListList2.add(reList);
                }
            }

        }
        System.out.println();
//        Map<Long, List<TRelationshipBO>> atlasIdMap = relatList.stream().collect(Collectors.groupingBy(TRelationshipBO::getAtlasId));
//        for (Map.Entry<Long, List<TRelationshipBO>> atlasMap : atlasIdMap.entrySet()) {
//            List<TRelationshipBO> atlasValueList = atlasMap.getValue();
//            Map<Long, List<Long>> fromNodeIdList = getFromNodeIdList(atlasValueList);
//            for (TRelationshipBO atlasValue : atlasValueList) {
//                Long toNodeId = atlasValue.getToNodeId();
//                Long fromNodeId = atlasValue.getFromNodeId();
//                for (Map.Entry<Long, List<Long>> longListEntry : fromNodeIdList.entrySet()) {
//                    if (toNodeId.equals(longListEntry.getKey()) && longListEntry.getValue().contains(fromNodeId)) {
//                        list.add(atlasValue);
//                    }
//                }
//            }
//        }
//        List<List<TRelationshipBO>> reListList = new ArrayList<> ();
//        for (TRelationshipBO relationship : list) {
//            Long atlasId = relationship.getAtlasId();
//            Long toNodeId = relationship.getToNodeId();
//            Long fromNodeId = relationship.getFromNodeId();
//            List<TRelationshipBO> reList = new ArrayList<>();
//            if (atlasId.equals(143091804504330240L) && toNodeId.equals(131844640738381824L) && fromNodeId.equals(131901860557623296L)){
//                System.out.println();
//            }
//            for (TRelationshipBO relationship2 : list) {
//                Long newAtlasId = relationship.getAtlasId();
//                Long newToNodeId = relationship2.getToNodeId();
//                Long newFromNodeId = relationship2.getFromNodeId();
//                if (newAtlasId.equals(atlasId) && newToNodeId.equals(toNodeId) && newFromNodeId.equals(fromNodeId)) {
//                    reList.add(relationship2);
//                }
//            }
//            reListList.add(reList);
//        }
//
//        System.out.println(reListList);




//        // 根据图谱Id分组
//        Map<Long, List<TRelationshipBO>> atlasIdMap = relatList.stream().collect(Collectors.groupingBy(TRelationshipBO::getAtlasId));
//        for (Map.Entry<Long, List<TRelationshipBO>> atlasMap : atlasIdMap.entrySet()) {
//            // atlasId
//            List<TRelationshipBO> value = atlasMap.getValue();
//            Map<Long, List<TRelationshipBO>> collect = value.stream().collect(Collectors.groupingBy(TRelationshipBO::getToNodeId));
//            for (Map.Entry<Long, List<TRelationshipBO>> toNodeMap : collect.entrySet()) {
//                // toNodeId
//                List<Long> fromNodeIdList = toNodeMap.getValue().stream().map(TRelationshipBO::getFromNodeId).collect(Collectors.toList());
//                if (fromNodeIdList.size() > 1) {
//                    System.out.println(toNodeMap.getKey() + "===========" + fromNodeIdList);
//                    List<Long> duplicateElements = getDuplicateElements(fromNodeIdList);
//                    System.out.println(toNodeMap.getKey() + "===========" + duplicateElements);
//                    System.out.println();
//                    if (duplicateElements.size() > 0) {
//                        list.add(new TRelationshipBO(atlasMap.getKey(), ));
//                        return;
//                    }
//                }
//            }
//        }



//        TRelationshipBO judgeRelat = new TRelationshipBO();
//        for (TRelationshipBO relat : relatList) {
//            TRelationshipBO newRelat = new TRelationshipBO(relat.getAtlasId(), relat.getToNodeId(), relat.getFromNodeId());
//            if (!newRelat.equals(judgeRelat)) {
//                judgeRelat = newRelat;
//            } {
//                list.add();
//            }
//
//        }
    }

    private static List<TRelationshipBO> getRelatList() throws PropertyVetoException, SQLException {
        Connection conn = C3P0Utils.getconnection();
        String sql = "SELECT\n" +
                "\tatals_relat.atlas_id,\n" +
                "\trelat.relationship_name, relat.relationship_id, relat.to_node_id, relat.from_node_id, relat.create_time\n" +
                "FROM `t_atals_relationship` atals_relat \n" +
                "left join `t_relationship` relat on relat.relationship_id = atals_relat.relationship_id and relat.is_enable = \"Y\"\n" +
                "where atals_relat.is_enable = \"Y\" and relat.relationship_id is not null\n";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<TRelationshipBO> relationshipList = new ArrayList<>();
        while (rs.next()) {
            TRelationshipBO relationshipBo = new TRelationshipBO();
            relationshipBo.setAtlasId(rs.getLong("atlas_id"));
            relationshipBo.setRelationshipId(rs.getLong("relationship_id"));
            relationshipBo.setRelationshipName(rs.getString("relationship_name"));
            relationshipBo.setToNodeId(rs.getLong("to_node_id"));
            relationshipBo.setFromNodeId(rs.getLong("from_node_id"));
            relationshipBo.setCreateTime(rs.getDate("create_time"));
            relationshipList.add(relationshipBo);
        }
        rs.close();
        ps.close();
        conn.close();
        return relationshipList;
    }
}
