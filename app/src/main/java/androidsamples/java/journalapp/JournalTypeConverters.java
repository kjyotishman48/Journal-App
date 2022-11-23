package androidsamples.java.journalapp;

import androidx.room.TypeConverters;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JournalTypeConverters {
    @TypeConverters
    public UUID toUUID(@NotNull String uuid) {
        return UUID.fromString(uuid);
    }

    @TypeConverters
    public String fromUUID(@NotNull UUID uuid) {
        return uuid.toString();
    }
}
