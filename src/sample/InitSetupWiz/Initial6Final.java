package sample.InitSetupWiz;



        import javafx.animation.FadeTransition;
        import javafx.animation.SequentialTransition;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.control.*;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.BorderPane;
        import javafx.stage.Modality;
        import sample.PropertiesConfiguration.PropertyConfigs;
        import sample.Transitions.Fade;
        import sample.sAlearts;

        import java.io.File;
        import java.io.IOException;

public class Initial6Final {

    @FXML
    private BorderPane BPWhole;

    @FXML
    private TextField SchemaField;

    @FXML
    private Label TableName;

    @FXML
    private Label HeadLab;

    @FXML
    private TextField TableField;

    @FXML
    private Label SchemaLab;
private static  boolean main=false;
    @FXML
    void OnSubmit() throws IOException {
        File file = new File("Resources/PropertiesMain/Gamma.properties");
        if(!file.exists())
            file.createNewFile();

        if (!main) {

            if (SchemaField.getText().isEmpty() || TableField.getText().isEmpty()) {
                Alert a = sAlearts.aleart(Alert.AlertType.ERROR, null, "Both Fields must be filled",
                        "Fill the fields to continew", Modality.APPLICATION_MODAL);
                a.show();
            } else {
                main=true;
                PropertyConfigs.SetProperty("SchemaName", SchemaField.getText(),
                        "Resources/PropertiesMain/Alpha.properties", null, null);
                PropertyConfigs.SetProperty("TableName", TableField.getText(),
                        "Resources/PropertiesMain/Alpha.properties", null, null);
                FadeTransition fadeTransition = Fade.fade(1.0, 1.0, 0.0, BPWhole, null);

                SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
                sequentialTransition.play();

                sequentialTransition.setOnFinished(e -> {
                    FadeTransition fadeTransition1 = Fade.fade(1.0, 0.0, 1.0, BPWhole, null);
                    HeadLab.setText("Would You like to integrate reference Database? leave the fields empty if you don't");
                    SchemaLab.setText("Provide Reference Schema if any");
                    TableName.setText("Provide The reference table name if any");
                    SchemaField.clear();
                    TableField.clear();
                    SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition1);
                    sequentialTransition1.play();
                });


            }

        }





        else {

            if (TableField.getText().isEmpty()||TableField.equals(null)){
                FadeTransition fadeTransition = Fade.fade(1.0, 1.0, 0.0, BPWhole, null);





                Alert alert = sAlearts.aleart(Alert.AlertType.CONFIRMATION,null, "Are you sure",
                        "if you dont want to add a reference database feature press ok else cancel", Modality.APPLICATION_MODAL);

                alert.showAndWait();

                if (alert.getResult().equals(ButtonType.OK)){
                    PropertyConfigs.SetProperty("ReferenceDB","false","Resources/PropertiesMain/Gamma.properties"
                            ,null,null);
                    SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
                    sequentialTransition.play();
                    sequentialTransition.setOnFinished(e -> loadAnPane());
                }





        }

        else if ((SchemaField.getText().isEmpty()||SchemaField.getText().equals(""))&&!TableField.getText().isEmpty()&&!TableField.getText().equals(null)){
                String TableRef = TableField.getText();
                PropertyConfigs.SetProperty("ReferenceDB","true","Resources/PropertiesMain/Gamma.properties"
                        ,null,null);
                PropertyConfigs.SetProperty("ReferenceDBSchema","void","Resources/PropertiesMain/Gamma.properties",null,null);
                PropertyConfigs.SetProperty("ReferenceDBtable",TableRef,"Resources/PropertiesMain/Gamma.properties",null,null);



                FadeTransition fadeTransition = Fade.fade(1.0, 1.0, 0.0, BPWhole, null);
                SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
                sequentialTransition.play();
                sequentialTransition.setOnFinished(e -> loadAnPane());

            }

            else if (!SchemaField.getText().isEmpty()&&!SchemaField.getText().equals(null)&&!TableField.getText().isEmpty()&&!TableField.getText().equals(null)){
                String SchemaRef = SchemaField.getText();
                String TableRef = TableField.getText();
                PropertyConfigs.SetProperty("ReferenceDB","true","Resources/PropertiesMain/Gamma.properties"
                        ,null,null);
                PropertyConfigs.SetProperty("ReferenceDBSchema",SchemaRef,"Resources/PropertiesMain/Gamma.properties",null,null);
                PropertyConfigs.SetProperty("ReferenceDBtable",TableRef,"Resources/PropertiesMain/Gamma.properties",null,null);



                FadeTransition fadeTransition = Fade.fade(1.0, 1.0, 0.0, BPWhole, null);
                SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
                sequentialTransition.play();
                sequentialTransition.setOnFinished(e -> loadAnPane());

            }

            }


        }

        private void loadAnPane(){
            AnchorPane anchorPane = null;
            try {
                anchorPane = FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/InitialOver.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            BPWhole.getChildren().setAll(anchorPane);

            FadeTransition fadeTransition1 = Fade.fade(1.0, 0.0, 1.0, BPWhole, null);
            SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition1);
            sequentialTransition1.play();

        }


    }

//sequentialTransition.setOnFinished(e -> {
//
//        AnchorPane anchorPane = null;
//        try {
//        anchorPane = FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/InitialOver.fxml"));
//        } catch (IOException e1) {
//        e1.printStackTrace();
//        }
//        BPWhole.getChildren().setAll(anchorPane);
//
//        FadeTransition fadeTransition1 = Fade.fade(1.0, 0.0, 1.0, BPWhole, null);
//        SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition1);
//        sequentialTransition1.play();
//        });
//
//
//        }