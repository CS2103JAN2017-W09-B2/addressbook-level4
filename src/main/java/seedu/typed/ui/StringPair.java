package seedu.typed.ui;

//@@author A0139392X
public class StringPair<T1, T2> {

    private T1 elem1;
    private T2 elem2;

    public StringPair(T1 elem1, T2 elem2) {
        this.setElem1(elem1);
        this.setElem2(elem2);
    }

    public T1 getElem1() {
        return elem1;
    }

    public void setElem1(T1 elem1) {
        this.elem1 = elem1;
    }

    public T2 getElem2() {
        return elem2;
    }

    public void setElem2(T2 elem2) {
        this.elem2 = elem2;
    }
}
//@@author
