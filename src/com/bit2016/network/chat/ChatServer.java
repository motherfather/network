package com.bit2016.network.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static final int PORT = 5555;
	
	public static void main(String[] args) {
		Socket socket = null;	// 3. 소켓선언 
		
		List<PrintWriter> listPrintWriter = new ArrayList<PrintWriter>();	// 서버에선 쓰지는 않지만 스레드에서 사용(스레드끼리 전달을 위해서)
		
		ServerSocket serverSocket = null;	// 1. 서버소켓선언
		try {
			// 1. create server socket
			serverSocket = new ServerSocket();	// 예외처리로 try하고 소켓생성
			
			// 1-1. set option SO_REUSEADDR (종료 후 빨리 바인딩을 하기 위해서) 서버프로그램에서는 빠지지 않는 옵션
			serverSocket.setReuseAddress(true);

			// 2. binding
			String localhost = InetAddress.getLocalHost().getHostAddress();	// 내컴에서 주소를 뽑아오고 그 주소에서 아이피만 뽑아서 스트링에 저장
			serverSocket.bind(new InetSocketAddress(localhost, PORT), 5);	// 2. 서버소켓에 주소랑 아이피 바인딩(아이넷소켓어드레스가 소켓에 아이피랑 포트줄때?) 5는 백로그 제한수(기본이 5)
			consoleLog("binding " + localhost + ":" + PORT);	// 서버에 출력

			while (true) {
				// 3. wating for connection
				socket = serverSocket.accept();		// 3. 클라이언트와 연결되면  소켓 생성되고 ..?
				
				Thread thread = new ChatServerThread(socket, listPrintWriter);		// 4. 스레드 생성 (여러명이 주고받기 위해서 만들었으며 리스트로 구분??)
				thread.start();
				
			}
			
		} catch (IOException ex) {
			consoleLog("error:" + ex);
		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
					
				}
			} catch (IOException ex) {
				consoleLog("erro9r:" + ex);
			}
		}

	}

	public static void consoleLog(String message) {
		System.out.println("[chat server] " + message);
	}
}
