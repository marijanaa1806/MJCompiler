// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class WhileStat extends Matched {

    private WhileStart WhileStart;
    private WhileCond WhileCond;
    private InWhile InWhile;
    private WhileMatchEnd WhileMatchEnd;

    public WhileStat (WhileStart WhileStart, WhileCond WhileCond, InWhile InWhile, WhileMatchEnd WhileMatchEnd) {
        this.WhileStart=WhileStart;
        if(WhileStart!=null) WhileStart.setParent(this);
        this.WhileCond=WhileCond;
        if(WhileCond!=null) WhileCond.setParent(this);
        this.InWhile=InWhile;
        if(InWhile!=null) InWhile.setParent(this);
        this.WhileMatchEnd=WhileMatchEnd;
        if(WhileMatchEnd!=null) WhileMatchEnd.setParent(this);
    }

    public WhileStart getWhileStart() {
        return WhileStart;
    }

    public void setWhileStart(WhileStart WhileStart) {
        this.WhileStart=WhileStart;
    }

    public WhileCond getWhileCond() {
        return WhileCond;
    }

    public void setWhileCond(WhileCond WhileCond) {
        this.WhileCond=WhileCond;
    }

    public InWhile getInWhile() {
        return InWhile;
    }

    public void setInWhile(InWhile InWhile) {
        this.InWhile=InWhile;
    }

    public WhileMatchEnd getWhileMatchEnd() {
        return WhileMatchEnd;
    }

    public void setWhileMatchEnd(WhileMatchEnd WhileMatchEnd) {
        this.WhileMatchEnd=WhileMatchEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(WhileStart!=null) WhileStart.accept(visitor);
        if(WhileCond!=null) WhileCond.accept(visitor);
        if(InWhile!=null) InWhile.accept(visitor);
        if(WhileMatchEnd!=null) WhileMatchEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileStart!=null) WhileStart.traverseTopDown(visitor);
        if(WhileCond!=null) WhileCond.traverseTopDown(visitor);
        if(InWhile!=null) InWhile.traverseTopDown(visitor);
        if(WhileMatchEnd!=null) WhileMatchEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileStart!=null) WhileStart.traverseBottomUp(visitor);
        if(WhileCond!=null) WhileCond.traverseBottomUp(visitor);
        if(InWhile!=null) InWhile.traverseBottomUp(visitor);
        if(WhileMatchEnd!=null) WhileMatchEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WhileStat(\n");

        if(WhileStart!=null)
            buffer.append(WhileStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileCond!=null)
            buffer.append(WhileCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InWhile!=null)
            buffer.append(InWhile.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileMatchEnd!=null)
            buffer.append(WhileMatchEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WhileStat]");
        return buffer.toString();
    }
}
