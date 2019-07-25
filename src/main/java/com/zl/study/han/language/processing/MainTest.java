package com.zl.study.han.language.processing;

import com.hankcs.hanlp.HanLP;

import java.util.List;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/7/24
 */
public class MainTest {
    public static void main(String[] args) {
        String s = "a\n" +
                "          形容词\t\n" +
                "ad\n" +
                "          副形词\t\n" +
                "ag\n" +
                "          形容词性语素\t\n" +
                "al\n" +
                "          形容词性惯用语\t\n" +
                "an\n" +
                "          名形词\t\n" +
                "b\n" +
                "          区别词\t\n" +
                "begin\n" +
                "          仅用于始##始\t\n" +
                "bg\n" +
                "          区别语素\t\n" +
                "bl\n" +
                "          区别词性惯用语\t\n" +
                "c\n" +
                "          连词\t\n" +
                "cc\n" +
                "          并列连词\t\n" +
                "d\n" +
                "          副词\t\n" +
                "dg\n" +
                "          辄,俱,复之类的副词\t\n" +
                "dl\n" +
                "          连语\t\n" +
                "e\n" +
                "          叹词\t\n" +
                "end\n" +
                "          仅用于终##终\t\n" +
                "f\n" +
                "          方位词\t\n" +
                "g\n" +
                "          学术词汇\t\n" +
                "gb\n" +
                "          生物相关词汇\t\n" +
                "gbc\n" +
                "          生物类别\t\n" +
                "gc\n" +
                "          化学相关词汇\t\n" +
                "gg\n" +
                "          地理地质相关词汇\t\n" +
                "gi\n" +
                "          计算机相关词汇\t\n" +
                "gm\n" +
                "          数学相关词汇\t\n" +
                "gp\n" +
                "          物理相关词汇\t\n" +
                "h\n" +
                "          前缀\t\n" +
                "i\n" +
                "          成语\t\n" +
                "j\n" +
                "          简称略语\t\n" +
                "k\n" +
                "          后缀\t\n" +
                "l\n" +
                "          习用语\t\n" +
                "m\n" +
                "          数词\t\n" +
                "mg\n" +
                "          数语素\t\n" +
                "Mg\n" +
                "          甲乙丙丁之类的数词\t\n" +
                "mq\n" +
                "          数量词\t\n" +
                "n\n" +
                "          名词\t\n" +
                "nb\n" +
                "          生物名\t\n" +
                "nba\n" +
                "          动物名\t\n" +
                "nbc\n" +
                "          动物纲目\t\n" +
                "nbp\n" +
                "          植物名\t\n" +
                "nf\n" +
                "          食品，比如“薯片”\t\n" +
                "ng\n" +
                "          名词性语素\t\n" +
                "nh\n" +
                "          医药疾病等健康相关名词\t\n" +
                "nhd\n" +
                "          疾病\t\n" +
                "nhm\n" +
                "          药品\t\n" +
                "ni\n" +
                "          机构相关（不是独立机构名）\t\n" +
                "nic\n" +
                "          下属机构\t\n" +
                "nis\n" +
                "          机构后缀\t\n" +
                "nit\n" +
                "          教育相关机构\t\n" +
                "nl\n" +
                "          名词性惯用语\t\n" +
                "nm\n" +
                "          物品名\t\n" +
                "nmc\n" +
                "          化学品名\t\n" +
                "nn\n" +
                "          工作相关名词\t\n" +
                "nnd\n" +
                "          职业\t\n" +
                "nnt\n" +
                "          职务职称\t\n" +
                "nr\n" +
                "          人名\t\n" +
                "nr1\n" +
                "          复姓\t\n" +
                "nr2\n" +
                "          蒙古姓名\t\n" +
                "nrf\n" +
                "          音译人名\t\n" +
                "nrj\n" +
                "          日语人名\t\n" +
                "ns\n" +
                "          地名\t\n" +
                "nsf\n" +
                "          音译地名\t\n" +
                "nt\n" +
                "          机构团体名\t\n" +
                "ntc\n" +
                "          公司名\t\n" +
                "ntcb\n" +
                "          银行\t\n" +
                "ntcf\n" +
                "          工厂\t\n" +
                "ntch\n" +
                "          酒店宾馆\t\n" +
                "nth\n" +
                "          医院\t\n" +
                "nto\n" +
                "          政府机构\t\n" +
                "nts\n" +
                "          中小学\t\n" +
                "ntu\n" +
                "          大学\t\n" +
                "nx\n" +
                "          字母专名\t\n" +
                "nz\n" +
                "          其他专名\t\n" +
                "o\n" +
                "          拟声词\t\n" +
                "p\n" +
                "          介词\t\n" +
                "pba\n" +
                "          介词“把”\t\n" +
                "pbei\n" +
                "          介词“被”\t\n" +
                "q\n" +
                "          量词\t\n" +
                "qg\n" +
                "          量词语素\t\n" +
                "qt\n" +
                "          时量词\t\n" +
                "qv\n" +
                "          动量词\t\n" +
                "r\n" +
                "          代词\t\n" +
                "rg\n" +
                "          代词性语素\t\n" +
                "Rg\n" +
                "          古汉语代词性语素\t\n" +
                "rr\n" +
                "          人称代词\t\n" +
                "ry\n" +
                "          疑问代词\t\n" +
                "rys\n" +
                "          处所疑问代词\t\n" +
                "ryt\n" +
                "          时间疑问代词\t\n" +
                "ryv\n" +
                "          谓词性疑问代词\t\n" +
                "rz\n" +
                "          指示代词\t\n" +
                "rzs\n" +
                "          处所指示代词\t\n" +
                "rzt\n" +
                "          时间指示代词\t\n" +
                "rzv\n" +
                "          谓词性指示代词\t\n" +
                "s\n" +
                "          处所词\t\n" +
                "t\n" +
                "          时间词\t\n" +
                "tg\n" +
                "          时间词性语素\t\n" +
                "u\n" +
                "          助词\t\n" +
                "ud\n" +
                "          助词\t\n" +
                "ude1\n" +
                "          的 底\t\n" +
                "ude2\n" +
                "          地\t\n" +
                "ude3\n" +
                "          得\t\n" +
                "udeng\n" +
                "          等 等等 云云\t\n" +
                "udh\n" +
                "          的话\t\n" +
                "ug\n" +
                "          过\t\n" +
                "uguo\n" +
                "          过\t\n" +
                "uj\n" +
                "          助词\t\n" +
                "ul\n" +
                "          连词\t\n" +
                "ule\n" +
                "          了 喽\t\n" +
                "ulian\n" +
                "          连 （“连小学生都会”）\t\n" +
                "uls\n" +
                "          来讲 来说 而言 说来\t\n" +
                "usuo\n" +
                "          所\t\n" +
                "uv\n" +
                "          连词\t\n" +
                "uyy\n" +
                "          一样 一般 似的 般\t\n" +
                "uz\n" +
                "          着\t\n" +
                "uzhe\n" +
                "          着\t\n" +
                "uzhi\n" +
                "          之\t\n" +
                "v\n" +
                "          动词\t\n" +
                "vd\n" +
                "          副动词\t\n" +
                "vf\n" +
                "          趋向动词\t\n" +
                "vg\n" +
                "          动词性语素\t\n" +
                "vi\n" +
                "          不及物动词（内动词）\t\n" +
                "vl\n" +
                "          动词性惯用语\t\n" +
                "vn\n" +
                "          名动词\t\n" +
                "vshi\n" +
                "          动词“是”\t\n" +
                "vx\n" +
                "          形式动词\t\n" +
                "vyou\n" +
                "          动词“有”\t\n" +
                "w\n" +
                "          标点符号\t\n" +
                "wb\n" +
                "          百分号千分号，全角：％ ‰   半角：%\t\n" +
                "wd\n" +
                "          逗号，全角：， 半角：,\t\n" +
                "wf\n" +
                "          分号，全角：； 半角： ;\t\n" +
                "wh\n" +
                "          单位符号，全角：￥ ＄ ￡  °  ℃  半角：$\t\n" +
                "wj\n" +
                "          句号，全角：。\t\n" +
                "wky\n" +
                "          右括号，全角：） 〕  ］ ｝ 》  】 〗 〉 半角： ) ] { >\t\n" +
                "wkz\n" +
                "          左括号，全角：（ 〔  ［  ｛  《 【  〖 〈   半角：( [ { <\t\n" +
                "wm\n" +
                "          冒号，全角：： 半角： :\t\n" +
                "wn\n" +
                "          顿号，全角：、\t\n" +
                "wp\n" +
                "          破折号，全角：——   －－   ——－   半角：—  —-\t\n" +
                "ws\n" +
                "          省略号，全角：……  …\t\n" +
                "wt\n" +
                "          叹号，全角：！\t\n" +
                "ww\n" +
                "          问号，全角：？\t\n" +
                "wyy\n" +
                "          右引号，全角：” ’ 』\t\n" +
                "wyz\n" +
                "          左引号，全角：“ ‘ 『\t\n" +
                "x\n" +
                "          字符串\t\n" +
                "xu\n" +
                "          网址URL\t\n" +
                "xx\n" +
                "          非语素字\t\n" +
                "y\n" +
                "          语气词(delete yg)\t\n" +
                "yg\n" +
                "          语气语素\t\n" +
                "z\n" +
                "          状态词\t\n" +
                "zg\n" +
                "          状态词";
//        System.out.println(s);
        String trim = s.trim();
//        System.out.println(trim);
//        System.out.println(trim.replace("\n" +
//                "          ",":"));
        String t = "b\n" +
                "          区别词\t\n" +
                "al\n" +
                "          形容词性惯用语\t\n" +
                "end\n" +
                "          仅用于终##终\t\n" +
                "ntcb\n" +
                "          银行\t\n" +
                "begin\n" +
                "          仅用于始##始";

//        System.out.println("=======================");
//        String replace = t.replace("\n          ", ":");
//        System.out.println(replace);
//        String[] split = replace.split(":");
//        for (int i = 0; i < split.length; i++) {
//            System.out.println(split[i]+"------------");
//        }

        List<String> keywordList = HanLP.extractKeyword(t, 5);
        System.out.println(keywordList);
    }
}
