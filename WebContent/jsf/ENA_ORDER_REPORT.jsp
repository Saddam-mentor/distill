 
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
					    <h:outputText  value="ENA ORDER REPORT :  "
						            
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
     <div align="center">
       <h:outputText value="From date"></h:outputText>
       <rich:spacer width="20px;">
	</rich:spacer>
       
     <rich:calendar  
			value="#{ena_order_report_action.fromdate}" > </rich:calendar>
	 <rich:spacer width="20px;"/>
	
	
	<h:outputText value="To date"></h:outputText>
       <rich:spacer width="20px;"/>
	     
     <rich:calendar  
			value="#{ena_order_report_action.todate}"  > </rich:calendar>
     </div>
           
            <div>
         <rich:spacer height="50px;"/>
     </div>
       
       
     
     
       <div align="center">
       
        <h:selectOneRadio
								              value="#{ena_order_report_action.radio}"
								              valueChangeListener="#{ena_order_report_action.radiomethod}"
								            
								              onchange="this.form.submit();">
								              
								            
								              <f:selectItem itemValue="WUP" itemLabel="Purchase In State" />
								              <f:selectItem itemValue="OUP" itemLabel="Export Outside State"/>
								              <f:selectItem itemValue="IUP" itemLabel="Import Ena" />
			</h:selectOneRadio>
       </div>
       
         <div>
         <rich:spacer height="50px;"/>
     </div>
     
       <div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{ena_order_report_action.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{ena_order_report_action.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{ena_order_report_action.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{ena_order_report_action.reset}">
					</h:commandButton>
				</div>
       <div class="row">
	<rich:spacer height="20px;">
	</rich:spacer>
</div>
       
       
       <div class="col-md-12" align="center">
					<h:commandButton styleClass="btn btn-primary"
						action="#{ena_order_report_action.excel}" value="Generate Excel"
						rendered="true" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{ena_order_report_action.exlname}.xls"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{ena_order_report_action.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
				</div>
       
       
       
       
       
       
       
       
       
       
       
       
       
     <div>
         <rich:spacer height="50px;"/>
     </div>
       
       
       
       
       
       
       <div class="col-md-12" align="center">
				    <div class="row">
				         
					<h:commandButton styleClass="btn btn-warning" value="Close"
						action="#{ena_order_report_action.close}" />
				    </div>
				
				
				
				</div>    
          
	</h:form>
</f:view>				
   
</ui:composition>