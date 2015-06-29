package zh.rabbitmq.demo.exchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import zh.rabbitmq.demo.config.RabbitProduceConfiguration;
import zh.rabbitmq.demo.model.SimpleBean;

public class ExchangeSendTest {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		AbstractApplicationContext ctx =
				new AnnotationConfigApplicationContext(RabbitProduceConfiguration.class);
		RabbitTemplate template = (RabbitTemplate) ctx.getBean("amqpTemplate");
		for(int i=0;i<10;i++){
//			template.convertAndSend("fanoutTest","",SimpleBean.createSimpleBean(i));
			template.convertAndSend("directTest","directTestKey",SimpleBean.createSimpleBean(i));
//			template.convertAndSend("topicTest","11.abc.a.b",SimpleBean.createSimpleBean(i));
		}
		ctx.destroy();

	}

}
