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
			<h2><h:outputText value="Report on unused Barcode and QRcode"
							styleClass="generalHeaderOutputTable"
							style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;">
						</h:outputText></h2>
			</div>
			</div>
			
			 <div class="form-group newsdiv">
			 <div class=" row col-md-12"><rich:spacer height="20px"/></div>
			 
			 <div class="row col-md-12" align="center">
					Between Dates :<rich:calendar   
												value="#{unusedBarcodeAndQRCodeAction.fromdate}"></rich:calendar> and :  
												<rich:calendar   
												value="#{unusedBarcodeAndQRCodeAction.todate}"></rich:calendar>
				</div>
			  <rich:spacer  height="30px"/>
			  
			  <div class="col-md-12" align="center">
					<h:selectOneRadio style="FONT-WEIGHT: bold; "
						onclick="this.form.submit();"
						value="#{unusedBarcodeAndQRCodeAction.radioCLandFL}"
						valueChangeListener="#{unusedBarcodeAndQRCodeAction.radioListiner}" rendered="true" >

					<f:selectItem itemValue="BWFL" itemLabel="BWFL" />
					
						<f:selectItem itemValue="D" itemLabel=" Distillery" />
						<f:selectItem itemValue="B" itemLabel=" Brewery" />
						
					</h:selectOneRadio>
					
					</div>
					
				<div>	 <rich:spacer  height="20px"/></div>
					 <div align="center" class="row col-md-12">
					 <div class="col-md-4"></div>
			<div class="col-md-4"><h:selectOneRadio style="width:20%;" rendered="#{unusedBarcodeAndQRCodeAction.radioCLandFL eq 'D'}"
											onchange="this.form.submit();"
											valueChangeListener="#{unusedBarcodeAndQRCodeAction.flcllistner}"
													value="#{unusedBarcodeAndQRCodeAction.licenseing}">
										<f:selectItem itemLabel="CL" itemValue="CL" />
										<f:selectItem itemLabel="FL" itemValue="FL" />
												
					
												</h:selectOneRadio>
				</div>	
				 <div class="col-md-4"></div>
					  </div>
			
    <div  class="row ">
    <rich:spacer  height="30px"/>
    </div>
			  
			  
		 <div class="col-md-12" align="center">
		 
		 
		 <h:commandButton  action="#{unusedBarcodeAndQRCodeAction.print}"
				value="Print Report" style="background-color:#C5C5C5; font-size: large;"/>
				
		<h:outputLink styleClass="outputLinkEx" value="/doc/ExciseUp/WholeSale/pdf//#{unusedBarcodeAndQRCodeAction.pdfname}"
			target="_blank">
				<h:outputText styleClass="outputText" id="text223" value="View Report"
					rendered="#{unusedBarcodeAndQRCodeAction.printFlag==true}"
					style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
				</h:outputLink>		
				
				
														
			</div>	   
			<div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>
			<div class="col-md-12" align="center">
		 
		
		 
				
		
				
				 
														
			</div><div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>
			<div class="col-md-12" align="center">
		 
		
		  
				
		 
				<h:commandButton  action="#{unusedBarcodeAndQRCodeAction.reset}"
				value="Reset" style="background-color:#C5C5C5; font-size: large; width: 110px;"/>
														
			</div>
			<rich:spacer  height="20px"/>
			</div> 
</div>
</f:view>
</h:form>
</ui:composition>