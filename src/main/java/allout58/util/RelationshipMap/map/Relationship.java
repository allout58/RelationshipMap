
package allout58.util.RelationshipMap.map;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines a specific type of relationship between nodes.
 * Uses
 *
 * @author allout58
 */
public class Relationship
{
    private static Map<String, Relationship> availableRelationships = new HashMap<>();
    String name;
    Color color;
    int priority;

    /**
     * Defines a new relationship
     *
     * @param name     Name of the relationship.
     * @param color    Color to render the relationship as onscreen.
     * @param priority Priority of this relationship. Used to group nodes effectively.
     */
    private Relationship(String name, Color color, int priority)
    {
        this.name = name;
        this.color = color;
        this.priority = priority;
    }

    /**
     * Requests the creation of a new relationship
     *
     * @param name     Name of the relationship to register. Used to retrieve relationships later.
     * @param color    Color to render the relationship as onscreen
     * @param priority Priority of this relationship. Used to group nodes effectively.
     * @return True if the new relationship was successfully registered.
     */
    public static boolean NewRelationship(String name, Color color, int priority)
    {
        return availableRelationships.put(name, new Relationship(name, color, priority)) == null;
    }

    /**
     * Gets the specified Relationship
     *
     * @param name name of the relationship to get
     * @return the relationship specified by eht given name, or null if none exists.
     */
    public static Relationship GetRelationship(String name)
    {
        return availableRelationships.get(name);
    }

    /**
     * Retrieves a list of all registered relationships' names.
     *
     * @return A list of all the registered relationships' names.
     */
    public static String[] GetAvailableRelationships()
    {
        return availableRelationships.keySet().toArray(new String[availableRelationships.keySet().size()]);
    }

    public void render(Graphics2D g, Node node1, Node node2)
    {
        //Line2D line = new Line2D.Double();
    }
}
