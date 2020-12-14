package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.theme.Themes;

public class TexturedButton extends Component implements TextComponent {


    private TextState textState;

    public boolean hasChange = false;
    private String Texture;
    private String Hover;
   
    public TexturedButton(String text, float x, float y, float width, float height, String texturePath) {
        super(x, y, width, height);
        initialize(text);
        Texture = texturePath;
    }

   
  
    private void initialize(String text) {
        this.textState = new TextState(text);
        textState.setHorizontalAlign(HorizontalAlign.CENTER);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(TexturedButton.class).applyAll(this);
    }

    /**
     * Returns text data of button.
     *
     * @return text state of button.
     */
    
    public String getTexture()
    {
    	return Texture;
    }
    
    public void setTexture(String texture)
    {
    	Texture = texture;
    	hasChange = true;
    }
    
    public TextState getTextState() {
        return textState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Button button = (Button) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getTextState(), button.getTextState())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .toString();
    }
    
    public boolean HasHover()
    {
    	return Hover != null;
    }
    public String getHover()
    {
    	return Hover;
    }
    public void setHover(String s)
    {
    	Hover = s;
    }

}
