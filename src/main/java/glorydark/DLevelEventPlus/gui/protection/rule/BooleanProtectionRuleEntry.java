package glorydark.DLevelEventPlus.gui.protection.rule;

import glorydark.DLevelEventPlus.gui.protection.type.EntryElementType;

/**
 * @author glorydark
 * @date {2023/12/20} {10:31}
 */
public class BooleanProtectionRuleEntry extends ProtectionRuleEntry {

    public BooleanProtectionRuleEntry(String category, String entryName, String translation) {
        super(category, entryName, translation, EntryElementType.TOGGLE);
    }

}
