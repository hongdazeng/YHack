public class Pair {
    private User inNeed;
    private User helper;
    
    public Pair(User inNeed, User helper) {
        this.inNeed = inNeed;
        this.helper = helper;
    }
    
    public User getInNeed() {
        return inNeed;
    }
    
    public User getHelper() {
        return helper;
    }
}