package androidsamples.java.journalapp;

import androidx.room.TypeConverter;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JournalTypeConverters {
    @TypeConverter
    public UUID toUUID(@NotNull String uuid) {
        return UUID.fromString(uuid);
    }

    @TypeConverter
    public String fromUUID(@NotNull UUID uuid) {
        return uuid.toString();
    }
}
