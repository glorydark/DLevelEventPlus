package glorydark.DLevelEventPlus.gui.protection.rule;

import glorydark.DLevelEventPlus.MainClass;
import glorydark.DLevelEventPlus.gui.protection.rule.BooleanProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.DropdownProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.InputProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.ProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.type.InputSaveType;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author glorydark
 * @date {2023/12/20} {10:27}
 */
@NoArgsConstructor
public class ProtectionRuleEntries {

    protected static List<ProtectionRuleEntry> entries = new ArrayList<>();

    public void addBooleanProtectionEntry(String category, String entryName, String translation) {
        entries.add(new BooleanProtectionRuleEntry(category, entryName, MainClass.language.translateString(translation)));
    }

    public void addDropdownProtectionEntry(String category, String entryName, String translation, List<String> options) {
        entries.add(new DropdownProtectionRuleEntry(category, entryName, MainClass.language.translateString(translation), options));
    }

    public void addInputProtectionEntry(String category, String entryName, String translation, InputSaveType type) {
        entries.add(new InputProtectionRuleEntry(category, entryName, MainClass.language.translateString(translation), type));
    }

    public static List<ProtectionRuleEntry> getEntries() {
        return entries;
    }
}