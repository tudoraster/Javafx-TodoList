package ro.tudormatei.todolist;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class TodoData {
    private static TodoData instance = new TodoData();
    private static String fileName = "TodoListItems.txt";
    private List<TodoItem> todoItems;

    public static TodoData getInstance(){
        return instance;
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void loadTodoItems() throws IOException {
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);

        String input;

        try(BufferedReader br = Files.newBufferedReader(path)){
            while((input = br.readLine()) != null){
                String[] itemPieces = input.split("\t");

                String name = itemPieces[0];
                String description = itemPieces[1];

                TodoItem item = new TodoItem(name, description);
                todoItems.add(item);
            }
        }
    }

    public void storeTodoItem() throws IOException {
        Path path = Paths.get(fileName);
        try(BufferedWriter bw = Files.newBufferedWriter(path)){
            Iterator<TodoItem> iter = todoItems.iterator();
            while(iter.hasNext()){
                TodoItem item = iter.next();
                bw.write(String.format("%s\t%s\t", item.getName(), item.getDescription()));
                bw.newLine();
            }
        }
    }

    public void addTodoItem(TodoItem item){
        todoItems.add(item);
    }
}
