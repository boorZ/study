package com.zl.study.han.language.processing;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.junit.Test;

import java.util.List;

/**
 * @author 周林
 * @Description 自然语言处理Demo
 * @email prometheus@noask-ai.com
 * @date 2019/7/23
 */
public class HanLpDemo {
    public static void main(String[] args) {
        System.out.println(HanLP.segment("自2017年1月1日起，新疆维吾尔自治区实施境外旅客购物离境退税政策\n"));
    }
    /**
     * ---标准分词---
     * HanLP中有一系列“开箱即用”的静态分词器，以Tokenizer结尾，在接下来的例子中会继续介绍。
     * HanLP.segment其实是对StandardTokenizer.segment的包装。
     * 分词结果包含词性，每个词性的意思请查阅《HanLP词性标注集》。
     */
    @Test
    public void normParticiple() {
        List<Term> termList = StandardTokenizer.segment("商品和服务");
        System.out.println(termList);
    }
    /**
     * ---NLP分词---
     * NLP分词NLPTokenizer会执行词性标注和命名实体识别，由结构化感知机序列标注框架支撑。
     * 默认模型训练自9970万字的大型综合语料库，是已知范围内全世界最大的中文分词语料库。语料库规模决定实际效果，面向生产环境的语料库应当在千万字量级。欢迎用户在自己的语料上训练新模型以适应新领域、识别新的命名实体。
     */
    @Test
    public void nlpParticiple() {
        System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
        // 注意观察下面两个“希望”的词性、两个“晚霞”的词性
        System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
        System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));
    }

    /**
     * ---索引分词---
     * 索引分词IndexTokenizer是面向搜索引擎的分词器，能够对长词全切分，另外通过term.offset可以获取单词在文本中的偏移量。
     * 任何分词器都可以通过基类Segment的enableIndexMode方法激活索引模式。
     */
    @Test
    public void indexParticiple() {
        List<Term> termList = IndexTokenizer.segment("主副食品");
        for (Term term : termList)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }
    }

    @Test
    public void test01() {
        List<String> phraseList = HanLP.extractPhrase("农业税征收实物的作价、结算、付款办法的公告", 20);
        System.out.println(phraseList);
    }
}
