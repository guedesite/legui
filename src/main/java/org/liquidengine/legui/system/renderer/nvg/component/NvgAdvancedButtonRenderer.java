package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.AdvancedButton;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

public class NvgAdvancedButtonRenderer extends NvgDefaultComponentRenderer<AdvancedButton> {

    @Override
    protected void renderSelf(AdvancedButton button, Context context, long nanovg) {
        createScissor(nanovg, button);
        {
            Vector2f pos = button.getAbsolutePosition();
            Vector2f size = button.getSize();

            // render background
            renderAdvancedBackground(button, context, nanovg);

            // Render text
            nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
            NvgText.drawTextLineToRect(nanovg, button.getTextState(), pos, size, true);

        }
        resetScissor(nanovg);
    }

}
