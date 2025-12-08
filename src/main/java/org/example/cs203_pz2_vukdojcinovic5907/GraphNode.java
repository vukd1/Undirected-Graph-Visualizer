package org.example.cs203_pz2_vukdojcinovic5907;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GraphNode {
    private Circle circle;
    private Text label;
    private int id;

    public GraphNode(Circle circle, Text label, int id) {
        this.circle = circle;
        this.label = label;
        this.id = id;
    }

    public Circle getCircle() {
        return circle;
    }

    public Text getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }
}




