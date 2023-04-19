package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
public class SemanticPass extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	int inforEach = 0;
	int whileLoop = 0;
	Obj currentMethod = null;
	Struct currType = null;
	boolean returnFound = false;
	boolean voidFunc = false;
	boolean errorDetected = false;
	List<Struct> formParams=new ArrayList<>();
	List<Struct> actualParams=new ArrayList<>();
	List<Struct> designatorListType=new ArrayList<>();
	HashMap<String, List<Struct>> methodsDeclared = new HashMap<String,List<Struct>>();
	int nVars;
	String currFormPar;
	static Struct boolType = new Struct(Struct.Bool);
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public void visit(VarDeclIdent varDecl){
		String ime =varDecl.getVarName();
		Obj prom = Tab.currentScope.findSymbol(ime);
    	if(prom!=null)report_error("promenljiva sa imenom "+ime + " vec postoji u tabeli simbola", varDecl);
    	else {Obj promenljiva = Tab.insert(Obj.Var, ime, currType);}
		report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
		
	}
	public void visit(VarDeclArray varDecl) {
		String ime =varDecl.getArrName();
		Obj prom = Tab.currentScope.findSymbol(ime);
    	if(prom!=null)report_error("promenljiva sa imenom "+ime + " vec postoji u tabeli simbola", varDecl);
    	else {
    		Struct tip = new Struct(Struct.Array,currType);
    		report_info("tip niza je "+currType.getKind(), varDecl);
    		Obj promenljiva = Tab.insert(Obj.Var, ime, tip);
    	}
		report_info("Deklarisan niz "+ varDecl.getArrName(), varDecl);
	}
    
    public void visit(ProgName progName){
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    public void visit(Program program){
    	nVars = Tab.currentScope.getnVars();
    	report_info("ima "+nVars+"promenljivi", program);
    	if(Tab.currentScope.findSymbol("main")!=null) {
    		if(Tab.find("main").getLevel() == 0) {
    			if(Tab.find("main").getType()!=Tab.noType)report_error("Main nije void tipa", null);
    		}else report_error("Main treba da bude bez argumenata", null);
    	}else report_error("Main ne postoji", null);
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
    public void visit(Type type){
    	Obj typeNode = Tab.find(type.getType());
    	String tip = type.getType();
    	if(tip.equals("bool")) {
    		type.struct=boolType;
    		typeNode = Tab.insert(Obj.Type, "bool", boolType);
    		currType= typeNode.getType();
    	}else {
    		if(typeNode == Tab.noObj){
        		report_error("Nije pronadjen tip " + type.getType() + " u tabeli simbola! ", null);
        		type.struct = Tab.noType;
        	}else{
        		if(Obj.Type == typeNode.getKind()){
        			type.struct = typeNode.getType();
        			currType=type.struct;
        		}else{
        			report_error("Greska: Ime " + type.getType() + " ne predstavlja tip!", type);
        			type.struct = Tab.noType;
        		}
        	}
    	}
    	
    }
    //return ili void fje
    
   public void visit(TypeFunc tip) {
    	tip.struct=currType;
    }
    public void visit(VoidFunc voidfunkcija) {
    	voidfunkcija.struct=Tab.noType;
    }
    public void visit(MethodTypeName methodTypeName){
    	currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(),methodTypeName.getReturnType().struct);
    	methodTypeName.obj = currentMethod;
    	Tab.openScope();
		report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
    }
    
    public void visit(MethodDeclaration methodDecl){
    	if(!returnFound && currentMethod.getType() != Tab.noType){
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
    	}
    	Tab.chainLocalSymbols(currentMethod);
    	List<Struct> myParams=new ArrayList<>();
    	String myName=currentMethod.getName();
    	myParams=methodsDeclared.get(myName);
    	int prom =Tab.currentScope.getnVars();
    	report_info("metoda "+myName +"ima "+myParams.size()+" parametara, i " +prom+"promenljivih", methodDecl);
    	currentMethod.setLevel(myParams.size());
    	Tab.closeScope();
    	
    	returnFound = false;
    	currentMethod = null;
    	currType=null;
    }
    public void visit(NoFparMetohdDecl methodDecl){
    	if(!returnFound && currentMethod.getType() != Tab.noType){
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
    	}
    	Tab.chainLocalSymbols(currentMethod);
    	String myName=currentMethod.getName();
    	int prom =Tab.currentScope.getnVars();
    	report_info("metoda "+myName +"nema "+" parametara, i " +prom+"promenljivih", methodDecl);
    	currentMethod.setLevel(0);
    	Tab.closeScope();
    	
    	returnFound = false;
    	currentMethod = null;
    	currType=null;
    }

  
    public void visit(DeclConst constdecl) {
    	currType=constdecl.getType().struct;
    	
    }
    //deklaracija konstanti
    public void visit(ConstDec constdec) {
    	String ime = constdec.getConstName();
    	Obj konst = Tab.find(ime);
    	if(konst!=Tab.noObj)report_error("konstanta sa imenom "+ime + " vec postoji u tabeli simbola, linija "+constdec.getLine(), constdec);
    	else {
    		if(!(currType.equals(constdec.getConstVal().obj.getType())))report_error("konstanta nije odgovarajuceg tipa, linija "+constdec.getLine(), constdec);
    		else {
    			Obj konstanta = Tab.insert(Obj.Con, ime, currType);
    			report_info("deklarisana konstanta "+ime+" sa vrednosti "+constdec.getConstVal().obj.getAdr()+" na liniji "+constdec.getLine(), constdec);
        		konstanta.setAdr(constdec.getConstVal().obj.getAdr());
    		}
    	}
    }
   public void visit(ConstValueN cons) {
    	cons.obj=new Obj(Obj.Con,"",Tab.intType);
    	cons.obj.setAdr(cons.getValue());
    }
    public void visit(ConstValueC cons) {
    	cons.obj=new Obj(Obj.Con,"",Tab.charType);
    	cons.obj.setAdr(cons.getValue());
    }
    public void visit(ConstValueB cons) {
    	cons.obj=new Obj(Obj.Con,"",boolType);
    	String bul =cons.getValue();
    	int i;
    	if(bul.equals("true"))i=1;
    	else i=0;
    	cons.obj.setAdr(i);
    }
    public void visit(InFor inf) {
    	inforEach++;
    }
    public void visit(EndFor efr) {
    	inforEach--;
    }
    public void visit(ForEIdent fo) {
    	
    }
    //designators
   public void visit(DesAssign assdes) {
	   if(!(assdes.getDesignator().obj.getKind()==Obj.Elem) && !(assdes.getDesignator().obj.getKind()==Obj.Fld) && !(assdes.getDesignator().obj.getKind()==Obj.Var)) {
		   report_error("Semanticka greska na liniji " + assdes.getLine() + " designator pogresnog tipa ", assdes);
	   }else {
		   Expr ee=assdes.getExpr();
		   Struct tt = ee.struct;
		   Struct dd = assdes.getDesignator().obj.getType();
		  // report_info("exp je "+tt.getKind()+" a des je "+dd.getKind(), ee);
		   Struct desgn,ex;
		   if(tt.getKind()==Struct.Array && dd.getKind()==Struct.Array) {
			   ex=tt.getElemType();
			   desgn=dd.getElemType();
			   //report_info("exp1 je "+ex.getKind()+" a des1 je "+desgn.getKind(), ee);
		   }
		   else if(dd.getKind()==Struct.Array) {
			  desgn= dd.getElemType();
			  ex=tt;
		   }else {
			   desgn=assdes.getDesignator().obj.getType();
			   ex=tt;
		   }
		   boolean ep=ex.assignableTo(desgn);
		   if(!ep) {
			   //if(!assdes.getDesignator().equals(null) || !assdes.getExpr().struct.isRefType()) {
				  report_error("Greska na liniji " + assdes.getLine() + " : " + "nekompatibilni tipovi u dodeli vrednosti! dess=expr ", assdes);
			  // }
		   }
	   }
   }
    
    public void visit(DesInc indes) {
    	if(!(indes.getDesignator().obj.getKind()==Obj.Elem) && !(indes.getDesignator().obj.getKind()==Obj.Fld) && !(indes.getDesignator().obj.getKind()==Obj.Var)) {
  		   report_error("Semanticka greska na liniji " + indes.getLine() + " designator pogresnog tipa ", indes);
  	   }else {
  		   if(indes.getDesignator().obj.getType()!=Tab.intType) {
  			 report_error("Semanticka greska na liniji " + indes.getLine() + " designator mora da bude int ", indes);
  		   }
  	   }
    	
    }
    public void visit(DesDec indes) {
    	if(!(indes.getDesignator().obj.getKind()==Obj.Elem) && !(indes.getDesignator().obj.getKind()==Obj.Fld) && !(indes.getDesignator().obj.getKind()==Obj.Var)) {
 		   report_error("Semanticka greska na liniji " + indes.getLine() + " designator pogresnog tipa ", null);
 	   }else {
 		   if(indes.getDesignator().obj.getType()!=Tab.intType) {
 			 report_error("Semanticka greska na liniji " + indes.getLine() + " designator mora da bude int ", null);
 		   }
 	   }
   	
   }
    
   public void visit(FuncCallNoAct noparam) {
    	Designator des = noparam.getFuncCall().getDesignator();
    	String name =des.obj.getName();
    	if(des.obj.getLevel()!=0) {
    		report_error("Funkcija treba da bude bez parametara, linija "+noparam.getLine(), noparam);
    	}
    	
    }
    public void visit(FuncCallActP withpars) {
    	Designator des = withpars.getFuncCall().getDesignator();
    	String name =des.obj.getName();
    	if(des.obj.getLevel()!=actualParams.size()) {
    		report_error("Funkcija treba da ima " + des.obj.getLevel()+ " argumenata, linija "+withpars.getLine(), withpars);
    	}else {
    		List<Struct> parametri=methodsDeclared.get(name);
        	for(int i=0;i<parametri.size();i++) {
        		Struct f = parametri.get(i);
        		Struct a =actualParams.get(i);
        		if(a.assignableTo(f)) {
        			
        		}else {
        			report_error("formalni parametar i actual nisu istog tipa, linija "+withpars.getLine(), withpars);
        		}
        	}
        	actualParams.clear();
        	
    	}
    	
    }
    public void visit(OneActPar act) {
    	act.struct=act.getExpr().struct;
    	actualParams.add(act.struct);
    }
    public void visit(ActParsDecl actpar) {
    	actualParams.add(actpar.getExpr().struct);
    }
   public void visit(DesignatorDecl identdes) {
	   String name = identdes.getI1();
	   Obj des = Tab.find(name);
	   if(des == Tab.noObj){
			report_error("Greska na liniji " + identdes.getLine()+ " : ime "+name+" nije deklarisano! ", null);
   		}
	   identdes.obj = des;
   }
    public void visit(DesAsignList dl) {
    	Obj left=dl.getDesListAssign().obj;
    	Obj right = dl.getDesignator().obj;
    	if(left.getKind()==Obj.Elem || left.getKind()==Obj.Fld || left.getKind()==Obj.Var) {
    		if(right.getType().getKind()==Struct.Array) {
    			Struct desni=right.getType();
    			Struct skip = new Struct(Struct.None);
    			//report_info("broj des je "+designatorListType.size(), dl);
    			for(int i =0;i<designatorListType.size();i++) {
    				
    				if(designatorListType.get(i).getKind()==0) {
    					//report_info("preskacemo  ", dl);
    					continue;
    				}
    				if(desni.getElemType().assignableTo(designatorListType.get(i))) {
    					
    				}else {
    					report_error("tipovi levog i desnog niza se ne poklapaju, linija "+dl.getLine(), dl);
    				}
    			}
    		}else {
    			report_error("desni designator mora biti niz, linija "+dl.getLine(), dl);
    		}
    	}else {
    		report_error("levi designator mora biti odgovarajuceg tipa, linija "+dl.getLine(), dl);
    	}
    	designatorListType.clear();
    }
    public void visit(ArrayAsignList arras) {
    	arras.obj=arras.getDesignator().obj;
    	//report_info("u arraasign1 smo", arras);
    	Obj desgintype = arras.getDesignator().obj;
    	designatorListType.add(desgintype.getType());
    }
    public void visit(NoDesAsignList nds) {
    	nds.obj=nds.getDesListAsgn().obj;
    	//report_info("u nodes smo", nds);
    	Struct skip = new Struct(Struct.None);
    	designatorListType.add(skip);
    }
    public void visit(ArrayAsign arras) {
    	arras.obj=arras.getDesignator().obj;
    	//report_info("u arraasign2 smo", arras);
    	Obj desgintype = arras.getDesignator().obj;
    	designatorListType.add(desgintype.getType());
    }

    public void visit(SkippedDes skipped) {
    	//report_info("u skipped smo", skipped);
    	skipped.obj=skipped.getDesListAsgn().obj;
    	Struct skip = new Struct(Struct.None);
    	designatorListType.add(skip);
    }
    public void visit(ForeachStat fr) {
    	Obj des = fr.getDesignator().obj;
    	String name = fr.getForEIdent().getI1();
    	Obj var = Tab.find(name);
    	fr.getForEIdent().obj=var;
    	if(var==Tab.noObj) {
    		report_info("pronadjen ident imena", fr);
    	}
    	
    	Struct prom =var.getType();
    	if(des.getType().getKind()==Struct.Array) {
    		if(prom.getKind()==des.getType().getElemType().getKind()) {
    			
    		}else {
    			report_error("designator i promenljiva u foreach moraju biti istog tipa, linija "+fr.getLine(), fr);
    		}
    	}else {
    		report_error("designator u foreach mora biti niz, linija "+fr.getLine(), fr);
    	}
    }
   
    
    //break
    public void visit(BreakStat breakst) {
    	if(inforEach>0 || whileLoop>0) {
    		
    	}else {
    		report_error("break mora biti unutar while ili foreach petlje, greska na liniji "+breakst.getLine(), breakst);
    	}
    }
    //continue
    public void visit(ContinueStat breakst) {
    	if(inforEach>0 || whileLoop>0) {
    		
    	}else {
    		report_error("continue mora biti unutar while ili foreach petlje, greska na liniji "+breakst.getLine(), breakst);
    	}
    }
    //read
    public void visit(ReadStat readst) {
    	Obj design = readst.getDesignator().obj;
    	if(!(design.getKind()==Obj.Elem) && !(design.getKind()==Obj.Fld) && !(design.getKind()==Obj.Var)) {
    		report_error("read designator, designator je pogresnog tipa, linija "+readst.getLine(), readst);
    	}else {
    		if(design.getType().getKind()==Struct.Char || design.getType().getKind()==Struct.Int || design.getType().getKind()==Struct.Bool) {
    			
    		}else report_error("read designator, designator je pogresnog tipa, linija "+readst.getLine(), readst);
    	}
    }
    //print
    public void visit(PrintStat printst) {
    	
    	if(printst.getExpr().struct.getKind()==Struct.Char || printst.getExpr().struct.getKind()==Struct.Int || printst.getExpr().struct.getKind()==Struct.Bool) {
			
		}else report_error("print designator, designator je pogresnog tipa, linija "+printst.getLine(), printst);
    }
    
    
    //if

    
    //while
    public void visit(InWhile inw) {
    	whileLoop++;
    }
   public void visit(WhileCond wc) {
    	wc.struct=wc.getCondition().struct;
    }
    public void visit(WhileUnmatch wnot) {
    	if(!(wnot.getWhileCond().struct==boolType)) {
    		report_error("uslov u while nije bool tipa! linija "+wnot.getLine(), wnot);
    	}
    	if(whileLoop>0)whileLoop--;
    }
    public void visit(WhileStat wnot) {
    	if(!(wnot.getWhileCond().struct==boolType)) {
    		report_error("uslov u while nije bool tipa! linija "+wnot.getLine(), wnot);
    	}
    	if(whileLoop>0)whileLoop--;
    }
   

    //form pars count
 
    public void visit(VarFormPar fp) {
    	fp.struct=fp.getFormParameter().struct;
    	Obj fop =Tab.insert(Obj.Var, currFormPar, fp.getFormParameter().struct);
    	formParams.add(fp.getFormParameter().struct);
    	int n =formParams.size();
    	fop.setFpPos(n);
    }
    public void visit(ArrayFormPar farr) {
    	Struct st =farr.getFormParameter().struct;
    	Struct niz=new Struct(Struct.Array,st);
    	farr.struct=niz;
    	Obj fop =Tab.insert(Obj.Var, currFormPar, niz);
    	formParams.add(niz);
    	int n =formParams.size();
    	fop.setFpPos(n);
    }
    public void visit(FormParameter fp) {
    	currFormPar = fp.getFormParName();
    	Obj par = Tab.currentScope.findSymbol(currFormPar);
    	if(par==null) {
    		fp.struct=fp.getType().struct;	
    	}else {
    		currFormPar=null;
    		report_error("vec je definisan parametar sa imenom, linija "+fp.getLine(), fp);
    	}
    	
    }
    public void visit(EndFormPar ef) {
    	String metoda = currentMethod.getName();
    	List<Struct> formParams2=new ArrayList<>(formParams);
    	methodsDeclared.put(metoda, formParams2);
    	formParams.clear();
    }
   
  
    
    public void visit(RelopCondFact relcon) {
    	
    	Struct left = relcon.getExpr().struct;
    	Struct right = relcon.getExpr1().struct;
    	if(!(right.compatibleWith(left))) {
    		report_error("uslovi kod relop nisu kompatibilni, linija "+relcon.getLine(), relcon);
    	}
    	if(left.getKind()==Struct.Class || left.getKind()==Struct.Array) {
    		if(relcon.getRelop() instanceof Equ || relcon.getRelop() instanceof NotEqu) {
    			relcon.struct=boolType;
    		}else {
    			relcon.struct=Tab.noType;
    			report_error("relop sa klasom ili nizom mogu da budu samo != ili ==, linija "+relcon.getLine(), relcon);
    		}
    		
    	}else relcon.struct=boolType;
    }
    public void visit(NegatedTerm minusterm) {
    	Struct te = minusterm.getTermList().struct;
    	if(te == Tab.intType){
    		minusterm.struct = te;
    	}else{
			report_error("Greska na liniji "+ minusterm.getLine()+" : nekompatibilni tipovi u izrazu za - term.", null);
			minusterm.struct = Tab.noType;
    	}
    	report_info("u neg term smo ", minusterm);
    		
    }
    public void visit(AddopList tlist) {
    	Addop mul = tlist.getAddop();
    	String op;
    	if(mul instanceof Plusop)op="+";
    	else op=" - ";
    	report_info("u termlist smo " + op, tlist);
    	tlist.struct=tlist.getTerm().struct;
    	Struct tipList;
    	if(tlist.getTermList().struct.getKind()==Struct.Array) {
    		tipList = tlist.getTermList().struct.getElemType();
    	}else {
    		tipList=tlist.getTermList().struct;
    	}
    	boolean prvi = tipList.getKind()==Struct.Int;
    	boolean drugi = tlist.getTerm().struct.getKind()==Struct.Int;
    	if(!prvi || !drugi) {
    		report_error("expr i term moraju biti int tipa,linija "+tlist.getLine(), tlist);
    	}
    	if(!(tipList.compatibleWith(tlist.getTerm().struct))) {
    		report_error("expr i term nisu kompatibilni tipovi, linija "+tlist.getLine(), tlist);
    	}
    }
    public void visit(MulopList flist) {
    	Mulop mul = flist.getMulop();
    	String op;
    	if(mul instanceof Multop)op="*";
    	else if(mul instanceof Divop)op=" div ";
    	else op=" mod ";
    	report_info("u mul op smo "+op, flist);
    	Struct tipList;
    	flist.struct=flist.getFactor().struct;
    	if(flist.getFactorList().struct.getKind()==Struct.Array) {
    		tipList = flist.getFactorList().struct.getElemType();
    	}else {
    		tipList=flist.getFactorList().struct;
    	}
    	boolean prvi = tipList.getKind()==Struct.Int;
    	boolean drugi = flist.getFactor().struct.getKind()==Struct.Int;
    	if(!prvi || !drugi) {
    		report_error("expr i term moraju biti int tipa, linija "+flist.getLine(), flist);
    	}
    	
    }
    public void visit(FactorDesSingle fds) {
    	fds.struct=fds.getDesignator().obj.getType();
    }
   
    public void visit(ExprTermL exdec) {
    	exdec.struct=exdec.getTermList().struct;
    }
    public void visit(TermSingle etl) {
    	//report_info("u empty ter list smo", etl);
    	etl.struct=etl.getTerm().struct;
    }
    public void visit(FactorDesAct funcCall){
    	Designator name = funcCall.getFuncCall().getDesignator();
    	Obj func = name.obj;
    	if(Obj.Meth == func.getKind()){
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			String ime = func.getName();
			if(ime.equals("ord")) {
				int n= actualParams.size();
				if(n!=1) {
					report_error("ord funkcija mora da ima jedan act par", name);
				}else {
					Struct par =actualParams.get(0);
					if(par.getKind()==Struct.Char) {
						funcCall.struct=Tab.intType;
					}else {
						report_error("ord funkcija mora da prima char kao parametar", name);
					}
				}
				actualParams.clear();
			}else if(ime.equals("len")) {
				int n= actualParams.size();
				if(n!=1) {
					report_error("len funkcija mora da ima jedan act par", name);
				}else {
					Struct par =actualParams.get(0);
					if(par.getKind()==Struct.Array && (par.getElemType().getKind()==Struct.Int || par.getElemType().getKind()==Struct.Char)) {
						funcCall.struct=Tab.intType;
					}else {
						report_error("len funkcija mora da prima niz intova ili charova kao parametar", name);
					}
				}
				actualParams.clear();
			}else if(ime.equals("chr")) {
				int n= actualParams.size();
				if(n!=1) {
					report_error("chr funkcija mora da ima jedan act par", name);
				}else {
					Struct par =actualParams.get(0);
					if(par.getKind()==Struct.Int) {
						funcCall.struct=Tab.charType;
					}else {
						report_error("chr funkcija mora da prima int kao parametar", name);
					}
				}
				actualParams.clear();
				
			}else {
				if(func.getLevel()!=actualParams.size()) {
		    		report_error("Funkcija treba da ima " + func.getLevel()+ " argumenata, linija "+funcCall.getLine(), funcCall);
		    	}else {
		    		List<Struct> parametri=methodsDeclared.get(func.getName());
			    	for(int i=0;i<parametri.size();i++) {
			    		Struct f = parametri.get(i);
			    		Struct a =actualParams.get(i);
			    		if(a.assignableTo(f)) {
			    			
			    		}else {
			    			report_error("formalni parametar i actual nisu istog tipa, linija "+funcCall.getLine(), funcCall);
			    		}
			    	}
			    	actualParams.clear();
					funcCall.struct = func.getType();
		    	}
			}
			
	    	
    	}else{
			report_error("Greska na liniji " + funcCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
    	}
    }
    public void visit(EmptyDesCall funcCall){
    	Designator name = funcCall.getFuncCall().getDesignator();
    	Obj func = name.obj;
    	if(Obj.Meth == func.getKind()){
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			if(func.getLevel()!=0) {
				report_error("funkcija treba da bude bez parametara, linija "+funcCall.getLine(), funcCall);
			}else {
				funcCall.struct = func.getType();
			}
    	}else{
			report_error("Greska na liniji " + funcCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
    	}
    }

    public void visit(FactorNewExpr newex) {
    	boolean tip=newex.getExpr().struct==Tab.intType;
    	if(!tip) {
    		newex.struct=Tab.noType;
    		report_error("new type expr, expr mora biti int tipa", newex);
    	}else {
    		Struct tipniza = newex.getType().struct;
    		newex.struct=new Struct(Struct.Array,tipniza);
    	}
    }
    public void visit(DesignatorExprEl desexp) {
    	Struct tipdes = desexp.getDesignator().obj.getType();
    	Struct tipexp =desexp.getExpr().struct;
    	if(tipdes.getKind()==Struct.Array) {
    		desexp.obj = new Obj(Obj.Elem, desexp.getDesignator().obj.getName(), tipdes.getElemType());

    	}else {
    		report_error("des mora biti niz, linija "+desexp.getLine(), desexp);
    		desexp.obj=Tab.noObj;
    	}
    	if(tipexp.getKind()==Struct.Int) {
    		
    	}else {
    		desexp.obj=Tab.noObj;
    		report_error("exp mora biti int, linija "+desexp.getLine(), desexp);
    	}
    }
    public void visit(TermFactL tdec) {
    	//report_info("u term decl smo", tdec);
    	tdec.struct=tdec.getFactorList().struct;
    }
    public void visit(FactorSingle fone) {
    	fone.struct=fone.getFactor().struct;
    }
    public void visit(FactorNew fn) {
    	fn.struct=fn.getType().struct;
    }
    public void visit(FactorNewNoPars fn) {
    	fn.struct=fn.getType().struct;
    }
    public void visit(FactorExpr fn) {
    	fn.struct=fn.getExpr().struct;
    }
    
   
   
    public void visit(FactNum cnst){
    	cnst.struct = Tab.intType;
    }
    public void visit(FactCh cnst){
    	cnst.struct = Tab.charType;
    }
    public void visit(FactBo cnstbool){
    	cnstbool.struct=boolType;
    }
  
    public void visit(Condition con) {
    	con.struct=con.getCondTerms().struct;
    }
    public void visit(CondTermSingle cnl) {
    	cnl.struct=cnl.getCondTerm().struct;
    }
    public void visit(CondTerm ct) {
    	ct.struct=ct.getCondFacts().struct;
    }
    public void visit(CondFactSingle cl) {
    	cl.struct=cl.getCondFact().struct;
    }
    public void visit(CondFactExpr cone) {
    	cone.struct=cone.getExpr().struct;
    }
    public void visit(AndList ct) {
    	CondFacts left = ct.getCondFacts();
    	CondFact right = ct.getCondFact();
    	if(left.struct==boolType && right.struct==boolType) {
    		ct.struct=boolType;
    	}else ct.struct=Tab.noType;
    }
    public void visit(OrList ct) {
    	CondTerms left = ct.getCondTerms();
    	CondTerm right = ct.getCondTerm();
    	if(left.struct==boolType && right.struct==boolType) {
    		ct.struct=boolType;
    	}else ct.struct=Tab.noType;
    }
    public void visit(ReturnStat returnExpr){
    	returnFound = true;
    	
    	Struct currMethType=currentMethod.getType();
    	
    	if(!currMethType.compatibleWith(returnExpr.getExpr().struct)){
			report_error("Greska na liniji " + returnExpr.getLine() + " : " + "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), null);
    	}
    }
    public void visit(NoReturnStat noret) {
    	if(Tab.noObj!=currentMethod) {
    		returnFound=true;
    		Struct currMethType = currentMethod.getType();
    		if(!currMethType.equals(Tab.noType))report_error("metoda nema pobratnu vrednost, linija "+noret.getLine(), noret);
    	}
    }
    
    public boolean passed(){
    	return !errorDetected;
    }
    
}
