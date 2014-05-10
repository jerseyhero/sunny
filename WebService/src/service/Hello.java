
package service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class Hello {
	
	@WebMethod(operationName="sayHello1")
	public String sayHello(String userName)
	{
		return "Hello,"+userName+"!";
	}
	
	@WebMethod(operationName="sayHello2")
	public String sayHello(){
		return "Hello World!";
	}
	public static void main(String[] args){
		//将WebService发布到指定地址
		Endpoint.publish("http://localhost:8080/WebServiceTest/Hello", new Hello());			
	}
}