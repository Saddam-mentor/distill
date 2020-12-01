
 <ui:composition
       xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:rich="http://richfaces.org/rich"
       xmlns:a4j="http://richfaces.org/a4j">

<f:view>
 <h:form>
 

 <div>
        <a4j:outputPanel id="msg">
				<div class="col-md-12 wow shake">
					<h3>
					    <h:messages errorStyle="color:red"
									layout="table" id="messages"
									infoStyle="color:green">
						</h:messages>
					</h3>
				</div>
        </a4j:outputPanel>
      </div>
      
          <div class="row">
	         <rich:spacer height="20px;">
	         </rich:spacer>
          </div>
          
          
          
          <div style="width: 100%; height: 35px;">
				<div style="padding-top: 2px; 
				            padding-bottom: 2px; 
							margin-top: 2px;"
					        align="center">
					    <h:outputText  value="Report On Power Alcohal:  "
						            
						               style="font-weight: bold;
									   font-size: 20;
									   text-decoration: underline;margin-top: 2px;
									   font-family:Monotype Corsiva;font-size:30px;">
						</h:outputText>
						</div>
           </div>
           
           <div class="row">
	<rich:spacer height="20px;">
	</rich:spacer>
</div>

      <div>
        
      
      </div>
     <div  align="center">
       <h:outputText value="From date"></h:outputText>
       <rich:spacer width="20px;">
	</rich:spacer>
       
     <rich:calendar  
			value="#{report_power_alco_action.fromdate}"  > </rich:calendar>
	 <rich:spacer width="20px;"/>
	
	
	<h:outputText value="To date"></h:outputText>
       <rich:spacer width="20px;"/>
	     
     <rich:calendar  
			value="#{report_power_alco_action.todate}"  > </rich:calendar>
     </div>
           
            <div>
         <rich:spacer height="50px;"/>
     </div>
       
       
     
     
       <div align="center">
       
        <h:selectOneRadio
								              value="#{report_power_alco_action.radio}"
								              valueChangeListener="#{report_power_alco_action.radiomethod}"
								            
								              onchange="this.form.submit();">
								              
								            
								              <f:selectItem itemValue="WUP" itemLabel="Within UP" />
								              <f:selectItem itemValue="OUP" itemLabel="Outside UP"/>
								              <f:selectItem itemValue="SW" itemLabel="Statewise"/>
								             
			</h:selectOneRadio>
       </div>
       
         <div>
         <rich:spacer height="50px;"/>
     </div>
     
       <div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{report_power_alco_action.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{report_power_alco_action.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{report_power_alco_action.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{report_power_alco_action.reset}">
					</h:commandButton>
				</div>
				<rich:spacer height="50px;"></rich:spacer>
				<div class="col-md-12" align="center">
					<h:commandButton styleClass="btn btn-primary"
						action="#{report_power_alco_action.excel}" value="Generate Excel"
						rendered="true" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{report_power_alco_action.exlname}.xlsx"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{report_power_alco_action.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
				</div>
       <div class="row">
	<rich:spacer height="20px;">
	</rich:spacer>
</div>
 
       
     <div>
         <rich:spacer height="50px;"/>
     </div>
       
 
          
	</h:form>
</f:view>				
   
</ui:composition>