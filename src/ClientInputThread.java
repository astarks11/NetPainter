import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ClientInputThread implements Runnable {

	ObjectInputStream reader; // input stream
	Socket socket; // socket 
	private Vector<ObjectOutputStream> clientOutputStreams;
	private Vector<Object> objects; // object list
	
	public ClientInputThread(Socket clientSocket, Vector<ObjectOutputStream> clientOutputStreams, Vector<Object> objs)
	{
		this.clientOutputStreams = clientOutputStreams;
		this.socket = clientSocket;
		this.objects = objs;
		// check for null socket
		if (socket == null)
			JOptionPane.showMessageDialog(null, "socket is null");
		try {
			// set reader to inputstream
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("2");
		}
	}
	
	
	@Override
	public void run() {
		Object input; // user input
		
		try {
			while (true) 
			{
				updateClients(objects);
				// wait for client to send object
				input = reader.readObject();
				objects.add(input);
				// update everyone
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("1");
		}
	}
	
	
	
	// writes objects to all clients
	private void updateClients(Vector<Object> message)
	{
		for (ObjectOutputStream output : clientOutputStreams)
		{
			try {
			for (Object obj : message)
			{
			output.writeObject(obj);
			output.flush();
			}
			} catch (Exception e) {
				clientOutputStreams.remove(output);
			}
		}
	}
	
	public Vector<Object> getObjects() { return objects; }
	
}
