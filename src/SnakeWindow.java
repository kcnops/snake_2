import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class SnakeWindow {

	private JFrame frame;
	private JTextField textField;
	private JButton btnNewGame;
	private JPanel panel;
	
	private SnakeGame game;
	private int fieldSize;
	
	private final static int gap = 1;
	
	private Map<Integer,Map<Integer,JPanel>> fields;
	
	/**
	 * Create the application.
	 */
	public SnakeWindow(SnakeGame game, int size){
		this.game = game;
		this.fieldSize = size;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		frame.getContentPane().add(textField, BorderLayout.SOUTH);
		textField.setEditable(false);
		textField.setFocusable(false);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(fieldSize, fieldSize, gap, gap));
		panel.setPreferredSize(new Dimension(20*fieldSize,20*fieldSize));
		frame.pack();

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		btnNewGame = new JButton("New Game");
		menuBar.add(btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initializeField();
				game.newGame();	}
		});
		
		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP){
					game.setDirection(Direction.UP);
				} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
					game.setDirection(Direction.DOWN);
				} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					game.setDirection(Direction.RIGHT);
				} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
					game.setDirection(Direction.LEFT);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {			}
		};
		
		frame.addKeyListener(keyListener);
		panel.addKeyListener(keyListener);
		menuBar.addKeyListener(keyListener);
		btnNewGame.addKeyListener(keyListener);

	}

	
	private void initializeField(){
		panel.removeAll();
		fields = new HashMap<Integer,Map<Integer,JPanel>>();
		for(int i = 1; i<=fieldSize; i++){
			HashMap<Integer,JPanel> tempMap = new HashMap<Integer,JPanel>();
			for(int j = 1; j<=fieldSize; j++){
				JPanel tempPanel = new JPanel();
				tempPanel.setName(i + " " + j);
				tempPanel.setBackground(Color.WHITE);
				panel.add(tempPanel);
				tempMap.put(j, tempPanel);
			}
			fields.put(i, tempMap);
		}
		frame.revalidate();
	}
	
	public void setColor(Point point, Color color){
		fields.get((int) point.getX()).get((int) point.getY()).setBackground(color);
		frame.revalidate();
	}
	
	public void setText(String text){
		textField.setText(text);
	}
	
}
