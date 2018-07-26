import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public abstract class PaintObject implements Serializable
{
	private Point pointOne, pointTwo;
	private Color color;
	public PaintObject(Color color, Point pointOne, Point pointTwo)
	{
		this.setColor(color);
		this.setPointOne(pointOne);
		this.setPointTwo(pointTwo);

	}
	abstract void draw(Graphics g);
	public Point getPointOne()
	{
		return pointOne;
	}
	public void setPointOne(Point pointOne)
	{
		this.pointOne = pointOne;
	}
	public Point getPointTwo()
	{
		return pointTwo;
	}
	public void setPointTwo(Point pointTwo)
	{
		this.pointTwo = pointTwo;
	}
	public Color getColor()
	{
		return color;
	}
	public void setColor(Color color)
	{
		this.color = color;
	}

}
