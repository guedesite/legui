package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.*;


import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Loader;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgColorUtil;
import org.lwjgl.nanovg.NVGColor;

public class NvgLoaderRenderer extends NvgDefaultComponentRenderer<Loader> {

    @Override
    protected void renderSelf(Loader l, Context context, long nvg) {

        createScissor(nvg, l);
        {
        	nvgSave(nvg);
    
        	Vector2f pos = l.getAbsolutePosition();
        	  try (NVGColor fillColor = NVGColor.calloc()) {
        	
                  
                

                  nvgBeginPath(nvg);
                  NvgColorUtil.fillNvgColorWithRGBA(ColorConstants.white(), fillColor);
                  
                  nvgArc(nvg, pos.x, pos.y, 90,(float) Math.toRadians( 45+ l.Deg),(float) Math.toRadians(-45+ l.Deg), NVG_SOLID );
                 
                  
                 
                  NvgColorUtil.fillNvgColorWithRGBA(new Vector4f(0,0,0,0), fillColor);
                  
                  nvgArc(nvg, pos.x, pos.y, 80,(float) Math.toRadians( -45+ l.Deg),(float) Math.toRadians(45+ l.Deg), NVG_HOLE );
                  nvgFill(nvg);

                  
         
        	  }

        }
        resetScissor(nvg);
    }

}