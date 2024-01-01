package glorydark.DLevelEventPlus.protection.rule;

import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.protection.type.InputSaveType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author glorydark
 * @date {2023/12/20} {10:27}
 */
@NoArgsConstructor
@Getter
public class ProtectionRuleEntries {

    protected List<ProtectionRuleEntry> entries = new ArrayList<>();

    public void addBooleanProtectionEntry(String category, String entryName, String translation, Object defaultValue) {
        entries.add(new BooleanProtectionRuleEntry(category, entryName, LevelEventPlusMain.language.translateString(translation), defaultValue));
    }

    public void addDropdownProtectionEntry(String category, String entryName, String translation, List<String> options, Object defaultValue) {
        entries.add(new DropdownProtectionRuleEntry(category, entryName, LevelEventPlusMain.language.translateString(translation), options, defaultValue));
    }

    public void addInputProtectionEntry(String category, String entryName, String translation, InputSaveType type, Object defaultValue) {
        entries.add(new InputProtectionRuleEntry(category, entryName, LevelEventPlusMain.language.translateString(translation), type, defaultValue));
    }

    public List<ProtectionRuleEntry> getEntries() {
        return entries;
    }
}