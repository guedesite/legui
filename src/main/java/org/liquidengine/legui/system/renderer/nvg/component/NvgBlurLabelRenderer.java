package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.style.util.StyleUtilities.getInnerContentRectangle;
import static org.liquidengine.legui.style.util.StyleUtilities.getPadding;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgFontBlur;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.BlurLabel;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

public class NvgBlurLabelRenderer extends NvgDefaultComponentRenderer<BlurLabel> {

    @Override
    public void renderSelf(BlurLabel label, Context context, long nanovg) {
        createScissor(nanovg, label);
        {
            Vector2f pos = label.getAbsolutePosition();
            Vector2f size = label.getSize();

            
            /*Draw background rectangle*/
          //  renderBackground(label, context, nanovg);

            // draw text into box
            TextState textState = label.getTextState();
            Vector4f padding = getPadding(label, label.getStyle());
            Vector4f rect = getInnerContentRectangle(pos, size, padding);
            
            nvgFontBlur(nanovg,label.getBlur());
            NvgText.drawTextLineToRect(nanovg, textState, rect, false);
        }
        resetScissor(nanovg);
    }
}
