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
	private static final int PORT = 9000;
	
	public static void main(String[] args) {
		Socket socket = null;
		
		List<PrintWriter> listPrintWriter = new ArrayList<PrintWriter>();	// 서버에선 쓰지는 않지만 스레드에서 사용(스레드끼리 전달을 위해서)
		
		ServerSocket serverSocket = null;
		try {
			// 1. create server socket
			serverSocket = new ServerSocket();

			// 2. binding
			String localhost = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(localhost, PORT), 5);
			consoleLog("binding " + localhost + ":" + PORT);

			while (true) {
				// 3. wating for connection
				socket = serverSocket.accept();
				
				Thread thread = new ChatServerThread(socket, listPrintWriter);
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
				consoleLog("error:" + ex);
			}
		}

	}

	public static void consoleLog(String message) {
		System.out.println("[chat server] " + message);
	}
}
