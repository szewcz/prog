package programdografikiwektorowej;

import com.sun.org.apache.xerces.internal.dom.AttributeMap;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Ellipse extends Shape{
    
    private static final String cxString="cx";
    private static final String cyString="cy";
    private static final String rxString="rx";
    private static final String ryString="ry";
    private Ellipse2D ellipse;
    
    public Ellipse(Double cx,Double cy,Double rx,Double ry) {
        ellipse=new Ellipse2D.Double(cx,cy,rx-cx, ry-cy);
    }
    
    public Ellipse(AttributeMap attrMap)
    {
        double cx=Double.parseDouble(attrMap.getNamedItem(cxString).getNodeValue());
        double cy=Double.parseDouble(attrMap.getNamedItem(cyString).getNodeValue());
        double rx=Double.parseDouble(attrMap.getNamedItem(rxString).getNodeValue());
        double ry=Double.parseDouble(attrMap.getNamedItem(ryString).getNodeValue());
        ellipse =new Ellipse2D.Double(cx-rx,cy-ry,rx*2,ry*2);
    }
    
    public void drawYourself(Graphics2D g)
    {
        g.fill(ellipse);
    }
    
    public Element getXML(Document doc){
        Element ellipseElement = doc.createElementNS(namespace, "ellipse");
        ellipseElement.setAttribute(cxString, "" + (ellipse.getX()+ellipse.getWidth()/2) );
        ellipseElement.setAttribute(cyString, "" + (ellipse.getY()+ellipse.getHeight()/2) );
        ellipseElement.setAttribute(rxString, "" + ellipse.getWidth()/2);
        ellipseElement.setAttribute(ryString, "" + ellipse.getHeight()/2);
        ellipseElement.setAttribute("fill", "#000000");
        return ellipseElement;
    }
    
}