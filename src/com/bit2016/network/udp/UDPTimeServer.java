package com.bit2016.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UDPTimeServer {
	public static final int PORT = 5555;

	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null; // tcp 는 바인딩과 억셉트가 없어서 serverSokcet이 필요
										// 없다. socket을 serverSocket과 같이 쓰므로!!
		try {
			// 1 . 소켓생성
			socket = new DatagramSocket(PORT); // 그래서 소켓 생성시 바로 포트를 적어준다.

			while (true) {
				// 2. 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE); // 받을 편지!!
				
				System.out.println("[server]" + InetAddress.getLocalHost().getHostAddress() + ":" + PORT);
				System.out.println("[server] 대기중");
				socket.receive(receivePacket); // blocking!!
				System.out.println("[server] 수신");

				// String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "utf-8");
				// System.out.println("[server] received : " + message);

				// Date
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd aaa HH:mm:ss.SSS", Locale.US);		// Locale.US를 써서 오전/오후를 AM/PM으로 변경가능!!
				String data = format.format(new Date());
								
				// 3. 데이터 송신
				byte[] sendData = data.getBytes("utf-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),receivePacket.getPort()); // 4개짜리
				socket.send(sendPacket);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
