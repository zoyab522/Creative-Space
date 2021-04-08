package creativespace;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Sidebar extends FlowPane {

    private ToggleButton RectangleButton;
    private ToggleButton CircleButton;
    private ToggleButton EllipseButton;
    private ToggleButton LineButton;
    private ColorPicker Stroke;
    private ColorPicker Fill;
    private ToggleGroup Buttons;
	private Button undoButton;
	private Button removeLayer;
	private Button addLayer;
    private VBox Tools;
    private HBox fullPanel;
	private double screenWidth;
	private double screenHeight;
	private double toolboxWidth;
	private DrawingView drawingview;

    public Sidebar(double screenWidth, double screenHeight) {

		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		toolboxWidth = screenWidth / 6;

		drawingview = new DrawingView(screenWidth - toolboxWidth, screenHeight, toolboxWidth);
		
		undoButton = new Button("Undo Last Action");
			undoButton.setOnAction(this::processUndo);
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
        fullPanel = new HBox();
        Tools.getChildren().addAll(ShapeLabel, RectangleButton, CircleButton, EllipseButton, LineButton,
                StrokeColor, Stroke, FillColor, Fill, new Label("\n"), undoButton, new Label("\n"), addLayer, removeLayer);
        Tools.setPadding(new Insets(5));
        Tools.setStyle("-fx-background-color: #999");
        Tools.setPrefWidth(150);

        setOnMousePressed(this::processMouseClick);
        setOnMouseReleased(this::processDrawing);

        fullPanel.getChildren().addAll(Tools, drawingview);

        getChildren().add(fullPanel);
    }

    public void processMouseClick(MouseEvent event) {
		double x = event.getX();
		double y = event.getY();
		if (RectangleButton.isSelected())
			drawingview.createRectangle(x, y);
		else if (CircleButton.isSelected())
			drawingview.createCircle(x, y);
		else if (EllipseButton.isSelected())
			drawingview.createEllipse(x, y);
		else if (LineButton.isSelected())
			drawingview.createLine(x, y);
		else
			drawingview.createRectangle(x, y);
        
    }

    public void processDrawing(MouseEvent event) {
		Color strokeColor = Stroke.getValue();
		Color fillColor = Fill.getValue();

		double x = event.getX();
		double y = event.getY();
        if (RectangleButton.isSelected())
			drawingview.renderRectangle(x, y, strokeColor, fillColor);
		else if (CircleButton.isSelected())
			drawingview.renderCircle(x, y, strokeColor, fillColor);
		else if (EllipseButton.isSelected())
			drawingview.renderEllipse(x, y, strokeColor, fillColor);
		else if (LineButton.isSelected())
			drawingview.renderLine(x, y, strokeColor);
		else
			drawingview.renderRectangle(x, y, strokeColor, fillColor);
    }

	public void addNewLayer(ActionEvent event) {
		drawingview.addNewLayer();
	}

	public void removeTopLayer(ActionEvent event) {
		drawingview.removeTopLayer();
	}

	public void processUndo(ActionEvent event) {
		drawingview.undoLastAction();
	}
}
