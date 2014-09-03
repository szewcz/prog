/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programdografikiwektorowej;

import com.sun.org.apache.xerces.internal.dom.AttributeMap;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author ja
 */
public class RectangleDraw extends Shape{
    private static final String xString="x";
    private static final String yString="y";
    private static final String widthString="width";
    private static final String heightString="height";
    private Rectangle2D rect1;
    public BasicStroke stroke=new BasicStroke(5.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
    
    public RectangleDraw(Double x,Double y,Double width,Double height) {
        rect1=new Rectangle2D.Double(x, y, width-x,height-y);
    }
    
    public RectangleDraw(AttributeMap attrMap)
    {
        double x = Double.parseDouble(attrMap.getNamedItem(xString).getNodeValue());
        double y = Double.parseDouble(attrMap.getNamedItem(yString).getNodeValue());
        double width = Double.parseDouble(attrMap.getNamedItem(widthString).getNodeValue());
        double height = Double.parseDouble(attrMap.getNamedItem(heightString).getNodeValue());
        rect1 = new Rectangle2D.Double(x, y,width,height);
    }
    
    public void drawYourself(Graphics2D g)
    {
        g.draw(rect1);
    }

    @Override
    public Element getXML(Document doc){
        Element rectangleElement = doc.createElementNS(namespace, "rect");
        rectangleElement.setAttribute(xString, "" + rect1.getX());
        rectangleElement.setAttribute(yString, "" + rect1.getY() );
        rectangleElement.setAttribute(widthString, "" + rect1.getWidth());
        rectangleElement.setAttribute(heightString, "" + rect1.getHeight());
        rectangleElement.setAttribute("style", "stroke:black ;stroke-width:5" );
        rectangleElement.setAttribute("fill", "none");
        return rectangleElement;
    }
}
