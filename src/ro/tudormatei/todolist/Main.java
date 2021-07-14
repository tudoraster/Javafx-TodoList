package ro.tudormatei.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Daily Routine");
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.setResizable(false);

        Image icon = new Image("file:icon.png");
        primaryStage.getIcons().add(icon);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws IOException {
        try{
            TodoData.getInstance().storeTodoItem();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws IOException {
        try{
            TodoData.getInstance().loadTodoItems();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
