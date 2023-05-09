import java.util.ArrayList;

public class Teacher {
    private final Journal journal;
    private final ArrayList<Group> groups;

    private PublishingGradesThread thread;

    public Teacher(Journal journal, ArrayList<Group> groups) {
        this.journal = journal;
        this.groups = groups;
    }

    public void publishingGrades()  {
        thread = new PublishingGradesThread(journal, groups);
        thread.start();
    }

    public void waitEndPublishingGrades(){
        try {
            thread.join();
        } catch (InterruptedException ignore){}
    }
}
