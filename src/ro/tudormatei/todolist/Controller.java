package ro.tudormatei.todolist;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javafx.scene.paint.Color.*;

public class Controller {
    @FXML
    private Label timeLeft;

    @FXML
    private ListView<TodoItem> todoListView;

    @FXML
    private TextArea itemDescription;

    @FXML
    private BorderPane mainBorderpane;

    @FXML
    public void initialize() {
        todoListView.setFocusTraversable(false);

        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observableValue, TodoItem todoItem, TodoItem t1) {
                if(t1 != null){
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDescription.setText(item.getDescription());

                    Calendar rightNow = Calendar.getInstance();
                    int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                    int hoursLeftOfDay = 24 - hour;

                    String sufix;

                    if(hoursLeftOfDay == 1){
                        sufix = " hour";
                    }
                    else if(hoursLeftOfDay == 0){
                        sufix = "";
                    }
                    else{
                        sufix = " hours";
                    }
                    timeLeft.setText(String.valueOf(hoursLeftOfDay) + sufix);
                }
            }
        });

        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> todoItemListView) {
                ListCell<TodoItem> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(TodoItem item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }
                        else{
                            setText(item.getName());
                            setFont(new Font("Roboto Light", 20));
                            //setBorder(Border.EMPTY);
                            setTextFill(Color.rgb(255,250,243));
                        }
                        this.setStyle("-fx-control-inner-background: " + "#2f3136" + ";");
                        setPrefHeight(40);
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add new task");
        dialog.initOwner(mainBorderpane.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItem.fxml"));
        try{

            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        dialog.getDialogPane().setStyle("-fx-background-color: " + "#202225" + ";");

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = fxmlLoader.getController();
            TodoItem item = controller.processResult();

            todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
            todoListView.getSelectionModel().select(item);
        }
    }

    @FXML
    public void removeCurrentItem(){
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        TodoData.getInstance().getTodoItems().remove(item);
        todoListView.getItems().remove(item);

        TodoItem afterItem = todoListView.getSelectionModel().getSelectedItem();
        if(afterItem != null) {
            itemDescription.setText(afterItem.getDescription());
        }
        else{
            itemDescription.setText("");
        }
    }

    @FXML
    public void handleClickListView(){
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();

        itemDescription.setText(item.getDescription());
    }
}
