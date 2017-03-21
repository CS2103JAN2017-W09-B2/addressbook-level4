package seedu.typed.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.typed.commons.core.ComponentManager;
import seedu.typed.commons.core.LogsCenter;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.CommandResult;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.parser.Parser;
import seedu.typed.model.Model;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.storage.temp.Session;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final Session session;

    public LogicManager(Model model, Session session) {
        this.model = model;
        this.parser = new Parser();
        this.session = session;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseInput(commandText);
        command.setData(model, session);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
