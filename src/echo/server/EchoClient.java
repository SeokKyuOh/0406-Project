/*
	서버와는 별도의 프로그램으로 돌아가야하기 때문에 얘도 메인이 필요하다.
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
	
	//대화를 나눌 수 있는 소켓
	//말걸때 - 소켓으로부터 출력스트림
	//청취할때 - 소켓으로부터 입력스트림
	Socket socket;		//종이컵(소켓) 하나 보유하자
	BufferedReader buffr;	//청취용
	BufferedWriter buffw;	//말걸기용
	
	public EchoClient() {
		area = new JTextArea(12, 18);
		scroll = new JScrollPane(area);
		p_south = new JPanel();
		t_input = new JTextField(15);
		bt_connect = new JButton("접속");
		
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
				//엔터치면.
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
	//서버에 말 보내기
	public void send(){
		//텍스트상자의 메세지 값을 얻기
		String msg = t_input.getText();
		try {
			buffw.write(msg+"\n");	//스트림을 통해 출력. 즉 서버측의 소켓에 데이터 전송  //버퍼를 사용했기 때문에 끝에 "\n"이라는 줄마침 표시가 있어야 끝임을 인식한다.
			
			//버퍼처리된 것은 버퍼안에 남은 잔여물이 없도록 다 보내버려야한다. 이것이 flush . 이건 메모리상에 남아 있는 것을 내보내는 것 write는 스트림.
			buffw.flush();	//버퍼에 남아 있을지도 모를 데이터를 대상으로 모두 출력시켜 버린다.
			
			//서버에서 날아온 메세지를 area에 출력해보자
			String data = buffr.readLine();		//읽어온다.
			area.append(data+"\n");
			t_input.setText("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//서버에 접속하는 메서드
	public void connect(){
		try {
			socket = new Socket("localhost", 7777);	//IP와 포트입력
			
			//접속이 완료되었으니, 스트림 얻어놓자(서로 대화할 수 있는 종이컵)
			//왜?? 대화나누려고
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//듣기 빨대
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));	//말하기 빨대
			
		} catch (UnknownHostException e) { 	//호스트가 정확하지 않을때
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}		
		
	}
	
	public static void main(String[] args) {
	
		new EchoClient();
		

	}

}
