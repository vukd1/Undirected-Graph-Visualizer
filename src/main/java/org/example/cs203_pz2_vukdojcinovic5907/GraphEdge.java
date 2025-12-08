package org.example.cs203_pz2_vukdojcinovic5907;

import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class GraphEdge {
    private GraphNode startGraphNode;
    private GraphNode endGraphNode;
    private Line line;
    private String weight;
    private Text weightLabel;

    public GraphEdge(GraphNode startGraphNode, GraphNode endGraphNode, Line line, String weight, Text weightLabel) {
        this.startGraphNode = startGraphNode;
        this.endGraphNode = endGraphNode;
        this.line = line;
        this.weight = weight;
        this.weightLabel = weightLabel;
    }

    public GraphNode getStartGraphNode() {
        return startGraphNode;
    }

    public GraphNode getEndGraphNode() {
        return endGraphNode;
    }

    public Line getLine() {
        return line;
    }

    public String getWeight() {
        return weight;
    }

    public Text getWeightLabel() {
        return weightLabel;
    }
}






