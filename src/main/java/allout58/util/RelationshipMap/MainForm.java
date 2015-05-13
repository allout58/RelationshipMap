
package allout58.util.RelationshipMap;

import allout58.util.RelationshipMap.map.Node;
import allout58.util.RelationshipMap.rendering.DragableMap;

import javax.swing.*;
import java.awt.*;

/**
 * @author allout58
 */
public class MainForm extends JFrame
{
    public MainForm()
    {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        DragableMap mapPanel = new DragableMap();
        mapPanel.addComponent(new Node(new Point(0, 0)));
        mapPanel.addComponent(new Node(new Point(20, 20)));
        mapPanel.addComponent(new Node(new Point(-20, -20)));
        mapPanel.setBounds(0, 0, 200, 200);
        add(mapPanel);
    }
}
