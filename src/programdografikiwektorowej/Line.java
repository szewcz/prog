package programdografikiwektorowej;

import com.sun.org.apache.xerces.internal.dom.AttributeMap;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Line extends Shape{
    
    public Line2D line;
    public static final String X1 = "x1";
    public static final String Y1 = "y1";
    public static final String X2 = "x2";
    public static final String Y2 = "y2";
    public static final String STROKE = "stroke-width";
    
    public BasicStroke stroke=new BasicStroke(5.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
    public Line(Double x1, Double y1, Double x2, Double y2) {
        line=new Line2D.Double(x1,y1,x2,y2);
    }
    
    public Line(AttributeMap attrMap) {
        double x1 = Double.parseDouble(attrMap.getNamedItem(X1).getNodeValue());
        double y1 = Double.parseDouble(attrMap.getNamedItem(Y1).getNodeValue());
        double x2 = Double.parseDouble(attrMap.getNamedItem(X2).getNodeValue());
        double y2 = Double.parseDouble(attrMap.getNamedItem(Y2).getNodeValue());
        line = new Line2D.Double(x1, y1, x2, y2);
    }
    
    @Override
    public Element getXML(Document doc){
        Element lineElement = doc.createElementNS(namespace, "line");
        lineElement.setAttribute(X1, "" + line.getX1());
        lineElement.setAttribute(Y1, "" + line.getY1() );
        lineElement.setAttribute(X2, "" + line.getX2());
        lineElement.setAttribute(Y2, "" + line.getY2());
        lineElement.setAttribute("style", "stroke:black ;stroke-width:5" );
        lineElement.setAttribute("fill", "#000000");
        return lineElement;
    }
    
    @Override
    public void drawYourself(Graphics2D g)
    {   
        g.setStroke(stroke);
        g.draw(line);
    }
         
}
