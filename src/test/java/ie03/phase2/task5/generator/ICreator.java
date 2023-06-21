package ie03.phase2.task5.generator;

import java.util.concurrent.ExecutionException;

public interface ICreator<String> {
    String getTestText() throws InterruptedException, ExecutionException;
}
