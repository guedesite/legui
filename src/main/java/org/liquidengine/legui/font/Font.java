package org.liquidengine.legui.font;

import org.liquidengine.legui.util.IOUtil;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.liquidengine.legui.exception.LeguiExceptionTemplate.FAILED_TO_LOAD_FONT;

/**
 * Representation of font. Used by text components to specify font to use by renderer.
 */
public class Font {
    /**
     * Font data.
     */
    private final ByteBuffer data;

    /**
     * Path to font.
     */
    private final String     path;

    /**
     * Used to create font by specified path. Loads font from specified path.
     *
     * @param path path to font data.
     */
    public Font(String path) {
        this.path = path;
        try {
            data = IOUtil.ioResourceToByteBuffer(path, 1024);
        } catch (IOException e) {
            throw FAILED_TO_LOAD_FONT.create(e, path);
        }
    }

    /**
     * Used to create font with specified path and data.
     *
     * @param path path to font.
     * @param data font data.
     */
    public Font(String path, ByteBuffer data) {
        this.path = path;
        this.data = data;
    }

    /**
     * Returns font data.
     *
     * @return font data.
     */
    public ByteBuffer getData() {
        return data;
    }

    /**
     * Returns font path.
     *
     * @return font path.
     */
    public String getPath() {
        return path;
    }
}