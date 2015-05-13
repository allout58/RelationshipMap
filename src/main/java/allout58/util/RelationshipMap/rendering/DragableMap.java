/**
 * @author jthollo
 */
package allout58.util.RelationshipMap.rendering;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class DragableMap extends JPanel
{
    private static Logger logger = LogManager.getLogger("DragableMap");

    List<IMapComponent> componentList = new ArrayList<>();

    double scale = 1.0;
    //    int width, height;
    Point translate = new Point(0, 0);

    public DragableMap()
    {
        setBackground(Color.yellow);
        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    translate.translate(1, 0);
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    translate.translate(-1, 0);
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                    translate.translate(0, -1);
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    translate.translate(0, 1);
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP)
                {
                    scale -= 0.1;
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
                {
                    scale += 0.1;
                    repaint();
                }
            }
        });
        MouseListners listners = new MouseListners();
        addMouseWheelListener(listners);
        addMouseMotionListener(listners);
        addMouseListener(listners);
        setFocusable(true);
        grabFocus();
    }

    public void moveMap(Point newPoint)
    {
        translate = new Point(newPoint);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.translate(translate.x, translate.y);
        //Sanitize scale
        if (scale <= 0)
            scale = .1;
        g2.scale(scale, scale);
        for (IMapComponent component : componentList)
        {
            component.render(this, g2);
        }
    }

    public void addComponent(IMapComponent comp)
    {
        componentList.add(comp);
    }

    class MouseListners extends MouseAdapter
    {
        int currButton = MouseEvent.NOBUTTON;
        int origX = -42;//Hurray for magic constants!
        int origY = -42;

        @Override
        public void mousePressed(MouseEvent e)
        {
            currButton = e.getButton();
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            currButton = e.getButton();
            origX = origY = -42;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            scale += e.getPreciseWheelRotation() / 5;
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if (currButton != MouseEvent.BUTTON1)
                return;
            if (origX != -42 && origY != -42)
            {
                //                logger.info("Dragging: (" + e.getX() + "," + e.getY() + ") DELTA (" + (origX - e.getX()) + "," + (origY - e.getY()) + ") -" + e.getButton() + "VS" + currButton);
                translate.translate(e.getX() - origX, e.getY() - origY);
                repaint();
            }
            origX = e.getX();
            origY = e.getY();
        }

        //        @Override
        //        public void mouseMoved(MouseEvent e)
        //        {
        //            logger.info("Moving: "+isDragging+" ("+e.getX()+","+e.getY()+")-"+e.getButton());
        //            if (isDragging)
        //            {
        //                translate.translate(origX - e.getX(), origY - e.getY());
        //            }
        //            origX = e.getX();
        //            origY = e.getY();
        //        }
    }
}
