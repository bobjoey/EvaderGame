package GUI;
import General.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.HashMap;

public class EvFrame extends JFrame {
  private PanelControl controller;
  
  private JPanel panelCards;
  private HashMap<String, JPanel> panels;

  public EvFrame(PanelControl p) {
    super();
    controller = p;
    
    panelCards = new JPanel(new CardLayout());
    panels = new HashMap<String, JPanel>();
    
    panels.put("INFO", new InfoPanel(controller));
    panels.put("MENU", new MenuPanel(controller));
    panels.put("GAME", new GamePanel(controller));
    panels.put("PAUSE", new PausePanel(controller));
    panels.put("SELECT", new SelectPanel(controller));
    panels.put("LOSE", new LosePanel(controller));
    
    setup();
  }

  public void setup() {
    this.setSize(600, 600);
    this.setTitle("Evader");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    this.add(panelCards);
    for (String s: panels.keySet()) {
      panelCards.add(panels.get(s), s);
    }

    setScreen("INFO");
    this.setVisible(true);
  }

  public void setScreen(String s) {
    ((CardLayout)panelCards.getLayout()).show(panelCards, s);

    panels.get(s).requestFocusInWindow();
  }
  
  public HashMap<String, JPanel> getPanels() {
    return panels;
  }
  
}