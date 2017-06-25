import java.awt.*;

import javax.swing.JComponent;


public class Canvas extends JComponent {
		
	Graphics2D g2d;
	Point coordStart, coordFinish;
	Algorithm alg;
	
	
	
	public void drawMap(Map _map)
	{
		coordStart = null;
		coordFinish = null;
		for (int i = 0; i < _map.getHeight(); i++)
			for (int j = 0; j < _map.getWidth(); j++)
			{
				if (!_map.isExist(new Point(j, i)))
				{
					drawPoint(new Point(j*10, i*10), Color.black);
				}
				else 
				{
					drawPoint(new Point(j*10, i*10), Color.white);
				}
			}
			
		
	}
	
	public void drawPoint(Point newcoord, Color color)
	{
		Graphics g = super.getGraphics();
		g2d=(Graphics2D)g;
		g2d.setPaint(color);
		g2d.fillRect(newcoord.x + 1, newcoord.y + 1, 9, 9);	
	}

	public Point setStartFinish(Point newcoord, boolean Type, Point coord)
	{
		
		if (coord == null) {
			coord = new Point();
		}
		else {
			drawPoint(coord, Color.white);
		}
		
		coord.x = newcoord.x - (newcoord.x % 10);
		coord.y = newcoord.y - (newcoord.y % 10);
		drawPoint(coord, (Type) ? Color.blue : Color.green);
		
		return coord;
	}
	
	
	public void setStart(int x, int y)
	{
		coordStart = setStartFinish(new Point(x, y), true, coordStart);
		/*if (coordXStart != null || coordYStart != null)
			drawPoint(coordXStart, coordYStart, Color.white);
		coordXStart = x - (x % 10);
		coordYStart = y - (y % 10);
		drawPoint(coordXStart, coordYStart, Color.blue);*/
	}

	public void setFinish(int x, int y)
	{
		coordFinish = setStartFinish(new Point(x, y), false, coordFinish);

		/*if (coordXFinish != null || coordYFinish != null)
			drawPoint(coordXFinish, coordYFinish, Color.white);
		coordXFinish = x - (x % 10);
		coordYFinish = y - (y % 10);
		drawPoint(coordXFinish, coordYFinish, Color.green);*/
	}
	
	
	public void paintComponent(Graphics g){
		
		super.paintComponents(g);		
		g2d=(Graphics2D)g;

		g2d.setPaint(Color.white);
		g2d.fillRect(0, 0, 700, 500);
		g2d.setPaint(Color.black);
		g2d.drawRect(0, 0, 700, 500);
		
		for (int i = 0; i < 700; i += 10)
		{
			g2d.setPaint(Color.black);
			g2d.drawLine(i, 0, i, 500);
		}

		for (int i = 0; i < 500; i += 10)
		{
			g2d.setPaint(Color.black);
			g2d.drawLine(0, i, 700, i);
		}
			
		//super.repaint();
	  }	

}
