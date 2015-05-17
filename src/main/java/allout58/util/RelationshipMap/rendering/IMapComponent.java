/**
 * @author jthollo
 */
package allout58.util.RelationshipMap.rendering;

import java.awt.*;

public interface IMapComponent
{
    double getX();

    double getY();

    int getWidth();

    int getHeight();

    void render(DragableMap map, Graphics2D g);

    boolean contains(double x, double y);

    void toggleSelect();
}
