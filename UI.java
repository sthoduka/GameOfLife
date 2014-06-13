import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * UI for Game of Life
 * 
 * @author Santosh Thoduka
 * 
 */

public class UI implements ActionListener, MouseListener {
	private JFrame window;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem openfile, savefile;
	private JFileChooser filechooser;
	private JComboBox automata_types_cb;
	private GridPanel panel;
	private CellularAutomaton ca;
	private Dimension windowSize;
	private JButton start, clear;
	private JLabel iter_label;
	boolean pauseFlag = true;
	public static final int Y_SIZE = 65;
	public static final int X_SIZE = 130;
	public static final int SLEEP_TIME_MS = 5;
	public static final int TILE_SIZE = 10;

	public static final String[] AUTOMATA_TYPES = { "Life Like",
			"Langton's Ant" };
	public static final String[] LIFE_LIKE_TYPES = { "Game of Life", "Seeds" };

	/**
	 * Initialize frame, menubar and grid
	 */
	public UI() {
		// ca = new GameOfLife(X_SIZE, Y_SIZE);
		ca = new LifeLikeAutomaton(X_SIZE, Y_SIZE);
		window = new JFrame("Game of Life");
		windowSize = new Dimension(1316, 716);
		panel = new GridPanel(this, ca, X_SIZE, Y_SIZE, SLEEP_TIME_MS,
				TILE_SIZE);
		panel.addMouseListener(this);
		for (int i = 0; i < panel.xsize; i++) {
			for (int j = 0; j < panel.ysize; j++) {
				panel.values[i][j] = false;
			}
		}
		start = new JButton("Start");
		start.addActionListener(this);
		clear = new JButton("Clear");
		clear.addActionListener(this);

		filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(new File("."));

		menubar = new JMenuBar();
		menu = new JMenu("Load/Save");
		menubar.add(menu);
		menubar.add(start);
		menubar.add(clear);
		automata_types_cb = new JComboBox<String>(AUTOMATA_TYPES);
		automata_types_cb.addActionListener(this);
		automata_types_cb.setPreferredSize(new Dimension(200, 25));
		automata_types_cb.setMaximumSize(automata_types_cb.getPreferredSize());
		menubar.add(automata_types_cb);
		iter_label = new JLabel();
		menubar.add(iter_label);

		openfile = new JMenuItem("Open File");
		openfile.addActionListener(this);
		menu.add(openfile);
		savefile = new JMenuItem("Save File");
		savefile.addActionListener(this);
		menu.add(savefile);
		window.setJMenuBar(menubar);
		window.setSize(windowSize);
		window.getContentPane().add(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	/**
	 * Read preset configuration from file as x,y coordinates for live cells
	 * 
	 * @param filename
	 *            file from which to read
	 */
	private void readFile(String filename) {
		ca.reset();
		setIterations(ca.getIterationNum());
		try {
			FileInputStream fstream = new FileInputStream(filename);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String[] coords = strLine.split(",");
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);
				panel.values[x][y] = true;
			}

			fstream.close();
			in.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Save current configuration to file as x,y coordinates of live cells
	 * 
	 * @param filename
	 */
	private void saveFile(String filename) {
		try {
			File f = new File(filename);
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			for (int i = 0; i < panel.xsize; i++) {
				for (int j = 0; j < panel.ysize; j++) {
					if (panel.values[i][j]) {
						String line = Integer.toString(i) + ","
								+ Integer.toString(j) + "\n";
						writer.write(line);
					}
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setIterations(int iterations) {
		iter_label.setText(Integer.toString(iterations));
	}

	/**
	 * ActionListener for button presses and menu
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Start")) {
			pauseFlag = false;
			start.setText("Stop");
			automata_types_cb.setEnabled(false);
		} else if (command.equals("Stop")) {
			pauseFlag = true;
			start.setText("Start");
			automata_types_cb.setEnabled(true);
		} else if (command.equals("Clear")) {
			ca.reset();
			setIterations(ca.getIterationNum());
		} else if (command.equals("Open File")) {
			int ret = filechooser.showOpenDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File f = filechooser.getSelectedFile();
				readFile(f.getAbsolutePath());
			}
		} else if (command.equals("Save File")) {
			int ret = filechooser.showSaveDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File f = filechooser.getSelectedFile();
				saveFile(f.getAbsolutePath());
			}
		} else if (command.equals("comboBoxChanged")) {
			if (automata_types_cb.getSelectedItem().equals(AUTOMATA_TYPES[0])) {
				ca.reset();
				ca = new LifeLikeAutomaton(X_SIZE, Y_SIZE);
				panel.setAutomaton(ca);
			} else if (automata_types_cb.getSelectedItem().equals(
					AUTOMATA_TYPES[1])) {
				ca.reset();
				ca = new LangtonsAnt(X_SIZE, Y_SIZE);
				panel.setAutomaton(ca);
			}
		}
	}

	/**
	 * Listen to mouse clicks on the grid
	 */
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = 716 - e.getY();
		if (pauseFlag) {
			x = e.getX() / TILE_SIZE;
			y = e.getY() / TILE_SIZE;
			panel.values[x][y] = !panel.values[x][y];
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UI g = new UI();
			}
		});
	}
}
