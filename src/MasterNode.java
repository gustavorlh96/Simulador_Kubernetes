import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class MasterNode {
    private List<WorkerNode> workerNodes;

    public MasterNode(List<WorkerNode> workerNodes) {
        this.workerNodes = workerNodes;
    }

    public void schedulePod(Pod pod) {
        workerNodes.sort(Comparator.comparingInt(WorkerNode::getNetworkLatency)); // Ordena os nodos de acordo
                                                                                  // com a latência
        for (WorkerNode workerNode : workerNodes) {
            if (workerNode.canAllocate(pod)) {
                workerNode.allocatePod(pod);
                System.out.println("\nScheduling pod " + pod.getName());
                System.out.println("\nPod " + pod.getName() + " scheduled to node " + workerNode.getName());
                return;
            }
        }
        System.out.println("\nFailed to schedule pod " + pod.getName());
    }

    public void displayStatus() {
        System.out.printf("\n%-15s%-30s%-30s%-30s%-15s\n", "NAME", "CPU(cores)", "MEMORY", "DISK", "ROLE");

        for (WorkerNode workerNode : workerNodes) {
            System.out.printf("%-15s%-30s%-30s%-30s%-15s\n",
                    workerNode.getName(),
                    String.format("[%d/%d]", workerNode.getAllocatedCpu(), workerNode.getCpuCapacity()),
                    String.format("[%d/%dMB]", workerNode.getAvailableMemory(), workerNode.getMemoryCapacity()),
                    String.format("[%d/%dMB]", workerNode.getAvailableDisk(), workerNode.getDiskSpaceCapacity()),
                    workerNode.getRole());
        }

        System.out.printf("\n%-15s%-15s%-15s%-15s%-15s\n", "NAME", "STATUS", "CPU", "DISK", "NODE");
        for (WorkerNode workerNode : workerNodes) {
            for (Pod pod : workerNode.getAllocatedPods()) {
                System.out.printf("%-15s%-15s%-15s%-15s%-15s\n",
                        pod.getName(),
                        "Running",
                        String.format("%d", pod.getCpuRequest()),
                        String.format("%dMB", pod.getDiskSpaceRequest()),
                        workerNode.getName());
            }
        }
    }
}