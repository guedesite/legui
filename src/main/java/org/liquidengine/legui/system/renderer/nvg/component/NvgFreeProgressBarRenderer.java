package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import org.joml.Vector2f;
import org.liquidengine.legui.component.FreeProgressBar;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

public class NvgFreeProgressBarRenderer<T extends FreeProgressBar>  extends NvgDefaultComponentRenderer<T> {

	    @Override
	    protected void renderSelf(T p, Context context, long nvg) 
	    {
	    	
	    	createScissor(nvg, p);
	        {
	        	nvgSave(nvg);
	        
		    	for(int i = 0; i < p.AllPose.size(); i++) {
		    	
		    		NvgShapes.drawRect(nvg, p.AllPose.get(i), p.AllSize.get(i), p.Color);
		    	}
	        }
	    }
}
