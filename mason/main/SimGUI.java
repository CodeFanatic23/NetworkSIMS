package main;

import sim.portrayal.network.*;
import sim.portrayal.continuous.*;
import sim.engine.*;
import sim.display.*;
import sim.portrayal.simple.*;
import sim.portrayal.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;

public class SimGUI extends GUIState {
	//TODO:  Improve GUI, nodes are clashing
	
	
    public Display2D display;
    public JFrame displayFrame;
    ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
    NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();
    public static void main(String[] args) {
        SimGUI vid = new SimGUI();
        Console c = new Console(vid);
        c.setVisible(true);
    }
    public SimGUI() {
        super(new SimNetwork(System.currentTimeMillis()));
    }
    public SimGUI(SimState state) {
        super(state);
    }
    public static String getName() {
        return "Student Schoolyard Cliques";

    }
    public void start() {
        super.start();
        setupPortrayals();
    }
    public void load(SimState state) {
        super.load(state);
        setupPortrayals();
    }
    public void setupPortrayals() {
        SimNetwork SimNetwork = (SimNetwork) state;
        // tell the portrayals what to portray and how to portray them
        yardPortrayal.setField(SimNetwork.yard);
        yardPortrayal.setPortrayalForAll(new OvalPortrayal2D() {
            public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
                SimNode student = (SimNode) object;
                paint = new Color(123, 0, 255 - 123);
                super.draw(object, graphics, info);
            }
        });
        buddiesPortrayal.setField(new SpatialNetwork2D(SimNetwork.yard, SimNetwork.buddies));
        buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
        // reschedule the displayer
        display.reset();
        display.setBackdrop(Color.white);
        // redraw the display
        display.repaint();
    }
    public void init(Controller c) {
        super.init(c);
        // make the displayer
        display = new Display2D(600, 600, this);
        // turn off clipping
        display.setClipping(false);
        displayFrame = display.createFrame();
        displayFrame.setTitle("Schoolyard Display");
        c.registerFrame(displayFrame);
        // register the frame so it appears in the "Display" list
        displayFrame.setVisible(true);
        display.attach(buddiesPortrayal, "Buddies");
        display.attach(yardPortrayal, "Yard");
    }
    public void quit() {
        super.quit();
        if (displayFrame != null) displayFrame.dispose();
        displayFrame = null;
        display = null;
    }
}
