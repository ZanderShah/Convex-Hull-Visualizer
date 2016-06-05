public class Point implements Comparable<Point>
{
	private int x, y;

	Point(int xx, int yy)
	{
		x = xx;
		y = yy;
	}

	public int x()
	{
		return x;
	}

	public int y()
	{
		return y;
	}

	public int compareTo(Point p)
	{
		if (x == p.x)
			return y - p.y;
		return x - p.x;
	}

	public String toString()
	{
		return x + " " + y;
	}
}
