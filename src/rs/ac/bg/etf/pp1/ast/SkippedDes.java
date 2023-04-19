// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class SkippedDes extends DesListAsgn {

    private CommaSkip CommaSkip;
    private DesListAsgn DesListAsgn;

    public SkippedDes (CommaSkip CommaSkip, DesListAsgn DesListAsgn) {
        this.CommaSkip=CommaSkip;
        if(CommaSkip!=null) CommaSkip.setParent(this);
        this.DesListAsgn=DesListAsgn;
        if(DesListAsgn!=null) DesListAsgn.setParent(this);
    }

    public CommaSkip getCommaSkip() {
        return CommaSkip;
    }

    public void setCommaSkip(CommaSkip CommaSkip) {
        this.CommaSkip=CommaSkip;
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
        if(CommaSkip!=null) CommaSkip.accept(visitor);
        if(DesListAsgn!=null) DesListAsgn.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CommaSkip!=null) CommaSkip.traverseTopDown(visitor);
        if(DesListAsgn!=null) DesListAsgn.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CommaSkip!=null) CommaSkip.traverseBottomUp(visitor);
        if(DesListAsgn!=null) DesListAsgn.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SkippedDes(\n");

        if(CommaSkip!=null)
            buffer.append(CommaSkip.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesListAsgn!=null)
            buffer.append(DesListAsgn.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SkippedDes]");
        return buffer.toString();
    }
}
