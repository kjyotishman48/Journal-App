package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class EntryDetailsViewModel extends ViewModel {
    private final JournalRepository mRepository;

    /*A LiveData is ready only we are basically observing it.
    * LiveData wont allow us to call setValue on it
    * We want to observe it and set it id to the appropriate one
    * So qw need a MutableLiveData */
    private final MutableLiveData<UUID> entryIdLiveData = new MutableLiveData<>();

    public EntryDetailsViewModel() {
        mRepository = JournalRepository.getInstance();
    }

    /* getEntryLiveData ---> going to give to us LiveData
    * we are calling getEntry on Repository
    * but we are doing it through transformation
    * */
    LiveData<JournalEntry> getEntryLiveData() {
        return Transformations.switchMap(entryIdLiveData, mRepository::getEntry);
    }

    /* LoadEntry is called from the UI
    * Where it passes the entry ID and is going to trigger the mutableLiveData
    * and when that happens we are going to call the transformations
    * */
    void loadEntry(UUID entryId) {
        entryIdLiveData.setValue(entryId);
    }

    // saveEntry ---> Takes an entry and call update on Repository
    void saveEntry(JournalEntry entry) {
        mRepository.update(entry);
    }

    // deleteEntry ---> Takes an entry and call delete on Repository
    void deleteEntry(JournalEntry entry) {
        mRepository.delete(entry);
    }
}
