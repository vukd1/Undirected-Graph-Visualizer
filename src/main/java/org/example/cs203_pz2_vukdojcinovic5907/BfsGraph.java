package org.example.cs203_pz2_vukdojcinovic5907;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class BfsGraph {

    private final List<GraphNode> graphNodes;
    private final List<GraphEdge> graphEdges;

    public BfsGraph(List<GraphNode> graphNodes, List<GraphEdge> graphEdges) {
        this.graphNodes = graphNodes;
        this.graphEdges = graphEdges;
    }

    public void performBfsAndDrawGraph() {
        if (graphNodes.isEmpty() || graphEdges.isEmpty()) {
            showAlert("No Graph", "The graph is empty or incomplete.");
            return;
        }

        Optional<Integer> startNodeIdOpt = showStartNodeInputDialog();
        if (!startNodeIdOpt.isPresent()) {
            showAlert("No Start Node", "No start node was selected.");
            return;
        }

        int startNodeId = startNodeIdOpt.get();
        GraphNode startNode = getGraphNodeById(startNodeId);

        if (startNode == null) {
            showAlert("Invalid Node", "The selected node does not exist.");
            return;
        }

        Set<GraphNode> visited = new HashSet<>();
        Queue<GraphNode> queue = new LinkedList<>();
        List<GraphEdge> bfsEdges = new ArrayList<>();
        List<String> steps = new ArrayList<>();

        queue.add(startNode);
        visited.add(startNode);
        steps.add("Starting BFS from Node " + startNodeId);

        while (!queue.isEmpty()) {
            GraphNode currentNode = queue.poll();

            for (GraphEdge edge : getConnectedEdges(currentNode)) {
                GraphNode neighbor = edge.getStartGraphNode().equals(currentNode) ? edge.getEndGraphNode() : edge.getStartGraphNode();

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    bfsEdges.add(edge);
                    steps.add("Visited Node " + neighbor.getId() + " via edge from Node " + currentNode.getId() + " to Node " + neighbor.getId() + " with weight " + edge.getWeight());
                }
            }
        }

        drawGraph(bfsEdges, "BFS Graph", steps);
    }

    private void drawGraph(List<GraphEdge> edges, String title, List<String> steps) {
        Pane graphPane = new Pane();

        for (GraphEdge edge : edges) {
            Line line = new Line();
            line.setStartX(edge.getLine().getStartX());
            line.setStartY(edge.getLine().getStartY());
            line.setEndX(edge.getLine().getEndX());
            line.setEndY(edge.getLine().getEndY());
            line.setStroke(javafx.scene.paint.Color.BLUE);
            line.setStrokeWidth(4);

            Text weightLabel = new Text(edge.getWeightLabel().getX(), edge.getWeightLabel().getY(), edge.getWeight());
            weightLabel.setFont(edge.getWeightLabel().getFont());
            weightLabel.setFill(edge.getWeightLabel().getFill());

            graphPane.getChildren().addAll(line, weightLabel);
        }

        VBox stepsBox = new VBox();
        stepsBox.setSpacing(5);
        stepsBox.setStyle("-fx-padding: 10;");
        for (String step : steps) {
            Text stepText = new Text(step);
            stepsBox.getChildren().add(stepText);
        }

        HBox mainLayout = new HBox(graphPane, stepsBox);

        Stage graphStage = new Stage();
        graphStage.setTitle(title);
        graphStage.setScene(new Scene(mainLayout, 1000, 600));
        graphStage.show();
    }

    private Optional<Integer> showStartNodeInputDialog() {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("BFS Start Node");
        dialog.setHeaderText("Enter the ID of the start node:");
        dialog.setContentText("Node ID:");

        Optional<String> result = dialog.showAndWait();
        return result.map(Integer::parseInt);
    }

    private GraphNode getGraphNodeById(int id) {
        for (GraphNode node : graphNodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    private List<GraphEdge> getConnectedEdges(GraphNode node) {
        List<GraphEdge> connectedEdges = new ArrayList<>();
        for (GraphEdge edge : graphEdges) {
            if (edge.getStartGraphNode().equals(node) || edge.getEndGraphNode().equals(node)) {
                connectedEdges.add(edge);
            }
        }
        return connectedEdges;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}