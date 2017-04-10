# Manual Testing Guidelines

You can find the sample data at `SampleData.xml` in the same folder as this document. We will be referring to events as tasks with a start date and end date, deadlines as tasks with just a date and floating tasks as tasks with no dates.

_Typed_ offers multiple command keywords so that users can choose whichever keyword that is more natural to them. In our manual testing, we will specify the different keywords for the command. However, we will stick to one for the actual demonstration.

In _Typed_, the default view is the list of all uncompleted tasks sorted by their due dates. Events are compared using their starting dates and floating tasks are, by default having no dates to compare, at the bottom of the list.

## Importing Sample Data

To start doing manual scripted testing, follow these steps:

1. Download `typed.jar` from our [releases](https://github.com/CS2103JAN2017-W09-B2/main/releases).
2. Copy `typed.jar` in a directory that would be the home directory for _Typed_.
3. Double-click on `typed.jar`. You should have Java version `1.8.0_60` or later on your device - otherwise, download it [here](https://java.com/en/download/help/download_options.xml). You will notice that a `./data` folder would be created in the home directory for _Typed_ you chose.
4. Download `SampleData.xml` located in the same folder as this file - this is our sample data file of more than 50 to-dos.
5. Copy `SampleData.xml` into the newly created `./data` folder in the home directory.
6. Switch back to _Typed_, type `>> import ./data/SampleData.xml` and hit <kbd>Enter</kbd>.
7. You have just used the `import` command to import our sample to-do list into _Typed_! You will see a list of tasks on the right panel and the our Progress Bar on the left panel.

For more details on how _Typed_ works, visit our [user guide](https://github.com/CS2103JAN2017-W09-B2/main/blob/master/docs/UserGuide.md).

## Viewing Help: `help` command

Command to type | What should happen |
------- | :--------------
`>> help` | Our user guide will open up in a new window.

## Searching through _Typed_: `find|query|search` command

To search through this list of tasks, you can use the `find` command:

Command to type | What should happen |
------- | :--------------
`>> find life` | All tasks containing words close to or the same as "life" will appear. 
`>> find #life` | Only tasks, completed or uncompleted, with tag of "life" will be showed. This is an exact find for short tags of length 4 or below, so "#lif" will not be showed in this case.
`>> find 2103 jeffrey` | All tasks containing words close to or the same as either "2103" or "jeffrey" will appear. Currently the find returns tasks sorted by the dates they are due.

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
`>> add do homework` | A floating task is added with the name "do homework". It will appear, and the Floating count should increment by 1 in Task Overview panel.
`>> do hotplay concert from dec 10 2pm to 10 dec 4pm #hot` | An event task will appear with the name "hotplay concert", with date field as "From: Monday, Apr 10, 2017 14:00 To: Monday, Apr 10, 2017 16:00". There is also a tag in yellow box titled "hot". The Events count should increment by 1 in Task Overview panel.
`>> create tour with Johnny on next Saturday` | A deadline task will appear with the name "tour with Johnny", date field of "By: Saturday, Apr 22, 2017 23:59".
`>> new go shopping for ma's birthday + buy diamond necklace by 08/20/2017 ` | A deadline task will appear with extra notes `buy diamond necklace` in blue words. 

Do note that we do support natural language parsing for Dates such as "next Saturday". However, this would be based on the current time and hence "next Saturday" will not be fixed but instead be calculated according to the date and time of input.

## Adding Recurring Tasks: `add` command

In _Typed_, recurring tasks are added in a similar way as non-recurring tasks. The key exception is you need to specify the recurring rule after the keyword `every`.

Command to type | What should happen |
------- | :--------------
`>> add shower by today every day` | A deadline task will appear with name "shower", date field of "By: Tuesday, Apr 11, 2017 03:38 Every: day". It will also have the recurring icon on the right side. A deadline must be specified for the recurrence to take effect.
`>> add go church on monday every week` | A deadline task will with name "go church", date field of "By: Monday, Apr 17, 2017 03:41 Every: monday". It will also have the recurring icon on the right side.
`>> add go school from 8am 10 apr to 4pm 11 apr every month` | An event will appear with name "go school", date field of "From: Monday, Apr 10, 2017 08:00 To: Monday, Apr 10, 2017 16:00 Every: month". It will also have the recurring icon on the right side. In this case, we can have a recurring event that lasts more than a day.
`>> add wifey's birthday on 10th oct every year` | A task with wifey's birthday will appear, and will recur yearly. 

## Deleting Tasks: `delete|del|remove|rm` command
In deleting tasks, the tasks affected are the ones you see on Task List currently.

Command to type | What should happen |
------- | :--------------
`>> delete 1` | The task with the index of "1" will be deleted.
`>> del 1 to 5` | The tasks with the indices of "1", "2", "3", "4" and "5" will be deleted. The rest of the tasks will be shifted accordingly. This will delete a range of tasks from start index 1 to end index 5.
`>> rm all` | The tasks you see now will be deleted. It does not delete all of the tasks in _Typed_ but only the tasks you see on your current view.

## Editing Tasks: `edit|update|change` command

Command to type | What should happen |
------- | :--------------
`>> edit 1 #projectABC` | The to-do with the index of "1" will have its tags (if any) replaced with a single tag "#projectABC".
`>> edit 2 by 6 feb 2017` | The task with the index of "2" will have its due date changed (or added) to 6 Feb 2017, 2359h. If the target to-do is an _Event_ with a time window, there will be an error.

## Marking Tasks as Completed: `finish|complete|done|check|mark|end` command

A _Task_ can be marked as completed with the `finish` command. The completed task will be marked with a tick in the box to the left of its name. Completed tasks are not deleted from the _Typed_ and will not appear in default view.

Command to type | What should happen |
------- | :--------------
`>> finish 1` | The task with the index of "1" will be marked as completed and will disappear from the current list. If the task is already completed, it will show that you completed 0 tasks.
`>> end 1 to 3` | The tasks with the indices of "1", "2" and "3" will be marked as done. The rest of the tasks will be shifted up accordingly. This will finish a range of tasks from start index 1 to end index 3.
`>> done all` | All undone tasks in the current list will be marked as done.

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

## Clearing Task List: `clear|empty` command
`clear` command does a permanent delete of all the tasks in _Typed_ including completed tasks as well.

Command to type | What should happen |
------- | :--------------
`>> clear` |  All tasks, including completed, would be removed.

## Exiting: `exit|quit|logout|bye` command

Command to type | What should happen |
------- | :--------------
`>> exit` |  _Typed_ closes.

## Getting Help: `help|sos|man` command

Command to type | What should happen |
------- | :--------------
`>> help` |  Gets help and usage instructions. 

## Datetime Formats

For a list of accepted date and time formats, refer to the table below: <br>

|Precise Dates|Natural Dates|Relative Dates|
|-------- |--------|--------|
|12/22/2017 | 22 December 2017 | Today |
|12/22/17 | 22 Dec 2017 | Tomorrow |
|12/22 | Dec 22 | Tmr |
| | the 22nd of Dec | Yesterday |
| | 22nd day of Dec | Next Monday |

|Precise Times|Natural Times|Relative Times|
|--------|--------|--------|
|08:00 | 8am | Morning (8am) |
|1200 | 12.00pm | Afternoon (12pm) |
|1900h | 7:00pm | Evening (7pm) |
|20.00 | | Night (8pm) |


While we offer support for relative date formats such as `next friday`, this may occasionally be ambiguous. For foolproof support, we recommend using precise dates and natural dates. Precise dates use the American date format (MM/DD/YYYY). <br>

* You may choose to leave out the year if the date is in the current year. <br>
* You may leave out the whole date if the date is today. <br>
* The date should come before the time.
