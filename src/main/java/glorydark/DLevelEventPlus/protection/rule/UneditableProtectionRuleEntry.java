package glorydark.DLevelEventPlus.protection.rule;

import glorydark.DLevelEventPlus.protection.type.EntryElementType;
import glorydark.DLevelEventPlus.protection.type.InputSaveType;

/**
 * @author glorydark
 * @date {2023/12/20} {10:31}
 */
public class UneditableProtectionRuleEntry extends ProtectionRuleEntry {

    private final InputSaveType saveType;

    public UneditableProtectionRuleEntry(String category, String entryName, String translation, InputSaveType saveType, Object defaultValue) {
        super(category, entryName, translation, EntryElementType.UNEDITABLE, defaultValue, false);
        this.saveType = saveType;
    }

    public InputSaveType getSaveType() {
        return saveType;
    }
}
