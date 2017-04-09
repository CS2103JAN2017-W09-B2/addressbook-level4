package seedu.typed.commons.util;

//@@author A0139392X
public class StringPair {

    private String elem1;
    private String elem2;

    public StringPair(String elem1, String elem2) {
        this.setElem1(elem1);
        this.setElem2(elem2);
    }

    public String getElem1() {
        return elem1;
    }

    public void setElem1(String elem1) {
        this.elem1 = elem1;
    }

    public String getElem2() {
        return elem2;
    }

    public void setElem2(String elem2) {
        this.elem2 = elem2;
    }
}
//@@author
