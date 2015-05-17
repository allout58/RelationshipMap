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

/**
 * @author alllout58
 */
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
        private static final int MAGIC_ORIG = -42;
        private static final int SCROLL_SCALE = 5;
        int origX = MAGIC_ORIG;
        int origY = MAGIC_ORIG;

        @Override
        public void mouseReleased(MouseEvent e)
        {
            origX = origY = MAGIC_ORIG;
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            for (IMapComponent comp : componentList)
            {
                double cx = (e.getX() - translate.getX()) / scale;
                double cy = (e.getY() - translate.getY()) / scale;
                if (comp.contains(cx, cy))
                {
                    comp.toggleSelect();
                    repaint();
                }
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            scale += e.getPreciseWheelRotation() / SCROLL_SCALE;
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if (origX != MAGIC_ORIG && origY != MAGIC_ORIG)
            {
                translate.translate(e.getX() - origX, e.getY() - origY);
                repaint();
            }
            origX = e.getX();
            origY = e.getY();
        }
    }
}
