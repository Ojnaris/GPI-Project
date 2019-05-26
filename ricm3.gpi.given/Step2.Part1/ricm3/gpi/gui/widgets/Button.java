package ricm3.gpi.gui.widgets;

import ricm3.gpi.gui.Color;
import ricm3.gpi.gui.Font;
import ricm3.gpi.gui.Graphics;
import ricm3.gpi.gui.Image;
import ricm3.gpi.gui.MouseListener;
import ricm3.gpi.gui.Window;
import ricm3.gpi.gui.layout.Component;
import ricm3.gpi.gui.layout.Container;

/**
 * A widget that is a simple button than can be clicked.
 * To know about click events on a button, you need to register
 * an ActionListener on that button.
 * 
 * A button can have a label, that is, a name.
 * 
 * A button may also have two images, one for when the button 
 * is pressed down and the other for when the button is released.
 * 
 * 
 * @author Pr. Olivier Gruber  (olivier dot gruber at acm dot org)
 */
public class Button extends Component {

  String m_label;
  Font m_font;
  Image m_pressed;
  Image m_released;
  boolean isPressed;
  
  public Button(Container parent) {
    super(parent);
    this.m_label = null;
    this.m_font = null;
    this.m_pressed = null;
    this.m_released = null;
    this.isPressed = false;
    this.m_fgColor = Color.magenta;
  }

  public String getLabel() {
    return m_label;
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    //Case where the button is Pressed
    if(isPressed) {
    	g.drawImage(m_pressed, this.m_x, this.m_y, this.m_width, this.m_height);
    }
    else {
    	//Case where the button is released
    	g.drawImage(m_released, this.m_x, this.m_y, this.m_width, this.m_height);
    }
    if(this.getLabel() != null){
    	//Case when the button has a label to display
    	g.setFont(this.m_font);
    	g.setColor(this.m_fgColor);
    	g.drawString(this.m_label, this.m_x, this.m_y);
    }
  }

  public void setActionListener(ActionListener al) {
    this.setMouseListener(new ButtonListener(this, al));
  }

  public void setFont(Font f) {
	  this.m_font = f;
  }

  public void setLabel(String txt) {
    m_label = txt;
    Window win = Window.getWindow();
    m_font = win.font(Window.MONOSPACED, 12F);
  }

  public void setImages(Image released, Image pressed) {
    m_pressed = pressed;
    m_released = released;
  }

  class ButtonListener implements MouseListener {
	  
	  private Button m_button;
	  private ActionListener m_al;
	  
	  public ButtonListener(Button button, ActionListener acLi) {
		this.m_button = button;
		this.m_al = acLi;
		// TODO Auto-generated constructor stub
	}

	public void mousePressed(int x, int y, int buttons) {
		  this.m_button.isPressed = true;
		  System.out.println("Button is Pressed");
		  this.m_button.repaint();
	  }
	  
	  public void mouseReleased(int x, int y, int buttons) {
		  if(this.m_button.isPressed) {
			  this.m_button.isPressed = false;
			  System.out.println("Button is Released");
			  this.m_button.repaint();
		  }
	  }

	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub
		
	}
  }
  
}
