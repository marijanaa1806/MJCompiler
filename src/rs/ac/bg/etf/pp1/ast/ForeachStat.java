// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class ForeachStat extends Matched {

    private Designator Designator;
    private InFor InFor;
    private ForEIdent ForEIdent;
    private Statement Statement;
    private EndFor EndFor;

    public ForeachStat (Designator Designator, InFor InFor, ForEIdent ForEIdent, Statement Statement, EndFor EndFor) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.InFor=InFor;
        if(InFor!=null) InFor.setParent(this);
        this.ForEIdent=ForEIdent;
        if(ForEIdent!=null) ForEIdent.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.EndFor=EndFor;
        if(EndFor!=null) EndFor.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public InFor getInFor() {
        return InFor;
    }

    public void setInFor(InFor InFor) {
        this.InFor=InFor;
    }

    public ForEIdent getForEIdent() {
        return ForEIdent;
    }

    public void setForEIdent(ForEIdent ForEIdent) {
        this.ForEIdent=ForEIdent;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public EndFor getEndFor() {
        return EndFor;
    }

    public void setEndFor(EndFor EndFor) {
        this.EndFor=EndFor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(InFor!=null) InFor.accept(visitor);
        if(ForEIdent!=null) ForEIdent.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(EndFor!=null) EndFor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(InFor!=null) InFor.traverseTopDown(visitor);
        if(ForEIdent!=null) ForEIdent.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(EndFor!=null) EndFor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(InFor!=null) InFor.traverseBottomUp(visitor);
        if(ForEIdent!=null) ForEIdent.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(EndFor!=null) EndFor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForeachStat(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InFor!=null)
            buffer.append(InFor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForEIdent!=null)
            buffer.append(ForEIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EndFor!=null)
            buffer.append(EndFor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForeachStat]");
        return buffer.toString();
    }
}
