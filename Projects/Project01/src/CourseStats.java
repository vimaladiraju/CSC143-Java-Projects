/**
 * Record that stores statistics about selected user experiences. If recordCount equals 0, then all four averages will be set to -1.0 instead of being computed
 * @param recordCount Total count of applicable records that match criteria
 * @param avgHrsPerWeek Average hours spent by students per week on a course that match criteria
 * @param avgCourseDuration Average course duration for experiences that match criteria
 * @param avgCompletionPercent Average completion percent of students that match criteria
 * @param avgSatisfactionScore Average satisfaction score for students that match criteria
 */

public record CourseStats (
    int recordCount,
    double avgHrsPerWeek,
    double avgCourseDuration,
    double avgCompletionPercent,
    double avgSatisfactionScore
) {
    @Override
    public String toString() {
        return String.format("Records: %d, Avg Hrs: %.2f, Avg Course Duration: %.2f, " +
                "Avg Completion Percent: %.2f, Avg Satisfaction Score: %.2f",
                recordCount, avgHrsPerWeek, avgCourseDuration, avgCompletionPercent, avgSatisfactionScore);
    }
}
