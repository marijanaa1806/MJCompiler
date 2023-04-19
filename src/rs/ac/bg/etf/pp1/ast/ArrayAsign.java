// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class ArrayAsign extends DesListAsgn {

    private CommaIn CommaIn;
    private Designator Designator;
    private DesListAsgn DesListAsgn;

    public ArrayAsign (CommaIn CommaIn, Designator Designator, DesListAsgn DesListAsgn) {
        this.CommaIn=CommaIn;
        if(CommaIn!=null) CommaIn.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesListAsgn=DesListAsgn;
        if(DesListAsgn!=null) DesListAsgn.setParent(this);
    }

    public CommaIn getCommaIn() {
        return CommaIn;
    }

    public void setCommaIn(CommaIn CommaIn) {
        this.CommaIn=CommaIn;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesListAsgn getDesListAsgn() {
        return DesListAsgn;
    }

    public void setDesListAsgn(DesListAsgn DesListAsgn) {
        this.DesListAsgn=DesListAsgn;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CommaIn!=null) CommaIn.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
        if(DesListAsgn!=null) DesListAsgn.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CommaIn!=null) CommaIn.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesListAsgn!=null) DesListAsgn.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CommaIn!=null) CommaIn.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesListAsgn!=null) DesListAsgn.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayAsign(\n");

        if(CommaIn!=null)
            buffer.append(CommaIn.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesListAsgn!=null)
            buffer.append(DesListAsgn.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayAsign]");
        return buffer.toString();
    }
}
