package org.example.cs203_pz2_vukdojcinovic5907;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #809BCE;");

        GraphPane graphPane = new GraphPane();
        root.setCenter(graphPane);

        Button btnAddNode = new Button("Add Node");
        Button btnAddEdge = new Button("Add Edge");
        Button btnAbort = new Button("Abort");
        Button btnHighlight = new Button("Highlight");
        Button btnShortestPath = new Button("Find Shortest Path");
        Button btnBfs = new Button("BFS");
        Button btnDfs = new Button("DFS");

        btnAbort.setDisable(true);
        btnHighlight.setDisable(true);

        btnAddNode.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
        btnAddEdge.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
        btnAbort.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
        btnHighlight.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
        btnShortestPath.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
        btnBfs.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
        btnDfs.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");

        btnAddNode.setOnAction(e -> {
            graphPane.setMode(GraphPane.Mode.ADD_NODE);
            btnAddNode.setStyle("-fx-background-color: #D6EADF; -fx-text-fill: #28292B;");
            btnAddEdge.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnHighlight.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnShortestPath.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnBfs.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnDfs.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnAbort.setDisable(true);
        });

        btnAddEdge.setOnAction(e -> {
            graphPane.setMode(GraphPane.Mode.ADD_EDGE);
            btnAddEdge.setStyle("-fx-background-color: #D6EADF; -fx-text-fill: #28292B;");
            btnAddNode.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnHighlight.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnShortestPath.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnBfs.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnDfs.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnAbort.setDisable(false);
        });

        btnHighlight.setOnAction(e -> {
            graphPane.setMode(GraphPane.Mode.HIGHLIGHT);
            btnHighlight.setStyle("-fx-background-color: #D6EADF; -fx-text-fill: #28292B;");
            btnAddNode.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnAddEdge.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnShortestPath.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnBfs.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnDfs.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnAbort.setDisable(true);
        });

        btnShortestPath.setOnAction(e -> {
            MstGraph mstGraph = new MstGraph(graphPane.getGraphNodes(), graphPane.getGraphEdges());
            mstGraph.findAndDrawShortestPath();
        });

        btnBfs.setOnAction(e -> {
            BfsGraph bfsGraph = new BfsGraph(graphPane.getGraphNodes(), graphPane.getGraphEdges());
            bfsGraph.performBfsAndDrawGraph();
        });

        btnDfs.setOnAction(e -> {
            DfsGraph dfsGraph = new DfsGraph(graphPane.getGraphNodes(), graphPane.getGraphEdges());
            dfsGraph.performDfsAndDrawGraph();
        });

        btnAbort.setOnAction(e -> {
            graphPane.abortEdgeCreation();
            btnAddEdge.setStyle("-fx-background-color: #B8E0D2; -fx-text-fill: #28292B;");
            btnAbort.setDisable(true);
            showAlert("Operation Aborted", "Edge creation has been aborted.");
        });

        graphPane.setHighlightButton(btnHighlight);

        HBox topMenu = new HBox(10, btnAddNode, btnAddEdge, btnAbort, btnHighlight, btnShortestPath, btnBfs, btnDfs);
        topMenu.setStyle("-fx-alignment: center; -fx-padding: 10;");
        root.setTop(topMenu);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Graph Drawing Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}