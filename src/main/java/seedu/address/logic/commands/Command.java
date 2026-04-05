package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;
import java.util.logging.Logger;

import seedu.address.logic.Messages;
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
     * Undoes a person-level update by restoring the original person in place of the updated one.
     *
     * @param model the model to update
     * @param original the person state to restore
     * @param updated the current person state to replace
     * @param failureMessage message to show if undo cannot proceed
     * @param logger the logger used to record a successful undo
     * @param undoLogMessageSupplier supplies the undo log message after restoration succeeds
     * @param undoResultSupplier supplies the result to return after restoration succeeds
     * @return the command result for a successful undo
     * @throws CommandException if the original or updated state was not recorded
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

    /**
     * Creates a command result for an undo operation involving a single person.
     *
     * @param successMessage the undo success message template
     * @param person the person to include in the message
     * @return the formatted undo result
     */
    protected CommandResult createUndoPersonResult(String successMessage, Person person) {
        return new CommandResult(String.format(successMessage, Messages.format(person)));
    }
}
