
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A JPanel GUI for Netpaint that has all paint objects drawn on it.
 * Currently, a list of paint objects is hardcoded.  A JPanel exists
 * in this JFrame that will draw this list of paint objects.
 * 
 * @author Rick Mercer
 */

public class Client extends JFrame implements MouseListener, MouseMotionListener, ActionListener {

	public static void main(String[] args) {
		Client client = new Client();
		allPaintObjects = new Vector<>();
		client.setVisible(true);
		
		try {
		Socket server = new Socket("localhost", PORT_NUMBER);
		outputToServer = new ObjectOutputStream(server.getOutputStream());
		inputFromServer = new ObjectInputStream(server.getInputStream());
		} catch (IOException e) { 
			e.printStackTrace();
		}
		while (true) {
			try {
			allPaintObjects.add((PaintObject) inputFromServer.readObject());
			drawingPanel.repaint();
			} catch (ClassNotFoundException e1) {
				System.out.println("3");
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("4");
			}
		}
	}
	
	private static ObjectOutputStream outputToServer;
	private static ObjectInputStream inputFromServer;
	private static int PORT_NUMBER = 4004;
	private static DrawingPanel drawingPanel;
	private static Vector<PaintObject> allPaintObjects;
	private PaintObject ghostObject;
	private PaintObject currentObject;
	private Color currentColor;
	private boolean mousePressed;
	private String objectType;
	private Point firstPoint;
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		currentColor = Color.BLACK;
		objectType = "Line";
		setLocation(20, 20);
		setSize(800, 800);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		drawingPanel = new DrawingPanel();
		ghostObject = null;

		JRadioButton lineButton = new JRadioButton("Line");
		lineButton.setActionCommand("Line");
		lineButton.setSelected(true);

		JRadioButton ovalButton = new JRadioButton("Oval");
		ovalButton.setActionCommand("Oval");

		JRadioButton rectangleButton = new JRadioButton("Rectangle");
		rectangleButton.setActionCommand("Rectangle");

		JRadioButton imageButton = new JRadioButton("Image");
		imageButton.setActionCommand("Image");

		ButtonGroup group = new ButtonGroup();
		group.add(lineButton);
		group.add(ovalButton);
		group.add(rectangleButton);
		group.add(imageButton);

		lineButton.addActionListener(this);
		ovalButton.addActionListener(this);
		rectangleButton.addActionListener(this);
		imageButton.addActionListener(this);

		add(drawingPanel, BorderLayout.CENTER);
		JPanel subPanel = new JPanel();
		subPanel.add(lineButton);
		subPanel.add(ovalButton);
		subPanel.add(rectangleButton);
		subPanel.add(imageButton);

		JColorChooser chooser = new JColorChooser();
		
		chooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                currentColor = chooser.getColor();
            }

			
        });
		subPanel.add(chooser);
		
		add(subPanel, BorderLayout.SOUTH);
		setVisible(true);
	}

	/**
	 * This is where all the drawing goes.
	 * @author mercer
	 */
	class DrawingPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			/*
			try {
				Thread.sleep(50);  // 50 ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 */
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			// draw all of the paint objects
			
			
			for (int i = 0; i < allPaintObjects.size(); i++)
			{
				allPaintObjects.get(i).draw(g);
			}
			if(ghostObject != null)
			{
				ghostObject.draw(g);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(mousePressed)
		{
			try {
			outputToServer.writeObject(ghostObject);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			ghostObject = null;
		}
		mousePressed = !mousePressed;


		firstPoint = new Point(e.getX(), e.getY());


	}
	@Override
	public void mousePressed(MouseEvent e)
	{



	}
	private void setCurrentObject(Point firstPoint, Point secondPoint)
	{
		if(objectType.equals("Line"))currentObject = new Line(currentColor, firstPoint, secondPoint);
		else if(objectType.equals("Oval"))currentObject = new Oval(currentColor, firstPoint, secondPoint);
		else if(objectType.equals("Rectangle"))currentObject = new Rectangle(currentColor, firstPoint, secondPoint);
		else if(objectType.equals("Image"))currentObject = new ImageObject(currentColor, firstPoint, secondPoint);
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{

	}
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if(mousePressed)
		{
			setCurrentObject(firstPoint,e.getPoint());
			ghostObject = currentObject;
			drawingPanel.repaint();
		}

	}



	//Group the radio buttons.
	

	//Register a listener for the radio buttons.

	public void actionPerformed(ActionEvent e) {
		objectType = e.getActionCommand();

	}
}