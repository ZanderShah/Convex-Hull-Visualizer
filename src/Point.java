class Point implements Comparable<Point>
{
	private double x, y;

	Point(double xx, double yy)
	{
		x = xx;
		y = yy;
	}

	public int compareTo(Point p)
	{
		if (x == p.x())
			return ((Double) y).compareTo(p.y());
		return ((Double) x).compareTo(p.x());
	}

	public double x()
	{
		return x;
	}

	public double y()
	{
		return y;
	}

	public String toString()
	{
		return x + " " + y;
	}

	public Point subtract(Point p)
	{
		return new Point(x - p.x(), y - p.y());
	}

	public Point divide(Double scalar)
	{
		return new Point(x / scalar, y / scalar);
	}
}