package creativespace;

import java.util.ArrayList;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Sidebar extends FlowPane {

    private ToggleButton RectangleButton;
    private ToggleButton CircleButton;
    private ToggleButton EllipseButton;
    private ToggleButton LineButton;
    private GraphicsContext gc;
    private ColorPicker Stroke;
    private ColorPicker Fill;
    private Rectangle rectangle;
    private Circle circle;
    private Ellipse ellipse;
    private Line line;
    private double initialX;
    private double initialY;
    private ToggleGroup Buttons;
    private boolean move = false, drag = false;
	private ArrayList<Stack<Canvas>> layers;
	private Button undoButton;
	private Button removeLayer;
	private Button addLayer;
    private VBox Tools;
    private HBox tools1;
	private StackPane stackpane;

    public Sidebar() {

		undoButton = new Button("Undo Last Action");
			undoButton.setOnAction(this::processUndo);
		layers = new ArrayList<>();

		addLayer = new Button("Add a New Layer");
			addLayer.setOnAction(this::addNewLayer);
		removeLayer = new Button("Remove a Layer");
			removeLayer.setOnAction(this::removeTopLayer);

        RectangleButton = new ToggleButton("Rectangle");
        CircleButton = new ToggleButton("Circle");
        EllipseButton = new ToggleButton("Ellipse");
        LineButton = new ToggleButton("Line");

        ToggleButton[] ToolSet = {RectangleButton, CircleButton, EllipseButton, LineButton};

        Buttons = new ToggleGroup();

        for (ToggleButton tool : ToolSet) {
            tool.setMinWidth(90);
            tool.setToggleGroup(Buttons);
            tool.setCursor(Cursor.HAND);
        }

        Stroke = new ColorPicker(Color.BLACK);
        Fill = new ColorPicker(Color.TRANSPARENT);

        Label StrokeColor = new Label("Stroke Color");
        Label ShapeLabel = new Label("Shapes");
        Label FillColor = new Label("Fill Color");

        Tools = new VBox();
        tools1 = new HBox();
		stackpane = new StackPane();
        Tools.getChildren().addAll(ShapeLabel, RectangleButton, CircleButton, EllipseButton, LineButton,
                StrokeColor, Stroke, FillColor, Fill, new Label("\n"), undoButton, new Label("\n"), addLayer, removeLayer);
        Tools.setPadding(new Insets(5));
        Tools.setStyle("-fx-background-color: #999");
        Tools.setPrefWidth(150);

        setOnMousePressed(this::processMouseClick);
        setOnMouseReleased(this::processDrawing);

		layers.add(new Stack<Canvas>());
		layers.get(0).push(new Canvas(1200, 900));
		Stack<Canvas> topLayer = layers.get(layers.size() - 1);
		
		Canvas topCanvas = topLayer.peek();
        gc = topCanvas.getGraphicsContext2D();
        gc.setLineWidth(1);
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0, 1290, 900);

		stackpane.getChildren().add(layers.get(0).peek());
        tools1.getChildren().addAll(Tools, stackpane);
        //tools1.getChildren().add(Tools);

        getChildren().add(tools1);
    }

    public void processMouseClick(MouseEvent event) {
		int layerListSize = layers.size() - 1;
		layers.get(layerListSize).push(new Canvas(1200, 900));
		stackpane.getChildren().add(layers.get(layerListSize).peek());
		Canvas topCanvas = layers.get(layerListSize).peek();
        gc = topCanvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        rectangle = new Rectangle();
        circle = new Circle();
        ellipse = new Ellipse();
        line = new Line();
        if (RectangleButton.isSelected()) {
            rectangle.setX(event.getX());
            rectangle.setY(event.getY());
            gc.setStroke(Stroke.getValue());
            gc.setFill(Fill.getValue());
        } else if (CircleButton.isSelected()) {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            gc.setStroke(Stroke.getValue());
            gc.setFill(Fill.getValue());
        } else if (EllipseButton.isSelected()) {
            ellipse.setCenterX(event.getX());
            ellipse.setCenterY(event.getY());
            gc.setStroke(Stroke.getValue());
            gc.setFill(Fill.getValue());
        } else if (LineButton.isSelected()) {
            line.setStartX(event.getX());
            line.setStartY(event.getY());
            gc.setStroke(Stroke.getValue());
        }
        
    }

    public void processDrawing(MouseEvent event) {
        if (RectangleButton.isSelected() && !drag) {
            rectangle.setWidth(Math.abs((event.getX() - rectangle.getX())));
            rectangle.setHeight(Math.abs((event.getY() - rectangle.getY())));

            if (rectangle.getX() > event.getX()) {
                rectangle.setX(event.getX());
            }

            if (rectangle.getY() > event.getY()) {
                rectangle.setY(event.getY());
            }

            gc.fillRect(rectangle.getX() - 150, rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            gc.strokeRect(rectangle.getX() - 150, rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        } else {
            double newX = event.getX();
            double newY = event.getY();

            rectangle.setX(newX);
            rectangle.setY(newY);

            drag = false;
        }

        if (CircleButton.isSelected() && !drag) {
            circle.setRadius((Math.abs(event.getX() - circle.getCenterX()) + Math.abs(event.getY() - circle.getCenterY())) / 2);

            if (circle.getCenterX() > event.getX()) {
                circle.setCenterX(event.getX());
            }
            if (circle.getCenterY() > event.getY()) {
                circle.setCenterY(event.getY());
            }

            gc.fillOval(circle.getCenterX() - 150, circle.getCenterY(), circle.getRadius(), circle.getRadius());
            gc.strokeOval(circle.getCenterX() - 150, circle.getCenterY(), circle.getRadius(), circle.getRadius());

        } else {
            double newX = event.getX();
            double newY = event.getY();

            circle.setCenterX(newX);
            circle.setCenterY(newY);

            drag = false;
        }

        if (EllipseButton.isSelected() && !drag) {
            ellipse.setRadiusX(Math.abs(event.getX() - ellipse.getCenterX()));
            ellipse.setRadiusY(Math.abs(event.getY() - ellipse.getCenterY()));

            if (ellipse.getCenterX() > event.getX()) {
                ellipse.setCenterX(event.getX());
            }
            if (ellipse.getCenterY() > event.getY()) {
                ellipse.setCenterY(event.getY());
            }

            gc.strokeOval(ellipse.getCenterX() - 150, ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());
            gc.fillOval(ellipse.getCenterX() - 150, ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());

        } else {
            double newX = event.getX();
            double newY = event.getY();

            ellipse.setRadiusX(newX);
            ellipse.setRadiusY(newY);
		
	    drag = false;
        }
	
	    if (LineButton.isSelected() && !drag) {
            line.setEndX(event.getX());
            line.setEndY(event.getY());
            
            gc.strokeLine(line.getStartX() - 150, line.getStartY() - 150, line.getEndX(), line.getEndY());

        } else {
            double newX = event.getX();
            double newY = event.getY();
            
            line.setEndX(newX);
            line.setEndY(newY);
            
            drag = false;
        }


    }

	public void addNewLayer(ActionEvent event) {
		layers.add(new Stack<Canvas>());
		Canvas topCanvas = new Canvas(1200, 900);
		layers.get(layers.size() - 1).push(topCanvas);
		stackpane.getChildren().add(topCanvas);
	}

	public void removeTopLayer(ActionEvent event) {
		if (layers.size() > 1) {
			Stack<Canvas> topLayer = layers.get(layers.size() - 1);
			while (!topLayer.empty()) {
				stackpane.getChildren().remove(topLayer.pop());
			}
			layers.remove(layers.size() - 1);
		}
	}

	public void processUndo(ActionEvent event) {
		Stack<Canvas> topLayer = layers.get(layers.size() - 1);
		if (!topLayer.empty()) {
			stackpane.getChildren().remove(topLayer.pop());
		}
	}
}
