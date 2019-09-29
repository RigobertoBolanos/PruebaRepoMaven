package com.github.RigobertoBolanos.PruebaRepoMaven.basicHlvlPackage;

import java.util.List;

/**
 * Class in xtend implementing the IhlvlFactory 
 * this class implements the transformation rules from basic 
 * variability models in the basic dialect of HLVL (Hlvl(basic))
 * @author Angela Villota
 * Coffee V1
 * January 2019
 */
public class HlvlBasicFactory implements IHlvlBasicFactory, HlvlBasicKeys{
	
	private int numId=0;
	private String id="r";
	private CnfExpFactory expFactory= new CnfExpFactory();
	
	public String getElement(String identifier) {
		return  ELM_DECLARATION + SPACE+ identifier + "\n";
	}
	
	public String getCore(String element) {
		return  id+ (numId++) + COLON+ SPACE +  CORE+ OPEN_CALL+ element+ CLOSE_CALL + "\n";
	}
	
	public String getCoreList(List<String> identifiers) {
		String out= id+ numId++ + COLON+ SPACE+  CORE+ OPEN_CALL;
		for(String id: identifiers){
			out+= id+COMMA+SPACE; 
		}
		
		out= out.substring(0, out.length() -2 )+ CLOSE_CALL + "\n";

		return out;
	}
	
	public String getImplies(String left, String right) {
		// TODO Auto-generated method stub
		return  id+ numId++ +COLON+ SPACE +  IMPLIES+ OPEN_CALL+ left+ COMMA+  right+ CLOSE_CALL + "\n";
	}
	
	public String getMutex(String left, String right) {
		// TODO Auto-generated method stub
		return  id+ numId++ + COLON + SPACE + MUTEX + OPEN_CALL + left+ COMMA+ SPACE+  right+ CLOSE_CALL + "\n";
	}
	
	public String getDecomposition(String parent, String child, DecompositionType type) {
		String out= id+ numId++ + COLON+  DECOMPOSITION+ OPEN_CALL+ parent+ COMMA+  OPEN_LIST+ child+ CLOSE_LIST+ CLOSE_CALL;
		switch (type){
		case Mandatory: 
			out+=MANDATORY + "\n";
			break;
		case Optional: 
			out+=OPTIONAL+ "\n"	;
			break;
		}
		return out;
	}
	
	public String getDecompositionList(String parent, List<String> children, DecompositionType type) {
		String out=id+ numId++ + COLON+  DECOMPOSITION+ OPEN_CALL+  parent+ COMMA+  OPEN_LIST;

		for(String id: children){
			out+= id+ COMMA + SPACE;
		}
		out = out.substring(0, out.length() -2 )+ CLOSE_LIST+ CLOSE_CALL;
		switch (type){
		case Mandatory: 
			out+=MANDATORY + "\n";
			break;
		case Optional: 
			out+=OPTIONAL+ "\n"	;
			break;
		}
		return out;
	}
	
	public String getGroup(String parent, List<String> children, GroupType type) {
		String out=id+ (numId++) + COLON+  GROUP+ OPEN_CALL+  parent+ COMMA+  OPEN_LIST;

		for(String id: children){
			out+= id+ COMMA+ SPACE;
		}
		out = out.substring(0, out.length() -2 )+ CLOSE_LIST+ CLOSE_CALL;
		switch (type){
		case Xor: 
			out+=ALTERNATIVE + "\n";
			break;
		case Or: 
			out+=OR+ "\n";
			break;
		}
		return out;
	}
	
	public String parseCNF2expression(List<String> positives, List<String> negatives) {
		return expFactory.getCNF2expression(positives, negatives, numId++, id); 
	}
	
	public String getHeader(String targetName) {
		return MODEL_LABEL + SPACE + targetName + "\n" + ELEMENTS_LABEL;
		
	}
	
	public String getRelationsLab() {
		return RELATIONS_LABEL;
	}
	
	public String getBasicOperationsBlock() {
		
		return  OPERATIONS_LABEL + 
		VALID_MODEL +
		COMMA +
		NUM_CONF;
	}

}
