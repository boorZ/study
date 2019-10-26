package common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** 
 * @ClassName: IDGenerate 
 * @Description: TODO 数值型ID生成器
 * @author 杜贵科
 * @email  duguike@mininglamp.com 
 * @date 2018年6月15日 上午10:54:47 
 *  
 */
@Component("idGenerate")
public class IDGenerate implements InitializingBean {

	private final static Logger logger = LoggerFactory.getLogger(IDGenerate.class);

	@Value("${tax.knowledge.id.workerID:1}")
	private Long workerId;
	/** 基准时间  时间起始标记点*/
	private final long epoch = 1529046086128L;
	/** 机器标识位数*/
	private final long workerIdBits = 10L;
	/** 机器ID最大值:1023*/
	private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;
	/**并发访问 sequence */
	private long sequence = 0L; //
	/** 毫秒内自增位*/
	private final long sequenceBits = 12L;
	/**机器ID左移位数 12位*/
	private final long workerIdShift = this.sequenceBits;
	/**时间戳左移位数 22位*/
	private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;
	/**序号最大值 (0-4095),111111111111,12位 */
	private final long sequenceMask = -1L ^ -1L << this.sequenceBits;
	/**最后时间戳*/
	private long lastTimestamp = -1L;

	public synchronized long getId() {
		long timestamp = timeGen();
		if (this.lastTimestamp == timestamp) { // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环);
												// 对新的timestamp，sequence从0开始
			this.sequence = this.sequence + 1 & this.sequenceMask;
			if (this.sequence == 0) {
				timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
			}
		} else {
			this.sequence = 0;
		}

		if (timestamp < this.lastTimestamp) {
			logger.error(String.format("clock moved backwards.Refusing to generate id for %d milliseconds",
					(this.lastTimestamp - timestamp)));
			throw new RuntimeException(String.format("clock moved backwards.Refusing to generate id for %d milliseconds",
					(this.lastTimestamp - timestamp)));
		}

		this.lastTimestamp = timestamp;
		return timestamp - this.epoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
	}

	public void afterPropertiesSet() throws Exception {
		if (workerId > this.maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("workerId 不能够比 %d 大且不能够小于0", this.maxWorkerId));
		}
	}

	/**
	* 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
	*/
	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 获得系统当前毫秒数
	 */
	private static long timeGen() {
		return System.currentTimeMillis();
	}
}
