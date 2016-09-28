package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;

	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
			System.out.println("[server#" + getId() + "] connected by client[" 
									+ isa.getAddress().getHostAddress() + ":" + isa.getPort() + "]");
			
			InputStream is = socket.getInputStream(); // 입력스트림 생성
			OutputStream os = socket.getOutputStream(); // 출력스트림 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);

			while (true) {
				String data = br.readLine();
				if (data == null) {
					System.out.println("bye!!");
					break;
				}
				System.out.println(data);
				pw.println(data);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
