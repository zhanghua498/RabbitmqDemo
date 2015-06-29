package zh.rabbitmq.demo.exchange;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import zh.rabbitmq.demo.config.RabbitConsumerConfiguration;

public class ExchangeRecTest1 {

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) {
		ApplicationContext ctx =
				new AnnotationConfigApplicationContext(RabbitConsumerConfiguration.class);

	}

}
