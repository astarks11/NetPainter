import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public class Rectangle extends PaintObject implements Serializable
{

	public Rectangle(Color color, Point point, Point point2)
	{

		super(color, point, point2);

	}

	@Override
	void draw(Graphics g)
	{
		g.setColor(super.getColor());
		g.fillRect(Math.min(super.getPointOne().x,super.getPointTwo().x), Math.min(super.getPointOne().y,super.getPointTwo().y), Math.abs(super.getPointTwo().x-super.getPointOne().x), Math.abs(super.getPointTwo().y-super.getPointOne().y));

	}

}
