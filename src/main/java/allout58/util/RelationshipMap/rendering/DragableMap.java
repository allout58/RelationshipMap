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
    private static final Logger logger = LogManager.getLogger("DragableMap");
    private static final int KEY_TRANSLATE = 3;
    private static final double KEY_SCALE = 0.05;

    List<IMapComponent> componentList = new ArrayList<>();

    private double scale = 1.0;
    private Point translate = new Point(0, 0);
    private Point startSelect = null;
    private Point stopSelect = null;

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
                    translate.translate(KEY_TRANSLATE, 0);
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    translate.translate(-KEY_TRANSLATE, 0);
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                    translate.translate(0, -KEY_TRANSLATE);
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    translate.translate(0, KEY_TRANSLATE);
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP)
                {
                    scale -= KEY_SCALE;
                    repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
                {
                    scale += KEY_SCALE;
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
            startSelect = stopSelect = null;
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            for (IMapComponent comp : componentList)
            {
                double cx = (e.getX() - translate.getX()) / scale;
                double cy = (e.getY() - translate.getY()) / scale;
                Shape shape = comp.getBounds();
                if (shape.contains(cx, cy))
                {
                    comp.toggleSelected();
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
            if (e.isShiftDown())
            {
                if (startSelect == null)
                {
                    startSelect = new Point(e.getPoint());
                }
                if (stopSelect == null)
                    stopSelect = new Point(0, 0);
                stopSelect.x = e.getX();
                stopSelect.y = e.getY();
            }
            else
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
}
