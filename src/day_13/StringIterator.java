package day_13;

import java.util.Iterator;
import java.util.Objects;

public class StringIterator implements Iterator<String> {

    private final String str;
    private int index;

    public StringIterator(String str) {
        this.str = Objects.requireNonNull(str.substring(1, str.length() - 1));
    }

    @Override
    public boolean hasNext() {
        return index < str.length();
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new IllegalStateException();
        }
        var s = String.valueOf(str.charAt(index++));

        if (s.matches("\\d")) {
            for (int i = index; i < str.length(); i++) {
                var val = String.valueOf(str.charAt(i));
                if (val.matches("\\d")) {
                    s += val;
                    index++;
                } else {
                    break;
                }
            }
        }

        return s;
    }
}
