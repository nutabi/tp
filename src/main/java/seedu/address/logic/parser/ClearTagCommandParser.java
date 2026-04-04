package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENERAL_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE_TAG;
import static seedu.address.logic.parser.CliSyntax.TAGS_COMMAND_PREFIXES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagType;

/**
 * Parses input arguments and creates a new ClearTagCommand object.
 */
public class ClearTagCommandParser implements Parser<ClearTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearTagCommand
     * and returns an ClearTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ClearTagCommand parse(String args) throws ParseException {
        ParserUtil.validateNoInvalidPrefixInputs(args, TAGS_COMMAND_PREFIXES);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, TAGS_COMMAND_PREFIXES);
        String preamble = argMultimap.getPreamble().trim();
        if (preamble.isEmpty() || preamble.contains(" ")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearTagCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(preamble);

        argMultimap.verifyNoDuplicatePrefixesFor(TAGS_COMMAND_PREFIXES);
        ParserUtil.validateNoValuesAfterPrefix(argMultimap, TAGS_COMMAND_PREFIXES);

        boolean isClearRole = argMultimap.getValue(PREFIX_ROLE_TAG).isPresent();
        boolean isClearCourse = argMultimap.getValue(PREFIX_COURSE_TAG).isPresent();
        boolean isClearGeneral = argMultimap.getValue(PREFIX_GENERAL_TAG).isPresent();

        // count how many tag types are specified
        int totalPrefixes = (isClearRole ? 1 : 0) + (isClearCourse ? 1 : 0) + (isClearGeneral ? 1 : 0);

        if (totalPrefixes != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearTagCommand.MESSAGE_USAGE));
        }

        TagType typeToClear;
        if (isClearRole) {
            typeToClear = TagType.ROLE;
        } else if (isClearCourse) {
            typeToClear = TagType.COURSE;
        } else {
            typeToClear = TagType.GENERAL;
        }

        return new ClearTagCommand(index, typeToClear);
    }
}
