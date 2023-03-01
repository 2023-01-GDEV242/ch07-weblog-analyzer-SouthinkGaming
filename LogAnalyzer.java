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

    // Calculates the daily access counts
    private int[] dailyCounts;

    // Calculates the weekly access counts
    private int[] weeklyCounts;
    
    public LogAnalyzer(String name) {
        // This is an array object to hold the hourly access counts.
        hourCounts = new int[24];
        reader = new LogfileReader(name);
    }
    
    public static void main (String[] args) {
        LogfileCreator creator = new LogfileCreator();
        creator.createFile("entries.txt", 255);
        
        LogAnalyzer analyzer = new LogAnalyzer("entries.txt");
        analyzer.analyzeHourlyData();
        analyzer.printHourlyCounts();
    }

    /**
     * Creates an object to analyze hourly web accesses.
     */
    public LogAnalyzer() {
        // Create the array object to hold the hourly access counts.
        hourCounts = new int[24];
        
        // Creates the array to hold daily access counts
        dailyCounts = new int[366];
        
        // Creates the array to hold daily access counts
        weeklyCounts = new int[7];
        
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
    public int numberOfAccesses() {
        int total = 0;
        for (int i = 0; i < hourCounts.length; i++) {
            total += hourCounts[i];
        }
        return total;
    }
    
    public static void main () {
        LogfileCreator creator = new LogfileCreator();
        creator.createFile("entries.txt", 8);
        
        LogAnalyzer analyzer = new LogAnalyzer("entries.txt");
        analyzer.analyzeHourlyData();
        analyzer.printHourlyCounts();
        System.out.println(analyzer.numberOfAccesses());
    }

    /**
     * Keeps track of what hour is the busiest hour of a day or year
     */
    public int busiestHour() {
        int maxCount = 0;
        int busiestHour = 0;
    
        for(int i = 0; i < hourCounts.length; i++){
            if(hourCounts[i] > maxCount) {
                busiestHour = i;
                maxCount = hourCounts[i];
            }
        }
    
        return busiestHour;
    }

    /**
     * Keeps track of what hour is the quietest/least trafficed hour of a day or year
     */
    public int quietestHour() {
        int minCount = numberOfAccesses();
        int quietestHour = 0;
        
        for (int i = 0; i < hourCounts.length; i++){
            if (hourCounts[i] < minCount) {
                quietestHour = i;
                minCount = hourCounts[i];
            }
        }

        return quietestHour;
    }

    /**
     * Keeps track of the busiest 2 hours of a day or year
     */
    public int busiestTwoHours() {
        int maxCount = 0;
        int busiestTwoHours = 0;
        
        for(int i = 0; i < hourCounts.length/2; i++) {
            int hourPair = hourCounts[i * 2] + hourCounts[i * 2 + 1];
            
            if (hourPair > maxCount) {
                busiestTwoHours = i;
            }
        }

        return busiestTwoHours;
    }
    
    /**
     * The following method returns the month
     */
    public int getMonth(int MONTH) {
        return weeklyCounts[MONTH];
    }
    
    /**
     * The following method returns the day
     */
    public int getDay(int DAY) {
        return dailyCounts[DAY];
    }
    
    /**
     * Analyzes the dauly access of data from the log file
     */
    public void analyzeDailyData() {
        while(reader.hasNext()){
            LogEntry entry = reader.next();
            int day = getDay(366);
            dailyCounts[day]++;
        }
    }
    
    public int[] analyzeWeeklyPatterns() {
        // gets the sum of days in a week
        for(int i = 0; i < 52; i++){
            for(int j = 0; j < 7; j++) {
                weeklyCounts[j] += dailyCounts[i * 7 + j];
            }
        }

        weeklyCounts[0] += dailyCounts[364];
        weeklyCounts[1] += dailyCounts[365];
        return weeklyCounts;
    }
    
    public int buisestDay() {
        int maxCount = 0;
        int busiestDay = 0;

        for(int i = 0; i < weeklyCounts.length; i++){
            if(weeklyCounts[i] > maxCount){
                busiestDay = i;
            }
        }
        
        return busiestDay;
    }
    
    public int quietestDay() {
        int minCount = numberOfAccesses();
        int quietestDay = 0;
        
        for(int i = 0; i < weeklyCounts.length; i++){
            if(hourCounts[i] < minCount) {
                quietestDay = i;
                minCount = weeklyCounts[i];
            }
        }
        
        return quietestDay;
    }
}