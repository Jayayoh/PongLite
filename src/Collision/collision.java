package Collision;

//called to check and define collision events
public class collision
{
	public static boolean checkCollide(int x,int y,int width, int height, int x2, int y2, int width2, int height2)
	{
		boolean hit = false;
		
		//the following are to define different corners of the image

		int x2mod = x2 + width2; //top right (second)
		int y2mod = y2 + height2; // bottom left (second)

		//checking for collisions
		
		for(int a = 0; a < width; a++)
		{
			if((x + a) > x2 && (x + a) < x2mod)
			{
				for(int b = 0; b < height; b++)
				{
				
					if((y + b) > y2 && (y + b) < y2mod)
					{
						hit = true;
					}
				}
			}
		}
		return hit;
	}
	
	public static boolean checkLocationTop(int x,int y,int width, int height, int x2, int y2, int width2, int height2)
	{
		boolean top = false;
		
		if((y + (height / 2)) < y2)
		{
			top = true;
		}
		else
		{
			top = false;
		}
		
		return top;

	}
	
	public static boolean checkLocationBottom(int x,int y,int width, int height, int x2, int y2, int width2, int height2)
	{
		boolean bottom = false;

		if((y + (height / 2)) > (y2 + height2))
		{
			bottom = true;
		}
		else
		{
			bottom = false;
		}
		
		return bottom;
	}
	
	public static boolean checkLocationLeft(int x,int y,int width, int height, int x2, int y2, int width2, int height2)
	{
		boolean left = false;

		if((x + (width / 2)) < x2)
		{
			left = true;
		}
		else
		{
			left = false;
		}
		
		return left;
	}
	
	public static boolean checkLocationRight(int x,int y,int width, int height, int x2, int y2, int width2, int height2)
	{
		boolean right = false;
		
		if((x + (width / 2)) > (x2 + width2))
		{
			right = true;
		}
		else
		{
			right = false;
		}
		
		return right;
	}
}