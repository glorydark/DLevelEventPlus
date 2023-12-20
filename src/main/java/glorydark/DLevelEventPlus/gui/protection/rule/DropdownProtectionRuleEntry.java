package glorydark.DLevelEventPlus.gui.protection.rule;

import glorydark.DLevelEventPlus.gui.protection.type.EntryElementType;

import java.util.List;

/**
 * @author glorydark
 * @date {2023/12/20} {10:31}
 */
public class DropdownProtectionRuleEntry extends ProtectionRuleEntry {

    private final List<String> options;

    public DropdownProtectionRuleEntry(String category, String entryName, String translation, List<String> options) {
        super(category, entryName, translation, EntryElementType.DROPDOWN);
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

}
