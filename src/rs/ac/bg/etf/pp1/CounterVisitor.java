package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ListFormPars;
import rs.ac.bg.etf.pp1.ast.ElemFormPars;
import rs.ac.bg.etf.pp1.ast.VarElemDecl;
import rs.ac.bg.etf.pp1.ast.VarListDecl;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
	
	public int getCount(){
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
	
		public void visit(ElemFormPars formParamDecl){
			count++;
		}
		public void visit(ListFormPars formdl) {
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor{
		
		public void visit(VarElemDecl varDecl){
			count++;
		}
		public void visit(VarListDecl vard) {
			count++;
		}
	}
}
