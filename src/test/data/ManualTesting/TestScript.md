# Manual Testing Guidelines

You can find the sample data at `SampleData.xml` in the same folder as this document. We will be referring to events as tasks with a start date and end date, deadlines as tasks with just a date and floating tasks as tasks with no dates.

_Typed_ offers multiple command keywords so that users can choose whichever keyword more natural to them. In our manual testing, we will specify the different keywords for the command. However, we will stick to one for the actual demonstration.

In _Typed_, the default view is the list of all uncompleted tasks sorted by their due dates. Events are compared using their starting dates and floating tasks are, by default having no dates to compare, at the bottom of the list.

## Importing Sample Data

To start doing manual scripted testing, follow these steps:

1. Download `typed.jar` from our [releases](https://github.com/CS2103JAN2017-W09-B2/main/releases).
2. Copy `typed.jar` in a directory that would be the home directory for _Typed_.
3. Double-click on `typed.jar`. You should have Java version `1.8.0_60` or later on your device - otherwise, download it [here](https://java.com/en/download/help/download_options.xml). You will notice that a `./data` folder would be created in the home directory for _Typed_ you chose.
4. Download `SampleData.xml` [UPDATE LINK](?) - this is our sample data file of more than 50 to-dos.
5. Copy `SampleData.xml` into the newly created `./data` folder in the home directory.
6. Switch back to _Typed_, type `>> import ./data/SampleData.xml` and hit <kbd>Enter</kbd>.
7. You have just used the `import` command to import our sample to-do list into _Typed_! You will see a list of tasks on the right panel and the our Progress Circle on the left panel.

For more details on how _Typed_ works, visit our [user guide](https://github.com/CS2103JAN2017-W09-B2/main/blob/master/docs/UserGuide.md).

## Viewing Help: `help` command

Command to type | What should happen |
------- | :--------------
`>> help` | Our user guide will open up in a new window.

## Searching through _Typed_: `find|query|search` command

To search through this list of tasks, you can use the `find` command:

Command to type | What should happen |
------- | :--------------
`>> find life` | All tasks containing words close to or the same as "life" will appear. We try to sort them based on how close they are to what the users intended to find.
`>> find #life` | Only tasks, completed or uncompleted, with tag of "life" will be showed. This is an exact find so tag of "lif" will not be showed.
`>> find 2103 jeffrey` | All tasks containing or similar to either "2103" or "jeffrey" will appear. Currently it will be sorted based on the dates they are due.

## Listing Tasks: `list|filter|show|ls|display` command

To browse through the list of tasks based on what you need to see, you can use `list` command:

Command to type | What should happen |
------- | :--------------
`>> list` | Shows the default view of _Typed_.
`>> list done` | Shows all completed tasks.
`>> list undone` | Shows all uncompleted tasks.

## Adding Tasks: `add|create|do|new` command

In _Typed_, we do not explicitly differentiate between different type of tasks. You can add events or floating tasks using the same add command.

You can differentiate between different type of tasks easily from the colored circles on the right side of the right panel. The description of these colored circles are given in the Task Overview Panel.

However, do ensure that the correct parameters are entered.

Command to type | What should happen |
------- | :--------------
`>> add do homework` | A floating task is added with the name "do homework". It will appear right at the top and has a grey background. The Floating count should increment by 1 in Task Overview panel.
`>> add hotplay concert from 10 april 2pm to 10 april 4pm #hot` | An event task will appear right at the top with the name "hotplay concert", with date field as "From: Monday, Apr 10, 2017 14:00 To: Monday, Apr 10, 2017 16:00". There is also a tag in yellow box titled "hot". The Events count should increment by 1 in Task Overview panel.
`>> add tour with Johnny on next Saturday` | A deadline task will appear on top with the name "tour with Johnny", date field of "By: Saturday, Apr 22, 2017 23:59".

Do note that we do support natural language parsing for Dates such as "next Saturday". However, this would be based on the current time and hence "next Saturday" will not be fixed but according to when you type the command.

## Adding Recurring Tasks: `add` command

In _Typed_, recurring tasks are added in a similar way as non-recurring tasks. The key exception is you need to specify the recurring rule after the keyword `every`.

Command to type | What should happen |
------- | :--------------
`>> add shower every day` | A deadline task will appear at the top with name "shower", date field of "By: Tuesday, Apr 11, 2017 03:38 Every: day". It will also have the recurring icon on the right side. As the date is not specified, we will use the current time as reference and obtain the next occurrence of it.
`>> add go church every monday` | A deadline task will appear at the top with name "go church", date field of "By: Monday, Apr 17, 2017 03:41 Every: monday". It will also have the recurring icon on the right side.
`>> add submit weekly report every week` | A deadline task will appear at the top with name "shower", date field of "By: Monday, Apr 17, 2017 03:42 Every: week". It will also have the recurring icon on the right side.
`>> add go school from 8am 10 apr to 4pm 11 apr every month` | An event will appear at the top with name "shower", date field of "From: Monday, Apr 10, 2017 08:00 To: Monday, Apr 10, 2017 16:00 Every: month". It will also have the recurring icon on the right side. In this case, we can have a recurring event that lasts more than a day.

We support many variations of recurrence rules such as "every monday", "every year" etc.


## Deleting Tasks: `delete|del|remove|rm` command
In deleting tasks, the tasks affected are the ones you see on Task List currently.

Command to type | What should happen |
------- | :--------------
`>> delete 1` | The task with the index of "1" will be deleted.
`>> delete 1 to 5` | The tasks with the indices of "1", "2", "3", "4" and "5" will be deleted. The rest of the tasks will be shifted accordingly. This will delete a range of tasks from start index 1 to end index 5.
`>> delete all` | The tasks you see now will be deleted. It does not delete all of the tasks in _Typed_ but only the tasks you see on your current view.

## Editing Tasks: `edit TO DO` command

Command to type | What should happen |
------- | :--------------
`>> edit 1 #projectABC` | The to-do with the index of "1" will have its tags (if any) replaced with a single tag "#projectABC".
`>> edit 1 from today 14:00 to 16:00 #important #work` | The task with the index of "1" will have its time window changed to the current day 1400h to 1600h and replaces its tags (if any) by #important and #work. If the target to-do is a _Task_ with a due date, there will be an error.
`>> edit 2 by 6 feb 2017` | The task with the index of "2" will have its due date changed (or added) to 6 Feb 2017, 2359h. If the target to-do is an _Event_ with a time window, there will be an error.

## Marking Tasks as Completed: `finish` command

A _Task_ can be marked as completed with the `finish` command. The completed task will be marked with a tick in the box to the left of its name. Completed tasks are not deleted from the _Typed_ and will not appear in default view.

Command to type | What should happen |
------- | :--------------
`>> finish 1` | The task with the index of "1" will be marked as completed and will disappear from the current list. If the task is already completed, it will show that you completed 0 tasks.
`>> finish 1 to 3` | The tasks with the indices of "1", "2" and "3" will be marked as done. The rest of the tasks will be shifted up accordingly. This will finish a range of tasks from start index 1 to end index 3.

## Setting Save Location: `save` command

Command to type | What should happen |
------- | :--------------
`>> save  C:\Users\Public\Desktop\typed.xml` | The save location for _Typed_ will be changed to this file path. It gives an error if the file name is already taken.
## Exporting: `export` command

Command to type | What should happen |
------- | :--------------
`>> export new.xml` | A copy of the latest task list data will be saved in the working directory you are in with this file name.
`>> export C:\Users\Public\Desktop\typed.xml` | A copy of the latest task list data will be saved in the specified directory and file name. Do note that the directory name is different for different operating system.

## Undoing: `undo` command

Multiple `>> undo` can be used in succcession, only limited in your current session, which ends when you close the application.

Command to type | What should happen |
------- | :--------------
`>> undo` | The most recent `add`, `edit`, `delete`, `clear` would be reverted.
`>> undo 3` | The most recent 3 of these commands `add`, `edit`, `delete`, `clear` would be reverted.
`>> undo all` | The most recent of all these commands `add`, `edit`, `delete`, `clear` would be reverted.

## Redoing: `redo` command

Multiple `>> redo` can be used in succession, only limited in your current session, which ends when you close the application.

Command to type | What should happen |
------- | :--------------
`>> redo` | The most recent `undo` command would be reverted.
`>> redo 3` | The most recent 3 of these `undo` command would be reverted.
`>> redo all` | The most recent of all these `undo` command would be reverted.

## Clearing Task List: `clear` command
`clear` command does a permanent delete of all the tasks in _Typed_ including completed tasks as well.

Command to type | What should happen |
------- | :--------------
`>> clear` |  All tasks, including completed, would be removed.

## Exiting: `exit` command

Command to type | What should happen |
------- | :--------------
`>> exit` |  _Typed_ closes.

## Datetime Formats TODO

Here is a quick brief on the formats of date and times accepted.

For dates, you must follow this format: `Day of the month, Month, Year`. * `28/12/2018`, `3 feb 2019`

* You may choose to leave out the year if the date is in the current year.
* You may leave out the whole date if the date is today.
* You may also state relative dates such as `Today`, `Yesterday`, `Tomorrow`, `next/last/coming <weekday/month/year>`.

For times, you must follow either of these formats: `HH:MM`, `HHMMh`, `HHam/pm`. * `23:59`, `2350h`, `9am`

* You may state relative times such as `Morning`, `Afternoon`, `Evening`, `Night`, `Midnight`

The date must come before the time.
Refer to the section on [input link](?) for more information.

