<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
  
     <f:view>
     
       <h:form>
       
       
       <rich:spacer height="20px"></rich:spacer>
          <div>
              <h:messages errorStyle="color:red" style="background-color: white;font-size: 20px">
              </h:messages>
           </div>
       
           <div align="center" class="row">
              <h:inputHidden
							value="#{report_On_Dispatches_Action.hidden}">
			  </h:inputHidden>
               <h:outputText style="FONT-STYLE: italic; COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;"
                       value="Report on Dispatch">
                       
               </h:outputText>          
           </div>
           
           <div align="center" class="row">
              <rich:spacer height="20px">
              
              </rich:spacer>
           
           </div>
           <div align="center" style="FONT-WEIGHT: bold;">
           <h:outputText value="#{report_On_Dispatches_Action.firm_name }"/>
           </div>
           
           <div align="center" class="row">
              <rich:spacer height="40px">
              
              </rich:spacer>
           
           </div>
           
           <div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
							value="#{report_On_Dispatches_Action.radio}" >
							<f:selectItem itemValue="FL2" itemLabel="FL2" />
							<f:selectItem itemValue="FL2B" itemLabel="FL2B" />
							<f:selectItem itemValue="CL2" itemLabel="CL2" />

						</h:selectOneRadio>
					</div>
				</div>
           <rich:spacer height="20px"></rich:spacer>
           <div class="col-md=12" align="center">
 
          
              <h:outputText>Form Date : </h:outputText>
             
             <rich:calendar value="#{report_On_Dispatches_Action.formdate}"></rich:calendar>
             <rich:spacer width="30px"></rich:spacer>
               <h:outputText>To Date : </h:outputText>
               <rich:calendar value="#{report_On_Dispatches_Action.todate}"></rich:calendar>
           
           </div>
           
           <rich:spacer height="20px"></rich:spacer>
           
   				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{report_On_Dispatches_Action.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{report_On_Dispatches_Action.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{report_On_Dispatches_Action.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt">
							</h:outputText>
					</h:outputLink>
          </div>
         
          <div class="col-md-12" align="center">
					<h:commandButton action="#{report_On_Dispatches_Action.excel}"
						value="Generate Excel" styleClass="btn btn-success" />
						
					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{report_On_Dispatches_Action.exlname}"
						target="_blank">
						
						<h:outputText styleClass="outputText" value="Download Excel" 
							rendered="#{report_On_Dispatches_Action.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
					
				
							<rich:spacer width="10px"></rich:spacer>
								<h:commandButton styleClass="btn btn-warning"
									action="#{report_On_Dispatches_Action.reset}" value="Reset"></h:commandButton>
					
				</div>
           <rich:spacer height="20px"></rich:spacer>
            
       </h:form>
     
     </f:view>
</ui:composition>