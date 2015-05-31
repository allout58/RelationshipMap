package allout58.util.RelationshipMap.map;

import allout58.util.RelationshipMap.rendering.DragableMap;
import allout58.util.RelationshipMap.rendering.IMapComponent;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author allout58
 */
public class Node implements Serializable, IMapComponent
{
    private Point location;
    private Map<Node, Relationship> relationships = new HashMap<>();

    private List<Node> prerendered = new ArrayList<>();

    private String data = "";
    private int width = -1;
    private int height = -1;
    private RoundRectangle2D bounds = null;
    private boolean selected = false;

    public Node(String data)
    {
        this(data, new Point(0, 0));
    }

    public Node(String data, Point location)
    {
        this.location = location;
        this.data = data;
    }

    public void addRelationship(Node other, Relationship relationshipType)
    {
        relationships.put(other, relationshipType);
    }

    public void removeRelationship(Node other)
    {
        relationships.remove(other);
    }

    public void notifyRelationshipRendered(Node other)
    {
        prerendered.add(other);
    }

    @Override
    public int getX()
    {
        return location.x;
    }

    @Override
    public void setX(int x)
    {
        this.location.x = x;
    }

    @Override
    public int getY()
    {
        return location.y;
    }

    @Override
    public void setY(int y)
    {
        this.location.y = y;
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    public void setData(String data)
    {
        this.data = data;
        //Force the height and width to reupdate.
        height = 0;
        width = 0;
    }

    @Override
    public void render(DragableMap map, Graphics2D g)
    {
        if (width <= 0)
            width = g.getFontMetrics().stringWidth(data) + 4;
        if (height <= 0)
            height = g.getFontMetrics().getHeight() + 4;
        for (Map.Entry<Node, Relationship> entry : relationships.entrySet())
        {
            if (!prerendered.contains(entry.getKey()))
            {
                entry.getValue().render(g, this, entry.getKey());
                entry.getKey().notifyRelationshipRendered(this);
            }
        }
        prerendered.clear();

        if (bounds == null)
            bounds = new RoundRectangle2D.Double(location.getX(), location.getY(), width, height, 5, 5);

        Color oc = g.getColor();
        Stroke os = g.getStroke();
        g.setStroke(new BasicStroke(3f));
        g.setColor(selected ? Color.red : Color.black);
        g.draw(bounds);
        g.setStroke(os);
        g.setColor(Color.blue);
        g.fill(bounds);
        g.setColor(Color.white);
        g.drawString(data, (float) location.getX() + 2, (float) location.getY() + height - 4);
        g.setColor(oc);
    }

    @Override
    public Shape getBounds()
    {
        return bounds;
    }

    @Override
    public void toggleSelected()
    {
        this.selected = !this.selected;
    }

    @Override
    public boolean getSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
