package com.wellpoint.mde.helperCode;

import java.util.ArrayList;
import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.Context;
import madison.mpi.GetType;
import madison.mpi.IxnMemGet;
import madison.mpi.KeyType;
import madison.mpi.MemHead;
import madison.mpi.MemRowList;
import madison.mpi.UsrHead;

import com.wellpoint.mde.CR209.ProcessProvMemget;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ExecuteProv {




	private String segmentData;
	private String outputType;
	
	/*-------------------Start Of Helper Code-----------------*/
	
	private int l_memgetSize = -1;
	private int l_memgetTime = -1;
	private int l_postMemgetTime = -1;
	
	private Context ctx;
    private boolean connFlag=true;
    
    private int l_errorCd;
    private String l_errorTxt;
	
	protected String hostName;
	protected int hostPort;
	protected String userName;
	protected String userPassword;
	
	protected String strDelim = "~";
	protected String strBlank = "";
	protected String l_memIdNum="";
	protected String l_strSrcCd = "";
	
	protected boolean dumpMemRowsFlag=false;
	protected boolean memgetStatus;
	protected long procEndTime;
	protected long memgetStartTime;
	protected long memgetEndTime;
	protected String srcCodesDelimited = "";
	protected boolean l_memHeadStatus = false;
	protected String l_strCaudrecno;
	
	protected List<outData> outDataList = new ArrayList<outData>();

	ProcessProvMemget objInstance;
	
    
    public void setSegmentData(String segmentData) {
        this.segmentData = segmentData;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }
    
    public void createConnection() {
		try {
			hostName = getHost();
			hostPort = getPort();
			userName = getUser();
			userPassword = getPass();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ctx = createContext(hostName, hostPort, userName, userPassword);
		System.out.println("Connection successful");
	}
	
	public Context createContext(String hostName, int hostPort,
			String userName, String userPassword) {
		System.out.println("User - " + userName + " Pwd - " + userPassword);
		System.out.println("Host - " + hostName + " Port - " + hostPort);
		UsrHead usr = new UsrHead(userName, userPassword);
		Context ctx = new Context(hostName, hostPort, usr);
		if (!ctx.isConnected()) {
			System.err.println("Error:Invalid context.See MADLOG for details.");
			System.exit(1);
		}
		return ctx;
	}
	
	public void execute_memGet(long entRecNum) throws Exception
	{
		
		objInstance = new ProcessProvMemget();
        
		try{
	        	if (connFlag){
	    			createConnection();
	    			connFlag = false;
	    			if (getDumpMemRowsFlag().equalsIgnoreCase("Y"))
	    				dumpMemRowsFlag = true;
	    		}
		
		
		memgetStartTime = System.nanoTime();

		// Create the member put interaction object.
		IxnMemGet memGet = new IxnMemGet(ctx);

		// Create a member rowlist to hold input and output member row(s).
		MemRowList  inpMemList = new MemRowList();
		MemRowList  outMemList = new MemRowList();
		AudRowList outAudRows = new AudRowList();

		MemHead memHead = new MemHead();
		memHead.setEntRecno(entRecNum);

		inpMemList.addRow(memHead);
		objInstance.setMemGetProp(memGet);
		// Set entity management priority.
		// Default is 0.

		/* Issue MemGet */
		memgetStatus = memGet.execute(inpMemList, outMemList, GetType.ASENTITY, KeyType.ENTRECNO, outAudRows);
		memgetEndTime = System.nanoTime();
		l_memgetSize = outMemList.size();
		/* Process MemGet response row list */
		if(memgetStatus)
		{
			/* Dump Rows depending on the mapping parameter */
			if (dumpMemRowsFlag)	{
				ExtMemgetIxnUtils.dumpRows(outAudRows, "MEMGET Audit Row Dump");
				ExtMemgetIxnUtils.dumpRows(outMemList, "MEMGET Out Row Dump");
			}
			outDataList = objInstance.processMemRows(outMemList, outAudRows, entRecNum);
			l_errorCd = objInstance.getL_errorCd();
			l_errorTxt = objInstance.getL_errorTxt();
			for (outData data: outDataList){
        		if(data.getType().equals("GR")){
        			setSegmentData(data.getSegData());
        			setOutputType(data.getOutType());
        			generateRow();
        		}
        		else if(data.getType().equals("LI")){
        			logInfo(data.getSegData());
        		}
        	}
		}
		else
		{
			l_errorCd = memGet.getErrCode().toInt();
			l_errorTxt = memGet.getErrText();
			logInfo("Get failed: entrecno = " + entRecNum);
			logInfo(l_errorCd + ":" + l_errorTxt);
		}

		/* Calculate Time & Size */
		//long setupEndTime = System.nanoTime();
		procEndTime = System.nanoTime();
		l_memgetTime = new Integer((int)((memgetEndTime - memgetStartTime) / 1000000));
		l_postMemgetTime = new Integer((int)((procEndTime - memgetEndTime) / 1000000));
		System.out.println("EntRecNum: " + entRecNum + "; Size: " + l_memgetSize +
				"; Memget time: " + l_memgetTime + " ms; Post Memget Time: " + l_postMemgetTime + " ms");

		/* Clean up variables for next memget */
		l_memHeadStatus = false;
		
		}
		catch (Exception e){
			l_errorCd = -1;
			l_errorTxt = e.toString();
			logInfo("MDE Error: entrecno = " + entRecNum);
			logInfo(l_errorCd + ":" + l_errorTxt);
		}
		
	}
	/*-------------------End Of Helper Code------------------------*/
	
	
	
  public static void main(String[] args) {
    	ExecuteProv jtx = new ExecuteProv();
		try	{
			
			jtx.execute_memGet(26900271);
			
			/*52593449*//*51356080*/

			/*51080204*//*51314722*//*51080203*//*50677837*/ /*51124885*/
			/*51184217*/ /*51212198*/ /*50692868*/  
			 
		}
		catch (Exception ex)	{
			ex.printStackTrace();
		}

	}
  
  String getHost()
  {
	  //return "30.128.143.50";
	 //return "30.135.90.76";
	  return "30.135.95.59";
  }
  
  int getPort()
  {
	  return 16000;
  }
  
  String getUser()
  {
     return "inbounduser";
	  //return "system";
  }
  
  String getPass()
  {
    return "mdsload@123";
	  //return "system";
  }
  
  String getDumpMemRowsFlag()
  {
      return "Y";
  }
  
  void logInfo(String s)
	{
		System.out.println(s);
	}

  void generateRow()
	{
		System.out.println(segmentData);
	}
}
