/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nsecalculator;

import java.io.File;
import static java.lang.Math.pow;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author John
 */
public class NSECalculator extends Application {
    private Label lblTitle = new Label("Net Surplus Energy\nCalculator:\n"
            + "---------------------------");
    private static int  worldNSE = 0;
    private static int  DNSE = 0;
    private double worldEnergyConsumption = 1.75 * pow(10,13);
    private double intVals[][] = {{.32, 20},
            {.2, 10}, {.28, 50}, {.05, 10},
            {.05, 7}, {.025, 12}};
    private ObservableList<String> options = 
    FXCollections.observableArrayList(
        "Crude Oil",
        "Natural Gas",
        "Coal",
        "BioFuels",
        "Nuclear Power",
        "Hydro Electric",
        "Solar Photovoltaic",
        "Wind",
        "Geothermal",
        "Biodiesel",
        "SugarCaneEthanol"
    );
    
    private final ComboBox comboBox = new ComboBox(options);
    
    private int getIndex(){
        int i = -1;
        String  temp = comboBox.getValue().toString();
        if (temp.equals("Crude Oil")) i = 0;
            else if (temp.equals("Natural Gas")) i = 1;
            else if (temp.equals("Coal")) i = 2;
            else if (temp.equals("BioFuels")) i = 3;
            else if (temp.equals("Nuclear Power"))i= 4;
            else if (temp.equals("Hydro Electric")) i = 5;
            else ;
        return i;
    }
    private String ED(double perc){
        double val = 0;
        
        val = 17.5 * pow(10, 12) * perc;
        
        return String.format("%12.3E", val);
    }
    
    private String NSE(int er , double ed){
        return String.format("%12.3E",((er-1)*ed)/er);
    }
    
    private String ESP (double nse, int er){
        return String.format("%12.3E",nse/(er-1));
    }
    private HBox addRow(String text, int er, double perc, String color){
        HBox groupBox2 = new HBox();
            Label lblone = new Label(text);
            lblone.setStyle("-fx-border-color: black;"
                    + "-fx-background-color: " + color + ";"
                    + "-fx-text-fill: white;");
            lblone.setMinWidth(100); lblone.setMaxWidth(100);
            
            Label lblED = new Label();
            lblED.setStyle("-fx-border-color: black;" + 
            "-fx-background-color: white;");
            lblED.setMinWidth(100); lblED.setMaxWidth(100);
            lblED.setText(ED(perc));
            
            Label lblEsp = new Label();
            lblEsp.setStyle("-fx-border-color: black;"+ 
            "-fx-background-color: white;");
            double temped = 17.5 * pow(10, 12) * perc;
            lblEsp.setMinWidth(100); lblEsp.setMaxWidth(100);
            lblEsp.setText(ESP(((er-1)*temped)/er, er));
            
            Label lblNSE = new Label();
            lblNSE.setStyle("-fx-border-color: black;"+ 
            "-fx-background-color: white;");
            lblNSE.setMinWidth(100); lblNSE.setMaxWidth(100);
            lblNSE.setText(NSE(er, temped));
            groupBox2.getChildren().addAll(lblone, lblED, lblEsp, lblNSE);
            
            return groupBox2;
    }
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        
        
        Label lblWEC = new Label("World Energy Consumption");
        HBox firstBox = new HBox();
        TextField txtWEC = new TextField("1.75E+12");
        Label lblWY = new Label("Wy");
        firstBox.getChildren().addAll(txtWEC, lblWY);
        
        Label lblTotalP = new Label("Total %:");
        HBox secondBox = new HBox();
        TextField txtTotalP = new TextField();
        Label lblP = new Label("%");
        secondBox.getChildren().addAll(txtTotalP, lblP);
        
        Label lblEROEI = new Label("EROEI:");
        HBox thirdBox = new HBox();
        TextField txtEROEI = new TextField();
        txtTotalP.setText("32");
        txtEROEI.setText("20");
        txtEROEI.setMaxWidth(150);
        
        comboBox.getSelectionModel().select(0);
        Button btnSub = new Button("Submit:");
   
        
        VBox leftBox = new VBox();
        leftBox.getChildren().addAll(lblTitle, lblWEC, comboBox, firstBox, lblTotalP,
                secondBox, lblEROEI, txtEROEI, btnSub);
        leftBox.setTranslateX(60);
        StackPane root = new StackPane();
        root.getChildren().add(leftBox);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            String  temp = comboBox.getValue().toString();
            if (temp.equals("Crude Oil")){
                txtTotalP.setText("32");
                txtEROEI.setText("20");
            }
            else if (temp.equals("Natural Gas")){
                txtTotalP.setText("20");
                txtEROEI.setText("10");
            }
            else if (temp.equals("Coal")){
                txtTotalP.setText("28");
                txtEROEI.setText("50");
            }
            else if (temp.equals("BioFuels")){
                txtTotalP.setText("10");
                txtEROEI.setText("5");
            }
            else if (temp.equals("Nuclear Power")){
                txtTotalP.setText("5");
                txtEROEI.setText("7");
            }
            else if (temp.equals("Hydro Electric")){
                txtTotalP.setText("2.5");
                txtEROEI.setText("12");
            }
            
            else if (temp.equals("Solar Photovoltaic")){
                txtTotalP.setText("100");
                txtEROEI.setText("6");
            }
            else if (temp.equals("Wind")){
                txtTotalP.setText("100");
                txtEROEI.setText("18");
            }
            else if (temp.equals("Geothermal")){
                txtTotalP.setText("100");
                txtEROEI.setText("10");
            }
            else if (temp.equals("Wind")){
                txtTotalP.setText("18");
                txtEROEI.setText("5");
            } else {
                txtTotalP.setText("100");
                txtEROEI.setText("8");
            }
            }
        });
        
        txtWEC.textProperty().addListener((observable, oldValue, newValue) -> {
            worldEnergyConsumption = Integer.parseInt(txtWEC.getText());
        });
      
        txtTotalP.textProperty().addListener((observable, oldValue, newValue) -> {
            int j = getIndex();
            
            if( j!= -1)
            intVals[j][0] = Integer.parseInt(txtTotalP.getText())/100.0;
        });
        txtEROEI.textProperty().addListener((observable, oldValue, newValue) -> {
            int j = getIndex();
            
            if( j!= -1)
            intVals[j][1] = Integer.parseInt(txtEROEI.getText());
        });
        
        btnSub.setOnAction((ActionEvent event) -> {
            VBox parentBox = new VBox();
            HBox groupBox = new HBox();
            Label lblFill = new Label("");
            lblFill.setStyle("-fx-border-color: black;"
                    + "-fx-background-color: green;"
                    + "-fx-text-fill: white;");
            lblFill.setMinWidth(100); lblFill.setMaxWidth(100);
            Label lblED = new Label("ED");
            lblED.setStyle("-fx-border-color: black;"
                    + "-fx-background-color: purple;"
                    + "-fx-text-fill: white;");
            
            
            lblED.setMinWidth(100); lblED.setMaxWidth(100);
            Label lblEsp = new Label("Esp");
            lblEsp.setStyle("-fx-border-color: black;"
                    + "-fx-background-color: green;"
                    + "-fx-text-fill: white;");
            lblEsp.setMinWidth(100); lblEsp.setMaxWidth(100);
            Label lblNSE = new Label("NSE");
            lblNSE.setStyle("-fx-border-color: black;"
                    + "-fx-background-color: red;"
                    + "-fx-text-fill: white;");
            lblNSE.setMinWidth(100); lblNSE.setMaxWidth(100);
            groupBox.getChildren().addAll(lblFill, lblED, lblEsp, lblNSE);
            
            
            parentBox.getChildren().add(groupBox);
            parentBox.getChildren().add(addRow("Crude Oil", (int)intVals[0][1], intVals[0][0], "grey"));
            double temped = 17.5 * intVals[0][0];
            worldNSE += (((((int)intVals[0][1])-1)*temped)/((int)intVals[0][1]));
            parentBox.getChildren().add(addRow("Natural Gas",(int)intVals[1][1], intVals[1][0], "yellow"));
            temped = 17.5 * intVals[1][0];
            worldNSE += (((((int)intVals[1][1])-1)*temped)/(intVals[1][1]));
            parentBox.getChildren().add(addRow("Coal", (int)intVals[2][1], intVals[2][0], "black"));
            temped = 17.5  * intVals[2][0];
            worldNSE += (((((int)intVals[2][1])-1)*temped)/(intVals[2][1]));
            parentBox.getChildren().add(addRow("Bio-fuels", (int)intVals[3][1], intVals[3][0], "lightgreen"));
            
            
            
            temped = 17.5  * intVals[3][0];
            worldNSE += (((((int)intVals[3][1])-1)*temped)/(intVals[3][1]));
            parentBox.getChildren().add(addRow("Nuclear",(int)intVals[4][1], intVals[4][0], "orange"));
            temped = 17.5 * intVals[4][0];
            worldNSE += (((((int)intVals[4][1])-1)*temped)/(intVals[4][1]));
            parentBox.getChildren().add(addRow("Hydro Electric", (int)intVals[5][1], intVals[5][0], "blue"));
            temped = 17.5  * intVals[5][0];
            worldNSE += (((((int)intVals[5][1])-1)*temped)/(intVals[5][1]));
            Label lblbc = new Label("B) & C) ");
            lblbc.setStyle("-fx-border-color: blue;"
                    + "-fx-background-color: lightblue;"
                    + "-fx-text-fill: white;");
            lblbc.setMinWidth(400); lblbc.setMaxWidth(400);
        
            
            
            
            parentBox.getChildren().addAll(lblbc);
            parentBox.getChildren().add(addRow("Solar Photovoltaic", 6, 1, "yellow"));
            temped = 17.5  * .025;
            worldNSE += (((6-1)*temped)/6);
            parentBox.getChildren().add(addRow("Geothermal", 10, 1, "darkred"));
            parentBox.getChildren().add(addRow("Wind", 18, 1, "lightblue"));
            parentBox.getChildren().add(addRow("Biodiseal", 18, 1, "lightgreen"));
            parentBox.getChildren().add(addRow("Sugarcane Ethanol", 5, 1, "limegreen"));
            
            Label lblTNSE = new Label("World NSE");
            lblTNSE.setStyle("-fx-border-color: green;"
                    + "-fx-background-color: lightgreen;"
                    + "-fx-text-fill: white;");
            lblTNSE.setMinWidth(400); lblTNSE.setMaxWidth(400);
            parentBox.getChildren().addAll(lblTNSE);
            
            NumberFormat formatter = new DecimalFormat();
            formatter = new DecimalFormat("0.###E0");
            Label lblwns = new Label(formatter.format(worldNSE * pow(10, 12)));
            lblwns.setStyle("-fx-border-color: black;"+ 
            "-fx-background-color: white;");
            lblwns.setMinWidth(400); lblwns.setMaxWidth(400);
            parentBox.getChildren().addAll(lblwns);
            
            
            Label lblDNSE = new Label("D) NSE");
            lblDNSE.setStyle("-fx-border-color: red;"
                    + "-fx-background-color: red;"
                    + "-fx-text-fill: white;");
            lblDNSE.setMinWidth(400); lblDNSE.setMaxWidth(400);
            parentBox.getChildren().addAll(lblDNSE);
            temped = 17.5 * .3;
            DNSE += (((18-1)*temped)/18);
            temped = 17.5 * .3;
            DNSE += (((6-1)*temped)/6);
            temped = 17.5 * .2;
            DNSE += (((5-1)*temped)/5);
            temped = 17.5 * .1;
            DNSE += (((10-1)*temped)/10);
            temped = 17.5 * .1;
            DNSE += (((8-1)*temped)/8);
            
            Label lbldns = new Label(formatter.format(DNSE * pow(10, 12)));
            lbldns.setStyle("-fx-border-color: black;"+ 
            "-fx-background-color: white;");
            lbldns.setMinWidth(400); lbldns.setMaxWidth(400);
            parentBox.getChildren().addAll(lbldns);
            
            Stage ps = new Stage();
            StackPane root2 = new StackPane();
            root2.getChildren().add(parentBox);
        
            Scene scene2 = new Scene(root2, 400, 320);
        
            ps.setTitle("Figure 1");
            ps.setScene(scene2);
            ps.show();SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(javafx.scene.paint.Color.TRANSPARENT);
            WritableImage image = parentBox.snapshot(parameters, null);

            File file = new File("figure1.png");

    
            try{ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);}
            catch(Exception e){};
    
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
