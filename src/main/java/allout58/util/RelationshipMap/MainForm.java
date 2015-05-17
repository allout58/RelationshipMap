
package allout58.util.RelationshipMap;

import allout58.util.RelationshipMap.map.Node;
import allout58.util.RelationshipMap.map.Relationship;
import allout58.util.RelationshipMap.rendering.DragableMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author allout58
 */
public class MainForm extends JFrame
{
    public MainForm()
    {
        super("RelationshipMap");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Relationship.NewRelationship("Roomates", Color.red, 1);
        Relationship.NewRelationship("Friends", Color.GREEN, 2);
        Relationship roomies = Relationship.GetRelationship("Roomates");
        Relationship friends = Relationship.GetRelationship("Friends");

        Node jt = new Node("James Hollowell", new Point(0, 0));
        Node jg = new Node("Jenna Hill", new Point(-40, -40));
        Node et = new Node("Eric Wilkins", new Point(40, 40));

        jt.addRelationship(jg, friends);
        jg.addRelationship(jt, friends);
        jt.addRelationship(et, roomies);
        et.addRelationship(jt, roomies);

        JToolBar toolBar = new JToolBar();
        toolBar.add(new AbstractAction("New Node")
        {
            {
                putValue(SHORT_DESCRIPTION, "Add a node to the map");
                putValue(MNEMONIC_KEY, KeyEvent.VK_N);
            }

            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });

        add(toolBar, BorderLayout.NORTH);

        DragableMap mapPanel = new DragableMap();
        Dimension d = new Dimension(200, 200);
        mapPanel.setMinimumSize(d);
        mapPanel.setPreferredSize(d);
        mapPanel.addComponent(jt);
        mapPanel.addComponent(jg);
        mapPanel.addComponent(et);
        mapPanel.setBounds(0, 0, 200, 200);

        add(mapPanel, BorderLayout.SOUTH);
    }
}
