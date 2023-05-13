import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SPaint_MouseHandler extends MouseMotionAdapter implements MouseListener {

	public int xPos;
	public int yPos;

	public boolean addLine = true;

	public SPaint_MouseHandler() {
		super();
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		//System.out.println("pressed at " + e.getX() + "," + e.getY());
		xPos = e.getX();
		yPos = e.getY();
		addLine = true;
	}

	public void mouseReleased(MouseEvent e) {
		//System.out.println("released at " + e.getX() + "," + e.getY());
		xPos = e.getX();
		yPos = e.getY();
		addLine = true;
	}

	public void mouseDragged(MouseEvent e) {
		//System.out.println("dragged at " + e.getX() + "," + e.getY());
		xPos = e.getX();
		yPos = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		//System.out.println("moved at " + e.getX() + "," + e.getY());
		//xPos = e.getX();
		//yPos = e.getY();
	}

	public int xReturn() {
		return xPos;
	}
	public int yReturn() {
		return yPos;
	}
	public boolean needsNewLine() {
		return addLine;
	}

	public void newLineChange(boolean a) {
		addLine = a;
	}

}