package zh.rabbitmq.demo.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import de.ruedigermoeller.serialization.FSTObjectInput;
import de.ruedigermoeller.serialization.FSTObjectOutput;

public class FstMessageConvert extends AbstractMessageConverter{

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
	
	public static byte[] serialize(Object obj) {
		ByteArrayOutputStream out = null;
		FSTObjectOutput fout = null;
		try {
			out = new ByteArrayOutputStream();
			fout = new FSTObjectOutput(out);
			fout.writeObject(obj);
			return out.toByteArray();
		} catch (IOException e){
			
		} finally {
			if(fout != null)
			try {
				fout.close();
			} catch (IOException e) {}
		}
		return null;
	}

	public static Object deserialize(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		FSTObjectInput in = null;
		try {
			in = new FSTObjectInput(new ByteArrayInputStream(bytes));
			return in.readObject();
		} catch (IOException e){
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		} finally {
			if(in != null)
			try {
				in.close();
			} catch (IOException e) {}
		}
	}

}
