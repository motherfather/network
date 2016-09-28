package com.bit2016.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		while(true) {
			
			Scanner scan = new Scanner(System.in);
			System.out.print("> ");
			String host = scan.nextLine();
			
			if("exit".equals(host)) {
				break;
			}
			try {
				InetAddress[] inetAddresses = InetAddress.getAllByName(host);
				
				for(InetAddress inetAddress : inetAddresses) {
					System.out.println(host + " : " + inetAddress.getHostAddress());
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

	}

}
