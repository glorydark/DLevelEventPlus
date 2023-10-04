package glorydark.DLevelEventPlus.developing;

import glorydark.DLevelEventPlus.developing.Developing;

import java.util.*;

/**
 * @author glorydark
 * @date {2023/10/2} {14:50}
 */
@Developing
public class CastUtils {

    public static <T> Optional<T> cast(Object obj, Class<T> clazz) {
        if (obj.getClass().isAssignableFrom(clazz)) {
            return Optional.of(clazz.cast(obj));
        }
        return Optional.empty();
    }

    public static <T> List<T> castList(Object objects, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (objects instanceof List<?>) {
            for (Object object : (List<?>) objects) {
                result.add(clazz.cast(object));
            }
            return result;
        }
        return new ArrayList<>();
    }

    public static LinkedHashMap<String, String> castStringLinkedHashMap(Object object) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        if (object instanceof Map<?, ?>) {
            for (Map.Entry<?, ?> entry : ((LinkedHashMap<?, ?>) object).entrySet()) {
                if (entry.getKey() instanceof String && entry.getValue().getClass().isAssignableFrom(String.class)) {
                    result.put((String) entry.getKey(), (String) entry.getValue());
                }
            }
            return result;
        }
        return new LinkedHashMap<>();
    }
}
