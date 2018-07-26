import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class ImageObject extends PaintObject implements Serializable
{
	public ImageObject(Color color, Point pointOne, Point pointTwo)
	{
		super(color, pointOne, pointTwo);
	}
	@Override
	void draw(Graphics g)
	{
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("Image/strawberry.jpeg"));
		} catch (IOException e) {
		}
		g.setColor(super.getColor());
		g.drawImage(img, super.getPointOne().x, super.getPointOne().y, super.getPointTwo().x-super.getPointOne().x, super.getPointTwo().y-super.getPointOne().y, null);
	}

}
