package glorydark.DLevelEventPlus.protection.rule;

import glorydark.DLevelEventPlus.protection.type.EntryElementType;
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

    private Object defaultValue;

    public EntryElementType getType() {
        return type;
    }

    public void setType(EntryElementType type) {
        this.type = type;
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

    public Object getDefaultValue() {
        return defaultValue;
    }
}
