import java.util.ArrayList;

public class JournalTest {
    public static void main(String[] args) {
        Group group1 = new Group( "IP-01", 20);
        Group group2 = new Group("IP-02", 30);
        Group group3 = new Group("IP-03", 25);

        Journal journal = new Journal();
        journal.addGroup(group1);
        journal.addGroup(group2);
        journal.addGroup(group3);

        ArrayList<Group> groupsLecturer = new ArrayList<>();
        groupsLecturer.add(group1);
        groupsLecturer.add(group2);
        groupsLecturer.add(group3);
        Teacher lecturer = new Teacher(journal, groupsLecturer);

        ArrayList<Group> groupsAssistant1 = new ArrayList<>();
        groupsAssistant1.add(group1);
        Teacher assistant1 = new Teacher(journal, groupsAssistant1);

        ArrayList<Group> groupsAssistant2 = new ArrayList<>();
        groupsAssistant2.add(group2);
        Teacher assistant2 = new Teacher(journal, groupsAssistant2);

        ArrayList<Group> groupsAssistant3 = new ArrayList<>();
        groupsAssistant3.add(group3);
        Teacher assistant3 = new Teacher(journal, groupsAssistant3);

        for (int i = 0; i < 2; i++) {
            lecturer.publishingGrades();
            assistant1.publishingGrades();
            assistant2.publishingGrades();
            assistant3.publishingGrades();

            lecturer.waitEndPublishingGrades();
            assistant1.waitEndPublishingGrades();
            assistant2.waitEndPublishingGrades();
            assistant3.waitEndPublishingGrades();
        }

        journal.print();
    }
}