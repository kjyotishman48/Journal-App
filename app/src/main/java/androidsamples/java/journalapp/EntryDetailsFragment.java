package androidsamples.java.journalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.NavArgs;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {
  private EditText mTitle;
  private Button mBtnDate, mBtnStart, mBtnEnd;
  private EntryDetailsViewModel mEntryDetailsViewModel;
  private JournalEntry mEntry;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    mEntryDetailsViewModel = new ViewModelProvider(getActivity()).get(EntryDetailsViewModel.class);

    try {
      UUID mEntryId = UUID.fromString(EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId());
      mEntryDetailsViewModel.loadEntry(mEntryId);
    }catch (IllegalArgumentException e){
      Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
      getActivity().onBackPressed();
    }

    // This callback will only be called when MyFragment is at least Started.
//    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//      @Override
//      public void handleOnBackPressed() {
//        mEntry.setTitle(mTitle.getText().toString());
//        mEntry.setDate(mBtnDate.getText().toString());
//        mEntry.setStartTime(mBtnStart.getText().toString());
//        mEntry.setEndTime(mBtnEnd.getText().toString());
//        mEntryDetailsViewModel.saveEntry(mEntry);
//        mEntryDetailsViewModel.deleteEntry(mEntry);
//        // Handle the back button event
//      }
//    };
//    requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_details, container, false);
    mTitle = view.findViewById(R.id.edit_title);
    mBtnDate = view.findViewById(R.id.btn_entry_date);
    mBtnStart = view.findViewById(R.id.btn_start_time);
    mBtnEnd = view.findViewById(R.id.btn_end_time);
    view.findViewById(R.id.btn_save).setOnClickListener(this::saveEntry);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      view.findViewById(R.id.btn_entry_date).setOnClickListener(this::launchDateDialog);
    }

    view.findViewById(R.id.btn_start_time).setOnClickListener(this::launchStartTimerDialog);
    view.findViewById(R.id.btn_end_time).setOnClickListener(this::launchEndTimerDialog);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mEntryDetailsViewModel.getEntryLiveData().observe(getActivity(),
            entry -> {
              this.mEntry = entry;
              if (entry != null) updateUI();
            });
  }

  private void updateUI() {
    mTitle.setText(mEntry.title().isEmpty()?null:mEntry.title());
    mBtnDate.setText(mEntry.date().isEmpty()?"Date":mEntry.date());
    mBtnStart.setText(mEntry.getStartTime().isEmpty()?"Start Time":mEntry.getStartTime());
    mBtnEnd.setText(mEntry.getEndTime().isEmpty()?"End Time":mEntry.getEndTime());
  }

  private void saveEntry(View v) {
    mEntry.setTitle(mTitle.getText().toString());
    mEntry.setDate(mBtnDate.getText().toString());
    mEntry.setStartTime(mBtnStart.getText().toString());
    mEntry.setEndTime(mBtnEnd.getText().toString());
    mEntryDetailsViewModel.saveEntry(mEntry);

    getActivity().onBackPressed();
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  private void launchDateDialog(View view) {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog dialog =
            new DatePickerDialog(getContext(),
                    R.style.ThemeOverlay_AppCompat_Dialog_Alert,
                    this::mDateSetListener, year, month, day);
    dialog.show();
  }

  private void launchStartTimerDialog(View view) {
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    TimePickerDialog dialog =
            new TimePickerDialog(getContext(),
                    this::mStartTimeListener,
                    hour, minute, false);
    dialog.show();
  }

  private String formatTime(int hour, int minute) {
    String time = "";
    if(hour<10)time = time.concat("0");
    time = time.concat(Integer.toString(hour));
    time = time.concat(":");
    if(minute<10)time = time.concat("0");
    time = time.concat(Integer.toString(minute));
    return time;
  }

  private void mStartTimeListener(TimePicker timePicker, int hour, int minute) {
    mBtnStart.setText(formatTime(hour, minute));
  }

  private void launchEndTimerDialog(View view) {
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    TimePickerDialog dialog =
            new TimePickerDialog(getContext(),
                    this::mEndTimeListener,
                    hour, minute, false);
    dialog.show();
  }

  private void mEndTimeListener(TimePicker timePicker, int hour, int minute) {
    mBtnEnd.setText(formatTime(hour, minute));
  }

  public void mDateSetListener(DatePicker view, int year, int month, int dayOfMonth) {
    Calendar mCalendar = Calendar.getInstance();
    mCalendar.set(Calendar.YEAR, year);
    mCalendar.set(Calendar.MONTH, month);
    mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    Date d = new Date();
    d.setTime(mCalendar.getTimeInMillis());
    SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
    String dateToDisplay = formatter.format(d);
    mBtnDate.setText(dateToDisplay);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_entry_details, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.menu_delete_entry) {
      new AlertDialog.Builder(getContext())
              .setTitle("Delete Entry")
              .setMessage("Do you really want to delete this entry?")
              .setIcon(android.R.drawable.ic_delete)
              .setPositiveButton(R.string.yes, (dialog, id) -> {
                mEntryDetailsViewModel.deleteEntry(mEntry);
                getActivity().onBackPressed();
              })
              .setNegativeButton(R.string.cancel, null).show();
    }
    return super.onOptionsItemSelected(item);
  }


}