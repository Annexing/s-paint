import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SPaint_KeyHandler implements KeyListener {

	public boolean keyPressed = false;
	public int keyCode;

	public SPaint_KeyHandler() {
		super();
	}

	public void keyTyped(KeyEvent e) {
//		System.out.println("typed");
	}

	public void keyPressed(KeyEvent e) {
//		System.out.println("pressed");
		System.out.println("the key code is " + e.getKeyCode());

		keyCode = e.getKeyCode();
		keyPressed = true;
	}

	public void keyReleased(KeyEvent e) {
//		System.out.println("released");
	}

	public boolean isKeyPressed() {
		return keyPressed;
	}
	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyPressed(Boolean b) {
		this.keyPressed = b;
	}

}