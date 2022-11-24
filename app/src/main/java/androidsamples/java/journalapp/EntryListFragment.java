package androidsamples.java.journalapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  private View view;
  private EntryListViewModel mEntryListViewModel;

  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    //Inside onCreate we load the view model
    super.onCreate(savedInstanceState);
    mEntryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    //Must load the UI

    //Inflate the List anf grab the
    view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    // SET THE CLICK LISTENER OF THE FLOATING ACTION BUTTON btn_add_entry
    //---------------------------------------------------------------------------------------------------
    // SAFE ARGS
    view.findViewById(R.id.btn_add_entry).setOnClickListener((viewArg) -> {
      JournalEntry entry = new JournalEntry("", "", "","");
      mEntryListViewModel.insert(entry);
      EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
      action.setEntryId(entry.getUid().toString());
      Navigation.findNavController(view).navigate(action);
    });
    //--------------------------------------------------------------------------------------------------
    RecyclerView entriesList = view.findViewById(R.id.recyclerView);
    entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    EntryListAdapter adapter = new EntryListAdapter(getActivity());
    entriesList.setAdapter(adapter);

    //Inside onCreateView we observe the changes to the
    //This is going to trigger the database if there is any change on the database
    mEntryListViewModel.getAllEntries().observe(getActivity(), adapter::setEntries);

    return view;
  }

  private class EntryViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTxtTitle;
    private final TextView mTxtDate;
    private final TextView mTxtStartTime;
    private final TextView mTxtEndTime;
    private JournalEntry mEntry;


    public EntryViewHolder(@NonNull View itemView) {
      /*
       * A placeholder just for one data item in the recycler view
       * viewHolder knows about the textViews and is responsible for populating them
       * */
      super(itemView);

      mTxtTitle = itemView.findViewById(R.id.txt_item_title);
      mTxtDate = itemView.findViewById(R.id.txt_item_date);
      mTxtStartTime = itemView.findViewById(R.id.txt_item_start_time);
      mTxtEndTime = itemView.findViewById(R.id.txt_item_end_time);

      // CLICK LISTENER FOR THE ITEM VIEW WHICH IS INVOKED WHEN WE TAP ON A PARTICULAR ENTRY
      itemView.setOnClickListener(this::launchJournalEntryFragment);
    }

    void bind(JournalEntry entry) {
      mEntry = entry;
      this.mTxtTitle.setText(mEntry.title().isEmpty()?"Title":mEntry.title());
      this.mTxtDate.setText(mEntry.date().isEmpty()?"Date":mEntry.date());
      this.mTxtStartTime.setText(mEntry.getStartTime().isEmpty()?"Start Time":mEntry.getStartTime());
      this.mTxtEndTime.setText(mEntry.getEndTime().isEmpty()?"End Time":mEntry.getEndTime());
    }

    //---------------------------------------------------------------------------------------------------
    // SAFE ARGS
    private void launchJournalEntryFragment(View v) {
      /* EntryListFragmentDirections is the generated class name since the originating destination is EntryListFragments
       * Create an action object of EntryListFragmentDirections.AddEntryAction
       * call setEntryId on action since we are passing UUID of the entry as a argument
       */
      EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
      action.setEntryId(mEntry.getUid().toString());
      Navigation.findNavController(view).navigate(action);
    }
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_entry_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.menu_info_entry) {
      NavDirections action = EntryListFragmentDirections.actionEntryListFragmentToInfoFragment();
      Navigation.findNavController(view).navigate(action);
    }
    return super.onOptionsItemSelected(item);
  }

  /*
   * Adapter acts as a bridge between two two different things, data which is a different type and UI
   * controller which is a different type
   *
   * What the adapter does is it receives the data and makes sure it gets displayed on the view and places it
   * in the widgets that are on the view. In this case our data is List<JournalEntry>
   * */

  /*
   * We create a JournalEntryListAdapter class which extends Adapter
   * Adapter which is a generic class takes a parameter type which is EntryViewHolder [inner class]
   * EntryViewHolder will itself extend a ViewHolder
   *
   * Adapter is going to add data to each viewHolder
   * viewHolder is itself has a layout which is a xml resource file
   * */

  private class EntryListAdapter extends RecyclerView.Adapter<EntryViewHolder> {
    /*
     * Layout inflator to deal with the layout and things inside it, i.e. VIEW
     * and List<JournalEntry> because it is the DATA
     * */
    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;

    //The constructor takes the context since it needs to know where the layout should be taken
    public EntryListAdapter(Context context) {
      mInflater = LayoutInflater.from(context);
    }

    /* onCreateViewHolder
     * parameters ViewGroup, viewType both are being used to inflate the layout
     * Life cycle of the Adapter where we are inflating the journal_item layout
     * and returning an instance of ViewHolder [EntryViewHolder]
     * */
    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
      return new EntryViewHolder(itemView);
    }

    /*
     * onBindViewHolder is a method, called when the ViewHolder is bound to the adapter and it takes the ViewHolder
     * and a position, we can see if the entries are not null, get the entry at the given position and place it on the text view
     * for title and duration
     * */
    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
      if (mEntries != null) {
        JournalEntry current = mEntries.get(position);
        holder.bind(current);
      }
    }

    // Returns the count of number of entries
    @Override
    public int getItemCount() {
      return (mEntries == null) ? 0 : mEntries.size();
    }

    /* where we will actually assign value to the mEntries field by setting it to the parameter value
     * notifyDataSetChanged is a method that will indicate any changes to the data set and will be picked up by the observer
     * in the main activity
     * */
    public void setEntries(List<JournalEntry> entries) {
      mEntries = entries;
      notifyDataSetChanged();
    }
  }
}