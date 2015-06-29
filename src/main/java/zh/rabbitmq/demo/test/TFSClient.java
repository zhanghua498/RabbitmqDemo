package zh.rabbitmq.demo.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TFSClient {

	public static void main(String[] args){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TfsManagerConfig.class);
//		TFSImageService tfs = (TFSImageService) ctx.getBean("TFSImageService");
//		tfs.saveImageToTFS("/Users/zhanghua/Desktop/imgSave/0a2cd77331672385.jpg");
		System.out.println("OK");
	}

}
