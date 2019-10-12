package com.zl.study.jdbc;

import com.zl.study.jdbc.bean.TRelationshipBO;
import com.zl.study.jdbc.c3p0.C3P0Utils;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
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
        // 已处理的关系
        List<TRelationshipBO> list = new ArrayList<>();
        // 要修改的关系
        List<TRelationshipBO> putList = new ArrayList<>();
        String time = "2019-09-18";

        List<TRelationshipBO> relatList = getRelatList();
        // 包含2条关系
        List<List<TRelationshipBO>> reListList = new ArrayList<> ();
        // 包含3条及3条以上
        List<List<TRelationshipBO>> reListList2 = new ArrayList<> ();

        Map<Long, List<TRelationshipBO>> atlasIdMap = relatList.stream().collect(Collectors.groupingBy(TRelationshipBO::getAtlasId));
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
                if (reList.size() == 2) {
                    reListList.add(reList);
                }
                if (reList.size() > 2) {
                    reListList2.add(reList);
                }
            }

        }

        reListList = reListList.stream().distinct().collect(Collectors.toList());
        reListList2 = reListList2.stream().distinct().collect(Collectors.toList());

        // 未处理的2条关系
        List<List<TRelationshipBO>> reUntreatedListList = new ArrayList<> ();
        for (List<TRelationshipBO> relationshipList : reListList) {
            TRelationshipBO relationshipBo1 = relationshipList.get(0);
            TRelationshipBO relationshipBo2 = relationshipList.get(1);
            String createTime1 = relationshipBo1.getCreateTime().toString();
            String createTime2 = relationshipBo2.getCreateTime().toString();
            if ((createTime1.equals(time) && !createTime2.equals(time))) {
                list.add(relationshipBo1);
            } else if ((createTime2.equals(time) && !createTime1.equals(time))) {
                list.add(relationshipBo2);
            } else if ((createTime2.equals(time) && createTime1.equals(time))) {
                if (relationshipBo1.getRelationshipName().equals("导入关系")) {
                    list.add(relationshipBo1);
                } else if (relationshipBo2.getRelationshipName().equals("导入关系")) {
                    list.add(relationshipBo2);
                } else {
                    relationshipBo1.setRelationshipName("废止、修改-导入关系");
                    putList.add(relationshipBo1);
                    list.add(relationshipBo2);
                }
            } else {
                reUntreatedListList.add(relationshipList);
            }

        }

        // 未处理的3条及3条以上关系
        List<List<TRelationshipBO>> re3UntreatedListList = new ArrayList<> ();
        for (List<TRelationshipBO> relationship : reListList2) {
            int size = relationship.size();
            // 创建时间为2019-09-18
            List<TRelationshipBO> collect = relationship.stream().filter(re -> re.getCreateTime().toString().equals(time)).collect(Collectors.toList());
            if ((size - collect.size()) == 1) {
                list.addAll(collect);
            } else if ((size - collect.size()) == 0) {
                TRelationshipBO tRelationshipBo = relationship.get(0);
                // 修改该关系的名称为 修改、废止-导入关系
                tRelationshipBo.setRelationshipName("修改、废止-导入关系");
                putList.add(tRelationshipBo);
                for (int i = 1; i < relationship.size(); i++) {
                    list.add(relationship.get(i));
                }
            } else{
                re3UntreatedListList.add(relationship);
            }
        }
//        System.out.println(list);

        // 不同图谱，同关系 关系
        List<List<TRelationshipBO>> untreatedListList = new ArrayList<> ();
        for (TRelationshipBO tRelationship1 : list) {
            List<TRelationshipBO> zl = new ArrayList<>();
            for (TRelationshipBO tRelationship2 : list) {
                if ((!tRelationship1.getAtlasId().equals(tRelationship2)) && ((tRelationship1.getToNodeId().equals(tRelationship2.getToNodeId()) && (tRelationship1.getFromNodeId().equals(tRelationship2.getFromNodeId()))) )) {
                    zl.add(tRelationship2);
                }
            }
            if (zl.size() > 1) {
                untreatedListList.add(zl);
            }
        }
        untreatedListList.forEach(list::removeAll);

         String s = list.stream().map(m->m.getRelationshipId().toString()).collect(Collectors.joining(", ", "(", ")"));
         String s2 = putList.stream().map(m->m.getRelationshipId().toString()).collect(Collectors.joining(", ", "(", ")"));

        System.out.println(s);

    }
    @Test
    public void testDate() {
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date1);
        System.out.println(format);
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

    @Test
    public void test () {
        String a = "废止-导入关系", b = "修改-导入关系";



        List<String> strList = new ArrayList<String>();
        List<String> strList2 = new ArrayList<String>();
//        for(int i = 0; i < 10; i ++) {
//            strList.add("aaa>>" + i);
//            strList2.add("aaa>>" + (10 - i));
//        }
        strList.add(a);
        strList2.add(b);
        //求出并集
        strList2.removeAll(strList);
        strList2.addAll(strList);
        System.out.println("并集大小：" + strList2.size());

        for(int i = 0; i < strList2.size(); i++) {
            System.out.println(strList2.get(i));
        }
    }
}
