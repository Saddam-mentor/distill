 <ui:composition
       xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
       xmlns:a4j="http://richfaces.org/a4j"
   	xmlns:rich="http://richfaces.org/rich" >
      <h:form>
      <f:view>
           <div class="form-group">
                 <div>
			         <a4j:outputPanel id="msg">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table" id="messages"
								infoStyle="color:green">
							</h:messages>
						</a4j:outputPanel>
			     </div>
			
			<div  class="row " align="center">
			    <div>
			        <h2>
			            <h:outputText value="Report On ENA Purchases for CL By The Seller"
							styleClass="generalHeaderOutputTable"
							style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;">
						</h:outputText>
				    </h2>
			     </div>
			     
		     </div>
			<div class="row col-md-12" align="center">
					Between Order Dates :<rich:calendar   
												value="#{report_On_ENA_For_CL_Action.fromdate}"></rich:calendar> and :  
												<rich:calendar   
												value="#{report_On_ENA_For_CL_Action.todate}"></rich:calendar>
		</div>
				 
	   <div>
	   <rich:spacer height="20px"></rich:spacer>
	   </div> 
		 <div class="col-md-12" align="center">
		 
		
		 <h:commandButton  action="#{report_On_ENA_For_CL_Action.print}"
				value="Print Report" style="background-color:#C5C5C5; font-size: large;"/>
				
		<h:outputLink styleClass="outputLinkEx" value="/doc/ExciseUp/WholeSale/pdf//#{report_On_ENA_For_CL_Action.pdfname}"
			target="_blank">
			<rich:spacer width="15px"></rich:spacer>
				<h:outputText styleClass="outputText" id="text223" value="View Report"
					rendered="#{report_On_ENA_For_CL_Action.printFlag==true}"
					style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
				</h:outputLink>		
				
				
														
			</div>	 
           
           </div>
      
      </f:view>
      </h:form>

</ui:composition>