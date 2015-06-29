package zh.rabbitmq.demo.quene;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import zh.rabbitmq.demo.converter.KryoMessageConvert;
import zh.rabbitmq.demo.model.SimpleBean;

import com.rabbitmq.client.Channel;

public class DemoObjectQueue implements ChannelAwareMessageListener{

	@Override
	public void onMessage(Message msg, Channel ch) {		
		SimpleBean sb = (SimpleBean)KryoMessageConvert.deserialize(msg.getBody());
		System.out.println("DemoObjectQueue:"+sb);		
		 try {
			ch.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
