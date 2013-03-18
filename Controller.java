import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Controller
{
	private float forwards = 25.0f;
	private float left = 0.0f;
	private float up = 160.0f;
	private int rotX = 0;
	private int rotY = 0;

	public double mouseSpeed = 0.035;	
	public double walkSpeed = 10;
	public double runSpeed = walkSpeed * 2;
	public double moveSpeed = walkSpeed;
	
	private boolean upArrow = false;
	private boolean downArrow = false;
	private boolean leftArrow = false;
	private boolean rightArrow = false;
	private boolean lShift = false;
	
	private World world;
	
	public Controller()
	{
	
	}
	
	public void assignWorld(World _world)
	{
		world = _world;
	}
	
	public double getAngleX()
	{
		return  rotX * mouseSpeed;
	}
	
	public double getAngleY()
	{
		return rotY * mouseSpeed;
	}

	public void UpdateInput()
	{
		GeneralInput();
		
		double angleX = rotX * mouseSpeed;
		double angleY = rotY * mouseSpeed;

		if(lShift)
			moveSpeed = runSpeed;
		else
			moveSpeed = walkSpeed;

		if(upArrow)
		{
			/* X */ left += (float)(-moveSpeed * (Math.sin(Math.toRadians(angleX)))*(Math.cos(Math.toRadians(angleY))));	
			/* Y */ up += (float)(-moveSpeed * (Math.sin(Math.toRadians(angleY))));
			/* Z */ forwards += (float)(moveSpeed * (Math.cos(Math.toRadians(angleX)))*(Math.cos(Math.toRadians(angleY))));
		}

		if(downArrow)
		{
			forwards -= (float)(moveSpeed * (Math.cos(Math.toRadians(angleX))));				
			left -= (float)(-moveSpeed * (Math.sin(Math.toRadians(angleX))));			
			up -= (float)(-moveSpeed * (Math.sin(Math.toRadians(angleY))));	
		}

		if(leftArrow)
		{
			forwards += (float)(moveSpeed * (Math.cos(Math.toRadians(angleX - 90))));				
			left += (float)(-moveSpeed * (Math.sin(Math.toRadians(angleX - 90))));
		}

		if(rightArrow)
		{
			forwards += (float)(moveSpeed * (Math.cos(Math.toRadians(angleX + 90))));				
			left += (float)(-moveSpeed * (Math.sin(Math.toRadians(angleX + 90))));
		}	
	}
	

	public void GeneralInput()
	{
		rotX += Mouse.getDX();
		rotY += Mouse.getDY();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			upArrow = true;
		else
			upArrow = false;

		if(Keyboard.isKeyDown(Keyboard.KEY_S))
			downArrow = true;
		else
			downArrow = false;

		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			leftArrow = true;
		else
			leftArrow = false;

		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			rightArrow = true;
		else
			rightArrow = false;

		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Mouse.setGrabbed(false);			
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
			Mouse.setGrabbed(true);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			lShift = true;
		else
			lShift = false;		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_LEFT)) { 
			world.topX *= 1.01;
			return;
		}	
				
		if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) { 
			world.topX *= 0.99;
			return;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_DOWN)) { 
			world.topY *= 1.01;
			return;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			world.topY *= 0.99;
			return;
		}				
			
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			world.offsetX *= 1.01;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			world.offsetX *= 0.99;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP))
			world.offsetY *= 1.01;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			world.offsetY *= 0.99;
		
				
	
	}
	
	public float getX()
	{
		return left;
	}

	public float getY()
	{
		return up;
	}

	public float getZ()
	{
		return forwards;
	}

	public int getRotX()
	{
		return rotX;
	}

	public int getRotY()
	{
		return rotY;
	}
	public static void main(String[] args)
	{
		new Boot();
	}

}
