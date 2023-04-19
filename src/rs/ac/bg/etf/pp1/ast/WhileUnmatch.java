// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class WhileUnmatch extends Unmatched {

    private WhileStart WhileStart;
    private WhileCond WhileCond;
    private InWhile InWhile;
    private WhileUnmatchEnd WhileUnmatchEnd;

    public WhileUnmatch (WhileStart WhileStart, WhileCond WhileCond, InWhile InWhile, WhileUnmatchEnd WhileUnmatchEnd) {
        this.WhileStart=WhileStart;
        if(WhileStart!=null) WhileStart.setParent(this);
        this.WhileCond=WhileCond;
        if(WhileCond!=null) WhileCond.setParent(this);
        this.InWhile=InWhile;
        if(InWhile!=null) InWhile.setParent(this);
        this.WhileUnmatchEnd=WhileUnmatchEnd;
        if(WhileUnmatchEnd!=null) WhileUnmatchEnd.setParent(this);
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

    public WhileUnmatchEnd getWhileUnmatchEnd() {
        return WhileUnmatchEnd;
    }

    public void setWhileUnmatchEnd(WhileUnmatchEnd WhileUnmatchEnd) {
        this.WhileUnmatchEnd=WhileUnmatchEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(WhileStart!=null) WhileStart.accept(visitor);
        if(WhileCond!=null) WhileCond.accept(visitor);
        if(InWhile!=null) InWhile.accept(visitor);
        if(WhileUnmatchEnd!=null) WhileUnmatchEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileStart!=null) WhileStart.traverseTopDown(visitor);
        if(WhileCond!=null) WhileCond.traverseTopDown(visitor);
        if(InWhile!=null) InWhile.traverseTopDown(visitor);
        if(WhileUnmatchEnd!=null) WhileUnmatchEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileStart!=null) WhileStart.traverseBottomUp(visitor);
        if(WhileCond!=null) WhileCond.traverseBottomUp(visitor);
        if(InWhile!=null) InWhile.traverseBottomUp(visitor);
        if(WhileUnmatchEnd!=null) WhileUnmatchEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WhileUnmatch(\n");

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

        if(WhileUnmatchEnd!=null)
            buffer.append(WhileUnmatchEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WhileUnmatch]");
        return buffer.toString();
    }
}
