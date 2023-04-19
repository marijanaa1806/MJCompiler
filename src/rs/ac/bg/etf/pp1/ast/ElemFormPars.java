// generated with ast extension for cup
// version 0.8
// 7/1/2023 21:32:52


package rs.ac.bg.etf.pp1.ast;

public class ElemFormPars extends FormPars {

    private FormParsElement FormParsElement;

    public ElemFormPars (FormParsElement FormParsElement) {
        this.FormParsElement=FormParsElement;
        if(FormParsElement!=null) FormParsElement.setParent(this);
    }

    public FormParsElement getFormParsElement() {
        return FormParsElement;
    }

    public void setFormParsElement(FormParsElement FormParsElement) {
        this.FormParsElement=FormParsElement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsElement!=null) FormParsElement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsElement!=null) FormParsElement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsElement!=null) FormParsElement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ElemFormPars(\n");

        if(FormParsElement!=null)
            buffer.append(FormParsElement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ElemFormPars]");
        return buffer.toString();
    }
}
