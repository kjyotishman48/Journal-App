package androidsamples.java.journalapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NotNull
    private UUID mUid;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "start_time")
    private String mStartTime;

    @ColumnInfo(name = "end_time")
    private String mEndTime;

    public JournalEntry(@NotNull String title, String date, String startTime, String endTime) {
        mUid = UUID.randomUUID();
        mTitle = title;
        mDate = date;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    //Getters and Setters

    @NotNull
    public UUID getmUid() {
        return mUid;
    }

    public void setmUid(@NotNull UUID mUid) {
        this.mUid = mUid;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }
}
