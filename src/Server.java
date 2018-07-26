import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

	public static int PORT_NUMBER = 4004; // port number
	Vector<ObjectOutputStream> objectStreams; // object output stream list
	Vector<Object> objects; // list of objects
	public static void main(String[] args) {
		// create server
		new Server();
	}

	// create server
	private Server() {
		objectStreams = new Vector<ObjectOutputStream>(); // object output stream list
		ServerSocket socket; // server socket
		ObjectOutputStream outputStream; // output stream
		Thread thread; // thread
		ClientInputThread inputThread = null; // inputThread object
		objects = new Vector<Object>();
		
		
		try {
			socket = new ServerSocket(PORT_NUMBER);
			while (true) {
				Socket connection = socket.accept();
				if (inputThread != null)
				{
					objects = inputThread.getObjects();
				}
				outputStream = new ObjectOutputStream(connection.getOutputStream());
				objectStreams.add(outputStream);
				inputThread = new ClientInputThread(connection, objectStreams, objects);
				thread = new Thread(inputThread);
				thread.start();
				System.out.println("got a connection");
			}
		} catch (Exception e) {
			System.out.println("5");
			e.printStackTrace();
		}
	}
}