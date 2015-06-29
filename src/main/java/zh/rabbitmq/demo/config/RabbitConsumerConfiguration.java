package zh.rabbitmq.demo.config;

import java.util.UUID;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import zh.rabbitmq.demo.converter.KryoMessageConvert;
import zh.rabbitmq.demo.exchange.DemoExchange1;
import zh.rabbitmq.demo.exchange.DemoExchange2;
import zh.rabbitmq.demo.quene.DemoObjectQueue;
import zh.rabbitmq.demo.quene.DemoSimpleQueue;

@Configuration
public class RabbitConsumerConfiguration {
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
				"112.124.24.55");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		connectionFactory.setPort(5672);
//		connectionFactory.setPort(5670);
		
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean(name="amqpTemplate")
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setMessageConverter(messageConverter());
		
		RetryTemplate retryTemplate = new RetryTemplate(); 
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy(); 
		backOffPolicy.setInitialInterval(100); 
		backOffPolicy.setMultiplier(10.0);
		backOffPolicy.setMaxInterval(5000); 
		retryTemplate.setBackOffPolicy(backOffPolicy);
		
		rabbitTemplate.setRetryTemplate(retryTemplate);
		
		return rabbitTemplate;
	}
	
	@Bean
	public MessageConverter messageConverter() {
		return new KryoMessageConvert();
	}

	/////////////////////////////////////Queue/////////////////////////////////////////
	@Bean(name="hello")
	public Queue helloQueue() {
		Queue queue = new Queue("hello",true,false,false);
		return queue;
	}
	
	@Bean(name="exchangeTest")
	public Queue exchangeTest() {
		Queue queue = new Queue("exchangeTest"+UUID.randomUUID(),true,true,false);
		return queue;
	}
	
	
	//////////////////////////////////////Exchange//////////////////////////////////////////
	@Bean(name="fanoutTest")
	public FanoutExchange fanoutExchangeTest() {
		FanoutExchange fanoutTest = new FanoutExchange("fanoutTest", true, false);
		return fanoutTest;
	}
	
	@Bean(name="directTest")
	public DirectExchange directExchangeTest() {
		DirectExchange directTest = new DirectExchange("directTest", true, false);
		return directTest;
	}
	
	@Bean(name="topicTest")
	public TopicExchange topicExchangeTest() {
		TopicExchange topicTest = new TopicExchange("topicTest", true, false);
		return topicTest;
	}
	
	/////////////////////////////////binding///////////////////////////////////////
	@Bean
	public Binding fanoutBinding(){
		return BindingBuilder.bind(exchangeTest()).to(fanoutExchangeTest());
	}
	
	@Bean
	public Binding directBinding(){
		return BindingBuilder.bind(exchangeTest()).to(directExchangeTest()).with("directTestKey");
	}
	
//	@Bean
//	public Binding directBinding2(){
//		return BindingBuilder.bind(helloQueue()).to(directExchangeTest()).with("directTestKey2");
//	}
	
	@Bean
	public Binding topicBinding(){
		return BindingBuilder.bind(exchangeTest()).to(topicExchangeTest()).with("*.abc.#");
	}
	
	/////////////////////////////////Listener//////////////////////////////////////
	@Bean
	public DemoObjectQueue queueRec1() {
		return new DemoObjectQueue();
	}
	
	@Bean
	public DemoSimpleQueue queueRec2() {
		return new DemoSimpleQueue();
	}
	
	@Bean
	public DemoExchange1 fanoutExchange1(){
		return new DemoExchange1();
	}
	
	@Bean
	public DemoExchange2 fanoutExchange2(){
		return new DemoExchange2();
	}
	
	
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer1(){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(); 
		container.setConnectionFactory(connectionFactory()); 
//		container.setQueueNames("exchangeTest");
		container.addQueues(exchangeTest());
		container.setMessageListener(fanoutExchange2());
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setConcurrentConsumers(1);
		container.setPrefetchCount(1);
		return container;
	}
	
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer2(){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(); 
		container.setConnectionFactory(connectionFactory()); 
//		container.setQueueNames("exchangeTest");
		container.addQueues(exchangeTest());
		container.setMessageListener(fanoutExchange1());
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setConcurrentConsumers(1);
		container.setPrefetchCount(1);
		return container;
	}
}
