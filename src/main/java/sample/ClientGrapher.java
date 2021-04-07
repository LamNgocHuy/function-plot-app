package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientGrapher extends Application{
    String lb_css = "-fx-font: 20px \"Serif\"; -fx-text-fill: #333";
    String lb_xy = "-fx-font: 20px \"Segoe UI\"; -fx-text-fill: #ffffff; -fx-font-weight: 600; -fx-padding: 0,0,0,10 ";
    BorderPane appPane;
    GridPane settingPane;
    TextField txt_xMin,txt_xMax,txt_yMin,txt_yMax,txt_function;
    Label lb_xMin,lb_xMax,lb_yMin,lb_yMax,lb_function,lb_title,lb_x,lb_y;
    Button btn_plot;
    double xMin,xMax,yMax,yMin;
    Series series;
    LineChart<Number, Number> lineChart;
    Pane pane1;
    NumberAxis xAxis,yAxis;
    Handle handle;
    static ObjectOutputStream os = null;
    static ObjectInputStream is = null;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        appPane = new BorderPane();
        settingPane = new GridPane();
        settingPane.setStyle("-fx-background-color: #ffffff;");
        txt_function = new TextField();
        txt_xMin = new TextField();

        txt_xMax = new TextField();
        txt_yMin = new TextField();
        txt_yMax = new TextField();
        txt_xMin.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(txt_xMin.getText()!=null){
                    try{
                        xMin = Double.parseDouble(txt_xMin.getText().trim());
                        setLineChart(txt_function.getText(),xMin,xMax,yMin,yMax);
                    }catch (Exception e){
                        setLineChart("0",0,0,0,0);
                    }
                }
            }
        });
        txt_xMax.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(txt_xMax.getText()!=null){
                    try{
                        xMax = Double.parseDouble(txt_xMax.getText().trim());
                        setLineChart(txt_function.getText(),xMin,xMax,yMin,yMax);
                    }catch (Exception e){
                        setLineChart("0",0,0,0,0);
                    }

                }
            }
        });
        txt_yMin.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(txt_yMin.getText()!=null){
                    try{
                        yMin = Double.parseDouble(txt_yMin.getText().trim());
                        setLineChart(txt_function.getText(),xMin,xMax,yMin,yMax);
                    }catch (Exception e){
                        setLineChart("0",0,0,0,0);
                    }

                }
            }
        });
        txt_yMax.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(txt_yMax.getText()!=null){
                    try{
                        yMax = Double.parseDouble(txt_yMax.getText().trim());
                        setLineChart(txt_function.getText(),xMin,xMax,yMin,yMax);
                    }catch (Exception e){
                        setLineChart("0",0,0,0,0);
                    }

                }
            }
        });
        txt_function.setStyle(lb_css);
        txt_xMin.setStyle(lb_css);
        txt_xMax.setStyle(lb_css);
        txt_yMin.setStyle(lb_css);
        txt_yMax.setStyle(lb_css);
        txt_xMin.setPrefSize(258,35);
        txt_xMax.setPrefSize(258,35);
        txt_yMin.setPrefSize(258,35);
        txt_yMax.setPrefSize(258,35);
        txt_function.setPrefSize(258,35);
        lb_title = new Label("Plot Function Application");
        lb_title.setStyle("-fx-font-size: 20pt;\n" +
                "    -fx-font-family: \"Segoe UI Semibold\";\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-opacity: 0.6;");

        lb_function = new Label("Function");
        lb_xMin = new Label("xMin");
        lb_xMax = new Label("xMax");
        lb_yMin = new Label("yMin");
        lb_yMax = new Label("yMax");
        lb_x = new Label();
        lb_x.setStyle(lb_xy);
        lb_y = new Label();
        lb_y.setStyle(lb_xy);
        lb_function.setStyle(lb_css);
        lb_xMin.setStyle(lb_css);
        lb_xMax.setStyle(lb_css);
        lb_yMin.setStyle(lb_css);
        lb_yMax.setStyle(lb_css);
        btn_plot = new Button("Plot");
        btn_plot.setPrefSize(135,39);
        btn_plot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    xMin = Double.parseDouble(txt_xMin.getText().trim());
                    xMax = Double.parseDouble(txt_xMax.getText().trim());
                    yMin = Double.parseDouble(txt_yMin.getText().trim());
                    yMax = Double.parseDouble(txt_yMax.getText().trim());
                    setLineChart(txt_function.getText(),xMin,xMax,yMin,yMax);
                }catch (Exception e){
                    setLineChart("0",0,0,0,0);
                }
            }
        });
        btn_plot.setStyle("-fx-background-color: #d35400; -fx-font: 20px \"Serif\"; -fx-background-radius: 100; -fx-text-fill: white;");
        settingPane.add(lb_title,0,0,2,1);
        settingPane.add(lb_function,0,1);
        settingPane.add(lb_xMin,0,2);
        settingPane.add(lb_xMax,0,3);
        settingPane.add(lb_yMin,0,4);
        settingPane.add(lb_yMax,0,5);
        settingPane.add(txt_function,1,1);
        settingPane.add(txt_xMin,1,2);
        settingPane.add(txt_xMax,1,3);
        settingPane.add(txt_yMin,1,4);
        settingPane.add(txt_yMax,1,5);
        settingPane.add(btn_plot,1,6);
        settingPane.setHalignment(btn_plot,HPos.CENTER);
        settingPane.setHalignment(lb_title,HPos.CENTER);
        settingPane.setPadding(new Insets(40));
        settingPane.setHgap(30);
        settingPane.setVgap(30);
        settingPane.setPrefSize(440,560);
        settingPane.setStyle("-fx-background-radius: 10;-fx-background-color: #ffffff");

        pane1 = new Pane();
        HBox pane2 = new HBox();
        pane2.getChildren().addAll(lb_x,lb_y);

        appPane.setLeft(settingPane);
        appPane.setCenter(pane1);
        appPane.setTop(pane2);
        appPane.setPadding(new Insets(20));
        appPane.setStyle("-fx-background-color: #3498db");
        appPane.setMargin(pane2, new Insets(1,0,0,500));
        appPane.setMargin(pane1, new Insets(0,0,0,20));
        Scene scene = new Scene(appPane,1200,620);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void setLineChart(String fx, final double xMin, final double xMax, final double yMin, final double yMax){
        // Line Chart
        xAxis = new NumberAxis(xMin,xMax,(xMax-xMin)/5);
        xAxis.setStyle("-fx-stroke: #ffffff");
        yAxis = new NumberAxis(yMin,yMax,(yMax-yMin)/5);
        yAxis.setStyle("-fx-stroke: #ffffff");
        lineChart = new LineChart<Number, Number>(xAxis,yAxis);
        lineChart.setPrefSize(700,560);
        series = new Series();
        lineChart.setLegendVisible(false);
        lineChart.setCreateSymbols(false);
        pane1.getChildren().add(lineChart);
        lineChart.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 10;");
        List<Double> data_y = new ArrayList<>();
        data_y = sendServer();
        int i = 0;
        for(double x=xMin; x<=xMax; x+=0.01){
            series.getData().add(new XYChart.Data(x,data_y.get(i)));
            i++;
        }
        lineChart.getData().addAll(series);
        series.getNode().setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = ((xMax-xMin)*(event.getX()-2)/640)+xMin;
                lb_x.setText("X:"+String.valueOf((double) Math.ceil(x * 1000) / 1000));
                double y = ((yMax-yMin)*(event.getY()-0)/505)+yMin;
                lb_y.setText("Y:"+String.valueOf((double) -Math.ceil(y * 1000) / 1000));
            }
        });
    }
    public List<Double> sendServer(){
        Socket socketOfClient = null;
        List<Double> y_value = new ArrayList<Double>();
        try {
            socketOfClient = new Socket("localhost", 7777);
            os = new ObjectOutputStream(socketOfClient.getOutputStream());
            is = new ObjectInputStream(socketOfClient.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " );
            return null;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +"localhost");
            return null;
        }
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("function", txt_function.getText());
            try{
                map.put("xMin", Double.parseDouble(txt_xMin.getText()));
                map.put("xMax", Double.parseDouble(txt_xMax.getText()));
            }catch(Exception e){
                map.put("xMin", 0.0);
                map.put("xMax", 0.0);
            }
            os.writeObject(map);
            y_value = (List<Double>) is.readObject();
            os.close();
            is.close();
//            System.out.println("Server send: " + yAxis_value);
        } catch (UnknownHostException e) {
//            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
//            System.err.println("IOException:  " + e);
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        if(y_value==null) y_value.add(0.0);
        return y_value;
    }
}