/*
	�����ʹ� ������ ���α׷����� ���ư����ϱ� ������ �굵 ������ �ʿ��ϴ�.
*/

package echo.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EchoClient extends JFrame {
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south;
	JTextField t_input;
	JButton bt_connect;
	
	//��ȭ�� ���� �� �ִ� ����
	//���ɶ� - �������κ��� ��½�Ʈ��
	//û���Ҷ� - �������κ��� �Է½�Ʈ��
	Socket socket;		//������(����) �ϳ� ��������
	BufferedReader buffr;	//û���
	BufferedWriter buffw;	//���ɱ��
	
	public EchoClient() {
		area = new JTextArea(12, 18);
		scroll = new JScrollPane(area);
		p_south = new JPanel();
		t_input = new JTextField(15);
		bt_connect = new JButton("����");
		
		add(scroll);
		p_south.add(t_input);
		p_south.add(bt_connect);
		add(p_south, BorderLayout.SOUTH);
		
		bt_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//����ġ��.
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER){
					send();
				}
			}
		});
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	//������ �� ������
	public void send(){
		//�ؽ�Ʈ������ �޼��� ���� ���
		String msg = t_input.getText();
		try {
			buffw.write(msg+"\n");	//��Ʈ���� ���� ���. �� �������� ���Ͽ� ������ ����  //���۸� ����߱� ������ ���� "\n"�̶�� �ٸ�ħ ǥ�ð� �־�� ������ �ν��Ѵ�.
			
			//����ó���� ���� ���۾ȿ� ���� �ܿ����� ������ �� �����������Ѵ�. �̰��� flush . �̰� �޸𸮻� ���� �ִ� ���� �������� �� write�� ��Ʈ��.
			buffw.flush();	//���ۿ� ���� �������� �� �����͸� ������� ��� ��½��� ������.
			
			//�������� ���ƿ� �޼����� area�� ����غ���
			String data = buffr.readLine();		//�о�´�.
			area.append(data+"\n");
			t_input.setText("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//������ �����ϴ� �޼���
	public void connect(){
		try {
			socket = new Socket("localhost", 7777);	//IP�� ��Ʈ�Է�
			
			//������ �Ϸ�Ǿ�����, ��Ʈ�� ������(���� ��ȭ�� �� �ִ� ������)
			//��?? ��ȭ��������
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//��� ����
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));	//���ϱ� ����
			
		} catch (UnknownHostException e) { 	//ȣ��Ʈ�� ��Ȯ���� ������
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}		
		
	}
	
	public static void main(String[] args) {
	
		new EchoClient();
		

	}

}
