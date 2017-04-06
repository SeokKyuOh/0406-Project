/*
	�ڹٸ� �̿��Ͽ� ������ ���α׷��� �ۼ��Ѵ�.
	������ ��ȭ�� �޴���.(�츮�� 114�� ��ȭ�ϸ� 114�� ����, �츮�� Ŭ���̾�Ʈ)
	�� ���α׷��� Ŭ���̾�Ʈ�� ���� �����ϱ� ���� ������ ��Ʈ�� �����Ѵ�.
	�׷��� �پ��� ��� ���α׷� �ӿ����� ���� ��Ʈ������ ���� �� ����
	
	����� ���� ä���̳� ��ȭ�� input�� output���� �̷���� �����
*/
package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	//��ȭ�� ������ ���� ������ �˷��ֱ� ���� ��ü.
	//�� ���� ��ȭ�� �� ������.
	//������ Ŭ���̾�Ʈ�� ����� ��Ʈ��ȣ�� �����ϸ� �ȴ�.
	//��Ʈ��ȣ�� �����ڰ� �����Ӱ� ���� �� �ִ�.
	//����1) �ٸ� 0~1023 �̺κ��� �̹� �ý����� �����ϰ� �ִ�.
	//����2) ������ ���α׷����� ������.
	//			ex)����Ŭ 1521, mysql 3306, web 80
	
	ServerSocket server;
	Socket socket;	
	int port=8888;
	
	public MyServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("��������");
			
			//Ŭ���̾�Ʈ�� ������ ��ٸ���
			//������ ���������� ���Ѵ�� �� ������ ������.
			//��ġ ��Ʈ���� read()�迭�� ����.
			while(true){
				socket = server.accept();	//accept�� ��ȯ���� socket  //�������� �����ϸ� ��ȯ�Ѵ�.
				System.out.println("������ �߰�!");
				
				//������ �̿��Ͽ� �����͸� �ް����ϴ� ��쿣 �Է½�Ʈ����
				//�����͸� ���������ϴ� ��쿣 ��½�Ʈ��
				InputStream is = socket.getInputStream();		//byte ��� �Է½�Ʈ��.
				InputStreamReader reader = null;
				reader = new InputStreamReader(is);		//���ۻ���
				
				int data;
				while(true){
					data = reader.read();	//1byte �о����
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









