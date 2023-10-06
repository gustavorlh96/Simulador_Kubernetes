import java.util.List;
import java.util.Arrays;

public class Simulation {
    public static void main(String[] args) {

        List<WorkerNode> workerNodes = Arrays.asList(
                new WorkerNode("Delta", 4, 8192, 100000, 10),
                new WorkerNode("Charlie", 8, 16384, 200000, 15)
        );

        MasterNode masterNode = new MasterNode(workerNodes);

        List<Pod> pods = Arrays.asList(
                new Pod("Pod1", 2, 4096, 50000, 5),
                new Pod("Pod2", 4, 8192, 100000, 8),
                new Pod("Pod3", 1, 512, 1500, 5)
        );

        // Segura um momento pra primeira exibição dos workers sem os pods
        masterNode.displayStatus();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Pod pod : pods) {                                          // Inicia as threads pra
            new Thread(() -> masterNode.schedulePod(pod)).start();      // simular a execução concorrente
        }

        while (true) {
            masterNode.displayStatus();
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}