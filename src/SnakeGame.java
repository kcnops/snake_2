import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame {

	private final static int fieldSize = 10;
	
	private SnakeWindow window;
	
	private Direction direction;
	private ArrayList<Point> snake;
	private Point food;
	private Boolean gameover;
	
	public SnakeGame(){
		window = new SnakeWindow(this, fieldSize);	
	}
	
	public void newGame(){
		snake = new ArrayList<Point>();
		int midRow = fieldSize/2;
		snake.add(new Point(midRow,1));
		snake.add(new Point(midRow,2));
		snake.add(new Point(midRow,3));
		for(Point point : snake){
			window.setColor(point, Color.BLACK);
		}
		direction = Direction.RIGHT;
		putRandomFood();
		loop();
	}
	
	private void loop(){
		gameover = false;
		while(!gameover){
			try {
				Thread.sleep(500);
				moveSnake();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Boolean contains(Point point){
		for(Point snakePoint : snake){
			if(snakePoint.equals(point)){
				return true;
			}
		}
		return false;
	}
	
	private void putRandomFood(){
		Random rand = new Random();
		while(true){
			int row = rand.nextInt(fieldSize) + 1;
			int col = rand.nextInt(fieldSize) + 1;
			food = new Point(row, col);
			if(!contains(food)){
				window.setColor(food, Color.GREEN);
				return;
			}
		}		
	}
	
	private void moveSnake(){
		Point head = snake.get(snake.size()-1);
		int headX = (int) head.getX();
		int headY = (int) head.getY();
		switch(direction){
			case RIGHT: headY = headY + 1; break;
			case LEFT: headY = headY - 1; break;
			case UP: headX = headX - 1; break;
			case DOWN: headX = headX + 1; break;
		}
		Point newHead = new Point(headX,headY);
		if( headX == 0 || headY == 0 || headX > fieldSize || headY > fieldSize || 
				contains(newHead)){
			gameover = true;
			window.setText("Failed!");
			return;
		}
		if(newHead.equals(food)){
			//add new head
			snake.add(newHead);
			window.setColor(newHead, Color.BLACK);
			//add new piece of food
			putRandomFood();
		} else {
			//add new head
			snake.add(newHead);
			window.setColor(newHead, Color.BLACK);
			//remove old tail
			Point tail = snake.remove(0);
			window.setColor(tail, Color.WHITE);
		}
	}
	
	public void setDirection(Direction dir){
		this.direction = dir;
		moveSnake();
	}

}
