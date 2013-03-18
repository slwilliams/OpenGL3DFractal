import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.ARBBufferObject.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import java.nio.*;

public class World
{
    Controller controller;
    int width = 1000;
    int height = 1000;
   	FloatBuffer cBuffer = null;
	FloatBuffer vBuffer = null;
	IntBuffer ib = null;
	int vHandle = -1;
	int cHandle = -1;
	double topX = 2.5;
	double topY = 2.5;
	double offsetX = 1.75;
	double offsetY = 1.20;

    public World(Controller _controller)
	{
		controller = _controller;
	}

	public int mandel(double px, double py)
	{
		double zx = 0.0, zy = 0.0;
    	double zx2 = 0.0, zy2 = 0.0;
    	int value = 0;
    	while (value < 200 && zx2 + zy2 < 4.0)
    	{
      		zy = Math.abs(2.0 * zx * zy + py);
      		zx = Math.abs(zx2 - zy2 + px);
      		zx2 = zx * zx;
      		zy2 = zy * zy;
      		value++;
    	}
   		return value;
	}

	public void initi()
	{
		cBuffer = BufferUtils.createFloatBuffer(3*width*height);
		vBuffer = BufferUtils.createFloatBuffer(3*width*height);
     
		for(int i = 0; i < width; i ++)
	    {
	    	for(int j = 0; j < height; j ++)
	    	{
	    		double dx = (((topX * i) / (width))) - offsetX;
        		double dy = offsetY - (((topY * j) / (height)));
	    		int result = -(mandel(dx, dy));
	    		vBuffer.put((float)i).put((float)j).put((float)result);
	    		cBuffer.put((float)Math.abs(Math.tanh(result))).put((float)Math.abs(Math.sin(Math.pow(Math.E,result)))).put((float)Math.abs(Math.cos(result)));
	    	}
	    }    	    
      	cBuffer.flip();
      	vBuffer.flip();
      	ib = BufferUtils.createIntBuffer(2);
		
		glGenBuffersARB(ib);
		vHandle = ib.get(0);
		cHandle = ib.get(1);
	}

	public void render()
	{		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vHandle);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, vBuffer, GL_STATIC_DRAW_ARB);
		glVertexPointer(3, GL_FLOAT, 3 << 2, 0L);
		
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, cHandle);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, cBuffer, GL_STATIC_DRAW_ARB);
		glColorPointer(3, GL_FLOAT, 3 << 2, 0L);
		
		glDrawArrays(GL_POINTS, 0, 3*width*height);
		
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
		
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);		
	}

	public static void main(String[] args)
	{
		new Boot();
	}
}
