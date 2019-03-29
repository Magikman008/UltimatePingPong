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
	
	// размер поля
	private static final int width = 800;
	private static final int height = 600;
	
	// ширина и высота ракетки
	private static final int RACKET_WIDTH = 10;
	private static final int RACKET_HEIGHT = 90;
	
	// диаметр мяча
	private static final int rad = 30;
	
	// начальные координаты ракетки игрока
	double playerX = 0;
	double playerY = height/2;
	
	// начальные координаты ракетки компа
	double compX = width - RACKET_WIDTH;
	double compY = height/2;
	
	// координаты мяча
	double ballX = width/2 - rad/2;
	double ballY = height/2 - rad/2;
	
	// инструмент рисовани¤
	GraphicsContext gc;
	
	// скорость мяча
	double ballYSpeed = 3;
	double ballXSpeed = 3;
	
	// игровой цикл
	boolean gameStarted;
	
	int comp = 0;
	int player = 0;
	String strcomp = String.valueOf(comp);
	String strplay = String.valueOf(player);
	double mouse;
	
	private void drawTable() {
		// рисуем поле
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height);			
		// рисуем разделительную линию
		gc.setFill(Color.WHITE);
		gc.fillRect(width/2-1, 0, 2, height);		
		gc.strokeText(strplay, width/2-10, 10);
		gc.strokeText(strcomp, width/2+10, 10);
		// рисуем м¤ч
		if(gameStarted) {
			ballX+=ballXSpeed;
			ballY+=ballYSpeed;
			if ((playerY + RACKET_HEIGHT/2 > mouse ) && (playerY>0)) {
				playerY = playerY - 3;
			}
			if ((playerY + RACKET_HEIGHT/2 < mouse) && (playerY + RACKET_HEIGHT<height)) {
				playerY = playerY + 3;
			}
			if ((compY + RACKET_HEIGHT/2 > ballY) && (ballXSpeed>0) && (ballX>width/2) && (compY>0)) {
				compY = compY - 3;
			}
			if ((compY + RACKET_HEIGHT/2 < ballY) && (ballXSpeed>0) && (ballX>width/2) && (compY + RACKET_HEIGHT<height)) {
				compY = compY + 3;
			}
			gc.fillOval(ballX, ballY, rad, rad);
		} else {
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("Click to start", width/2, height/2);			
		}
		if(ballX<0) {
			gameStarted = false;
			compX = width - RACKET_WIDTH;
			compY = height/2;
			ballX = width/2 - rad/2;
			ballY = height/2 - rad/2;
			comp = comp + 1;
			strcomp = String.valueOf(comp);
			ballXSpeed = ballXSpeed * (-1);
		}
		if(ballX>width) {
			gameStarted=false;
			compX = width - RACKET_WIDTH;
			compY = height/2;
			ballX = width/2 - rad/2;
			ballY = height/2 - rad/2;
			player = player + 1;
			strplay = String.valueOf(player);
			ballXSpeed=ballXSpeed * (-1);
		}
	
		// рисуем ракетки
		gc.fillRect(playerX, playerY, RACKET_WIDTH, RACKET_HEIGHT);
		gc.fillRect(compX, compY, RACKET_WIDTH, RACKET_HEIGHT);
		if (ballX + rad/2>  compX - RACKET_WIDTH) {
			ballXSpeed = ballXSpeed * (-1);
		}
		if ((ballX < playerX + RACKET_WIDTH) && (ballY + rad/2 > playerY) && (ballY + rad/2 < playerY + RACKET_HEIGHT)){
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
