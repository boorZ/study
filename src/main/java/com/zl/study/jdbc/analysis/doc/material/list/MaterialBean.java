package com.zl.study.jdbc.analysis.doc.material.list;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author 周林
 * @Description 资料清单Bean
 * @email prometheus@noask-ai.com
 * @date 2019/10/11
 */
public class MaterialBean {
    private File doc;
    private Map<String, List<String>> materialContent;

    public File getDoc() {
        return doc;
    }

    public void setDoc(File doc) {
        this.doc = doc;
    }

    public Map<String, List<String>> getMaterialContent() {
        return materialContent;
    }

    public void setMaterialContent(Map<String, List<String>> materialContent) {
        this.materialContent = materialContent;
    }

    @Override
    public String toString() {
        return "MaterialBean{" +
                "doc=" + doc +
                ", materialContent=" + materialContent +
                '}';
    }
}
