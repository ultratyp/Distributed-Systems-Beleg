package de.htw.ds.sudoku;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public final class ServiceStopper {

	public static void main(final String[] args) throws IOException {
		final int servicePort = Integer.parseInt(args[1]);
		
		try {
			final Socket connection = new Socket(args[0], servicePort);
			
			final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
			final DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
			final DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
			
			dataOutputStream.writeUTF(args[2]);
			bufferedOutputStream.flush();

			System.out.println("shutdown command sent");
			
			final String response = dataInputStream.readUTF();
			if (response.equals("ok")) {
				System.out.println("server stopped");
			} else if (response.equals("fail")) {
				System.out.println("server stopped not");
			} else {
				System.out.println("unknown server response");
			}
			
		} catch(Throwable exception) {
			System.out.println("could not connect to server");
		}
	}
}