package org.example.cs203_pz2_vukdojcinovic5907;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GraphPane extends Pane {

    public enum Mode { ADD_NODE, ADD_EDGE, HIGHLIGHT, NONE }

    private Mode mode = Mode.NONE;
    private List<GraphNode> graphNodes = new ArrayList<>();
    private List<GraphEdge> graphEdges = new ArrayList<>();
    private int nodeCounter = 0;
    private GraphNode startNode = null;
    private Button highlightButton;

    public void setMode(Mode mode) {
        if (this.mode == Mode.HIGHLIGHT && mode != Mode.HIGHLIGHT) {
            resetAllHighlights();
        }
        this.mode = mode;
        if (mode == Mode.ADD_NODE) {
            this.setOnMouseClicked(e -> addGraphNode(e.getX(), e.getY()));
        } else if (mode == Mode.ADD_EDGE) {
            this.setOnMouseClicked(e -> startEdgeCreation(e.getX(), e.getY()));
        } else if (mode == Mode.HIGHLIGHT) {
            this.setOnMouseClicked(e -> highlightEdge(e.getX(), e.getY()));
        } else {
            this.setOnMouseClicked(null);
        }
    }

    public void setHighlightButton(Button highlightButton) {
        this.highlightButton = highlightButton;
    }

    public void abortEdgeCreation() {
        if (startNode != null) {
            resetNodeColor(startNode);
            startNode = null;
        }
        setMode(Mode.NONE);
    }

    private void addGraphNode(double x, double y) {
        if (getGraphNodeAt(x, y) != null) {
            showAlert("Invalid Placement", "Cannot place a node on top of another node.");
            return;
        }

        Circle circle = new Circle(x, y, 25);
        circle.setFill(javafx.scene.paint.Color.web("#EAC4D5"));

        Text label = new Text("Node " + nodeCounter);
        label.setX(x - 15);
        label.setY(y + 5);
        label.setFill(javafx.scene.paint.Color.web("#28292B"));
        label.setFont(Font.font(null, FontWeight.BOLD, 12));

        GraphNode graphNode = new GraphNode(circle, label, nodeCounter++);
        circle.setUserData(graphNode);

        graphNodes.add(graphNode);
        this.getChildren().addAll(circle, label);

        if (graphNodes.size() >= 2 && highlightButton != null) {
            highlightButton.setDisable(false);
        }
    }

    private void startEdgeCreation(double x, double y) {
        if (startNode == null) {
            startNode = getGraphNodeAt(x, y);
            if (startNode == null) {
                showAlert("Invalid Selection", "Please select a valid start node.");
            } else {
                highlightNode(startNode, "#E185AF");
                showAlert("Start Node Selected", "Now, please select the end node. You can press 'Abort' to cancel.");
            }
        } else {
            GraphNode endNode = getGraphNodeAt(x, y);
            if (endNode == null) {
                showAlert("Invalid Selection", "Please select a valid end node.");
            } else if (startNode.equals(endNode)) {
                showAlert("Invalid Selection", "Cannot create an edge between the same node.");
            } else if (isEdgeDuplicate(startNode, endNode)) {
                showAlert("Duplicate Edge", "An edge between these two nodes already exists.");
            } else {

                Optional<String> result = showWeightInputDialog();
                if (result.isPresent()) {
                    String weightText = result.get();

                    Line line = new Line();
                    line.setStartX(startNode.getCircle().getCenterX());
                    line.setStartY(startNode.getCircle().getCenterY());
                    line.setEndX(endNode.getCircle().getCenterX());
                    line.setEndY(endNode.getCircle().getCenterY());
                    line.setStroke(javafx.scene.paint.Color.web("#EAC4D5"));
                    line.setStrokeWidth(3);

                    double midX = (line.getStartX() + line.getEndX()) / 2;
                    double midY = (line.getStartY() + line.getEndY()) / 2;
                    Text weightLabel = new Text(midX, midY, weightText);
                    weightLabel.setFill(javafx.scene.paint.Color.web("#28292B"));
                    weightLabel.setFont(Font.font(null, FontWeight.BOLD, 12));

                    this.getChildren().addAll(line, weightLabel);
                    graphEdges.add(new GraphEdge(startNode, endNode, line, weightText, weightLabel));

                    //showAlert("Edge Created", "Edge successfully created between nodes with weight: " + weightText);
                    resetNodeColor(startNode);
                    startNode = null;
                }
            }
        }
    }

    private void highlightEdge(double x, double y) {
        if (startNode == null) {
            startNode = getGraphNodeAt(x, y);
            if (startNode == null) {
                showAlert("Invalid Selection", "Please select a valid start node.");
            } else {
                highlightNode(startNode, "#E1AF7D");
                showAlert("Start Node Selected", "Now, please select the end node to highlight the edge.");
            }
        } else {
            GraphNode endNode = getGraphNodeAt(x, y);
            if (endNode == null) {
                showAlert("Invalid Selection", "Please select a valid end node.");
            } else {
                GraphEdge edge = getEdgeBetweenNodes(startNode, endNode);
                if (edge != null) {
                    highlightNode(startNode, "#E1AF7D");
                    highlightNode(endNode, "#E1AF7D");
                    highlightEdge(edge, "#E1AF7D");
                } else {
                    showAlert("No Edge Found", "There is no edge between the selected nodes.");
                    resetNodeColor(startNode);
                }
                startNode = null;
            }
        }
    }

    private Optional<String> showWeightInputDialog() {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Edge Weight");
        dialog.setHeaderText("Enter the weight for the edge:");
        dialog.setContentText("Weight:");

        return dialog.showAndWait();
    }

    private GraphEdge getEdgeBetweenNodes(GraphNode node1, GraphNode node2) {
        for (GraphEdge edge : graphEdges) {
            if ((edge.getStartGraphNode().equals(node1) && edge.getEndGraphNode().equals(node2)) ||
                    (edge.getStartGraphNode().equals(node2) && edge.getEndGraphNode().equals(node1))) {
                return edge;
            }
        }
        return null;
    }

    private boolean isEdgeDuplicate(GraphNode node1, GraphNode node2) {
        return getEdgeBetweenNodes(node1, node2) != null;
    }

    private GraphNode getGraphNodeAt(double x, double y) {
        for (GraphNode graphNode : graphNodes) {
            Circle circle = graphNode.getCircle();
            if (Math.sqrt(Math.pow(circle.getCenterX() - x, 2) + Math.pow(circle.getCenterY() - y, 2)) <= circle.getRadius()) {
                return graphNode;
            }
        }
        return null;
    }

    private void highlightNode(GraphNode node, String color) {
        node.getCircle().setFill(javafx.scene.paint.Color.web(color));
    }

    private void resetNodeColor(GraphNode node) {
        node.getCircle().setFill(javafx.scene.paint.Color.web("#EAC4D5"));
    }

    private void highlightEdge(GraphEdge edge, String color) {
        if (edge.getLine() != null) {
            edge.getLine().setStroke(javafx.scene.paint.Color.web(color));
            edge.getWeightLabel().setFill(javafx.scene.paint.Color.web(color));
        }
    }

    private void resetEdgeColor(GraphEdge edge) {
        if (edge.getLine() != null) {
            edge.getLine().setStroke(javafx.scene.paint.Color.web("#EAC4D5"));
            edge.getWeightLabel().setFill(javafx.scene.paint.Color.web("#28292B"));
        }
    }

    private void resetAllHighlights() {
        for (GraphNode node : graphNodes) {
            resetNodeColor(node);
        }
        for (GraphEdge edge : graphEdges) {
            resetEdgeColor(edge);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public List<GraphNode> getGraphNodes() {
        return graphNodes;
    }

    public List<GraphEdge> getGraphEdges() {
        return graphEdges;
    }
}