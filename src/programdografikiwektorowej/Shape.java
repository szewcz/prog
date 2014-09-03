package programdografikiwektorowej;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class  Shape  {
    
    public BasicStroke stroke=new BasicStroke(5.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
    
    protected String namespace = "http://www.w3.org/2000/svg";

    public abstract void drawYourself(Graphics2D g2) ;

    public abstract Element getXML(Document dokument);
}
