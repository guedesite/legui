package org.liquidengine.legui.component;

public class Loader extends Component{
	
	public float Deg = 0;
	
	
	public Loader(float x, float y)
	{
		super(x,y,0,0);
	}
	
	
	public void addDeg(float deg) {
		Deg += deg;
		if(Deg > 360)
		{
			Deg = Deg - 360;
		}
	}
	

}
