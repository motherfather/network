package com.bit2016.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;

public class UDPEchoClient {
	private static final String SERVER_IP = "192.168.1.18";
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner scanner = null;

		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. 소켓 생성
			socket = new DatagramSocket();

			while (true) {
				// 3. 사용자 입력 받음 
				System.out.print(">>");
				String message = scanner.nextLine();		// 메세지
				if (message == null || "".equals(message)) {
					continue;
				}
				
				// 4. 메세지 전송
				byte[] sendData = message.getBytes("utf-8"); // 바이트로 변환, 보낼 편지!!
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));		// 3개짜리

				socket.send(sendPacket); // 전송!!
				
				// 5. 메세지 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				
				message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "utf-8");
				System.out.println("<<" + message);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원정리
			if (scanner != null) {
				scanner.close();
			}
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
