package com.example;

import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class PrimaryController {
    Model model;
    int sizeOfV = 14;
    Timeline timeline = new Timeline();
    boolean isStart = true;
    final double delay = 250;
    GridPane grid = new GridPane();

    @FXML
    ScrollPane scrollpane;
    @FXML
    Button btnStartStop;
    @FXML
    TextField edtRule;
    @FXML
    HBox hbox;
    @FXML
    AnchorPane anchor;

    @FXML
    private void drawState(int j, Boolean[] r){
        for(int i=0;i<sizeOfV;i++){
            Pane cell = new Pane();
            if(r[i]){
                cell.setStyle("-fx-background-color: green;");
            }
            cell.setPrefWidth(39);
            cell.setPrefHeight(39);
            grid.add(cell, i, j);
        }
    }

    @FXML
    private void changeStateOfBtn(){
        if(isStart) btnStartStop.setText("Start");
        else btnStartStop.setText("Stop");
        isStart = !isStart;
    }

    @FXML
    private void onStartStop(ActionEvent event){

        if(isStart) {
            model.setRule(Integer.parseInt(edtRule.getText()));
            timeline.play();
        } 
        else timeline.stop();
        changeStateOfBtn();

        System.err.println("onStartStop");
    }

    @FXML
    public void initialize() {
        grid.setGridLinesVisible(true);
        grid.getRowConstraints().add(new RowConstraints());
        hbox.getChildren().add(grid);

        model = new Model(Integer.parseInt(edtRule.getText()));
        var r = model.getNowState();
        drawState(0, r);

        timeline.getKeyFrames().add(
            new KeyFrame(Duration.millis(delay), e->{
                var v = model.getNextState();
                drawState(model.getNumberOfState(), v);
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.stop();

        System.out.println("initialize()");
    }
}
