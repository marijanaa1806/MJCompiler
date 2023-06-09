// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class ArrayFormPar extends FormParsElement {

    private FormParameter FormParameter;

    public ArrayFormPar (FormParameter FormParameter) {
        this.FormParameter=FormParameter;
        if(FormParameter!=null) FormParameter.setParent(this);
    }

    public FormParameter getFormParameter() {
        return FormParameter;
    }

    public void setFormParameter(FormParameter FormParameter) {
        this.FormParameter=FormParameter;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParameter!=null) FormParameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParameter!=null) FormParameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParameter!=null) FormParameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayFormPar(\n");

        if(FormParameter!=null)
            buffer.append(FormParameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayFormPar]");
        return buffer.toString();
    }
}
