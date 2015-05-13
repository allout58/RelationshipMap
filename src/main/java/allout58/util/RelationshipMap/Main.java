package allout58.util.RelationshipMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

/**
 * @author allout58
 */
public class Main
{
    private static Logger logger = LogManager.getLogger("Main");

    public static void main(String[] args) throws Exception
    {
        logger.info("Hello World");
        JFrame mainFrame = new MainForm();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
