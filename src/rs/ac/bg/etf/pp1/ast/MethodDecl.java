// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl extends MethodDeclList {

    private MethodDeclList MethodDeclList;
    private MethodDeclElement MethodDeclElement;

    public MethodDecl (MethodDeclList MethodDeclList, MethodDeclElement MethodDeclElement) {
        this.MethodDeclList=MethodDeclList;
        if(MethodDeclList!=null) MethodDeclList.setParent(this);
        this.MethodDeclElement=MethodDeclElement;
        if(MethodDeclElement!=null) MethodDeclElement.setParent(this);
    }

    public MethodDeclList getMethodDeclList() {
        return MethodDeclList;
    }

    public void setMethodDeclList(MethodDeclList MethodDeclList) {
        this.MethodDeclList=MethodDeclList;
    }

    public MethodDeclElement getMethodDeclElement() {
        return MethodDeclElement;
    }

    public void setMethodDeclElement(MethodDeclElement MethodDeclElement) {
        this.MethodDeclElement=MethodDeclElement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclList!=null) MethodDeclList.accept(visitor);
        if(MethodDeclElement!=null) MethodDeclElement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseTopDown(visitor);
        if(MethodDeclElement!=null) MethodDeclElement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclList!=null) MethodDeclList.traverseBottomUp(visitor);
        if(MethodDeclElement!=null) MethodDeclElement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodDeclList!=null)
            buffer.append(MethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclElement!=null)
            buffer.append(MethodDeclElement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
