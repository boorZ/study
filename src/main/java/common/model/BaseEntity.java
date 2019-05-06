package common.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects
 * needing this property.
 * Serializable     http://www.cnblogs.com/mengfanrong/p/4053646.html
 * @author zhoulin
 * @date 2019/1/15
 */
@MappedSuperclass
/**
    @MappedSuperclass
        1.@MappedSuperclass注解使用在父类上面，是用来标识父类的
        2.@MappedSuperclass标识的类表示其不能映射到数据库表，因为其不是一个完整的实体类，但是它所拥有的属性能够隐射在其子类对用的数据库表中
        3.@MappedSuperclass标识得类不能再有@Entity或@Table注解
 **/
public class BaseEntity implements Serializable {
    // 用于声明一个实体类的属性映射为数据库的主键列
    @Id
    /**
     * 为一个实体生成一个唯一标识的主键(JPA要求每一个实体Entity,必须有且只有一个主键),
     * @GeneratedValue提供了主键的生成策略。
     * @GeneratedValue注解有两个属性,分别是strategy和generator,其中generator属性的值是一个字符串,默认为"",其声明了主键生成器的名称(对应于同名的主键生成器
     **/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
