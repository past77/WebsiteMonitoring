package Model;

import java.util.HashMap;
import java.util.Iterator;

public class MonitoredUrls implements Iterable<Monitor>{

    private HashMap<String, Monitor> monitors;

    public MonitoredUrls(){
        monitors = new HashMap<>();
    }

    public boolean isEmpty(){
        return monitors.size() == 0;
    }

    public boolean contains(String url){
        return monitors.containsKey(url);
    }

    public void add(Monitor monitor){
        monitors.put(monitor.getMonitoredURL().getUrl(), monitor);
    }

    public void remove(String url){
        monitors.remove(url);
    }

    public Monitor get(String url){
        return monitors.get(url);
    }

    @Override
    public Iterator<Monitor> iterator() {
            return monitors.values().iterator();
        }

}
