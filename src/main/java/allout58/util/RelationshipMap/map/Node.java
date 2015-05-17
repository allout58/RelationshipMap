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
    public double getX()
    {
        return location.getX();
    }

    @Override
    public double getY()
    {
        return location.getY();
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

        RoundRectangle2D rect = new RoundRectangle2D.Double(location.getX(), location.getY(), width, height, 5, 5);
        Color oc = g.getColor();
        g.setColor(selected ? Color.red : Color.white);
        g.draw(rect);
        g.setColor(Color.blue);
        g.fill(rect);
        g.setColor(Color.white);
        g.drawString(data, (float) location.getX() + 2, (float) location.getY() + height - 4);
        g.setColor(oc);
    }

    @Override
    public boolean contains(double x, double y)
    {
        RoundRectangle2D rect = new RoundRectangle2D.Double(location.getX(), location.getY(), width, height, 5, 5);
        return rect.contains(x, y);
    }

    @Override
    public void toggleSelect()
    {
        this.selected = !this.selected;
    }
}
