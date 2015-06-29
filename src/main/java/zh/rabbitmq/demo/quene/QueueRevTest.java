package zh.rabbitmq.demo.quene;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import zh.rabbitmq.demo.config.RabbitConsumerConfiguration;

public class QueueRevTest {

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) {
		ApplicationContext ctx =
				new AnnotationConfigApplicationContext(RabbitConsumerConfiguration.class);
	}

}
