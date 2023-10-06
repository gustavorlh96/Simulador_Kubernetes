import java.util.ArrayList;
import java.util.List;

public class WorkerNode {
    private String name;
    private int cpuCapacity;
    private int allocatedCpu;
    private int memoryCapacity;
    private int availableMemory;
    private int diskSpaceCapacity;
    private int availableDisk;
    private int networkLatency;
    private String role;
    private List<Pod> allocatedPods;

    public WorkerNode(String name, int cpuCapacity, int memoryCapacity, int diskSpaceCapacity, int networkLatency) {
        this.name = name;
        this.cpuCapacity = cpuCapacity;
        this.allocatedCpu = 0;
        this.memoryCapacity = memoryCapacity;
        this.availableMemory = memoryCapacity;
        this.diskSpaceCapacity = diskSpaceCapacity;
        this.availableDisk = diskSpaceCapacity;
        this.networkLatency = networkLatency;
        this.role = "Worker";
        this.allocatedPods = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCpuCapacity() {
        return cpuCapacity;
    }

    public int getAllocatedCpu() {
        return allocatedCpu;
    }

    public int getMemoryCapacity() {
        return memoryCapacity;
    }

    public int getAvailableMemory() {
        return availableMemory;
    }

    public int getDiskSpaceCapacity() {
        return diskSpaceCapacity;
    }

    public int getAvailableDisk() {
        return availableDisk;
    }

    public int getNetworkLatency() {
        return networkLatency;
    }

    public String getRole() {
        return role;
    }

    public List<Pod> getAllocatedPods() {
        return allocatedPods;
    }

    private boolean isLatencyAcceptable(int podNetworkLatency) {
        return Math.abs(networkLatency - podNetworkLatency) <= 20;
    }

    private boolean isPodAlreadyAllocated(Pod pod) {
        return allocatedPods.stream().anyMatch(p -> p.getName().equals(pod.getName()));
    }

    public synchronized boolean canAllocate(Pod pod) {
        boolean result = allocatedCpu <= pod.getCpuRequest() &&
                availableMemory >= pod.getMemoryRequest() &&
                availableDisk >= pod.getDiskSpaceRequest() &&
                isLatencyAcceptable(pod.getNetworkLatency()) &&
                !isPodAlreadyAllocated(pod);
        return result;
    }

    public synchronized void allocatePod(Pod pod) {
        if (canAllocate(pod)) {
            allocatedCpu += pod.getCpuRequest();
            availableMemory -= pod.getMemoryRequest();
            availableDisk -= pod.getDiskSpaceRequest();
            networkLatency += pod.getNetworkLatency();
            allocatedPods.add(pod);
        }
    }
}
