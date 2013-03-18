import static org.lwjgl.util.glu.GLU.gluPerspective;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import java.util.ArrayList;

public class Boot
{
	private Controller controller = new Controller();
	private World world = new World(controller);
	private float fov = 50;

	public Boot()
	{
		try
		{
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			//Display.setDisplayMode(new DisplayMode(800,600));
			Display.setTitle("Test7");
			Display.create();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov, (float)Display.getWidth() / (float)Display.getHeight(), 0.001f, 10000);
		glMatrixMode(GL_MODELVIEW);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_POINT_SMOOTH);
		glPointSize(1.0f);

		controller.assignWorld(world);

		Mouse.setGrabbed(true);
		
		world.initi();
	
		while(!Display.isCloseRequested())
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			controller.UpdateInput();

			glPushMatrix();
				glRotatef((float)(controller.getRotY() * -controller.mouseSpeed), 1.0f, 0.0f, 0.0f);
				glRotatef((float)(controller.getRotX() * controller.mouseSpeed), 0.0f, 1.0f, 0.0f);

				glTranslatef(controller.getX(), controller.getY(), controller.getZ());
			//	world.initi();
			
				world.render();
			glPopMatrix();

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}

	public static void main(String[] args)
	{
		new Boot();
	}
}
