package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Main extends Application {
	
	private static final int width = 800;
	private static final int height = 600;
	private static final int racket_width = 10;
	private static final int racket_height = 90;
	private static final int rad = 30;
	double playerX = 0;
	double playerY = height/2;
	double compX = width - racket_width;
	double compY = height/2;
	double ballX = width/2 - rad/2;
	double ballY = height/2 - rad/2;
	double ballYSpeed = 3;
	double ballXSpeed = 3;
	boolean gameStarted;
	int comp = 0;
	int player = 0;
	String strcomp = String.valueOf(comp);
	String strplay = String.valueOf(player);
	double mouse;
	GraphicsContext gc;
	
	private void drawTable() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height);	
		gc.setFill(Color.WHITE);
		gc.fillRect(width/2 - 1, 0, 2, height);		
		gc.strokeText(strplay, width/2-10, 10);
		gc.strokeText(strcomp, width/2+10, 10);
		if (gameStarted) {
			ballX += ballXSpeed;
			ballY += ballYSpeed;
			if ((playerY + racket_height/2 > mouse ) && (playerY>0)) {
				playerY = playerY - 3;
			}
			
			if ((playerY + racket_height/2 < mouse) && (playerY + racket_height<height)) {
				playerY = playerY + 3;
			}
			
			if ((compY + racket_height/2 > ballY) && (ballXSpeed>0) && (ballX>width/2) && (compY>0)) {
				compY = compY - 3;
			}
			
			if ((compY + racket_height/2 < ballY) && (ballXSpeed>0) && (ballX>width/2) && (compY + racket_height<height)) {
				compY = compY + 3;
			}
			
			gc.fillOval(ballX, ballY, rad, rad);
		} else {
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("Click to start", width/2, height/2);			
		}
		
		if (ballX < 0) {
			gameStarted = false;
			compX = width - racket_width;
			compY = height/2;
			ballX = width/2 - rad/2;
			ballY = height/2 - rad/2;
			comp = comp + 1;
			strcomp = String.valueOf(comp);
			ballXSpeed = ballXSpeed * (-1);
		}
		
		if (ballX > width) {
			gameStarted = false;
			compX = width - racket_width;
			compY = height/2;
			ballX = width/2 - rad/2;
			ballY = height/2 - rad/2;
			player = player + 1;
			strplay = String.valueOf(player);
			ballXSpeed=ballXSpeed * (-1);
		}
		gc.fillRect(playerX, playerY, racket_width, racket_height);
		gc.fillRect(compX, compY, racket_width, racket_height);
		if (ballX + rad/2 >  compX - racket_width) {
			ballXSpeed = ballXSpeed * (-1);
		}
		
		if ((ballX < playerX + racket_width) && (ballY + rad/2 > playerY) && (ballY + rad/2 < playerY + racket_height)){
			ballXSpeed = ballXSpeed * (-1);
		}
		
		if (ballY > height - rad) {
			ballYSpeed = ballYSpeed * (-1);
		}
		
		if (ballY < 0) {
			ballYSpeed = ballYSpeed * (-1);
		}
	}
	
	@Override
	public void start(Stage root) {
		Canvas canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		drawTable();		
		Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e->drawTable()));
		t1.setCycleCount(Timeline.INDEFINITE);
		
		canvas.setOnMouseClicked(e -> gameStarted = true);
		canvas.setOnMouseMoved(e -> mouse = e.getY());

		root.setScene(new Scene(new StackPane(canvas)));
		root.setTitle("Ping-pong");
		root.show();
		t1.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
