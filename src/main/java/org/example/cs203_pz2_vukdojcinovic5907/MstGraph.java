package org.example.cs203_pz2_vukdojcinovic5907;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class MstGraph {

    private final List<GraphNode> graphNodes;
    private final List<GraphEdge> graphEdges;

    public MstGraph(List<GraphNode> graphNodes, List<GraphEdge> graphEdges) {
        this.graphNodes = graphNodes;
        this.graphEdges = graphEdges;
    }

    public void findAndDrawShortestPath() {
        if (graphNodes.isEmpty() || graphEdges.isEmpty()) {
            showAlert("No Graph", "The graph is empty or incomplete.");
            return;
        }

        graphEdges.sort(Comparator.comparingInt(edge -> Integer.parseInt(edge.getWeight())));

        UnionFind unionFind = new UnionFind(graphNodes.size());

        List<GraphEdge> mstEdges = new ArrayList<>();
        List<String> steps = new ArrayList<>();

        for (GraphEdge edge : graphEdges) {
            GraphNode startNode = edge.getStartGraphNode();
            GraphNode endNode = edge.getEndGraphNode();

            int startNodeIndex = startNode.getId();
            int endNodeIndex = endNode.getId();

            if (unionFind.find(startNodeIndex) != unionFind.find(endNodeIndex)) {
                unionFind.union(startNodeIndex, endNodeIndex);
                mstEdges.add(edge);
                steps.add("Added edge from Node " + startNodeIndex + " to Node " + endNodeIndex + " with weight " + edge.getWeight());
            }

            if (mstEdges.size() == graphNodes.size() - 1) {
                break;
            }
        }

        drawShortestPathGraph(mstEdges, steps);
    }

    private void drawShortestPathGraph(List<GraphEdge> mstEdges, List<String> steps) {
        Pane graphPane = new Pane();

        for (GraphEdge edge : mstEdges) {
            Line line = new Line();
            line.setStartX(edge.getLine().getStartX());
            line.setStartY(edge.getLine().getStartY());
            line.setEndX(edge.getLine().getEndX());
            line.setEndY(edge.getLine().getEndY());
            line.setStroke(javafx.scene.paint.Color.RED);
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

        Stage shortestPathStage = new Stage();
        shortestPathStage.setTitle("Minimum Spanning Tree (MST) Graph");
        shortestPathStage.setScene(new Scene(mainLayout, 1000, 600));
        shortestPathStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int p) {
            if (p != parent[p]) {
                parent[p] = find(parent[p]);
            }
            return parent[p];
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);

            if (rootP != rootQ) {
                if (rank[rootP] > rank[rootQ]) {
                    parent[rootQ] = rootP;
                } else if (rank[rootP] < rank[rootQ]) {
                    parent[rootP] = rootQ;
                } else {
                    parent[rootQ] = rootP;
                    rank[rootP]++;
                }
            }
        }
    }
}