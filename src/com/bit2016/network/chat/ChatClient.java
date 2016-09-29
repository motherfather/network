package com.bit2016.network.chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scan = null;

		// List<PrintWriter> listPrintWriter = new ArrayList<PrintWriter>();

		try {
			socket = new Socket();
			scan = new Scanner(System.in);

			System.out.println("접속할 아이피와 포트 입력 ex)192.168.1.18 9000");
			String data = scan.nextLine();

			String[] address = data.split(" ");


			socket.connect(new InetSocketAddress(address[0], Integer.parseInt(address[1])));

			System.out.println("접속");

//			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while (true) {
				Thread thread = new ChatClientThread(socket/* , listPrintWriter */);
				thread.start();
				String x = scan.nextLine();
				pw.println(x);
				if (x.equals("QUIT")) {
					System.out.println();
					break;
				}
				// String x = br.readLine();
				// if ((br.readLine()).equals(null)) {
				// break;
				// }
			}

		} catch (IOException e) {
			consoleLog("error1 : " + e);
		} //finally {
			//try {
//				if (socket != null && socket.isClosed() == false) {
//					socket.close();
//				}
				//if (scan != null) {
					scan.close();
//				}
//			} catch (IOException e) {
//				consoleLog("error2 : " + e);
//			}
//		}
	}

	public static void consoleLog(String data) {
		System.out.println(data);
	}

}
