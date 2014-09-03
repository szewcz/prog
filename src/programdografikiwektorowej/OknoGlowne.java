package programdografikiwektorowej;

import programdografikiwektorowej.Shape;

import com.sun.org.apache.xerces.internal.dom.AttributeMap;
import org.w3c.dom.*;
import programdografikiwektorowej.Ellipse;
import programdografikiwektorowej.Line;
import programdografikiwektorowej.Rectangle;
import programdografikiwektorowej.Stave;
import programdografikiwektorowej.RectangleDraw;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OknoGlowne extends JFrame{
    
    private int szerokoscPlotna = 700;
    private int wysokoscPlotna = 600;
    private double Xpocz, Ypocz, Xkon, Ykon;
    private List<Shape> listaKsztaltow = new ArrayList<Shape>();
    private EnumMap<Tool, Constructor<? extends Shape>> wartoscKonstruktora = new EnumMap<Tool, Constructor<? extends Shape>>(Tool.class);
    private EnumMap<Tool, Constructor<? extends Shape>> xmlKonstruktor = new EnumMap<Tool, Constructor<? extends Shape>>(Tool.class);
    private enum Tool {line, rect, ellipse,rectangleDraw}
    private Tool wybraneNarzedzie=Tool.line;
    private JFileChooser chooser = new JFileChooser();
    private JPanel panelRysowania= null;
    private boolean drawing=false;
    private Shape tempShape;
    public BasicStroke stroke=new BasicStroke(5.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
    
    public OknoGlowne() 
    {
        super("Program do grafiki wektorowej");
        setSize(szerokoscPlotna, wysokoscPlotna);
        setupTools();
        
        JMenuBar menuBar = setupMenu();
        
        Box lewyBox=setupToolsBox();
        Box prawyBox=setupCanvasBox();
        
        Box toolsPlusCanvasBox = Box.createHorizontalBox();
        toolsPlusCanvasBox.add(lewyBox);
        toolsPlusCanvasBox.add(prawyBox);
        
        Box mainBox=Box.createVerticalBox();
        mainBox.add(menuBar);
        mainBox.add(toolsPlusCanvasBox);
        
        setupDrawingPanel();
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(menuBar, BorderLayout.PAGE_START);
        content.add(mainBox, BorderLayout.CENTER);
        this.pack();

        setVisible(true);
    } 
    
    private void setupDrawingPanel()
    {
        panelRysowania.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                Xpocz=e.getX();
                Ypocz=e.getY();
                drawing=true;
            }
            @Override
            public void mouseReleased(MouseEvent e){
                drawing=false;
                Xkon=e.getX();
                Ykon=e.getY();
                Constructor<? extends Shape> tool=wartoscKonstruktora.get(wybraneNarzedzie);
                try {
                    listaKsztaltow.add(tool.newInstance(Xpocz,Ypocz,Xkon,Ykon));
                } catch (        InstantiationException | IllegalAccessException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
                repaint();
            }
        });
        
        panelRysowania.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                Constructor<? extends Shape> tool=wartoscKonstruktora.get(wybraneNarzedzie);
                if(drawing==true){
                    Xkon=e.getX();
                    Ykon=e.getY();
                    try {
                        tempShape=tool.newInstance(Xpocz,Ypocz,Xkon,Ykon);
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(OknoGlowne.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                }
            }
        });
        
    };
    
    private Box setupCanvasBox()
    {
        Box canvasBox=Box.createVerticalBox();
         
        panelRysowania=new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;

                g2.setColor(Color.BLACK);
                g2.setStroke(stroke);
                
                for (Shape s : listaKsztaltow) {
                    s.drawYourself(g2);
                }
                if(drawing==true)
                {
                tempShape.drawYourself(g2);
                }
            }
        };    
        
        panelRysowania.setBackground(Color.white);
        panelRysowania.setPreferredSize(new Dimension(szerokoscPlotna,wysokoscPlotna));
        canvasBox.add(panelRysowania);
        return canvasBox;
    }
    
    private Box setupToolsBox()
    {
        Box toolsBox=Box.createVerticalBox();
        ButtonGroup toolsButtonGroup = new ButtonGroup();
        
        final JRadioButton rysujLinieRadioButton=new JRadioButton("Rysuj linię");
        final JRadioButton rysujKwadratRadioButton=new JRadioButton("Rysuj kwadrat");
        final JRadioButton rysujKoloRadioButton=new JRadioButton("Rysuj koło");
        final JRadioButton rysujPustyKwadratRadioButton=new JRadioButton("Rysuj pusty kwadrat");
      
        toolsButtonGroup.add(rysujLinieRadioButton);
        toolsButtonGroup.add(rysujKwadratRadioButton);
        toolsButtonGroup.add(rysujKoloRadioButton);
        toolsButtonGroup.add(rysujPustyKwadratRadioButton);
        rysujLinieRadioButton.setSelected(true);
        
        rysujLinieRadioButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                wybraneNarzedzie=Tool.line;
            }
        });
        rysujKwadratRadioButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                wybraneNarzedzie=Tool.rect;
            }
        });
        rysujKoloRadioButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                wybraneNarzedzie=Tool.ellipse;
            }
        });
        rysujPustyKwadratRadioButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                wybraneNarzedzie=Tool.rectangleDraw;
            }
        });
        
        toolsBox.add(rysujLinieRadioButton);
        toolsBox.add(rysujKwadratRadioButton);
        toolsBox.add(rysujKoloRadioButton);
        toolsBox.add(rysujPustyKwadratRadioButton);
        return toolsBox;
    }
    
    private JMenuBar setupMenu()
    {
        JMenuBar menuBar=new JMenuBar();
        JMenu menu=new JMenu("File");
        menuBar.add(menu);
        JMenuItem zapiszMenuItem=new JMenuItem("Zapisz");
        JMenuItem otworzMenuItem=new JMenuItem("Otwórz");
        JMenuItem nowyMenuItem=new JMenuItem("Nowy");
        menu.add(zapiszMenuItem);
        menu.add(otworzMenuItem);
        menu.add(nowyMenuItem);
        
        zapiszMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveDocument();
                } catch (TransformerException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        otworzMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                openDocument();
            }
        });
        
        nowyMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clearDocument();
            }
        });
        return menuBar;
    }
    
    private void clearDocument()
    {
        listaKsztaltow.clear();
        repaint();
    }
    
    private void setupTools() 
    {
        try
        {
            wartoscKonstruktora.put(Tool.line, Line.class.getConstructor(Double.class, Double.class, Double.class, Double.class));
            wartoscKonstruktora.put(Tool.ellipse, Ellipse.class.getConstructor(Double.class, Double.class, Double.class, Double.class));
            wartoscKonstruktora.put(Tool.rect, Rectangle.class.getConstructor(Double.class, Double.class, Double.class, Double.class));
            wartoscKonstruktora.put(Tool.rectangleDraw, RectangleDraw.class.getConstructor(Double.class, Double.class, Double.class, Double.class));
            xmlKonstruktor.put(Tool.line, Line.class.getConstructor(AttributeMap.class));
            xmlKonstruktor.put(Tool.rect, Rectangle.class.getConstructor(AttributeMap.class));
            xmlKonstruktor.put(Tool.ellipse, Ellipse.class.getConstructor(AttributeMap.class));
            xmlKonstruktor.put(Tool.rectangleDraw, RectangleDraw.class.getConstructor(AttributeMap.class));
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    
    public void saveDocument() throws TransformerException, IOException
    {
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try
        {
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document dokument = builder.newDocument();
            String namespace = "http://www.w3.org/2000/svg";
            Element svgElement = dokument.createElementNS(namespace, "svg");
            dokument.appendChild(svgElement);
            svgElement.setAttribute("width", "" + szerokoscPlotna );
            svgElement.setAttribute("height", "" + wysokoscPlotna);
            for(programdografikiwektorowej.Shape s : listaKsztaltow)
            {
                svgElement.appendChild( s.getXML(dokument));
            }
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 20000802//EN");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            t.transform(new DOMSource(dokument), new StreamResult(Files.newOutputStream(file.toPath())));
            
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
    }
    
    public List<Shape> readShapesFromFile(Document doc)
    {
        List<Shape> fromFile = new ArrayList<Shape>();
        Node svg = doc.getElementsByTagName("svg").item(0);
        NodeList shapes = svg.getChildNodes();
        
        for(int i=0;i<shapes.getLength();i++){
            Node shape = shapes.item(i);
            try
            {
                Shape parsedShape = createShapeFromXML(shape);
                fromFile.add(parsedShape);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                //expected
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return fromFile;
    }
    
    private Shape createShapeFromXML(Node shape) throws IllegalAccessException, InvocationTargetException, InstantiationException 
    {
        String nazwa=shape.getNodeName();
        try
        {
            Constructor<? extends Shape> constructor=xmlKonstruktor.get(Tool.valueOf(nazwa));
            NamedNodeMap attrMap = shape.getAttributes();
            return constructor.newInstance(attrMap);
        }
        catch(IllegalArgumentException e)
        {
            throw new InvocationTargetException(e);
        }
    }
    
    public void openDocument()
    {
        try
        {
            if(chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
            File file = chooser.getSelectedFile();
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document doc=builder.parse(file);
            doc.getDocumentElement().normalize();
            List<Shape> fromFile=readShapesFromFile(doc);
            listaKsztaltow.clear();
            listaKsztaltow.addAll(fromFile);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        panelRysowania.repaint();
        repaint();
    }
    
    
 
    
    
}
 