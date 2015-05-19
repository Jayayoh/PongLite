package MouseBounds;

public class mouseBounds
{
	public static boolean mouseCollide(int mx,int my,int x,int y,int width,int height)
	{
		boolean hit = false;
		
		if(mx > x && mx < (x + width))
		{
			if(my > y && my < (y + height))
			{
				hit = true;
			}
		}
		else
		{
			hit = false;
		}
		
		return hit;
	}
}