package main.java.FileFlow.models;

public class FileProcessResult {
    private String result;
    private long time;

    public FileProcessResult(String result, long time) {
        this.result = result;
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public long getTime() {
        return time;
    }


}
