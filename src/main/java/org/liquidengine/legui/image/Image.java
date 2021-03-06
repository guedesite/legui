package org.liquidengine.legui.image;

/**
 * Fixed size picture.
 */
public abstract class Image {

	public boolean HasChange = true;
    /**
     * Returns image width.
     *
     * @return image width.
     */
    public abstract int getWidth();

    /**
     * Returns image height.
     *
     * @return image height.
     */
    public abstract int getHeight();
}
