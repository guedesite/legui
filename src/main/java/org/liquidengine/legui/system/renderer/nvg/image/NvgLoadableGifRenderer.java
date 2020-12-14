package org.liquidengine.legui.system.renderer.nvg.image;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.IMAGE_REFERENCE_MANAGER;

import java.util.Map;

import org.joml.Vector2fc;
import org.liquidengine.legui.image.LoadableGif;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgLoadableImageReferenceManager;

public class NvgLoadableGifRenderer <I extends LoadableGif> extends NvgImageRenderer<I> {


    /**
     * Used to render specific Icon.
     *
     * @param image image to render.
     * @param position image position.
     * @param size image size.
     * @param context context.
     * @param nanovg nanoVG context.
     * @param properties properties map.
     */
    @Override
    protected void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg) {
    
        NvgLoadableImageReferenceManager manager = (NvgLoadableImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
        int imageRef = manager.getGifReference(image, nanovg);

        renderImage(imageRef, position, size, properties, nanovg);
    }
}
