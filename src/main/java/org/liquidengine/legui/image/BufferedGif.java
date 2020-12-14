package org.liquidengine.legui.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptionTemplate;
import org.liquidengine.leutil.io.IOUtil;
import org.lwjgl.stb.STBImage;

public class BufferedGif  extends LoadableGif {

	
	
    private static final Logger LOGGER = LogManager.getLogger();
    private int[] width;
    private int[] height;
    private ImageChannels[] channels;
    private ByteBuffer[] imageData;
    private int activeFrame = 0;
    private int maxFrame;
    private boolean first = true;
    
    private boolean boucle = true;

    /**
     * This constructor should be used with {@link #setPath(String)} and {@link #load()} methods.
     */
    public BufferedGif() {
    }

    /**
     * Used to create buffered image object and load it.
     *
     * @param path path to image source.
     */
    public BufferedGif(String path) {
        super(path);
        try {
            load();
        } catch (LeguiException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e);
            }
        }
    }

    public BufferedGif setBoucle(boolean b)
    {
    	this.boucle = b;
    	return this;
    }
    
    /**
     * Should be used to load image data from source.
     */
    @Override
    public void load() {
        try {
        	
        	ImageReader reader = (ImageReader)ImageIO.getImageReadersByFormatName("gif").next();
    	    ImageInputStream ciis = ImageIO.createImageInputStream(new File(getPath()));
    	    reader.setInput(ciis, false);

    	    
    	    maxFrame = reader.getNumImages(true);
    	 
    	    this.imageData = new ByteBuffer[maxFrame];
    	    this.width= new int[maxFrame];
    	    this.height = new int[maxFrame];
    	    this.channels = new ImageChannels[maxFrame];
    	    
    	    for (int i = 0; i <  maxFrame; i++) { 
    	    	
    
    	    	
    	    	
    	    	
    	    	ByteBuffer byteBuffer =convertImageData(reader.read(i));
    	
    	    	 	int[] width = {0};
    	            int[] height = {0};
    	            int[] channels = {0};
    	            ByteBuffer imageData = STBImage.stbi_load_from_memory(byteBuffer, width, height, channels, 4);

    	            if (imageData != null) {
    	            	
    	                this.width[i] = width[0];
    	                this.height[i] = height[0];
    	                this.channels[i] = ImageChannels.instance(channels[0]);
    	                this.imageData[i] = imageData;
    	            } else { // if error occurs
    	                throw LeguiExceptionTemplate.FAILED_TO_LOAD_IMAGE.create(STBImage.stbi_failure_reason());
    	            }
    	        
    	    }

        
        } catch (IOException e) {
            throw LeguiExceptionTemplate.FAILED_TO_LOAD_IMAGE.create(e, e.getMessage());
        }
    }
    
    public static ByteBuffer convertImageData(java.awt.image.BufferedImage bi) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", out);
            InputStream is = new ByteArrayInputStream(out.toByteArray());
            byte[] bytes = IOUtils.toByteArray(is);
            ByteBuffer data = null;
            data = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
            data.flip();
            return data;
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        return null;
    }

    /**
     * Returns image width.
     *
     * @return image width.
     */
    public int getWidth() {
        return width[activeFrame];
    }

    /**
     * Returns image height.
     *
     * @return image height.
     */
    public int getHeight() {
        return height[activeFrame];
    }

    /**
     * Returns image data.
     *
     * @return image data.
     */
    public ByteBuffer getImageData() {
        return imageData[NextFrame()];
    }

    
    public int NextFrame()
    {
    	if(!first)
    	{
    		activeFrame++;
	    	if(boucle)
	    	{
	    		if(maxFrame <= activeFrame)
	    		{
	    			activeFrame = 0;
	    		}
	    	}
	    	else if(maxFrame <= activeFrame){
	    		activeFrame--;
	    	}
    	} else {
    		first = false;
    	}
    	return activeFrame;
    }
    /**
     * Returns image channels.
     *
     * @return image channels.
     */
    public ImageChannels getChannels() {
        return channels[activeFrame];
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("path", getPath())
            .append("width", width)
            .append("height", height)
            .append("channels", channels)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
     
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getPath())
            .append(width)
            .append(height)
            .append(channels)
            .toHashCode();
    }

	@Override
	public int getFrame() {
		// TODO Auto-generated method stub
		return activeFrame;
	}

}
