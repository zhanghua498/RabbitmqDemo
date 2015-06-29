package zh.rabbitmq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("zh.rabbitmq.demo")
public class RabbitMqApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RabbitMqApplication.class, args);
		System.out.println("RabbitMq Demo Start Success!");
		synchronized (RabbitMqApplication.class) {
			while (true) {
				RabbitMqApplication.class.wait();
			}
		}
	}
}
