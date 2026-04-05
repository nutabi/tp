package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;
import java.util.function.Supplier;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Returns whether this command supports undo.
     */
    public boolean isUndoable() {
        return false;
    }

    /**
     * Undoes the effect of a previously executed command.
     *
     * @throws CommandException If undo cannot be completed.
     */
    public CommandResult undo(Model model) throws CommandException {
        throw new CommandException("This command cannot be undone.");
    }

    /**
     * Helper for undoing a person-level change.
     *
     * @param model the model
     * @param original the original person
     * @param updated the updated person
     * @param failureMessage message to show if undo fails
     * @param logger the command logger
     * @param undoLogMessageSupplier supplies the log message to emit after undo succeeds
     * @param undoResultSupplier supplies the command result to return after undo succeeds
     * @return CommandResult with the formatted success message
     * @throws CommandException if undo cannot be completed
     */
    protected CommandResult undoPersonChange(Model model, Person original, Person updated,
                                             String failureMessage, Logger logger,
                                             Supplier<String> undoLogMessageSupplier,
                                             Supplier<CommandResult> undoResultSupplier)
            throws CommandException {
        requireNonNull(model);
        requireNonNull(logger);
        requireNonNull(undoLogMessageSupplier);
        requireNonNull(undoResultSupplier);

        if (original == null || updated == null) {
            throw new CommandException(failureMessage);
        }
        model.setPerson(updated, original);
        logger.info(undoLogMessageSupplier.get());
        return undoResultSupplier.get();
    }
}
