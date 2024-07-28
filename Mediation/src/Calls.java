public class Calls {
    private String source;
    private String destination;
    private int duration;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Calls{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", duration=" + duration +
                '}';
    }

    public Calls(String source, String destination, int duration) {
        this.source = source;
        this.destination = destination;
        this.duration = duration;
    }
}
