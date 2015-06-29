package zh.rabbitmq.demo.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoMessageConvert extends AbstractMessageConverter{
	
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	private volatile String defaultCharset = DEFAULT_CHARSET;
	
	public void setDefaultCharset(String defaultCharset) {
		this.defaultCharset = (defaultCharset != null) ? defaultCharset
	                : DEFAULT_CHARSET;
	}

	@Override
	protected Message createMessage(Object object,
			MessageProperties messageProperties) {
		byte[] bytes = serialize(object);
		messageProperties.setContentEncoding(this.defaultCharset);
		if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
		return new Message(bytes, messageProperties);
	}

	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException {
		byte[] bytes = message.getBody();
		return deserialize(bytes);
	}

	public static byte[] serialize(Object obj){
		Kryo kryo = new Kryo();
		Output output = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			output = new Output(bos);
			kryo.writeClassAndObject(output, obj);
			output.flush();
			return bos.toByteArray();
		}finally{
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(output != null)
				output.close();
		}
	}
	
	public static Object deserialize(byte[] bytes){
		Kryo kryo = new Kryo();
		kryo.register(Arrays.asList("").getClass()); 
		if(bytes == null || bytes.length == 0)
			return null;
		Input ois = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		try {
			ois = new Input(bis);
			return kryo.readClassAndObject(ois);
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(ois != null)
				ois.close();
		}
	}
}
