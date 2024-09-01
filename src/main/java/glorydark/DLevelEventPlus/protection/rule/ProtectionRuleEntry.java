package glorydark.DLevelEventPlus.protection.rule;

import glorydark.DLevelEventPlus.protection.type.EntryElementType;
import lombok.Data;

/**
 * @author glorydark
 * @date {2023/12/20} {10:31}
 */
@Data
public class ProtectionRuleEntry {

    private String category;

    private String entryName;

    private String translationKeyName;

    private EntryElementType type;

    private Object defaultValue;

    private boolean editableInGame;

    public ProtectionRuleEntry(String category, String entryName, String translationKeyName, EntryElementType type, Object defaultValue) {
        this(category, entryName, translationKeyName, type, defaultValue, true);
    }

    public ProtectionRuleEntry(String category, String entryName, String translationKeyName, EntryElementType type, Object defaultValue, boolean editableInGame) {
        this.category = category;
        this.entryName = entryName;
        this.translationKeyName = translationKeyName;
        this.type = type;
        this.defaultValue = defaultValue;
        this.editableInGame = editableInGame;
    }
}
