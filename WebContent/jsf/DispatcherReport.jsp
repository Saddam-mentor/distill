<ui:composition
        xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
       xmlns:a4j="http://richfaces.org/a4j"
   	xmlns:rich="http://richfaces.org/rich" >


<h:form>





<f:view>
<div class="form-group"  >
         <div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>  
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
			<div  >
			<h2><h:outputText value="Report Of Dispatcher Old Stock "
							styleClass="generalHeaderOutputTable"
							style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;">
						</h:outputText></h2>
			</div>
			</div>
			
			 <div class="form-group newsdiv">
			 <div class=" row col-md-12"><rich:spacer height="20px"/></div>
			 
			 <div class="row col-md-12" align="center">
					Between Dates :<rich:calendar   
												value="#{dispatcherReportAction.fromdate}"></rich:calendar> and :  
												<rich:calendar   
												value="#{dispatcherReportAction.todate}"></rich:calendar>
				</div>
			  <rich:spacer  height="40px"/>
			  
			  <div class="col-md-12" align="center">
					<h:selectOneRadio style="FONT-WEIGHT: bold; "
						onclick="this.form.submit();"
						value="#{dispatcherReportAction.radioCLandFL}"
						valueChangeListener="#{dispatcherReportAction.radioListiner}" rendered="true" >

					<f:selectItem itemValue="BWFL" itemLabel="Dispatch and Duty for BWFL" />
					<f:selectItem itemValue="FL2D" itemLabel=" Dispatch and Duty for FL2D" />
						<f:selectItem itemValue="D" itemLabel=" Fl36 Dispatch for Distillery" />
						<f:selectItem itemValue="B" itemLabel=" Fl36 Dispatch for Brewary" />
						
					</h:selectOneRadio>
					
					</div>
					
					 <rich:spacer  height="40px"/>
					
					   <div class="row col-md-12" >
					   <div class="col-md-1"></div>
					<div class="col-md-2" align="right"  > 
					 <h:outputText value="Licence No."  style=" FONT-WEIGHT: bold ;" />
					 </div>
					 <div class="col-md-9"   align="left">
					<h:selectOneMenu styleClass="dropdown-menu" style="width: 450px; height: 28px;"
						value="#{dispatcherReportAction.bwfl_FL2d_Id}" onchange="this.form.submit();"  >
								<f:selectItems value="#{dispatcherReportAction.getAll_List}" />
															 
					</h:selectOneMenu>
					</div>
					
					
				</div>
			
    <div  class="row ">
    <rich:spacer  height="30px"/>
    </div>
			  
			  
		 <div class="col-md-12" align="center">
		 
		 
		 <h:commandButton  action="#{dispatcherReportAction.print}"
				value="Print Report" style="background-color:#C5C5C5; font-size: large;"/>
				
		<h:outputLink styleClass="outputLinkEx" value="/doc/ExciseUp/WholeSale/pdf//#{dispatcherReportAction.pdfname}"
			target="_blank">
				<h:outputText styleClass="outputText" id="text223" value="View Report"
					rendered="#{dispatcherReportAction.printFlag==true}"
					style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
				</h:outputLink>		
				
				
														
			</div>	   
			<div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>
			<div class="col-md-12" align="center">
		 
		
		 <h:commandButton  action="#{dispatcherReportAction.excel}"
				value="Generate Excel" style="background-color:#C5C5C5; font-size: large;" rendered="true"/>
				
		<h:outputLink styleClass="outputLinkEx" value="/doc/ExciseUp//WholeSale//Excel//#{dispatcherReportAction.exlname}Dispatch_BWFL.xls"
			target="_blank">
				<h:outputText styleClass="outputText"  value="Download Excel"
					rendered="#{dispatcherReportAction.excelFlag==true}"
					style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
				</h:outputLink>		
				
				 
														
			</div><div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>
			<div class="col-md-12" align="center">
		 
		
		  
				
		 
				<h:commandButton  action="#{dispatcherReportAction.reset}"
				value="Reset" style="background-color:#C5C5C5; font-size: large; width: 110px;"/>
														
			</div>
			<rich:spacer  height="20px"/>
			</div> 
</div>
</f:view>
</h:form>
</ui:composition>