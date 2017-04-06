/*
	자바를 이용하여 서버측 프로그램을 작성한다.
	서버는 전화를 받는측.(우리가 114에 전화하면 114가 서버, 우리는 클라이언트)
	각 프로그램과 클라이언트는 서로 연결하기 위한 별도의 포트가 존재한다.
	그래서 다양한 통신 프로그램 속에서도 같은 포트끼리만 만날 수 있음
	
	통신을 통한 채팅이나 대화는 input과 output으로 이루어진 결과물
*/
package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	//대화를 나누기 전에 접속을 알려주기 위한 객체.
	//즉 아직 대화는 못 나눈다.
	//서버는 클라이언트와 약속한 포트번호만 보유하면 된다.
	//포트번호는 개발자가 자유롭게 정할 수 있다.
	//예외1) 다만 0~1023 이부분은 이미 시스템이 점유하고 있다.
	//예외2) 유명한 프로그램들은 피하자.
	//			ex)오라클 1521, mysql 3306, web 80
	
	ServerSocket server;
	Socket socket;	
	int port=8888;
	
	public MyServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("서버생성");
			
			//클라이언트의 접속을 기다린다
			//접속이 있을때까지 무한대기 즉 지연에 빠진다.
			//마치 스트림의 read()계열과 같다.
			while(true){
				socket = server.accept();	//accept의 반환형은 socket  //누군가가 접속하면 반환한다.
				System.out.println("접속자 발견!");
				
				//소켓을 이용하여 데이터를 받고자하는 경우엔 입력스트림을
				//데이터를 보내고자하는 경우엔 출력스트림
				InputStream is = socket.getInputStream();		//byte 기반 입력스트림.
				InputStreamReader reader = null;
				reader = new InputStreamReader(is);		//업글빨대
				
				int data;
				while(true){
					data = reader.read();	//1byte 읽어들임
					System.out.print((char)data);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new MyServer();

	}

}









