/**
 * Read web server data & analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer {
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Creates an object to analyze hourly web accesses.
     */
    public LogAnalyzer() {
        // Create the array object to hold the hourly access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader("demo.log");
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData() {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Prints hourly counts.
     * These should have been set with a prior call to analyzeHourlyData.
     */
    public void printHourlyCounts() {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Prints the lines of data read by the LogfileReader
     */
    public void printData() {
        reader.printData();
    }

    /**
     * Accesses how many times the website has been accessed
     */
    public void numberOfAccesses() {
    }

    /**
     * Keeps track of what hour is the busiest hour of a day or year
     */
    public void busiestHour() {
    }

    /**
     * Keeps track of what hour is the quietest/least trafficed hour of a day or year
     */
    public void quietestHour() {
    }

    /**
     * Keeps track of the busiest 2 hours of a day or year
     */
    public void busiestTwoHours() {
    }
}