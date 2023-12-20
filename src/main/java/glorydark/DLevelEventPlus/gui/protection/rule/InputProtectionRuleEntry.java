package glorydark.DLevelEventPlus.gui.protection.rule;

import glorydark.DLevelEventPlus.gui.protection.type.EntryElementType;
import glorydark.DLevelEventPlus.gui.protection.type.InputSaveType;

/**
 * @author glorydark
 * @date {2023/12/20} {10:31}
 */
public class InputProtectionRuleEntry extends ProtectionRuleEntry {

    private final InputSaveType saveType;

    public InputProtectionRuleEntry(String category, String entryName, String translation, InputSaveType saveType) {
        super(category, entryName, translation, EntryElementType.INPUT);
        this.saveType = saveType;
    }

    public InputSaveType getSaveType() {
        return saveType;
    }
}
