import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public class Line extends PaintObject implements Serializable
{

	public Line(Color color, Point point, Point point2)
	{
		super(color, point, point2);
	}

	@Override
	void draw(Graphics g)
	{
		g.setColor(super.getColor());
		g.drawLine(super.getPointOne().x, super.getPointOne().y, super.getPointTwo().x, super.getPointTwo().y);
		
	}

}
