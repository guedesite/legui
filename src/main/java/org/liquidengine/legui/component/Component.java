package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.intersection.RectangleIntersector;
import org.liquidengine.legui.listener.ListenerMap;
import org.liquidengine.legui.theme.Theme;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Component is an object that have graphical representation in legui system.
 * <p>
 * Created by ShchAlexander on 9/14/2016.
 */
public abstract class Component implements Serializable {
    /**
     * Used to store corner radius of component.
     */
    private float               cornerRadius = Theme.DEFAULT_THEME.cornerRadius();
    /**
     * Metadata map, place where renderers or event processors can store state of component.
     */
    private Map<String, Object> metadata     = new HashMap<>();
    /**
     * Parent component container. For root components it could be null
     */
    private Container parent;
    /**
     * Map for UI event listeners.
     */
    private ListenerMap listenerMap     = new ListenerMap();
    /**
     * Position of component relative top left corner in parent component.
     * <p>
     * If component is the root component then position calculated relative window top left corner.
     */
    private Vector2f    position        = new Vector2f();
    /**
     * Size of component.
     */
    private Vector2f    size            = new Vector2f();
    /**
     * Component background color.
     * <p>Represented by vector where (x=r,y=g,z=b,w=a).
     * <p>For example white = {@code new Vector4f(1,1,1,1)}
     */
    private Vector4f    backgroundColor = Theme.DEFAULT_THEME.backgroundColor();
    /**
     * Component border.
     */
    private Border      border          = Theme.DEFAULT_THEME.border();
    /**
     * Used to enable and disable event processing for this component.
     * If enabled==false then component won't receive events.
     */
    private boolean     enabled         = true;
    /**
     * Determines whether this component should be visible when its
     * parent is visible. Components are initially visible.
     */
    private boolean     visible         = true;
    /**
     * Intersector which used to determine for example if cursor intersects component or not. Cannot be null.
     */
    private Intersector intersector     = new RectangleIntersector();

    private boolean hovered;
    private boolean focused;
    private boolean pressed;


    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json serializer/deserializer component should contain empty constructor.
     */
    public Component() {
        this(0, 0, 10, 10);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component
     * @param y      y position position in parent component
     * @param width  width of component
     * @param height height of component
     */
    public Component(float x, float y, float width, float height) {
        this(new Vector2f(x, y), new Vector2f(width, height));
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component
     * @param size     size of component
     */
    public Component(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
    }

    /**
     * Returns parent component. If returns null - current component is root component
     *
     * @return null or parent component
     */
    public Container getParent() {
        return parent;
    }

    /**
     * Used to set parent component. By default used by containers to attach component to container.
     * Parent component used by renderers and event listeners and processors.
     * <p>
     * Don't use this method if you want to attach component to container.
     * In this case use {@link Container#add(Component)} method.
     *
     * @param parent component container.
     */
    public void setParent(Container parent) {
        this.parent = parent;
    }

    /**
     * Returns event listeners for component instance.
     *
     * @return event listeners map.
     */
    public ListenerMap getListenerMap() {
        return listenerMap;
    }

    /**
     * Used to set event listener map for component.
     *
     * @param listenerMap map of event listeners.
     */
    public void setListenerMap(ListenerMap listenerMap) {
        this.listenerMap = listenerMap;
    }

    /**
     * Returns position vector.
     * Be careful during changing this vector.
     *
     * @return position vector
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * Used to set position of component
     *
     * @param position new position for component
     */
    public void setPosition(Vector2f position) {
        if (position != null) {
            this.position = position;
        } else {
            this.position.set(0);
        }
    }

    /**
     * Used to set current position.
     *
     * @param x x position relative to parent component
     * @param y y position relative to parent component
     */
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    /**
     * Returns size vector of component.
     * So to get width you can use
     * <pre>
     * {@code
     * Vector2f size = component.getSize();
     * float width = size.x;
     * float height = size.y;
     * }
     * </pre>
     *
     * @return size of component
     */
    public Vector2f getSize() {
        return size;
    }

    /**
     * Used to set size vector
     *
     * @param size size vector
     */
    public void setSize(Vector2f size) {
        if (size != null) {
            this.size = size;
        } else {
            this.size.set(0);
        }
    }

    /**
     * Used to set size vector
     *
     * @param width  width to set
     * @param height height to set
     */
    public void setSize(float width, float height) {
        this.size.set(width, height);
    }

    /**
     * Returns component position on the screen.
     *
     * @return position vector
     */
    public Vector2f getScreenPosition() {
        Vector2f screenPos = new Vector2f(this.position);
        for (Container parent = this.getParent(); parent != null; parent = parent.getParent()) {
            screenPos.add(parent.getPosition());
        }
        return screenPos;
    }

    /**
     * Returns {@link Vector4f} background color vector where x,y,z,w mapped to r,g,b,a values.
     * <ul>
     * <li>0,0,0,1 - black</li>
     * <li>1,0,0,1 - red.</li>
     * <li>0,1,0,1 - green.</li>
     * <li>0,0,1,1 - blue.</li>
     * <li>0,0,0,0 - transparent black.</li>
     * </ul>
     *
     * @return background color vector
     */
    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Used to set background color vector where x,y,z,w mapped to r,g,b,a values.
     * <ul>
     * <li>0,0,0,1 - black</li>
     * <li>1,0,0,1 - red.</li>
     * <li>0,1,0,1 - green.</li>
     * <li>0,0,1,1 - blue.</li>
     * <li>0,0,0,0 - transparent black.</li>
     * </ul>
     *
     * @param backgroundColor background color vector
     */
    public void setBackgroundColor(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Used to set background color vector.
     *
     * @param r red value
     * @param g green value
     * @param b blue value
     * @param a alpha value
     */
    public void setBackgroundColor(float r, float g, float b, float a) {
        backgroundColor.set(r, g, b, a);
    }

    /**
     * Returns true if component enabled. By default if component enabled it receives and proceed events
     *
     * @return true if component enabled. default value is {@link Boolean#TRUE}
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Used to enable or disable component. By default if component enabled it receives and proceed events
     *
     * @param enabled flag to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns true if component visible.
     * By default if component visible it will be rendered and will receive events
     *
     * @return true if component visible. default value is {@link Boolean#TRUE}
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Used to make component visible or invisible.
     * By default if component visible it will be rendered and will receive events
     *
     * @param visible flag to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Used to determine if point intersects component (in screen space).
     * This method uses component intersector.
     *
     * @param point point to check
     * @return true if component intersected by point.
     */
    public boolean intersects(Vector2f point) {
        return intersector.intersects(this, point);
    }

    /**
     * Returns component intersector which used to check if cursor intersect component or not.
     *
     * @return intersector
     */
    public Intersector getIntersector() {
        return intersector;
    }

    /**
     * Used to set intersector for component.
     *
     * @param intersector intersector
     */
    public void setIntersector(Intersector intersector) {
        if (intersector == null) return;
        this.intersector = intersector;
    }

    /**
     * Returns component metadata. Storage of some temporary statements. Can be used for example by stateless renderers.
     *
     * @return map of objects
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Returns border of component.
     *
     * @return border
     */
    public Border getBorder() {
        return border;
    }

    /**
     * Used to set border for component
     *
     * @param border border
     */
    public void setBorder(Border border) {
        this.border = border;
    }

    /**
     * Returns corner radius of component
     *
     * @return corner radius
     */
    public float getCornerRadius() {
        return cornerRadius;
    }

    /**
     * Used to set corner radius
     *
     * @param cornerRadius corner radius
     */
    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }


    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}