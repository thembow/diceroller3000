package diceRoller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author EKH
 */
public class DiceRoller3000 extends Application {

    int x = 0, o = 0;
    String out = "Output:\n---------------------------------------------------\n";
    String res;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Dice Roller 3000");
        Text text = new Text("Select Dice Size");
        text.setFill(Color.CADETBLUE);
        //basic setup

        Stage error = new Stage();
        error.initModality(Modality.APPLICATION_MODAL);
        error.initOwner(primaryStage);
        error.setTitle("Error!");
        VBox erBox = new VBox(new Label("Error! Please select a Dice Size!"));
        Scene erScene = new Scene(erBox);
        error.setScene(erScene);
        //error popup window for later

        int darr[] = {20, 12, 10, 8, 6, 100};
        ComboBox cBox = new ComboBox();
        cBox.getItems().add("d20");
        cBox.getItems().add("d12");
        cBox.getItems().add("d10");
        cBox.getItems().add("d8");
        cBox.getItems().add("d6");
        cBox.getItems().add("d100");
        //optionss
        cBox.setOnAction((event) -> {
            int sI = cBox.getSelectionModel().getSelectedIndex();
            x = darr[sI];
            //gets selected size and passes it to roll()
        });
        //cBox

        Text display = new Text(out);
        Button roll = new Button("Roll The Dice!");
        roll.setOnAction(actionEvent -> {
            if (x != 0) {
                //checks that a dice type was selected
                res = "d" + x + ": " + roll(x) + "\n";
                display.setText(display.getText() + "\n" + res);
            } else {
                error.showAndWait();
            }
        });
        //dice rolling button

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(display);
        //scrollpane

        Separator separator = new Separator(Orientation.HORIZONTAL);
        //seperator
        
        File tempFile = new File("logo.gif");
        InputStream in = this.getClass().getResourceAsStream("logo.gif");
        Files.copy(in, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        tempFile.deleteOnExit();
        /*FileInputStream requires an actual file to pull from.
        Unfortunately, .jar executables keep files as a sort of compressed data (InputStream in this case), and that doesn't work.
        So we create a temporary file and copy the data to that, which will self-delete once the program exits.
        */
        FileInputStream input = new FileInputStream("logo.gif");
        Image image = new Image(input);
        ImageView imgview = new ImageView(image);
        //cool logo setup

        VBox vbox = new VBox(imgview, text, cBox, roll, separator, scrollPane);
        Scene scene = new Scene(vbox, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        //final setup stuff
    }

    public static int roll(int r) {
        int n = (int) (Math.random() * (r - 1 + 1) + 1);
        //returns random number in range r
        return n;
    }
    public static void main(String[] args) {
        Application.launch(args);
    }

}
