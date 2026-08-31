/**
 * The CourseData record stores data regarding each student's experience.
 * @param userId Student user ID (UXXXX)
 * @param experienceLevel Index representing Student experience level
 * @param courseType Index representing Course Type
 * @param platform Index representing what platform was used
 * @param hoursPerWeek How many hours per week was spent
 * @param courseDuration Length of course in weeks
 * @param completionStatus Index representing Completion status
 * @param completionPercentage Completion percentage
 * @param dropoutReason Index representing Reason for dropout if applicable
 * @param satisfactionScore User satisfaction score for course
 */
public record CourseData (
    String userId,
    byte experienceLevel,
    byte courseType,
    byte platform,
    byte hoursPerWeek,
    byte courseDuration,
    byte completionStatus,
    byte completionPercentage,
    byte dropoutReason,
    byte satisfactionScore
) {
    @Override
    public String toString() {
        return "[" + userId + ", " + experienceLevel + ", " + courseType + ", " +
                platform + ", " + hoursPerWeek + ", " + courseDuration + ", " +
                completionStatus + ", " + completionPercentage + ", " +
                dropoutReason + ", " + satisfactionScore + "]";
    }
}
