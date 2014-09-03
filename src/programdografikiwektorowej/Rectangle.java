package programdografikiwektorowej;

import com.sun.org.apache.xerces.internal.dom.AttributeMap;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Rectangle extends Shape{

    private static final String xString="x";
    private static final String yString="y";
    private static final String widthString="width";
    private static final String heightString="height";
    private Rectangle2D rect;
    
    public Rectangle(Double x,Double y,Double width,Double height) {
        rect=new Rectangle2D.Double(x, y, width-x,height-y);
    }
    
    public Rectangle(AttributeMap attrMap)
    {
        double x = Double.parseDouble(attrMap.getNamedItem(xString).getNodeValue());
        double y = Double.parseDouble(attrMap.getNamedItem(yString).getNodeValue());
        double width = Double.parseDouble(attrMap.getNamedItem(widthString).getNodeValue());
        double height = Double.parseDouble(attrMap.getNamedItem(heightString).getNodeValue());
        rect = new Rectangle2D.Double(x, y,width,height);
    }
    
    @Override
    public void drawYourself(Graphics2D g)
    {
        g.fill(rect);
    }

    @Override
    public Element getXML(Document doc){
        Element rectangleElement = doc.createElementNS(namespace, "rect");
        rectangleElement.setAttribute(xString, "" + rect.getX());
        rectangleElement.setAttribute(yString, "" + rect.getY() );
        rectangleElement.setAttribute(widthString, "" + rect.getWidth());
        rectangleElement.setAttribute(heightString, "" + rect.getHeight());
        rectangleElement.setAttribute("fill", "#000000");
        return rectangleElement;
    }
    
}