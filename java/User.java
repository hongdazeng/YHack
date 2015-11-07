public class User {
    private String name;
    private boolean isHelper;
    private int range;
    private String comment;

    public User(String name, boolean isHelper, int range, String comment) {
        this.name = name;
        this.isHelper = isHelper;
        this.range = range;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHelper() {
        return isHelper;
    }

    public void setHelper(boolean isHelper) {
        this.isHelper = isHelper;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}