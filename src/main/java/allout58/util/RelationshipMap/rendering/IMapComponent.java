/**
 * @author jthollo
 */
package allout58.util.RelationshipMap.rendering;

import java.awt.*;

public interface IMapComponent
{
    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getWidth();

    int getHeight();

    void render(DragableMap map, Graphics2D g);

    Shape getBounds();

    void toggleSelected();

    boolean getSelected();

    void setSelected(boolean selected);
}
