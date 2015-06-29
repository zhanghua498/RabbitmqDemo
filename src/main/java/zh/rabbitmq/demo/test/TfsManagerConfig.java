package zh.rabbitmq.demo.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TfsManagerConfig {
	
	/**
	 * 整个进程中系统最多等待多少个请求,取决于你有多少个线程并发的请求TFS
	 */
//	@Value("tfs.maxWaitThread")
//	private int maxWaitThread;
	
	/**
	 * 单个请求最大的等待时间(ms) 超过这个时间放弃这次请求
	 */
//	@Value("tfs.timeout")
//	private int timeout;
	
	/**
	 * TFS master nameserver ip address
	 */
//	@Value("tfs.nsip")
//	private String nsip;
	
	/**
	 * TFS 集群的编号,这个编号只是一种参考,系统初始化的时候会从ns上取,取不到才用本地设置的!
	 */
//	@Value("tfs.tfsClusterIndex")
//	private char tfsClusterIndex;
	
//	@Bean(initMethod="init")
//	public DefaultTfsManager tfsManager(){
//		DefaultTfsManager tfs = new DefaultTfsManager();
//		
////		tfs.setMaxWaitThread(maxWaitThread);
////		tfs.setTimeout(timeout);
////		tfs.setNsip(nsip);
////		tfs.setTfsClusterIndex(tfsClusterIndex);
//		
//		tfs.setMaxWaitThread(100);
//		tfs.setTimeout(2000);
//		tfs.setNsip("pubcloud.dev.pajkdc.com:8108");
//		tfs.setTfsClusterIndex('1');
//		
//		return tfs;
//	}
	
	@Bean
	public Entry<String, String> entry(){
		return new Entry<String, String>("key", "value");
	}
}
