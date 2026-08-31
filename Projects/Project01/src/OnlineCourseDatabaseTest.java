import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OnlineCourseDatabaseTest {
    private OnlineCourseDatabase db;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        db = new OnlineCourseDatabase(new File("OnlineCourseDataset.csv"));;
    }

    @Test
    void sizeReturnsCorrectRecordCount() {
        int result = db.size();
        assertEquals(500, result);
    }

    @Test
    void getCourseRecordAtReturnsCorrectRecordForValidIndex() {
        CourseData record = db.getCourseRecordAt(0);
        assertEquals("U0001", record.userId());
    }

    @Test
    void getCourseRecordAtThrowsForInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> db.getCourseRecordAt(-1));
    }

    @Test
    void getCourseRecordStringReturnsResult() {
        CourseData record = db.getCourseRecordAt(0);
        String result = db.getCourseRecordString(record);
        assertEquals("[U0001, Fresher, Tech, Skillshare, 16, 8, Completed, 67, No Dropout, 2]", result);
    }

    @Test
    void getCourseRecordStringThrowsForNull() {
        assertThrows(IllegalArgumentException.class, () -> db.getCourseRecordString(null));
    }

    @Test
    void constructorThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> new OnlineCourseDatabase(null));
    }

    @Test
    void constructorThrowsWhenNonexistent() {
        assertThrows(FileNotFoundException.class, () -> new OnlineCourseDatabase(new File("thisfiledoesnotexist.csv")));
    }

    @Test
    void getExperienceLevelOptionsReturnsCorrectValues() {
        String[] expected = {"Fresher", "Student", "Working Professional"};
        String[] result = db.getExperienceLevelOptions();
        assertArrayEquals(expected, result);
    }

    @Test
    void getCourseTypeOptionsReturnsCorrectValues() {
        String[] expected = {"Tech", "Non-Tech"};
        String[] result = db.getCourseTypeOptions();
        assertArrayEquals(expected, result);
    }

    @Test
    void getPlatformOptionsReturnsCorrectValues() {
        String[] expected = {"Skillshare", "edX", "Coursera", "Udemy", "YouTube"};
        String[] result = db.getPlatformOptions();
        assertArrayEquals(expected, result);
    }

    @Test
    void getCompletionStatusOptionsReturnsCorrectValues() {
        String[] expected = {"Completed", "Dropped", "In Progress"};
        String[] result = db.getCompletionStatusOptions();
        assertArrayEquals(expected, result);
    }

    @Test
    void getDropoutReasonOptionsReturnsCorrectValues() {
        String[] expected = {"No Dropout", "Time Constraint", "Too Difficult", "Lost Interest"};
        String[] result = db.getDropoutReasonOptions();
        assertArrayEquals(expected, result);
    }

    @Test
    void calcFilteredAveragesWithNoFiltersReturnsOverallStats() {
        CourseStats stats = db.calcFilteredAverages((byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1);
        assertEquals(500, stats.recordCount());
        assertEquals(10.56, stats.avgHrsPerWeek(), 0.01);
        assertEquals(13.26, stats.avgCourseDuration(), 0.01);
        assertEquals(50.48, stats.avgCompletionPercent(), 0.01);
        assertEquals(3.02, stats.avgSatisfactionScore(), 0.01);
    }

    @Test
    void calcFilteredAveragesWithOneFilterReturnsRespectiveStats() {
        CourseStats stats = db.calcFilteredAverages((byte) 0, (byte) -1, (byte) -1, (byte) -1, (byte) -1);
        assertEquals(171, stats.recordCount());
        assertEquals(10.4, stats.avgHrsPerWeek(), 0.01);
        assertEquals(12.5, stats.avgCourseDuration(), 0.01);
        assertEquals(50.7, stats.avgCompletionPercent(), 0.10);
        assertEquals(3.08, stats.avgSatisfactionScore(), 0.01);

    }

    @Test
    void calcFilteredAveragesWithZeroMatches() {
        CourseStats stats = db.calcFilteredAverages((byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1);
        assertEquals(0, stats.recordCount());
        assertEquals(-1.0, stats.avgHrsPerWeek(), 0.01);
        assertEquals(-1.0, stats.avgCourseDuration(), 0.01);
        assertEquals(-1.0, stats.avgCompletionPercent(), 0.01);
        assertEquals(-1.0, stats.avgSatisfactionScore(), 0.01);

    }

    @Test
    void iteratorIteratesAllRecords() {
        int count = 0;
        for (CourseData record : db) {
            count++;
        }
        assertEquals(db.size(), count);
    }

}