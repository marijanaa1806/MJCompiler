// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class Grt extends Relop {

    public Grt () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Grt(\n");

        buffer.append(tab);
        buffer.append(") [Grt]");
        return buffer.toString();
    }
}
