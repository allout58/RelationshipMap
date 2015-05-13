package allout58.util.RelationshipMap.map;

import allout58.util.RelationshipMap.rendering.DragableMap;
import allout58.util.RelationshipMap.rendering.IMapComponent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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

    public Node()
    {
        this(new Point(0, 0));
    }

    public Node(Point location)
    {
        this.location = location;
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
    public void render(DragableMap map, Graphics2D g)
    {
        Rectangle2D rect = new Rectangle2D.Double(location.getX(), location.getY(), 20, 10);
        g.setBackground(Color.blue);
        g.draw(rect);
        for (Map.Entry<Node, Relationship> entry : relationships.entrySet())
        {
            if (!prerendered.contains(entry.getKey()))
            {
                entry.getValue().render(g, this, entry.getKey());
            }
        }
        prerendered.clear();
    }
}
