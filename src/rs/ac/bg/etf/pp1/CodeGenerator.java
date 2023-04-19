package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

enum conds{
	whileC,ifC
};
public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	private int relop;
	private int number;
	private boolean minusFound=false;
	private int mulop;
	private int trapAdr;
	private int krajForEach=0;
	List<Integer> indexi = new ArrayList<Integer>();
	List<Designator> designatori = new ArrayList<Designator>();
	Stack<Integer> whileAdr = new Stack<Integer>();
	Stack<List<Integer>> conditionOver = new Stack<List<Integer>>();
	Stack<List<Integer>> conditionNotOver = new Stack<List<Integer>>();
	Stack<List<Integer>> breakAdr = new Stack<List<Integer>>();
	Stack<Integer> contAdr = new Stack<Integer>();
	Stack<Integer> foreachAdr = new Stack<Integer>();
	public int getMainPc() {
		return mainPc;
	}
	public void leaveMethod() {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	public void initPrevDefMethods() {
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
	}
	public void initFs(String name) {
		initPrevDefMethods();
		if(name.equals("len"))Code.put(Code.arraylength);
		leaveMethod();
	}
	public void visit(ProgName prog) {
		Tab.find("chr").setAdr(Code.pc);
		initFs("chr");
		Tab.find("ord").setAdr(Code.pc);
		initFs("ord");
		Tab.find("len").setAdr(Code.pc);
		initFs("len");
	}
	public void visit(PrintStat printStmt){
		if(number==0) {
			Code.loadConst(5);
		}else if(number > 0) {
			Code.loadConst(number);
		}
		number = -1;
		if(printStmt.getExpr().struct == Tab.intType){
			Code.put(Code.print);
		}else{
			Code.put(Code.bprint);
		}
	}
	public void visit(MethodTypeName methodTypeName){
		
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
			Code.mainPc=Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		

		Code.put(Code.enter);
		Code.put(methodTypeName.obj.getLevel());
		Code.put(methodTypeName.obj.getLevel()+varCnt.getCount());
	
	}	
	public void visit(MethodDeclaration methodDecl){
		leaveMethod();
	}
	public void visit(NoFparMetohdDecl methodDecl){
		leaveMethod();
	}
	public void visit(ContinueStat cnt) {
		int adr= contAdr.peek();
		Code.putJump(adr);
	}
	public void visit(BreakStat br) {
		List<Integer> breaks = breakAdr.pop();
		Code.putJump(0);
		breaks.add(Code.pc-2);
		breakAdr.push(breaks);	
	}
	public void visit(WhileStat stm) {
		List<Integer> breaks = breakAdr.pop();
		int adr1 = whileAdr.pop();
		List<Integer> fixups = conditionOver.pop();
		Code.putJump(adr1);
		for(int i=0;i<fixups.size();i++) {
			Code.fixup(fixups.get(i));
		}
		for(int i=0;i<breaks.size();i++) {
			Code.fixup(breaks.get(i));
		}
		conditionNotOver.pop();
		contAdr.pop();
	}
	public void visit(WhileUnmatch stm) {
		List<Integer> breaks = breakAdr.pop();
		int adr1 = whileAdr.pop();
		List<Integer> fixups = conditionOver.pop();
		Code.putJump(adr1);
		for(int i=0;i<fixups.size();i++) {
			Code.fixup(fixups.get(i));
		}
		for(int i=0;i<breaks.size();i++) {
			Code.fixup(breaks.get(i));
		}
		conditionNotOver.pop();
		contAdr.pop();
	}
	public void visit(OrOp opp) {
		Code.putJump(0);
		int adr2 = Code.pc-2;
		List<Integer> adrSkoka = conditionNotOver.pop();
		adrSkoka.add(adr2);
		conditionNotOver.push(adrSkoka);
		List<Integer> fixups = conditionOver.pop();
		for(int i=0;i<fixups.size();i++) {
			Code.fixup(fixups.get(i));
		}
		List<Integer> fixups2 = new ArrayList<Integer>();
		conditionOver.push(fixups2);
	}
	public void visit(WhileStart whl) {
		List<Integer> breaks = new ArrayList<Integer>();
		List<Integer> fixups = new ArrayList<Integer>();
		conditionOver.push(fixups);
		List<Integer> fixups2 = new ArrayList<Integer>();
		conditionNotOver.push(fixups2);
		whileAdr.push(Code.pc);
		contAdr.push(Code.pc);
		breakAdr.push(breaks);
	}
	public void visit(ElseStart els) {
		Code.putJump(0);
		int adr2 = Code.pc-2;
		List<Integer> adrSkoka = conditionNotOver.pop();
		adrSkoka.add(adr2);
		conditionNotOver.push(adrSkoka);
		if(conditionOver.size()>0) {
			List<Integer> fixups = conditionOver.pop();
			for(int i=0;i<fixups.size();i++) {
				Code.fixup(fixups.get(i));
			}	
			List<Integer> fixups2 = new ArrayList<Integer>();
			conditionOver.push(fixups2);
		}
	}
	public void visit(IfCond ifc) {
		if(conditionNotOver.size()>0) {
			List<Integer> fixups = conditionNotOver.pop();
			for(int i=0;i<fixups.size();i++) {
				Code.fixup(fixups.get(i));
			}	
			List<Integer> fixups2 = new ArrayList<Integer>();
			conditionNotOver.push(fixups2);
		}
	}
	public void visit(IfStart ifst) {
		List<Integer> fixups = new ArrayList<Integer>();
		List<Integer> fixups2 = new ArrayList<Integer>();
		conditionNotOver.push(fixups2);
		conditionOver.push(fixups);
	}
	public void visit(IfSingleStat unm) {
		if(conditionOver.size()>0) {
			List<Integer> fixups = conditionOver.pop();
			for(int i=0;i<fixups.size();i++) {
				Code.fixup(fixups.get(i));
			}	
			conditionNotOver.pop();
		}
	}
	public void visit(IfElseUnmatch iff) {
		if(conditionNotOver.size()>0) {
			List<Integer> fixups = conditionNotOver.pop();
			for(int i=0;i<fixups.size();i++) {
				Code.fixup(fixups.get(i));
			}	
			conditionOver.pop();
		}
	} 
	public void visit(IfElseStat iff) {
		if(conditionNotOver.size()>0) {
			List<Integer> fixups = conditionNotOver.pop();
			for(int i=0;i<fixups.size();i++) {
				Code.fixup(fixups.get(i));
			}	
			conditionOver.pop();
		}
	}
	public void visit(CondFactExpr conf) {
		Code.load(new Obj(Obj.Con, "$", Tab.intType, 1, 0));
		Code.putFalseJump(Code.eq, 0);
		int adr =Code.pc-2;
		if(conditionOver.size()>0) {
			List<Integer> lista = conditionOver.pop();
			lista.add(adr);
			conditionOver.push(lista);
		}
	}
	public void visit(RelopCondFact rel) {
		Relop op = rel.getRelop();
		if(op instanceof Equ) {
			relop = Code.eq;
		}else if(op instanceof NotEqu) {
			relop = Code.ne;
		}else if(op instanceof Grt) {
			relop = Code.gt;
		}else if(op instanceof GrtEqu) {
			relop = Code.ge;
		}else if(op instanceof Lst) {
			relop = Code.lt;
		}else relop=Code.le;
		Code.putFalseJump(relop, 0);
		int adr =Code.pc-2;
		if(conditionOver.size()>0) {
			List<Integer> lista = conditionOver.pop();
			lista.add(adr);
			conditionOver.push(lista);
		}
	}
	public void visit(InFor infor) {
		List<Integer> breaks = new ArrayList<Integer>();
		breakAdr.push(breaks);
		SyntaxNode nod = infor.getParent();
		ForeachStat fst = (ForeachStat)nod;
		Obj des = fst.getDesignator().obj;
		Code.load(des);
		Code.put(Code.const_n);
		int adr = Code.pc;
		foreachAdr.push(adr);
		contAdr.push(adr);
		Code.put(Code.dup);
		Code.load(des);
		Code.put(Code.arraylength);
		Code.putFalseJump(Code.lt, 0);
		krajForEach=Code.pc-2;
		Obj var = fst.getForEIdent().obj;
		Code.put(Code.dup2);
		Code.put(Code.aload);
		Code.store(var);
	}
	public void incIterator() {
		Code.put(Code.const_1);
		Code.put(Code.add);
	}
	public void visit(ForeachStat forst) {
		incIterator();
		Code.putJump(foreachAdr.pop());
		List<Integer> breaks = breakAdr.pop();
		contAdr.pop();
		for(int i=0;i<breaks.size();i++) {
			Code.fixup(breaks.get(i));
		}
		Code.fixup(krajForEach);	
	}
	public void visit(DesInc inc) {
		Designator des = inc.getDesignator();
		if(des.obj.getKind()==Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(des.obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(des.obj);
	}
	public void visit(DesDec inc) {
		Designator des = inc.getDesignator();
		if(des.obj.getKind()==Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(des.obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(des.obj);
	}
	public void visit(DesAssign assignment){
		Code.store(assignment.getDesignator().obj);
	}
	public void visit(FactorDesSingle fd) {
		Designator dd=fd.getDesignator();
		Code.load(dd.obj);
	}
	public void visit(ArrayAsignList as) {
		Designator des =as.getDesignator();
		int n =indexi.size();
		indexi.add(n);
		designatori.add(des);
	}
	public void visit(ArrayAsign as) {
		Designator des =as.getDesignator();
		int n =indexi.size();
		indexi.add(n);
		designatori.add(des);
	}
	public void visit(NoDesAsignList noas) {
		indexi.add(-1);
	}
	public void visit(SkippedDes noas) {
		indexi.add(-1);
	}
	public void visit(DesAsignList dsl) {
		Designator desni = dsl.getDesignator();
		//Code.load(desni.obj);
		Collections.reverse(indexi);
		for(int i=0;i<indexi.size();i++) {
			if(indexi.get(i)!=-1)indexi.set(i, i);
		}
		for(int i=designatori.size()-1;i>=0;i--) {
			Designator des = designatori.get(i);
			if(des.obj.getKind()==Obj.Elem) {
				int n = indexi.size();
				for(int j=0;j<n;j++) {
					Code.loadConst(indexi.get(j));
					Code.put(Code.pop);
				}
				int zadnji = n-1;
				int curr = indexi.get(zadnji);
				indexi.remove(zadnji);
				while(curr==-1) {
					zadnji = indexi.size()-1;
					curr=indexi.get(zadnji);
					indexi.remove(zadnji);
				}
				Code.load(desni.obj);
				Code.put(Code.arraylength);
				Code.loadConst(curr);
				Code.putFalseJump(Code.gt, 0);
				trapAdr=Code.pc-2;
				Code.putJump(Code.pc+5);
				trapArrLen();
				Code.load(desni.obj);
				Code.loadConst(curr);
				Code.put(Code.aload);
				Code.store(des.obj);
				
			}else if(des.obj.getKind()==Obj.Var) {
				int n = indexi.size();
				for(int j=0;j<n;j++) {
					Code.loadConst(indexi.get(j));
					Code.put(Code.pop);
				}
				int curr = indexi.get(0);
				indexi.remove(0);
				while(curr==-1 && indexi.size()>0) {
					curr=indexi.get(0);
					indexi.remove(0);
				}
				Code.load(desni.obj);
				Code.put(Code.arraylength);
				Code.loadConst(curr);
				Code.putFalseJump(Code.gt, 0);
				trapAdr=Code.pc-2;
				Code.putJump(Code.pc+5);
				trapArrLen();
				Code.load(desni.obj);
				Code.loadConst(curr);
				Code.put(Code.aload);
				Code.store(des.obj);
			}	
		}
		designatori.clear();
		indexi.clear();
	}
	public void trapArrLen() {
		Code.fixup(trapAdr);
		Code.put(Code.trap);
		Code.put(1);
	}
	public void visit(DesignatorIdentEl desel) {
		Designator dd=desel.getDesignator();
		Code.load(dd.obj);
	}
	public void visit(WithNum nn) {
		number = nn.getVal();
	}
	public void visit(NoNum nn) {
		number = 0;
	}
	public void visit(FactorNewExpr fn) {
		Code.put(Code.newarray);
		if (fn.getType().struct == Tab.charType) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
	public void visit(DesignatorExprEl des) {
		Designator dd=des.getDesignator();
		Code.load(dd.obj);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
	}
	/*public void visit(NegatedTerm min) {
		Code.put(Code.neg);
	}*/
	public void visit(FactorDesAct funcCall){
		Obj functionObj = funcCall.getFuncCall().getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	public void visit(EmptyDesCall funcCall){
		Obj functionObj = funcCall.getFuncCall().getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	public void visit(FuncCallActP procCall){
		Obj functionObj = procCall. getFuncCall().getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(procCall. getFuncCall().getDesignator().obj.getType() != Tab.noType){
			Code.put(Code.pop);
		}
	}
	public void visit(FuncCallNoAct procCall) {
		Obj functionObj = procCall. getFuncCall().getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(procCall. getFuncCall().getDesignator().obj.getType() != Tab.noType){
			Code.put(Code.pop);
		}
	}	
	public void visit(ReadStat readst) {
		Designator des =readst.getDesignator();
		if(des.obj.getType()!=Tab.charType) {
			Code.put(Code.read);
		}else Code.put(Code.bread);
		if (des.obj.getKind() == Obj.Elem) { Code.put(Code.astore);
		} else Code.store(des.obj);
	}
	public void visit(Multop fl) {
		mulop=Code.mul;
	}
	public void visit(Divop fl) {
		mulop=Code.div;
	}
	public void visit(Modop fl) {
		mulop=Code.rem;
	}
	public void visit(TermSingle ts) {
		if(minusFound)Code.put(Code.neg);
		minusFound=false;
	}
	public void visit(AddopList tl) {
		//if(tl.getParent() instanceof NegatedTerm)Code.put(Code.neg);
		Addop mul = tl.getAddop();
    	if(mul instanceof Plusop)Code.put(Code.add);
    	else Code.put(Code.sub);
	}
	public void visit(MinusTerm mt) {
		minusFound=true;
	}
	public void visit(MulopList fl) {
		Code.put(mulop);
	}
	public void visit(AndList con) {
		CondFacts left=con.getCondFacts();
		CondFact right = con.getCondFact();
	}
	public void visit(FactNum num) {
		Code.loadConst(num.getN1());
	}
	public void visit(FactCh ch) {
		Code.loadConst(ch.getC1());
	}
	public void visit(FactBo bul) {
		String val =bul.getB1();
    	int i;
    	if(val.equals("true"))i=1;
    	else i=0;
		Code.loadConst(i);
	}
}
