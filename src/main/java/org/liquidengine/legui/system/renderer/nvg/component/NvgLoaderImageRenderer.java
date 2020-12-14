package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.ImageRenderer.C_RADIUS;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderImage;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.*;

import java.util.HashMap;

import org.joml.Vector2f;
import org.liquidengine.legui.component.LoaderImage;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;

public class NvgLoaderImageRenderer extends NvgDefaultComponentRenderer<LoaderImage> {

    @Override
    protected void renderSelf(LoaderImage imageView, Context context, long nanovg) {
        Vector2f size = imageView.getSize();
        Vector2f position = imageView.getAbsolutePosition();

        createScissor(nanovg, imageView);
        {
            HashMap<String, Object> p = new HashMap<>();
            p.put(C_RADIUS, NvgRenderUtils.getBorderRadius(imageView));

            nvgRotate(nanovg, imageView.getDeg());
            
            renderImage(imageView.getImage(), position, size, p, context);
            
            nvgRotate(nanovg, -imageView.getDeg());
        }
        resetScissor(nanovg);
    }
}
