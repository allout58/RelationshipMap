
package allout58.util.RelationshipMap;

import allout58.util.RelationshipMap.map.Node;
import allout58.util.RelationshipMap.map.Relationship;
import allout58.util.RelationshipMap.rendering.DragableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author allout58
 */
public class MainForm extends JFrame
{
    private static Logger logger = LogManager.getLogger("MainForm");
    private DragableMap mapPanel;

    public MainForm()
    {
        super("RelationshipMap");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

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

        JMenuBar menubar = new JMenuBar();
        menubar.setAlignmentX(RIGHT_ALIGNMENT);
        menubar.setMinimumSize(new Dimension(100, 30));
        menubar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JMenu fileM = new JMenu("File");
        fileM.getAccessibleContext().setAccessibleDescription("File operations, such as save and load.");
        JMenuItem loadMI = new JMenuItem("Load", 'l');
        loadMI.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onLoadButton();
            }
        });
        loadMI.getAccessibleContext().setAccessibleDescription("Load a relationship map from file");
        fileM.add(loadMI);
        JMenuItem saveMI = new JMenuItem("Save", 's');
        saveMI.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onSaveButton();
            }
        });
        saveMI.getAccessibleContext().setAccessibleDescription("Save the current state of the relationship map to file");
        fileM.add(saveMI);
        fileM.addSeparator();
        JMenuItem exitMI = new JMenuItem("Exit", 'x');
        exitMI.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onExitButton();
            }
        });
        exitMI.getAccessibleContext().setAccessibleDescription("Exit the application");
        fileM.add(exitMI);
        menubar.add(fileM);

        JMenu nodeM = new JMenu("Node");
        nodeM.getAccessibleContext().setAccessibleDescription("Node operations");
        JMenuItem newNodeMI = new JMenuItem("New Node");
        newNodeMI.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onNewNodeButton();
            }
        });
        newNodeMI.getAccessibleContext().setAccessibleDescription("Add a new node to the relationship map");
        nodeM.add(newNodeMI);
        JMenuItem deleteNodeMI = new JMenuItem("Delete Node");
        deleteNodeMI.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onDeleteNode();
            }
        });
        deleteNodeMI.getAccessibleContext().setAccessibleDescription("Deletes the currently selected node");
        nodeM.add(deleteNodeMI);
        menubar.add(nodeM);

        mainPanel.add(menubar);

        mapPanel = new DragableMap();
        Dimension d = new Dimension(200, 200);
        mapPanel.setMinimumSize(d);
        mapPanel.setPreferredSize(d);
        mapPanel.addComponent(jt);
        mapPanel.addComponent(jg);
        mapPanel.addComponent(et);
        //mapPanel.setBounds(0, 0, 200, 200);

        mainPanel.add(mapPanel);

        add(mainPanel);
    }

    private void onDeleteNode()
    {

    }

    private void onNewNodeButton()
    {

    }

    private void onExitButton()
    {
        this.setVisible(false);
        this.dispose();
    }

    private void onSaveButton()
    {

    }

    private void onLoadButton()
    {

    }
}
