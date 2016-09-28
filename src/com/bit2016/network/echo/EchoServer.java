package com.bit2016.network.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
//import java.net.SocketException;


public class EchoServer {
	private static final String SERVER_IP = "192.168.1.18";

	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket();	// 서버소켓생성
			
			serverSocket.bind(new InetSocketAddress(SERVER_IP, SERVER_PORT));	// 서버소켓바인딩
			System.out.println("[서버] 연결 기다림");
			
			while (true) {
				socket = serverSocket.accept();		// 소켓에 대한 연결을 받아들이고 정지!
				
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
				
			}
			
//		try {
//			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();		// 클라이언트 주소 확인
//			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();		// 클라이언트 IP
//			int remoteHostPort = inetSocketAddress.getPort();		// 클라이언트 port
//			System.out.println("[서버] 연결됨 from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort());
//			
			
//			InputStream is = socket.getInputStream();		// 입력스트림 생성
//			OutputStream os = socket.getOutputStream();	// 출력스트림 생성
//			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));	// 입력(바이트->문자 하고 버퍼에 저장하고 쏨)
//			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);		// 출력 (바이트->문자 하고 버퍼에 저장하고 쏨)
//			
//			while (true) {
//				String data = br.readLine();
//				if(data == null) {
//					break;
//				}
//				if ( "exit".equals(data)) {
//					break;
//				}
//				System.out.println(data);
//				pw.println(data);
//			}
//			// 1. socket 생성
//			socket = new Socket();
//
//			// 2. 서버연결
//			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
//			System.out.println("[client] connected");
		
//		} catch (SocketException e) {
//			e.printStackTrace();
		
		} catch (IOException ex) {
			ex.printStackTrace();
			
//		} finally {
//			try {
//				if (socket != null && socket.isClosed() == false) {
//					socket.close();
//				}
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
		}
	}
}
