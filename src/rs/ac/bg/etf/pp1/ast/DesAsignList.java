// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class DesAsignList extends DesignatorStatement {

    private DesListAssign DesListAssign;
    private Designator Designator;

    public DesAsignList (DesListAssign DesListAssign, Designator Designator) {
        this.DesListAssign=DesListAssign;
        if(DesListAssign!=null) DesListAssign.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public DesListAssign getDesListAssign() {
        return DesListAssign;
    }

    public void setDesListAssign(DesListAssign DesListAssign) {
        this.DesListAssign=DesListAssign;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesListAssign!=null) DesListAssign.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesListAssign!=null) DesListAssign.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesListAssign!=null) DesListAssign.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesAsignList(\n");

        if(DesListAssign!=null)
            buffer.append(DesListAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesAsignList]");
        return buffer.toString();
    }
}
