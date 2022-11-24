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
    public UUID getUid() {
        return mUid;
    }

    public void setUid(@NotNull UUID mUid) {
        this.mUid = mUid;
    }

    public String title() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String date() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }
}