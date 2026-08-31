import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        OnlineCourseDatabase db = new OnlineCourseDatabase(new File("OnlineCourseDataset.csv"));
//        System.out.println("Total records: " + db.size());
//        System.out.println(db.getCourseRecordString(db.getCourseRecordAt(0)));
//
//        System.out.println(Arrays.toString(db.getExperienceLevelOptions()));
//        System.out.println(Arrays.toString(db.getCourseTypeOptions()));
//        System.out.println(Arrays.toString(db.getPlatformOptions()));
//        System.out.println(Arrays.toString(db.getCompletionStatusOptions()));
//        System.out.println(Arrays.toString(db.getDropoutReasonOptions()));
//
//        CourseStats all = db.calcFilteredAverages((byte) -1, (byte)-1, (byte)-1, (byte)-1, (byte)-1);
//        System.out.println(all);

        new CourseDataFilteringGui(db);
    }
}
