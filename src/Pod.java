public class Pod {
    private String name;
    private int cpuRequest;
    private int memoryRequest;
    private int diskSpaceRequest;
    private int networkLatency;

    public Pod(String name, int cpuRequest, int memoryRequest, int diskSpaceRequest, int networkLatency) {
        this.name = name;
        this.cpuRequest = cpuRequest;
        this.memoryRequest = memoryRequest;
        this.diskSpaceRequest = diskSpaceRequest;
        this.networkLatency = networkLatency;
    }

    public String getName() {
        return name;
    }

    public int getCpuRequest() {
        return cpuRequest;
    }

    public int getMemoryRequest() {
        return memoryRequest;
    }

    public int getDiskSpaceRequest() {
        return diskSpaceRequest;
    }

    public int getNetworkLatency() {
        return networkLatency;
    }
}