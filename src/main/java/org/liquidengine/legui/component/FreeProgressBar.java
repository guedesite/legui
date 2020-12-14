package org.liquidengine.legui.component;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4fc;
import org.liquidengine.legui.style.color.ColorConstants;

public class FreeProgressBar extends Component{

	
	public FreeProgressBar() {
		AllSize = new  ArrayList<Vector2f>();
		AllPose = new  ArrayList<Vector2f>();
	}
	
	public ArrayList<Vector2f> AllSize;
	public ArrayList<Vector2f> AllPose;
	public Vector4fc Color = ColorConstants.WHITE;
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FreeProgressBar button = (FreeProgressBar) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(AllSize, button.AllSize)
                .append(AllPose, button.AllPose)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(AllSize)
                .append(AllPose)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        		.append(AllSize)
                .append(AllPose)
                .toString();
    }
}
