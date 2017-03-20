# Typed - User Guide

By : `Typedwriters`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `March 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

## About Me

Hello, my name is *Typed*. <br> 

Do you feel overwhelmed with deliverables and looming deadlines? Are you tired of writing your to-dos and schedule on a paper only to misplace that paper? Put your papers and worries away, for I am here to help you! <br>

I will be your personal task manager, storing thousands of tasks with no additional weight. You will be greeted with the more urgent tasks daily, to help you prioritise the day better. You may choose to pull up a calendar view of upcoming events, or sort your tasks by deadline, priority or type. Not sure if you saved the task under *Cara* or *Sara*? I have you covered! Search with either term and I will find what you are looking for. You can even dress me in the theme that pleases you most, should you dislike the way I look. <br>

Unlike other high-maintenance task managers, I am simply simple. Gone are the days of getting frustrated from navigating unfriendly and unintuitive interfaces. With me, there is no need to reach for the mouse anymore. Continue typing on your favourite keyboard, and I will address your commands accordingly. <br>

Ready to be productive? Here we go! :muscle:<br>

1. [Getting Started](#getting-started)
2. [Returning to *Typed*](#returning-to-typed)
3. [Features](#features)
4. [Command Summary](#command-summary)
5. [FAQ](#faq)

## 1. Getting Started 

With seemingly never-ending work and errands, it is easy to lose track of what is coming up next. With me, you will not forget another deadline or miss another event. I know it can be difficult to organise and prioritise tons of accumulated tasks. Leave the mess for me to handle, and focus on getting work done instead.  <br>

Before you get started with me, you should have Java version `1.8.0_60` or later installed. <br>

1. Download the latest `typed.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as my new home.
3. Press the Ctrl+Shift+T key to launch me. I will welcome you in just a few seconds.
4. Walk through the tutorial to try out basic features, or experiment with **[3. Features](#features)** below.

## 2. Returning to *Typed*

I am always happy to see you again! Forget the tutorial you saw the first time, and get ready for a productive day. <br>

1. You will be greeted with a view of today's focus. This view includes events happening today, tasks due today, as well as your overdue tasks. <br>

    <img src="images/TodayScreen.png" width="600"> <br>
    
2. Let me know when you are done viewing today's focus by pressing <kbd>Enter</kbd>. 
3. This brings you to the default view, with all your undone tasks sorted by priority.<br>

    <img src="images/DefaultUi.png" width="600"> <br>

4. Start typing any command and press <kbd>Enter</kbd> to execute it. 

## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Words in `lower_case` are the exact strings.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `|` indicates either or field.
> * Items with `...` after them can have multiple instances.

### 3.1. Getting Some Help

#### 3.1.1. Help for All Commands : `help`

Format: `help` <br>
> `help` shows you an overview of all the commands and their usage information. <br>

*e.g. help* <br>

#### 3.1.2. Help for Specific Command : `help COMMAND`

Format: `help COMMAND` <br>
> `help COMMAND` shows you a detailed explanation of the command's usage information.<br>

### 3.2. Adding a Task or Event

#### 3.2.1. Adding a Task or Event by Title Only: `add TITLE`

Format: `add TITLE`

> `add TITLE` adds your task or event described by TITLE with no additional information. <br>
> Your choice of TITLE should contain only alphanumeric characters.

*e.g. add read 1984 george orwell* <br>

#### 3.2.1. Adding a Task with Deadline: `add TITLE by DATE`

Format: `add TITLE by DATE`

> `add TITLE by DATE` adds your task along with its due date. <br>
> DATE takes the format DD/MM/YYYY.

*e.g. add lose 10 kg by 14/02/2014* <br>

#### 3.2.3. Adding a Recurring Task or Event: `add TITLE every FREQUENCY`

Format: `add TITLE every FREQUENCY`

> `add TITLE every FREQUENCY` adds an automatically recurring task or event according to the specified FREQUENCY. <br>
> You may indicate either `daily`, `monthly` or `yearly` when specifying FREQUENCY. <br>
> The recurrence will be calculated beginning from the day you enter the task.

*e.g. add boss' birthday every year* <br>

#### 3.2.4. Adding an Event with a Duration: `add TITLE from DATE to DATE`

Format: `add TITLE from DATE to DATE`

> `add TITLE from DATE to DATE` adds your event that is happening over days.

*e.g. add company retreat in Malaysia from 01/01/2017 to 05/01/2017* <br>

#### 3.2.5. Adding a Task or Event with Tags: `add TITLE #TAG ..`

Format: `add TITLE #TAG ..`

> `add TITLE #TAG ..` adds your task or event with one or more tags that allow for easy categorisation and retrieval. <br>
> #TAG must contain only alphanumeric characters. <br>
> Tagging is compatible with all the other `add` commands. 

*e.g. add plank for 2 minutes #fit* <br>
*e.g. add write minutes by 25/12/2017 #work #urgent #bosssays* <br>


### 3.3. Listing Tasks and Events

#### 3.3.1. Listing a Task or Event : `list`

Format: `list`

> `list` shows you your tasks and events in a similar manner as the default page. <br>

*e.g. list* <br>

#### 3.2.2. Listing a Task or Event : `list TYPE`

Format: `list TYPE`

> `list TYPE` shows you your tasks and events sorted by TYPE. <br>
> Valid formats for TYPE include events, done, undone and all.

*e.g. list done* <br>

### 3.4. Editing a Task or Event

#### 3.4.1. Editing Title of a Task or Event : `edit INDEX NEW_TITLE`

Format: `edit INDEX NEW_TITLE `

> `edit INDEX NEW_TITLE` changes the title of the task or event specified by INDEX. <br>
> INDEX is a positive integer referring to the index number shown on your *Typed* screen. <br>

*e.g. edit 1 buy green apples* <br>

#### 3.4.2. Editing Deadline of a Task or Event : `edit INDEX by [DATE]`

Format: `edit INDEX by [DATE] `

> `edit INDEX by [DATE]` changes the deadline of the task or event specified by INDEX. <br> 
> If you would like to remove a deadline from a task or event, simply enter `edit INDEX by `. <br>

*e.g. edit 1 by 05/05/2017* <br>
*e.g. edit 1 by* <br>

#### 3.4.3. Editing Tags of a Task or Event : `edit INDEX #[TAG] .. `

Format: `edit INDEX #[TAG] .. `

> `edit INDEX by #[TAG]` replaces the original tags of the task or event specified by INDEX with the new tags given in the command. <br> 
> If you would like to remove all associated tags in a task or event, use `edit INDEX # `. <br>

*e.g. edit 1 #i #can #add #anything #i #want* <br>
*e.g. edit 1 #* <br>

#### 3.4.4. Editing Multiple Fields of a Task or Event : `edit INDEX [TITLE] [DATE] #[TAG] .. `

Format: `edit INDEX [TITLE] [DATE] #[TAG] .. `

> `edit INDEX [TITLE] [DATE] #[TAG] .. ` updates the specified fields with new values, if any. <br> 
> You should provide minimally one of the optional fields when using this command format. <br>

*e.g. edit 1 buy groceries by 05/05/2017* <br>
*e.g. edit 1 buy groceries by* <br>


//TODO continue rewriting from here onwards.

### 3.5. Finding all tasks and events containing any keyword in their name : `find`

Finds tasks and events whose names contain any or close to the given keywords.<br>
Format: `find KEYWORD|#TAGS [MORE_KEYWORDS | #MORE_TAGS]`

> * The search is not case sensitive. e.g `boss` will match `Boss`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name of the tasks and events is searched.
> * Fuzzy find is used e.g. `bss` will match `boss`
> * Tasks or events matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Expo` will match `IT Expo`

Examples:

* `find johnny depp`<br>
  Returns any tasks and events that names have close to the keywords, johnny and depp
* `find boss #work`<br>
  Returns any tasks or events with similar words to boss or contains similar tagging to work.

### 3.6. Deleting a task or event : `delete`

Deletes the specified task or event from *Typed*. This action can be undone. <br>
Format: `delete [all]|INDEX [ to | ,] [INDEX] ...`

> Deletes the task at the specified `INDEX` or range of `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the task or event labelled as index 2 on the screen on *Typed*.
* `find boss`<br>
  `delete 1`<br>
  Deletes the 1st task or event in the results of the `find` command.

### 3.7. Showing history : `history`

Shows a list of the past commands used in that session.<br>
Format: `history`

Examples:

* `history`<br>

### 3.8. Undo : `undo`

Undo the last mutable command e.g. `add`, `delete`, `edit`.<br>
Format: `undo [INDEX | all]`

> There must be commands that mutate the data before `undo` is called. <br>
> Undo only works for if mutable commands are used in the **same** session. <br>

Examples:

* `add read the little prince`<br>
  `undo`<br>
  Undo the previous add command.<br>
* `add read the little prince`<br>
  `add push git commit by next wednesday`<br>
  `add write blog post`<br>
  `undo 2`<br>
  Undo the previous 2 command, leaving only `add read the little prince` as the only task.<br>

### 3.9. Redo : `redo`

Redo the last undo.<br>
Format: `redo [INDEX | all]`

> There must be undo(s) before `redo` is called. <br>
> `redo` only works for if `undo` commands are used in the **same** session. <br>
> If a mutable command (e.g. `add`, `delete`, `undo`) is used after `undo`, `redo` have no effect.<br>

Examples:

* `add read the little prince`<br>
  `undo`<br>
  Undo the previous add command.<br>
  'redo'<br>
  Redo the undo command i.e. read the little prince is added back to *Typed*.
* `add read the little prince`<br>
  `add push git commit by next wednesday` <br>
  `add write blog post`<br>
  `undo 3`<br>
  Undo the previous 2 command, leaving only read the little prince as the only task.<br>
  `redo 2`<br>
  Redo 2 undos i.e. push git commit and write blog post is added back to *Typed*.

### 3.10. Saving the data : `save`

*Typed* data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually, unless you would like to save to a new file.

Saves the data in a new file.<br>
Format: `save FILENAME`

Examples:

* `save newTyped.txt`<br>
  Saves data to the new file with file name newTyped.txt.

### 3.11. Quiting the program : `quit`

Exits *Typed* <br>

Format: `quit`

Examples:

* `quit`<br>

### 3.12. Completing the task : `complete`

Marks the task(s) as completed in *Typed*<br>

Format: `complete [all]|INDEX [ to | ,] [INDEX] ...`

> Marks the task at the specified `INDEX` or range of `INDEX` as completed. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...<br>

Examples:

* `complete 2`<br>
  Marks the task at index 2 of the listing shown as completed. Removes from the list of tasks shown.
* `complete 2 to 5`<br>
  Marks multiple tasks from index 2 to 5 as completed. Remove from the list of tasks shown.

## 4. Command Summary

* **Add**  `add TASK [ on DATE | by DATE | every RECURRENT | from DATE to DATE] [#TAG] [#MORE_TAGS]....` <br>
  e.g. `add read the little prince` <br>
  e.g. `add push git commit by next wednesday` <br>
  e.g. `add write blog post every day #/hobby` <br>
  e.g. `add meet boss every monday` <br>
  e.g. `add release paycheck every month` <br>
  e.g. `add my birthday every year` <br>
  e.g. `add training camp from 2 May to 5 May` <br>
  e.g. `add drinks with client tomorrow from 1PM to 2PM` <br>
  e.g. `add clean room on Sunday` <br>

* **Complete**  `complete [all]|INDEX [ to | ,] [INDEX] ...` <br>
  e.g. `complete 2` <br>
  e.g. `complete 2 to 10` <br>
  e.g. `complete 1,3,5` <br>
  e.g. `complete 1, 3, 5 to 10` <br>
  e.g. `complete all` <br>

* **Delete** : `delete [all]|INDEX [ to | ,] [INDEX] ...` <br>
  e.g. `delete 2` <br>
  e.g. `delete 2 to 10` <br>
  e.g. `delete 1,3,5` <br>
  e.g. `delete 1, 3, 5 to 10` <br>
  e.g. `delete all` <br>

* **Edit** : `edit INDEX [DESCRIPTION] [by DATE | TIME] [ #TAG | -#TAG ]` <br>
  e.g. `edit 1 by following Saturday` <br>
  e.g. `edit 2 on 2 May` <br>
  e.g. `edit 4 from 13:00 to 5pm` <br>
  e.g. `edit 3 every Wednesday` <br>
  e.g. `edit 7 play mousehunt` <br>
  e.g. `edit 5 +#work` <br>
  e.g. `edit 6 -#supplier` <br>

* **Find** : `find KEYWORD|#TAGS [MORE_KEYWORDS | #MORE_TAGS]...` <br>
  e.g. `find johnny depp` <br>
  e.g. `find boss #work` <br>
  e.g. `find #forever #alone` <br>

* **Help** : `help` <br>
  e.g. help <br>

* **History** : `history` <br>
  e.g. `history` <br>

* **List** : `list TYPE` <br>
  e.g. `list` <br>
  e.g. `list all` <br>
  e.g. `list undone` <br>
  e.g. `list done` <br>
  e.g. `list events` <br>

* **Redo** : `redo [INDEX|all]` <br>
  e.g.`redo` <br>
  e.g.`redo 5` <br>
  e.g.`redo all` <br>

* **Save** : `save FILENAME` <br>
  e.g.`save newfile.txt` <br>

* **Quit** : `quit` <br>
  e.g.`quit` <br>

* **Undo** : `undo [INDEX|all]` <br>
  e.g.`undo` <br>
  e.g.`undo 5` <br>
  e.g.`undo all` <br>

## 5. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Typed folder.

**Q**: How do I backup my data to another location?<br>
**A**: Copy the saved Typed file to any desired location of your choice. Alternatively,
       use our in-built backup feature as described in the [Features](#features) section above.<br>

**Q**: Is my personal data and information kept secure from the eyes of others (including the dev team)?<br>
**A**: Yes! Everything you enter into Typed is only stored on your local machine in your specified storage file.<br>
