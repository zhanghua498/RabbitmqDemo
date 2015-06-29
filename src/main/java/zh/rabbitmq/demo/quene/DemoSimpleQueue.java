package zh.rabbitmq.demo.quene;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class DemoSimpleQueue implements  ChannelAwareMessageListener{
	@Override
	public void onMessage(Message msg, Channel ch) {
		System.out.println("Queue2:"+new String(msg.getBody()));
		try {
			ch.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
