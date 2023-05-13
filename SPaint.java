// RGB values can be from 0-255
	// integer limit is 2147483647

	//   List<List<String>> listOfLists = new ArrayList<List<String>>();
	//   listOfLists.add(new ArrayList<String>());
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SPaint extends JPanel {
	JFrame frame;

	SPaint_MouseHandler m = new SPaint_MouseHandler();
	SPaint_KeyHandler k = new SPaint_KeyHandler();
	SPaint asdjke;

	public static int frameWidth = 800;
	public static int frameLength = 800;
	public int lineCount = 0;
	public boolean processing = false;

	public int redBackgroundColor = 255;
	public int blueBackgroundColor = 255;
	public int greenBackgroundColor = 255;

	public List<List<Integer>> xPositions = new ArrayList<List<Integer>>();
	public List<List<Integer>> yPositions = new ArrayList<List<Integer>>();

	public SPaint() {
		frame = new JFrame("we're doing art here");
		frame.setSize(frameWidth,frameLength);			//width, height
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		this.addMouseListener(m);
		this.addMouseMotionListener(m);
		frame.addKeyListener(k);

		xPositions.add(new ArrayList<Integer>());
		yPositions.add(new ArrayList<Integer>());

		openDrawing("1");
	}

	public void paintComponent(Graphics g) {
// painting stuff
		backgroundColor(g);

		if(!processing) {
			drawWithLines(g);
//			paint2(g);
		}

	}

	public void backgroundColor (Graphics g) {
//change this part.
		g.setColor(new Color(redBackgroundColor, blueBackgroundColor, greenBackgroundColor));
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		frame.repaint();
	}

//start main drawing method
	public void drawWithLines(Graphics g) {
		int heightDiff = frame.getHeight() - this.getHeight();
		int widthDiff = frame.getWidth() - this.getWidth();

		g.setColor(new Color(100,200,255));

		if(m.needsNewLine()==false && yPositions.size()==xPositions.size()) {
			xPositions.get(lineCount).add(m.xReturn());
			yPositions.get(lineCount).add(m.yReturn());
		}
		if(yPositions.size()==xPositions.size() && !processing && xPositions.size()>0)
			for(int i = 0; i<yPositions.size(); i++) {
				for(int j = 0; j<yPositions.get(i).size(); j++) {
					if(j>0)
						g.drawLine(xPositions.get(i).get(j), yPositions.get(i).get(j), xPositions.get(i).get(j-1), yPositions.get(i).get(j-1));
				}
			}

		if(m.needsNewLine()==true) {
			xPositions.add(new ArrayList<Integer>());
			yPositions.add(new ArrayList<Integer>());
			lineCount++;

			m.newLineChange(false);
		}

// key controls
		if(k.isKeyPressed() && k.getKeyCode() == 83) {
			processing = true;
			saveDrawing();
			k.setKeyPressed(false);
		}

		if(k.isKeyPressed() && k.getKeyCode() == 67) {
			clearBoard(g);
			k.setKeyPressed(false);
		}

		if(k.isKeyPressed() && k.getKeyCode() == 90) {
			undo(g);
			k.setKeyPressed(false);
		}

	}

//not scuffed drawing method
	public void paint2(Graphics g) {
		int heightDiff = frame.getHeight() - this.getHeight();
		int widthDiff = frame.getWidth() - this.getWidth();

		g.setColor(new Color(100,200,255));

		xPositions.get(0).add(m.xReturn());
		yPositions.get(0).add(m.yReturn());


		for(int i = 0; i<xPositions.get(0).size(); i++) {
			g.drawLine(xPositions.get(0).get(i), yPositions.get(0).get(i),10,10);
		}

		frame.repaint();
	}

	public void clearBoard(Graphics g) {
		processing = true;
		xPositions.clear();
		yPositions.clear();
		lineCount = 0;

		xPositions.add(new ArrayList<Integer>());
		yPositions.add(new ArrayList<Integer>());

		processing = false;
	}

	public void undo (Graphics g) {
		processing = true;
		xPositions.get(lineCount).clear();
		yPositions.get(lineCount).clear();
		lineCount--;
		processing = false;
	}

	public static String newLine = "NEWLINE ";
	public static String swapCoords = "SWAP ";
	public void saveDrawing() {
			String currentSave = "1";
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("pSave" + currentSave + ".txt"));
				BufferedReader br = new BufferedReader(new FileReader("pSave" + currentSave + ".txt"));
//loop through x and save
				for(int i = 0; i<xPositions.size(); i++) {
					for(int j = 0; j<xPositions.get(i).size(); j++) {
						bw.write(Integer.toString(xPositions.get(i).get(j)) + " ");
					}
					bw.write(newLine);
				}
				bw.write(swapCoords);
//loop through y and save
				for(int i = 0; i<yPositions.size(); i++) {
					for(int j = 0; j<yPositions.get(i).size(); j++) {

						bw.write(Integer.toString(yPositions.get(i).get(j)) + " ");
					}
					bw.write(newLine);
				}

				bw.write(swapCoords);
				bw.close();
				System.out.println("save created sucessfully");
			}

			catch(Exception e) {
				System.out.println("an error has occured, file couldnt be made.");
			}
		processing = false;
//		}
	}

	public void openDrawing(String currentSave) {
		String currentLine;
		String[] splitCoords;
		int newStart = 0;
		int lineCount2;
		processing = true;

		try {
			BufferedReader br = new BufferedReader(new FileReader("pSave" + currentSave + ".txt"));

			currentLine = br.readLine();
			splitCoords = currentLine.split(" ");

//x loop and add to array
			lineCount2 = 0;
			for(int i = 0; i<splitCoords.length; i++) {
				if(splitCoords[i].equals("SWAP")) {
					newStart = i+1;
					break;
				}

				if(splitCoords[i].equals("NEWLINE")) {
					xPositions.add(new ArrayList<Integer>());
					lineCount2++;
					continue;
				}

				xPositions.get(lineCount2).add(Integer.valueOf(splitCoords[i]));
			}
//y loop and add to array
			for(int i = newStart; i<splitCoords.length; i++) {
				if(splitCoords[i].equals("NEWLINE")) {
					yPositions.add(new ArrayList<Integer>());
					lineCount++;
					continue;
				}
				if(splitCoords[i].equals("SWAP"))
					break;

				yPositions.get(lineCount).add(Integer.valueOf(splitCoords[i]));
			}

		}
		catch(Exception e) {
			System.out.println("file could not be loaded");
			e.printStackTrace();
		}
		processing = false;
	}

	public static void main (String [] args) {
			SPaint asdjke = new SPaint();

	}

}