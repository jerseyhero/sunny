package client;

public class HelloClient {
	public static void main(String args[]) {
		HelloService service = new HelloService();
		Hello helloProxy = service.getHelloPort();
		String hello = helloProxy.sayHello1(" ¿ΩÁ");
		System.out.println(hello);
		String hello1 = helloProxy.sayHello2("123");
		System.out.println(hello1);
	}
}