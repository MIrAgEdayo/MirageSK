package si.f5.mirageserver.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static <E extends Enum<E>> Boolean isEnumExist(Class<E> clazz, String valueString){

        List<String> enums = new ArrayList<>();

        Arrays.stream(clazz.getEnumConstants())
                .forEach(e -> enums.add(e.name()));

        return enums.contains(valueString);
    }
}