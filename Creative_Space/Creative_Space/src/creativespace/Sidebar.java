package creativespace;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
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

    public Sidebar() {

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

        VBox Tools = new VBox();
        HBox tools1 = new HBox();
        Tools.getChildren().addAll(ShapeLabel, RectangleButton, CircleButton, EllipseButton, LineButton,
                StrokeColor, Stroke, FillColor, Fill);
        Tools.setPadding(new Insets(5));
        Tools.setStyle("-fx-background-color: #999");
        Tools.setPrefWidth(150);

        setOnMousePressed(this::processMouseClick);
        setOnMouseReleased(this::processDrawing);

        Canvas canvas = new Canvas(1290, 900);
        tools1.getChildren().addAll(Tools, canvas);
        //tools1.getChildren().add(Tools);
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);

        getChildren().add(tools1);
    }

    public void processMouseClick(MouseEvent event) {
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
        } else if (EllipseButton.isSelected())
            ellipse.setCenterX(event.getX());
            ellipse.setCenterY(event.getY());
            gc.setStroke(Stroke.getValue());
            gc.setFill(Fill.getValue());

        
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
        }

    }
}