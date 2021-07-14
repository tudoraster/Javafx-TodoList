package ro.tudormatei.todolist;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DialogController {
    @FXML
    private TextArea name;

    @FXML
    private TextArea desc;

    @FXML
    public TodoItem processResult(){
        String itemName = name.getText().trim();
        String itemDescription = desc.getText().trim();

        TodoItem item = new TodoItem(itemName, itemDescription);
        TodoData.getInstance().addTodoItem(item);

        return item;
    }
}
