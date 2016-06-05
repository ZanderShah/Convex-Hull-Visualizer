import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Calculates the convex hull for a set of points
 *
 * @author Alexander Shah
 * @version Jun 5, 2016
 */
public class ConvexHull
{
	private ArrayList<Point> p, up, low;

	public ConvexHull()
	{
		p = new ArrayList<Point>();
		up = new ArrayList<Point>();
		low = new ArrayList<Point>();
	}

	public void addPoint(Point x)
	{
		p.add(x);
		compute();
	}

	public void clear()
	{
		p = new ArrayList<Point>();
		up = new ArrayList<Point>();
		low = new ArrayList<Point>();
	}

	private static int ccw(Point p, Point q, Point r)
	{
		return (q.y() - p.y()) * (r.x() - p.x()) -
				(q.x() - p.x()) * (r.y() - p.y());
	}

	public void compute()
	{
		Collections.sort(p);
		up = new ArrayList<Point>();
		low = new ArrayList<Point>();

		// Checks for left turns on the upper half and right turns on the lower half
		for (int i = 0; i < p.size(); i++)
		{
			while (up.size() > 1
					&& ccw(up.get(up.size() - 2), up.get(up.size() - 1),
							p.get(i)) <= 0)
				up.remove(up.size() - 1);
			while (low.size() > 1
					&& ccw(low.get(low.size() - 2), low.get(low.size() - 1),
							p.get(i)) >= 0)
				low.remove(low.size() - 1);
			up.add(p.get(i));
			low.add(p.get(i));
		}
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		for (Point i : p)
			g.fillRect(i.x(), i.y(), 4, 4);
		g.setColor(Color.GREEN);
		for (int i = 0; i < low.size() - 1; i++)
			g.drawLine(low.get(i).x(), low.get(i).y(), low.get(i + 1).x(), low
					.get(i + 1).y());
		for (int i = 0; i < up.size() - 1; i++)
			g.drawLine(up.get(i).x(), up.get(i).y(), up.get(i + 1).x(),
					up.get(i + 1).y());
	}
}
