package zh.rabbitmq.demo.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import zh.rabbitmq.demo.converter.KryoMessageConvert;

@Configuration
public class RabbitProduceConfiguration {
	
	@Value("${rabbitmq.host}")
	private String host;
	
	@Value("${rabbitmq.username}")
	private String userName;
	
	@Value("${rabbitmq.password}")
	private String password;
	
	@Bean
	public ConnectionFactory connectionFactory() {
//		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
//		connectionFactory.setUsername(userName);
//		connectionFactory.setPassword(password);
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("112.124.24.55");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		connectionFactory.setPort(5672);
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
		
		return rabbitTemplate;
	}
	
	@Bean
	public MessageConverter messageConverter() {
		return new KryoMessageConvert();
	}

	//////////////////////////////////////Queue//////////////////////////////////////////
	@Bean(name="hello")
	public Queue helloQueue() {
		Queue queue = new Queue("hello",true,false,false);
		return queue;
	}
	
	@Bean(name="exchangeTest")
	public Queue exchangeTest() {
		Queue queue = new Queue("exchangeTest",true,true,false);
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
	
}
