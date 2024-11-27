package main.java.FileFlow;

import main.java.FileFlow.controllers.FileProcessorController;
import main.java.FileFlow.views.FileProcessorView;

public class Main {
    public static void main(String[] args) {

        String path = "C:\\NULP\\Lab4\\src\\main\\resources\\files";

        FileProcessorView view = new FileProcessorView();
        new FileProcessorController(view, path);
        view.show();
    }
}
