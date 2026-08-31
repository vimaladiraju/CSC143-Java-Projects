import java.util.Scanner;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents a database of student online course experience records.
 * Provides functionality to parse data from a CSV file.
 * Manages distinct option sub-lists, retrieves individual records,
 * and calculates filtered performance statistics.
 * Implements OnlineCourseDatabaseInterface and supports iteration.
 */
public class OnlineCourseDatabase implements OnlineCourseDatabaseInterface, Iterable<CourseData> {
    /** Internal collection storing all student course experience records. */
    private final ArrayList<CourseData> courseData = new ArrayList<>();

    /** Sub-list containing experience level options. */
    private final ArrayList<String> experienceLevelList = new ArrayList<>(5);

    /** Sub-list containing course type options. */
    private final ArrayList<String> courseTypeList = new ArrayList<>(5);

    /** Sub-list containing platform options. */
    private final ArrayList<String> platformList = new ArrayList<>(5);

    /** Sub-list containing completion status options. */
    private final ArrayList<String> completionStatusList = new ArrayList<>(5);

    /** Sub-list containing dropout reason options. */
    private final ArrayList<String> dropoutReasonList = new ArrayList<>(5);


    /** Index that corresponds to experience level in each line. */
    private static final int EXPERIENCE_LEVEL_INDEX = 1;

    /** Index that corresponds to course type in each line. */
    private static final int COURSE_TYPE_INDEX = 2;

    /** Index that corresponds to platform in each line. */
    private static final int PLATFORM_INDEX = 3;

    /** Index that corresponds to completion status in each line. */
    private static final int COMPLETION_STATUS_INDEX = 6;

    /** Index that corresponds to dropout reason in each line. */
    private static final int DROPOUT_REASON_INDEX = 8;

    /**
     * Constructs an OnlineCourseDatabase instance by loading and parsing course data from a specified CSV file.
     * @param dbFile the CSV file containing student course records
     * @throws FileNotFoundException if the specified file cannot be found or opened
     * @throws IllegalArgumentException if the provided dbFile is null
     */

    public OnlineCourseDatabase(File dbFile) throws FileNotFoundException {
        // Round 1: build the option lists
        if (dbFile == null) {
            throw new IllegalArgumentException("File reference cannot be null");
        }

        try (Scanner scanner = new Scanner(dbFile)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");

                addIfMissing(experienceLevelList, fields[EXPERIENCE_LEVEL_INDEX]);
                addIfMissing(courseTypeList, fields[COURSE_TYPE_INDEX]);
                addIfMissing(platformList, fields[PLATFORM_INDEX]);
                addIfMissing(completionStatusList, fields[COMPLETION_STATUS_INDEX]);
                addIfMissing(dropoutReasonList, fields[DROPOUT_REASON_INDEX]);
            }
        }

        // Round 2: populate course data records
        try (Scanner scanner = new Scanner(dbFile)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                courseData.add(new CourseData(fields[0],
                        (byte) experienceLevelList.indexOf(fields[1]),
                        (byte) courseTypeList.indexOf(fields[2]),
                        (byte) platformList.indexOf(fields[3]),
                        Byte.parseByte(fields[4]),
                        Byte.parseByte(fields[5]),
                        (byte) completionStatusList.indexOf(fields[6]),
                        Byte.parseByte(fields[7]),
                        (byte) dropoutReasonList.indexOf(fields[8]),
                        Byte.parseByte(fields[9])
                        ));
            }
        }

    }

    /**
     * Adds a string value to a specified list if not already present.
     * @param list the list to which the element should be added
     * @param value the string value to add
     */
    private void addIfMissing(ArrayList<String> list, String value) {
        if (!list.contains(value)) {
            list.add(value);
        }
    }

    /**
     * Retrieves the course record at the specified index in the database.
     * @param index the index of the desired course record
     * @return the course record at the specified position
     * @throws IndexOutOfBoundsException when index is out of valid range
     */
    @Override
    public CourseData getCourseRecordAt(int index) {
        return courseData.get(index);
    }

    /**
     * Retrieves a course record with all option indices converted into descriptive strings.
     * @param course online course record to render
     * @return human-readable, complete string representation of the course record
     * @throws IllegalArgumentException if the provided course record is null
     */
    @Override
    public String getCourseRecordString(CourseData course) {
        if (course == null) throw new IllegalArgumentException("Course cannot be null");
        return String.format("[%s, %s, %s, %s, %d, %d, %s, %d, %s, %d]",
                course.userId(), experienceLevelList.get(course.experienceLevel()),
                courseTypeList.get(course.courseType()), platformList.get(course.platform()),
                course.hoursPerWeek(), course.courseDuration(),
                completionStatusList.get(course.completionStatus()),
                course.completionPercentage(), dropoutReasonList.get(course.dropoutReason()),
                course.satisfactionScore());
    }

    @Override
    public int size() {
        return courseData.size();
    }

    @Override
    public String[] getExperienceLevelOptions() {
        return experienceLevelList.toArray(new String[0]);
    }

    @Override
    public String[] getCourseTypeOptions() {
        return courseTypeList.toArray(new String[0]);
    }

    @Override
    public String[] getPlatformOptions() {
        return platformList.toArray(new String[0]);
    }

    @Override
    public String[] getCompletionStatusOptions() {
        return completionStatusList.toArray(new String[0]);
    }

    @Override
    public String[] getDropoutReasonOptions() {
        return dropoutReasonList.toArray(new String[0]);
    }

    /**
     * Calculates and returns statistics based on filtered record results.
     * @param experienceLevelIndex      index of the experience level to filter, or -1 for no filtering on this field
     * @param courseTypeIndex           index of the course type to filter, or -1 for no filtering on this field
     * @param platformIndex             index of the platform to filter, or -1 for no filtering on this field
     * @param completionStatusIndex     index of the completion status to filter, or -1 for no filtering on this field
     * @param dropoutReasonIndex        index of the dropout reason to filter, or -1 for no filtering on this field
     * @return statistics for the results of the filtering operation. If filtering results in no records, or an
     *         out-of-range index is used, returns a CourseStats object with a record count of 0 and other stats set to -1.0
     */
    @Override
    public CourseStats calcFilteredAverages(byte experienceLevelIndex, byte courseTypeIndex, byte platformIndex, byte completionStatusIndex, byte dropoutReasonIndex) {
        int count = 0;
        double totalHrs = 0;
        double totalDuration = 0;
        double totalCompletion = 0;
        double totalSatisfaction = 0;

        for (CourseData record : courseData) {
            //
            if (experienceLevelIndex != -1 && record.experienceLevel() != experienceLevelIndex) continue;
            if (courseTypeIndex != -1 && record.courseType() != courseTypeIndex) continue;
            if (platformIndex != -1 && record.platform() != platformIndex) continue;
            if (completionStatusIndex != -1 && record.completionStatus() != completionStatusIndex) continue;
            if (dropoutReasonIndex != -1 && record.dropoutReason() != dropoutReasonIndex) continue;

            count++;
            totalHrs += record.hoursPerWeek();
            totalDuration += record.courseDuration();
            totalCompletion += record.completionPercentage();
            totalSatisfaction += record.satisfactionScore();

        }

        if (count == 0) {
            return new CourseStats(0, -1.0, -1.0, -1.0, -1.0);
        }

        return new CourseStats(count, totalHrs / count, totalDuration / count, totalCompletion / count, totalSatisfaction / count);
    }

    @Override
    public Iterator<CourseData> iterator() {
        return courseData.iterator();
    }
}
