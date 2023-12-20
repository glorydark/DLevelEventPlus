package glorydark.DLevelEventPlus.gui.protection.rule;

import glorydark.DLevelEventPlus.gui.protection.type.EntryElementType;
import lombok.AllArgsConstructor;

/**
 * @author glorydark
 * @date {2023/12/20} {10:31}
 */
@AllArgsConstructor
public class ProtectionRuleEntry {

    private String category;

    private String entryName;

    private String translation;

    private EntryElementType type;

    public void setType(EntryElementType type) {
        this.type = type;
    }

    public EntryElementType getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getTranslation() {
        return translation;
    }

    public String getEntryName() {
        return entryName;
    }
}
