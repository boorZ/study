package com.zl.study.parse;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/12/10 11:10
 */
public class ExcelDemo {
    /**
     * 18.用户需求+需求收集
     **/
    String needPath = "C:\\Users\\Administrator\\Desktop\\doc\\18.用户需求+法规查询需求收集\\";

    @Test
    public void excelNeed() {
        String fileName = needPath + "1.已验证。法规查询需求收集(从事资源税征管的税务人员).xlsx";
        Map<Object, Object> dataMap = getDataMap(fileName);
        dataMap.forEach((k, v) -> {
            String[] splitValue = v.toString().split("、");
            System.out.println(splitValue.toString());
        });

        System.out.println();
    }

    private Map<Object, Object> getDataMap(String fileName) {
        Map<Object, Object> dataMap = new HashMap<>();
        List<Map<Integer, Map<Integer, Object>>> excelData = ExcelUtils.getExcelData(fileName, 0);
        excelData.forEach(map -> map.entrySet().forEach(valueMap -> {
            Map<Integer, Object> value = valueMap.getValue();
            Object searchValue = value.get(1);
            Object needDoc = value.get(2);
            if (StringUtils.isEmpty(searchValue.toString()) || StringUtils.isEmpty(needDoc.toString())) {
                return;
            }
            if (StringUtils.equals(searchValue.toString(), "查询输入") || StringUtils.equals(needDoc.toString(), "不需要")) {
                return;
            }
            dataMap.put(searchValue, needDoc);
//            System.out.println(searchValue + "——" + needDoc);
        }));
        return dataMap;
    }
    @Test
    public void statisticalForm() {
        String fileName = "C:\\Users\\Administrator\\Desktop\\doc\\18.用户需求+法规查询需求收集\\词向量统计报表\\统计报表.xlsx";
        List<Map<Integer, Map<Integer, Object>>> excelData = ExcelUtils.getExcelData(fileName, 0);
        excelData.forEach(map -> map.entrySet().forEach(valueMap -> {
            if (valueMap.getKey() == 0) return;

            // 获取分词
            Object id = valueMap.getValue().get(0);
            Object searchValue = valueMap.getValue().get(1);



            System.out.println(id + ", " + searchValue);
        }));
    }
}
