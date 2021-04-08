package creativespace;

import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class DrawingView extends StackPane {
	private ArrayList<Stack<Canvas>> layers;
	private Stack<Canvas> topLayer;
	
	private GraphicsContext currentGC;
	private Canvas currentCanvas;
	
	private Rectangle rect;
	private Circle circle;
	private Ellipse ellipse;
	private Line line;
	
	private double canvasWidth;
	private double canvasHeight;
	private double offSetX;
	
	public DrawingView(double canvasWidth, double canvasHeight, double offSetX) {
		layers = new ArrayList<>();
		topLayer = new Stack<>();
		currentCanvas = new Canvas(canvasWidth, canvasHeight);
		getChildren().add(currentCanvas);
		currentGC = currentCanvas.getGraphicsContext2D();
		currentGC.setLineWidth(1);
		topLayer.push(currentCanvas);
		layers.add(topLayer);
		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.offSetX = offSetX;
	}
	
	public void createRectangle(double x, double y) {
		rect = new Rectangle();
		rect.setX(x);
		rect.setY(y);
	}
	
	public void renderRectangle(double newX, double newY, Color strokeColor, Color fillColor) {
		double currentX = rect.getX();
		double currentY = rect.getY();
		
		double width = Math.abs(newX - currentX);
		double height = Math.abs(newY - currentY);
		
		rect.setWidth(width);
		rect.setHeight(width);
		currentGC.setStroke(strokeColor);
		currentGC.setFill(fillColor);		

		if (newX < currentX) {
			rect.setX(newX);
		}
		
		if (newY < currentY) {
			rect.setY(newY);
		}
		
		currentGC.setStroke(strokeColor);
		currentGC.setFill(fillColor);
		currentGC.fillRect(rect.getX() - offSetX, rect.getY(), rect.getWidth(), rect.getHeight());
		currentGC.strokeRect(rect.getX() - offSetX, rect.getY(), rect.getWidth(), rect.getHeight());
	}
	
	public void createCircle(double x, double y) {
		circle = new Circle();
		circle.setCenterX(x);
		circle.setCenterY(y);
	}
	
	public void renderCircle(double x, double y, Color strokeColor, Color fillColor) {
		double dx = Math.abs(x - circle.getCenterX());
		double dy = Math.abs(y - circle.getCenterY());
		double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		circle.setRadius(radius);
		
		currentGC.setStroke(strokeColor);
		currentGC.setFill(fillColor);
		currentGC.fillOval(circle.getCenterX() - offSetX, circle.getCenterY(), radius, radius);
		currentGC.strokeOval(circle.getCenterX() - offSetX, circle.getCenterY(), radius, radius);
	}

	public void createEllipse(double x, double y) {
		ellipse = new Ellipse();
		ellipse.setCenterX(x);
		ellipse.setCenterY(y);
	}
	
	public void renderEllipse(double x, double y, Color strokeColor, Color fillColor) {
		double dx = Math.abs(x - ellipse.getCenterX());
		double dy = Math.abs(y - ellipse.getCenterY());
		ellipse.setRadiusX(dx);
		ellipse.setRadiusY(dy);
		
		currentGC.setStroke(strokeColor);
		currentGC.setFill(fillColor);
		currentGC.fillOval(ellipse.getCenterX() - offSetX, ellipse.getCenterY(), dx, dy);
		currentGC.strokeOval(ellipse.getCenterX() - offSetX, ellipse.getCenterY(), dx, dy);

	}

	public void createLine(double x, double y) {
		line = new Line();
		line.setStartX(x);
		line.setStartY(y);
	}

	public void renderLine(double x, double y, Color strokeColor) {
		line.setEndX(x);
		line.setEndY(y);
		
		currentGC.setStroke(strokeColor);
		currentGC.strokeLine(line.getStartX() - offSetX, line.getStartY(), x - offSetX, y);
	}
	
	public void createNewAction() {
		topLayer = layers.get(layers.size() - 1);
		currentCanvas = new Canvas(canvasWidth, canvasHeight);
		currentGC = currentCanvas.getGraphicsContext2D();
		currentGC.setLineWidth(1);
		topLayer.push(currentCanvas);
	}
	
	public void addNewLayer() {
		topLayer = new Stack<>();
		currentCanvas = new Canvas(canvasWidth, canvasHeight);
		currentGC = currentCanvas.getGraphicsContext2D();
		currentGC.setLineWidth(1);
		topLayer.push(currentCanvas);
		layers.add(topLayer);
	}
	
	public void removeTopLayer() {
		if (layers.size() > 1) {
			topLayer = layers.get(layers.size() - 1);
			while (!topLayer.empty()) {
				getChildren().remove(topLayer.pop());
			}
			layers.remove(layers.size() - 1);
		}
	
	}
	
	public void undoLastAction() {
		topLayer = layers.get(layers.size() - 1);
		if (!topLayer.empty()) {
			getChildren().remove(topLayer.pop());
		}

		
	}
}
