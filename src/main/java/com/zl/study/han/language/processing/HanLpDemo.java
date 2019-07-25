//package com.zl.study.han.language.processing;
//
//import com.hankcs.hanlp.HanLP;
//import com.hankcs.hanlp.seg.Segment;
//import com.hankcs.hanlp.seg.common.Term;
//import com.hankcs.hanlp.tokenizer.IndexTokenizer;
//import com.hankcs.hanlp.tokenizer.NLPTokenizer;
//import com.hankcs.hanlp.tokenizer.StandardTokenizer;
//
//import java.util.List;
//
///**
// * @author 周林
// * @Description 自然语言处理Demo
// * @email prometheus@noask-ai.com
// * @date 2019/7/23
// */
//public class HanLpDemo {
//    public static void main(String[] args) {
//        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
//    }
//
//    /**
//     * 标准分词
//     *
//     */
//    private void normWord() {
//        List<Term> termList = StandardTokenizer.segment("商品和服务");
//        System.out.println(termList);
//    }
//    /**
//     * NLP分词
//     *
//     */
//    private void nlpWord() {
//        System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
//        // 注意观察下面两个“希望”的词性、两个“晚霞”的词性
//        System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
//        System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));
//    }
//
//    /**
//     * 索引分词
//     */
//    private void indexWord() {
//        List<Term> termList = IndexTokenizer.segment("主副食品");
//        for (Term term : termList)
//        {
//            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
//        }
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//
//    /**
//     * 标准分词
//     */
//    private void indexWord() {
//    }
//}
