package seedu.typed.commons.util;

//@@author A0143853A
/**
 * Stores two items.
 */

public class PairUtil<F, S> {
    private F first;
    private S second;

    public PairUtil(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof PairUtil) {
            @SuppressWarnings("unchecked")
            PairUtil<F, S> pair = (PairUtil<F, S>) obj;
            return first.equals(pair.getFirst())
                   && second.equals(pair.getSecond());
        } else {
            return false;
        }
    }
}
