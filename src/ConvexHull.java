import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ConvexHull
{
	private ArrayList<Point> p, c, b;
	private long minArea;

	public ConvexHull()
	{
		p = new ArrayList<Point>();
		c = new ArrayList<Point>();
		b = new ArrayList<Point>();
		minArea = -1;
	}

	public void addPoint(Point x)
	{
		p.add(x);
		if (p.size() >= 3)
			compute();
	}

	public void clear()
	{
		p = new ArrayList<Point>();
		c = new ArrayList<Point>();
		b = new ArrayList<Point>();
		minArea = -1;
	}

	private static double cross(Point a, Point b, Point c)
	{
		return (b.x() - a.x()) * (c.y() - a.y()) - (b.y() - a.y())
				* (c.x() - a.x());
	}

	private static double cross2(Point a, Point b)
	{
		return a.x() * b.y() - a.y() * b.x();
	}

	private static double dot(Point a, Point b)
	{
		return a.x() * b.x() + a.y() * b.y();
	}

	private static double dist(Point a, Point b)
	{
		return Math.sqrt((a.x() - b.x()) * (a.x() - b.x()) + (a.y() - b.y())
				* (a.y() - b.y()));
	}

	private static double dist2(Point a)
	{
		return Math.sqrt(a.x() * a.x() + a.y() * a.y());
	}

	private double angle(Point a, Point b)
	{
		return Math.acos(dot(a, b) / (dist2(a) * dist2(b)));
	}

	public void compute()
	{
		Collections.sort(p);
		c = new ArrayList<Point>();
		b = new ArrayList<Point>();

		// Monotone chain
		for (int i = 0; i < p.size(); i++)
		{
			while (c.size() >= 2
					&& cross(c.get(c.size() - 2), c.get(c.size() - 1), p.get(i)) <= 0)
				c.remove(c.size() - 1);
			c.add(p.get(i));
		}

		for (int i = p.size() - 1, t = c.size() + 1; i >= 0; i--)
		{
			while (c.size() >= t
					&& cross(c.get(c.size() - 2), c.get(c.size() - 1), p.get(i)) <= 0)
				c.remove(c.size() - 1);
			c.add(p.get(i));
		}

		// Rotating calipers
		int l = 1, r = 1, u = 1, n = c.size() - 1;
		long area = Long.MAX_VALUE;

		for (int i = 0; i < n; i++)
		{
			Point edge = c.get((i + 1) % n).subtract(c.get(i))
					.divide(dist(c.get((i + 1) % n), c.get(i)));

			while (dot(edge, c.get(r % n).subtract(c.get(i))) < dot(edge, c
					.get((r + 1) % n).subtract(c.get(i))))
				r++;
			while (u < r
					|| cross2(edge, c.get(u % n).subtract(c.get(i))) < cross2(
							edge, c.get((u + 1) % n).subtract(c.get(i))))
				u++;
			while (l < u
					|| dot(edge, c.get(l % n).subtract(c.get(i))) > dot(edge, c
							.get((l + 1) % n).subtract(c.get(i))))
				l++;

			double w = dot(edge, c.get(r % n).subtract(c.get(i)))
					- dot(edge, c.get(l % n).subtract(c.get(i)));
			double h = Math.abs(cross2(c.get(i).subtract(c.get(u % n)),
					c.get((i + 1) % n).subtract(c.get(u % n)))
					/ (dist(c.get(i), c.get((i + 1) % n))));

			if (w * h < area)
			{
				area = (long) (w * h);
				b = new ArrayList<Point>();
				// Edge, Left-most point, Right-most point, Upper-most point
				b.add(c.get(i));
				b.add(c.get(i + 1));
				b.add(c.get(l % n));
				b.add(c.get(r % n));
				b.add(c.get(u % n));
			}
		}

		minArea = area;
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		for (Point i : p)
			g.fillRect((int) i.x(), (int) i.y(), 4, 4);

		g.setFont(new Font(Font.MONOSPACED, 0, 20));
		g.drawString(
				String.format("Minimum Area: %s", (c.size() <= 3 ? "N/A"
						: minArea + "px")), 50, 50);

		g.setColor(Color.GREEN);
		for (int i = 0; i < c.size() - 1; i++)
			g.drawLine((int) c.get(i).x(), (int) c.get(i).y(),
					(int) c.get(i + 1).x(),
					(int) c.get(i + 1).y());

		if (b.size() == 0)
			return;

		g.setColor(Color.RED);
		Point edge = b.get(1).subtract(b.get(0));
		Point left = b.get(2).subtract(b.get(0));
		Point right = b.get(3).subtract(b.get(1));

		Point normal = new Point(-edge.y(), edge.x()).divide(dist2(edge))
				.multiply(dist(b.get(4), b.get(0))
						* Math.sin(angle(edge,
								b.get(4).subtract(b.get(0)))));

		Point lowerLeft = edge.multiply(dot(edge, left)
				/ (dist2(edge) * dist2(edge))).add(b.get(0));
		Point lowerRight = edge.multiply(dot(edge, right)
				/ (dist2(edge) * dist2(edge))).add(b.get(1));

		g.drawLine((int) lowerLeft.x(), (int) lowerLeft.y(),
				(int) lowerRight.x(), (int) lowerRight.y());
		g.drawLine((int) lowerLeft.x(), (int) lowerLeft.y(),
				(int) normal.add(lowerLeft).x(), (int) normal.add(lowerLeft)
						.y());
		g.drawLine((int) lowerRight.x(), (int) lowerRight.y(),
				(int) normal.add(lowerRight).x(), (int) normal.add(lowerRight)
						.y());
		g.drawLine((int) normal.add(lowerLeft).x(), (int) normal.add(lowerLeft)
				.y(), (int) normal.add(lowerRight).x(),
				(int) normal.add(lowerRight)
						.y());
	}
}
