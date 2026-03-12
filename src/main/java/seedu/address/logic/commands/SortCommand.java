package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final Prefix PREFIX_ORDER = new Prefix("o/");
    public static final Prefix PREFIX_REVERSE = new Prefix("r/");

    public static final String MESSAGE_USAGE = COMMAND_WORD  + ": Sorts the persons by their names\n"
            + "Parameters: "
            + PREFIX_ORDER + "ORDER "
            + PREFIX_REVERSE;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Sort command", false, false);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
