# JournalApp


## A) Submission Details
   Name of Project : Assignment 4 - JournalApp <br />
   Name : Jyotishman Kashyap <br />
   ID Number : 2019B2A70911G <br />
   
## B) Description of the App and Known Bugs 

### Description of the App

This app allows users to record their day to day activities. It uses the Room database and Architecture components

In the EntryListFragment which consists of recyler view and a floating action button, on clicking on the + floating action button the user is navigated to the EntryDetailsFragment where the user can enter the Title, date, start time and end time and on clicking save the entry is entered into the database and the user is navigated back to EntryListFragment and the recyler view in the EntryListFragment is updated with the all the Journal entries inserted in the database.

### Known Bugs

- If the journal duration cannot spans between multiple days, it will show show the start time and end time for the same date only.
- End time can be before start time.


## C) Description of how the task was completed

### Task 1

To pass the EntryDetailsFragmentTest, inside the EndtryListFragment class used a setOnClickListener on the floating action button implemented the addEntryAction which is responsible for navigating from the EntryListFragment to EntryDetailsFragment using navigation controller and passing the Entry ID "NEW ENTRY" as an argument.

Inside EntryDetailsFragmentTest class testRecyclerView() UI test for recycler view using import androidx.test.espresso.contrib.RecyclerViewActions. The test is performing click action on the item at position 1 in the RecylerView and that should navigate to the EntryDetailsFragment (At least create one Journal Entry for this test to run).

### Task 2

Added the following function signature in the JournalEntryDao, then the function was implemented in JournalRepository using executor. Then inside the EntryDetailsViewModel class we use an JournalRepository object to access the delete function, this happens inside deleteEntry function.

If user clicks on delete dialog, a confirmation dialog is shown to the user. When pressed ok, the entry gets deleted.

### Task 3

Created a resource layout file (of menu type) and named the file as fragment_entry_details inside which a item is added with the ic_menu_delete icon from the drawables folder and with the title Delete and id menu_delete_entry.

Inside the EntryDetailsFragment inside onCreateView() have used setHasOptionsMenu(true);

Inside the EntryDetailsFragment overriden the onCreateOptionsMenu() to inflate the layout 

Inside the EntryDetailsFragment have overriden onOptionsItemSelected and checking if the id of the item selected is menu_delete_entry. If the id matches with it, a new alert dialog box is created with the title Delete Entry and message "Do you really want to delete this entry?" also ic_delete icon is used form the drawable folder. Positive button is set using text "YES" and a onClickListener attached to it invoked deleteEntry() on the mEntryDetailsViewModel object and passes the mEntry as argument and after that getActivity().onBackPressed() is invoked to take the user back to the EntryListFragment. Negative button is set using text cancel.

### Task 4

Inside the fragment_entry_details include another item tag, with the ic_menu_share icon from the drawables folder and with the title Share and id menu_share_entry.

Inside the EntryDetailsFragment have overriden onOptionsItemSelected included another check condition if the id is equal to menu_share_entry. If the id matches, we create a new Intent and set the action as ACTION_SEND and set the intent type as text/plain also defined a String message in the formal specified ("Look what I have been up...") and using getters to get the values of the attributes associated with the entry. This message string is passed with the intent using putExtra. Then another Intent object is created createChooser() , passing it Intent object, it returns a version of your intent that will always display the Android Sharesheet.

### Task 5

We add a new destination to our navigation graph for which a new component is created whose name is InfoFragment and the layout xml file associalted with is named as fragment_info. In the navigation graph we create an action actionEntryListFragmentToInfoFragment which will enable to navigate from the entryListFragment to the EntryDetails Fragment.

Created another resource layout file of menu type with the file name as fragment_entry_list. Inside this file a item tag is placed with id as menu_info_entry, title as "INFO" and using c_menu_info_details from the drawables folder as an icon.

In the entryListFragment class inside the inside onCreateView() have used setHasOptionsMenu(true);

Inside the same class overriden the onCreateOptionsMenu() to inflate the layout

Then inside the onOptionsItemSelected() method we checking if the id of the item selected is menu_info_entry. If the id matches, we are using action actionEntryListFragmentToInfoFragment to navigate from the EntryListFragment to the InfoFragment

### Task 6  Acessibility 

#### TalkBack Experience

On tapping on the application icon Talkback responds as "JournalApp double tap to activate, double tap and hold to long press". When the app is launched by double tap talk back the things in the action bar which is the JournalApp title and info button, it responds as "JournalApp, Info button double tap to activate". When the user single taps the floating action button, it reads as "Add a new entry button, double tap to activate". When the user double taps on the floating action button and is navigated to EntryDetails Fragment, the TalkBack only reads "JournalApp, share button double tap to activate", it doesnot do anything to inform the user that he fragment has changed. Inside the EntryDetails fragment when the user presses on title edit box it reads as "Title Editbox, double tap to edit text, double tap and hold to long press". A on different buttons it reads as "Date button, double tap to activate", "Start time button double tap to activate", "End time button double tap to activate", "Save button double tap to activate". If the user tries to do some invalid action such as saving an entry in which certain attributes are empty or sharing or deleting an empty entry an appropriate error toast is displayed which is then read by the TalkBack. When the date button is clicked the calender dialog box opens and reads the current date to the user when the user taps on a perticular date, the date is read out by talk back, similarly for start time and end time picker dialog boxes.

#### Report using Accessibility Scanner

- Item label
androidsamples.java.journalapp:id/recyclerView
This item may not have a label readable by screen readers.
<br>Fix : Included contentDescription for the recylerView

- Image contrast
androidsamples.java.journalapp:id/btn_add_entry
The image's contrast ratio is 1.77. This ratio is based on an estimated foreground color of #FFFFFF and an estimated background color of #03DAC5. Consider increasing this ratio to 3.00 or greater.
<br>Fix : Changed background of the button to white and change the color res file perticularly teal_200 color to black, which sets it a white background with black border.

- Text contrast
androidsamples.java.journalapp:id/txt_item_date
The item's text contrast ratio is 3.98. This ratio is based on an estimated foreground color of #01645B and an estimated background color of #03DAC6. Consider increasing this item's text contrast ratio to 4.50 or greater.<br>
Text contrast
androidsamples.java.journalapp:id/txt_item_start_time
The item's text contrast ratio is 3.98. This ratio is based on an estimated foreground color of #01645B and an estimated background color of #03DAC6. Consider increasing this item's text contrast ratio to 4.50 or greater.<br>
Text contrast
androidsamples.java.journalapp:id/txt_item_end_time
The item's text contrast ratio is 3.98. This ratio is based on an estimated foreground color of #01645B and an estimated background color of #03DAC6. Consider increasing this item's text contrast ratio to 4.50 or greater.
<br>
Fix : In the fragment entry xml file set the background color to light_purple (defined in colors.xml) and foreground color to black.

#### Accesibility tests

Included the dependency 'androidx.test.espresso:espresso-accessibility:3.5.0-alpha01'

Inside the EntryDetailsFragmentTest added a @BeforeClass tag and used a function enableAccessibilityChecks() and used androidx.test.espresso.accessibility.AccessibilityChecks.enable(); to enable accessibility tests.

And also included @Rule tag and created ActivityScenarioRule object.

After this all the instrumented automatically checks for accessibility issues while performing the actions.

## D) Testing

### Monkey Test

Yes I did use the Monkey Tool (with 500 events)</br>
I ran monkey in my emulator it ran succesfully without any crash <br>
<p align="center">
   <img width="940" height="600" src="https://user-images.githubusercontent.com/68853069/203917551-fea5a138-b4f7-48b8-bfc2-9113e5396389.png"> 
</p>

## E) Time taken to finish the Assignment

I took me 48 hours to finish the assignment.

## F) The difficulty level of the Assignment

For me the difficulty level of the assignment 9/10. 
